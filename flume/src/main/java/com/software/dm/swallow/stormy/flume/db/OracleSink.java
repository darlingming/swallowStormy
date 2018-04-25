package com.software.dm.swallow.stormy.flume.db;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author darlingming@126.com
 */
public class OracleSink extends AbstractSink implements Configurable {

    private static final Logger logger = LoggerFactory.getLogger(OracleSink.class);
    private String driver;
    private String url;
    private String user;
    private String password;
    private PreparedStatement preparedStatement;
    private Connection conn;
    private int batchSize;
    private String regex;
    private String sql;
    private int fieldCount = 0;

    public OracleSink() {
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

        fieldCount = context.getInteger("fieldCount", 0);
        logger.info("fieldCount=" + fieldCount);
        Preconditions.checkNotNull(fieldCount > 0, "fieldCount must be a positive number!!");


    }

    @Override
    public void start() {
        super.start();
        createConnect();

    }

    private void createConnect() {
        try {
            //调用Class.forName()方法加载驱动程序
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//        String url = "jdbc:oracle:thin:@" + hostname + ":" + port + ":" + databaseName;
        //调用DriverManager对象的getConnection()方法，获得一个Connection对象
        try {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            //创建一个Statement对象
            preparedStatement = conn.prepareStatement(sql);
            logger.info("oracle 创建成功---");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void stop() {
        logger.info("----执行结束---");
        super.stop();
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public Status process() throws EventDeliveryException {
        Status result = Status.READY;
        Channel channel = getChannel();
        Transaction transaction = channel.getTransaction();
        Event event;
        String content;

        List<String[]> infos = Lists.newArrayList();
        transaction.begin();
        try {
            for (int i = 0; i < batchSize; i++) {
                event = channel.take();
                if (event != null) {//对事件进行处理
                    if (conn.isClosed()) {
                        logger.warn("--数据库连接已关闭--" + conn.isClosed());
                        createConnect();
                    }
                    content = new String(event.getBody());
                    String[] infostrs = null;
                    if (content != null && content != "") {
                        //logger.info("---" + content + "---");
                        //logger.info("---" + regex + "---");
                        //文件的字段顺序固定且有值？
                        infostrs = content.split(this.regex, -1);
                        if (fieldCount == infostrs.length) {
                            //logger.info("匹配上的长度==" + infostrs.length + "---每个匹配后的值" + Arrays.toString(infostrs) + "---");
                            infos.add(infostrs);
                        }
                    }
                } else {
                    result = Status.BACKOFF;
                    break;
                }
            }

            if (infos.size() > 0) {
                //logger.info("infos的长度==" + infos.size());
                preparedStatement.clearBatch();
                for (int i = 0; i < infos.size(); i++) {
                    String[] valls = infos.get(i);
                    if (null != valls) {
                        for (int j = 0; j < valls.length; j++) {
                            preparedStatement.setString((j + 1), valls[j] != null ? valls[j] : "");
                        }
                        preparedStatement.addBatch();
                    }
                }

                try {
                    preparedStatement.executeBatch();
                } catch (SQLException e) {
                    logger.error("------批量执行sql错误");
                    e.printStackTrace();
                }


                conn.commit();
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            try {
                transaction.rollback();
            } catch (Exception e2) {
                logger.error("Exception in rollback. Rollback might not have been" +
                        "successful.", e2);
            }
            logger.error("Failed to commit transaction." +
                    "Transaction rolled back.", e);
            Throwables.propagate(e);
        } finally {
            transaction.close();
        }
        return result;
    }


}
