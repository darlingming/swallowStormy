package com.software.dm.swallow.stormy.flume.db;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.software.dm.swallow.stormy.flume.rwb.service.DataInterface;
import com.software.dm.swallow.stormy.flume.rwb.service.DataService;
import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author darlingming@126.com
 */
public class OracleQueueSink extends AbstractSink implements Configurable {

    private static final Logger logger = LoggerFactory.getLogger(OracleQueueSink.class);
    private String driver;
    private String url;
    private String user;
    private String password;


    private int batchSize;
    private int poolSize;
    private String regex;
    private String sql;
    private int fieldCount = 0;
    private String baseurl;
    private int queueSize = 10000;

    private static boolean boolexe = true;

    public OracleQueueSink() {
        logger.info("---start---");
    }


    public void configure(Context context) {
        url = context.getString("url");
        logger.info("url=" + url);
        Preconditions.checkNotNull(url, "url must be set!!");
        driver = context.getString("driver");
        logger.info("driver=" + driver);
        Preconditions.checkNotNull(driver, "driver must be set!!");
        user = context.getString("user");
        //logger.info("user=" + user);
        Preconditions.checkNotNull(user, "user must be set!!");
        password = context.getString("password");
        //logger.info("password=" + password);
        Preconditions.checkNotNull(password, "password must be set!!");
        regex = context.getString("regex", "\001");
        logger.info("regex=" + regex);
        Preconditions.checkNotNull(regex, "regex must be set!!");
        sql = context.getString("sql");
        logger.info("sql=" + sql);
        Preconditions.checkNotNull(sql, "sql must be set!!");
        batchSize = context.getInteger("batchSize", 100);
        logger.info("batchSize=" + batchSize);
        Preconditions.checkNotNull(batchSize > 0, "batchSize must be a positive number!!");

        poolSize = context.getInteger("poolSize", 10);
        logger.info("poolSize=" + poolSize);
        Preconditions.checkNotNull(poolSize > 0, "poolSize must be a positive number!!");

        queueSize = context.getInteger("queueSize", 10000);
        logger.info("queueSize=" + queueSize);
        Preconditions.checkNotNull(queueSize > 0, "queueSize must be a positive number!!");

        fieldCount = context.getInteger("fieldCount", 0);
        logger.info("fieldCount=" + fieldCount);
        Preconditions.checkNotNull(fieldCount > 0, "fieldCount must be a positive number!!");

        baseurl = context.getString("baseurl", null);
        logger.info("baseurl=" + baseurl);
        Preconditions.checkNotNull(baseurl, "baseurl must be set!!");

    }

    private static LinkedBlockingQueue<String[]> lbkq;
    private ExecutorService executors = null;

    @Override
    public void start() {
        super.start();
        lbkq = new LinkedBlockingQueue<String[]>(queueSize);
        ExecutorService executors = Executors.newFixedThreadPool(poolSize);
        //CountDownLatch countDownLatch = new CountDownLatch(batchSize);
        for (int i = 0; i < poolSize; i++) {
            executors.execute(new Task(lbkq));
        }
    }


    @Override
    public void stop() {
        logger.info("----end---");
        super.stop();
        this.boolexe = false;
        if (null != executors) {
            executors.shutdown();
        }
    }


    public Status process() throws EventDeliveryException {
        Status result = Status.READY;
        Channel channel = getChannel();
        Transaction transaction = channel.getTransaction();
        Event event;
        String content;

        transaction.begin();
        try {
            event = channel.take();
            if (event != null) {//对事件进行处理
                content = new String(event.getBody());
                if (content != null && content != "") {
                    String[] infostrs = content.split(this.regex, -1);
                    if (fieldCount == infostrs.length) {
                        this.lbkq.put(infostrs);
                    }
                }
            } else {
                result = Status.BACKOFF;
            }
            transaction.commit();
        } catch (Exception e) {
            try {
                transaction.rollback();
            } catch (Exception e2) {
                logger.error("Exception in rollback. Rollback might not have been successful.", e2);
            }
            logger.error("Failed to commit transaction. Transaction rolled back.", e);
            Throwables.propagate(e);
        } finally {
            transaction.close();
        }
        return result;
    }


    /**
     *
     */
    class Task implements Runnable {

        private PreparedStatement preparedStatement;
        private Connection conn;
        private LinkedBlockingQueue<String[]> lbkq;

        public Task(LinkedBlockingQueue<String[]> lbkq) {
            createConnect();
            this.lbkq = lbkq;
        }

        private void createConnect() {
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, user, password);
                conn.setAutoCommit(false);
                //创建一个Statement对象
                preparedStatement = conn.prepareStatement(sql);
                logger.info(Thread.currentThread().getId() + " oracle create success---");
            } catch (SQLException e) {
                logger.error("SQLException", e);
            } catch (ClassNotFoundException e) {
                logger.error("ClassNotFoundException", e);
            }
        }

        public void run() {
            DataInterface di = new DataService(baseurl);
            long batchCount = 0;
            String[] infoDatas = null;
            try {
                while (boolexe) {

                    if (conn.isClosed()) {
                        logger.warn("--db closed--" + conn.isClosed());
                        createConnect();
                    }
                    if (lbkq.isEmpty() || batchCount > batchSize) {
                        try {
                            if (batchCount > 0) {
                                preparedStatement.executeBatch();
                                conn.commit();
                                preparedStatement.clearBatch();
                                batchCount = 0;
                            }
                        } catch (SQLException e) {
                            logger.error("-SQLException----- ", e);
                            try {
                                conn.rollback();
                            } catch (SQLException e1) {
                                logger.error("SQLException in rollback. Rollback might not have been successful.", e1);
                            }

                        }
                    }
                    infoDatas = lbkq.take();
                    String[] valls = di.execute(infoDatas);
                    if (null != valls) {
                        //logger.info("valls=" + Arrays.toString(valls));
                        for (int j = 0; j < valls.length; j++) {
                            if (j == 3) {
                                preparedStatement.setTimestamp((j + 1), new Timestamp(Long.valueOf(valls[j]) * 1000));
                            } else {
                                preparedStatement.setString((j + 1), valls[j] != null ? valls[j] : "");
                            }
                        }
                        preparedStatement.addBatch();
                        batchCount++;
                    }

                }
            } catch (Exception e) {
                logger.error("Failed to commit transaction. Transaction rolled back.", e);
            } finally {
                try {
                    if (batchCount > 0) {
                        preparedStatement.executeBatch();
                        conn.commit();
                    }
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    logger.error("SQLException finally successful.", e);
                }
            }
        }
    }

}