package com.software.dm.swallow.stormy.redis;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;


public class RedisServiceTest {


    private RedisService redisService  ;

    @Before
    public void init() {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/spring-redis.xml");
        RedisTemplate redisTemplate = appContext.getBean(RedisTemplate.class);
          redisService = new RedisServiceImpl(redisTemplate);
    }

    @Test
    public void del() {
        redisService.set("a1", "a1");
        long result = redisService.del("a1");
        Assert.assertEquals(1L, result);
    }

    @Test
    public void set() {
        for(int i = 0;i< 1000;i++)
        redisService.set("UUID-"+i,  UUID.randomUUID().toString());
    }

    @Test
    public void get() {
      //  redisService.set("a1", "a1");
        String result = redisService.get("a1");
        System.out.println(result);
        Assert.assertEquals("a1", result);
    }



    @Test
    public void flushDB(){
         this.redisService.flushDB();

    }
}
