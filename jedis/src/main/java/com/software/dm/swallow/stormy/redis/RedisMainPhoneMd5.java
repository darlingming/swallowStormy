package com.software.dm.swallow.stormy.redis;

import com.software.dm.swallow.stormy.redis.service.RedisSpringService;
import com.software.dm.swallow.stormy.redis.service.impl.RedisSpringServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author DM
 */
public class RedisMainPhoneMd5 {
    private static final Logger logger = LogManager.getLogger(RedisMainPhoneMd5.class);

    public static void main(String[] args) {
        try {
            logger.info("Args>>" + Arrays.toString(args));

            //long minNum = 15300000000l;
            //long maxNum = 15400000000l;

            long minNum = Long.valueOf(args[0]);
            long maxNum = minNum + 100000000L;
            int threadCount = 500;

            //long minNum = Long.valueOf(args[0]);
            //long maxNum = minNum + 1000L;
            //int threadCount = 5;


            long lots = (maxNum - minNum) / threadCount;
            //ExecutorService executor = Executors.newCachedThreadPool();        //创建一个缓冲池，缓冲池容量大小为Integer.MAX_VALUE
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            CountDownLatch cdl = new CountDownLatch(threadCount);

            ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/spring-redis.xml");
            RedisTemplate redisTemplate = appContext.getBean(RedisTemplate.class);
            for (int i = 1; i <= threadCount; i++) {
                maxNum = minNum + lots;
                MyTask myTask = new MyTask(new RedisSpringServiceImpl(redisTemplate), minNum, maxNum, cdl);
                executor.execute(myTask);
                minNum = maxNum;
            }

            logger.info("Count==" + cdl.getCount());
            cdl.await();
            logger.info("Count=end=" + cdl.getCount());
            executor.shutdown();
        }  catch (Exception e) {
            logger.error("RedisMainPhoneMd5 Task:", e);
        } finally {
            System.exit(0);
            logger.info("RedisMainPhoneMd5 end----------");
        }

    }

    public static RedisSpringServiceImpl redisSpringServiceInstance() {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/spring-redis.xml");
        RedisTemplate redisTemplate = appContext.getBean(RedisTemplate.class);
        return new RedisSpringServiceImpl(redisTemplate);
    }
}

/**
 * MyTask
 */
class MyTask implements Runnable {
    private static final Logger logger = LogManager.getLogger(MyTask.class);
    private RedisSpringService redisService;
    private long minNum;
    private long maxNum;
    private CountDownLatch cdl;

    public MyTask(RedisSpringService redisService, long minNum, long maxNum, CountDownLatch cdl) {
        this.minNum = minNum;
        this.maxNum = maxNum;
        this.redisService = redisService;
        this.cdl = cdl;
    }

    @Override
    public void run() {

        logger.info("正在执行Task " + minNum + ">>>>" + maxNum);
        try {
            boolean bool = true;
            while (minNum <= maxNum) {
                try {
                    String md5 = DigestUtils.md5DigestAsHex(String.valueOf(minNum).getBytes()).toUpperCase();
                    bool = this.redisService.cacheValue("PHONE:MD5:" + md5, String.valueOf(minNum));
                    if (!bool) {
                        System.err.println(minNum + "===" + md5);
                    } else {
                        minNum++;
                    }
                } catch (Exception e) {
                    logger.error("Task:" + minNum, e);
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e1) {
                        logger.error("Task Sleep:", e1);
                    }
                }
            }
        } finally {
            cdl.countDown();
        }

        logger.info("Task " + minNum + ">>>>" + maxNum + "执行完毕");
    }
}