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

import java.util.*;

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
    public void addValue() {
        logger.info("addValue start .....");
        this.redisService.cacheValue("valkey", "b");
        String v = this.redisService.getValue("valkey");
        System.out.println("cacheValue = " + v);

        this.redisService.cacheList(100, "kList", "a", "b", "c");
        List<String> resList = this.redisService.getList("kList", 0, -1);
        System.out.println("list = " + Arrays.toString(resList.toArray()));

        this.redisService.cacheSet(200,"kSet", "a1", "b1", "c1");
        Set<String> setList = this.redisService.getSet("kSet");

        System.out.println("cacheSet = " + Arrays.toString(setList.toArray()));

        Map<String, String> maps = new HashMap<String, String>();
        maps.put("multi11", "multi111");
        maps.put("multi22", "multi222");
        maps.put("multi33", "multi333");
        this.redisService.cacheHash("maps", maps, 100);
        Map<String, String> mapsResult = this.redisService.getHash("maps");

        System.out.println("cacheHash = " + Arrays.toString(mapsResult.values().toArray()));


        Set s = this.redisService.keys("*");
        System.out.println("keys=" + Arrays.toString(s.toArray()));
        String datatype = this.redisService.getDataType(RedisSpringService.KEY_PREFIX_VALUE+"valkey");
        System.out.println("datatype="+datatype);

        Set ss1 = this.redisService.scan("dm*",100);
        System.out.println("scan=" + Arrays.toString(ss1.toArray()));



        logger.info("addValue end .....");
    }
}
