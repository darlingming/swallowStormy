package com.software.dm.swallow.stormy.redis;

import com.software.dm.swallow.stormy.redis.service.RedisSpringService;
import com.software.dm.swallow.stormy.redis.service.impl.RedisSpringServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisTest {
    Logger logger = LoggerFactory.getLogger(RedisTest.class);
    private RedisSpringService redisService = null;

    @Before
    public void init() {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/spring-redis.xml");
        RedisTemplate redisTemplate = appContext.getBean(RedisTemplate.class);
        redisService = new RedisSpringServiceImpl(redisTemplate);
    }

    @Test
    public void initPhone() {
        //"133", "153", "180", "181", "189", "173", "177"
        long vl = 13300000000l;
        try {
            while (vl < 13399999999l) {
                String md5 = DigestUtils.md5DigestAsHex(String.valueOf(vl).getBytes()).toUpperCase();
                boolean bool = redisService.cacheValue("PHONE:MD5:" + md5, String.valueOf(vl));
                if (!bool) {
                    System.err.println(vl + "===" + md5);
                }
                vl++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR:" + vl);
        } finally {
            System.err.println("133>OK");
        }

    }

    @Test
    public void main() {
        ExecutorService executor = Executors.newCachedThreadPool();        //创建一个缓冲池，缓冲池容量大小为Integer.MAX_VALUE
        long minNum = 13300000000l;
        long maxNum = 13400000000l;
        int threadCount = 500;
        long lots = (maxNum - minNum) / threadCount;
            CountDownLatch cdl = new CountDownLatch(threadCount);

        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/spring-redis.xml");
        RedisTemplate redisTemplate = appContext.getBean(RedisTemplate.class);
        for (int i = 1; i <= threadCount; i++) {
            maxNum = minNum + lots;

            MyTask myTask = new MyTask(new RedisSpringServiceImpl(redisTemplate), minNum, maxNum,cdl);
            executor.execute(myTask);
            minNum = maxNum;
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    class MyTask implements Runnable {
        private RedisSpringService redisService;
        private long minNum;
        private long maxNum;
        private CountDownLatch cdl;

        public MyTask(RedisSpringService redisService, long minNum, long maxNum,CountDownLatch cdl) {
            this.minNum = minNum;
            this.maxNum = maxNum;
            this.redisService = redisService;
            this.cdl=cdl;
        }

        @Override
        public void run() {
            System.out.println("正在执行Task " + minNum + ">>>>" + maxNum);
            try {
                while (minNum <= maxNum) {
                    try {
                        String md5 = DigestUtils.md5DigestAsHex(String.valueOf(minNum).getBytes()).toUpperCase();
                        boolean bool = this.redisService.cacheValue("PHONE:MD5:" + md5, String.valueOf(minNum));
                        if (!bool) {
                            System.err.println(minNum + "===" + md5);
                        }else{
                            minNum++;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("ERROR:" + minNum);
                        try {
                            Thread.currentThread().sleep(1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            } finally {
                  cdl.countDown();
            }

            System.out.println("Task " + minNum + ">>>>" + maxNum + "执行完毕");
        }
    }

    @Test
    public void addValue() {
        logger.info("addValue start .....");
        this.redisService.cacheValue("valkey", "b");
        String v = this.redisService.getValue("valkey");
        System.out.println("cacheValue = " + v);

        this.redisService.cacheList(100, "kList", "a", "b", "c");
        List<String> resList = this.redisService.getList("kList", 0, -1);
        System.out.println("list = " + Arrays.toString(resList.toArray()));

        this.redisService.cacheSet(200, "kSet", "a1", "b1", "c1");
        Set<String> setList = this.redisService.getSet("kSet");

        System.out.println("cacheSet = " + Arrays.toString(setList.toArray()));

        Map<String, String> maps = new HashMap<String, String>();
        maps.put("multi11", "multi111");
        maps.put("multi22", "multi222");
        maps.put("multi33", "multi333");
        this.redisService.cacheHash("maps", maps, 100);
        Map<String, String> mapsResult = this.redisService.getHash("maps");

        System.out.println("cacheHash = " + Arrays.toString(mapsResult.values().toArray()));


        Set s = this.redisService.keys("dm*");
        System.out.println("keys=" + Arrays.toString(s.toArray()));
        String datatype = this.redisService.getDataType(RedisSpringService.KEY_PREFIX_VALUE + "valkey");
        System.out.println("datatype=" + datatype);

        Set ss1 = this.redisService.scan("dm*", 100);
        System.out.println("scan=" + Arrays.toString(ss1.toArray()));


        logger.info("addValue end .....");
    }
}
