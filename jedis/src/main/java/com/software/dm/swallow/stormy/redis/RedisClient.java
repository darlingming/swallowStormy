package com.software.dm.swallow.stormy.redis;

import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RedisClient {


    private Jedis jedis;//非切片额客户端连接
    private JedisPool jedisPool;//非切片连接池
    private ShardedJedis shardedJedis;//切片额客户端连接
    private ShardedJedisPool shardedJedisPool;//切片连接池
    private JedisCluster cluster;

    public RedisClient() {
        initialPool();
        initialShardedPool();
        shardedJedis = shardedJedisPool.getResource();
        jedis = jedisPool.getResource();
        initialJedisClusterPool();


    }

    /**
     * 初始化非切片池
     */
    private void initialPool() {
        // 池基本配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //连接超时时是否阻塞，false时报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(true);
        //逐出策略（默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)）
        jedisPoolConfig.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");

        //最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(8);

        //最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(8);

        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setMaxWaitMillis(-1);

        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setMinEvictableIdleTimeMillis(1800000);

        //最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(0);

        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(3);

        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(1800000);

        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
        // 默认-1
        jedisPoolConfig.setMaxWaitMillis(100);

        //对拿到的connection进行validateObject校验
        jedisPoolConfig.setTestOnBorrow(true);

        //在进行returnObject对返回的connection进行validateObject校验
        jedisPoolConfig.setTestOnReturn(true);

        //定时对线程池中空闲的链接进行validateObject校验
        jedisPoolConfig.setTestWhileIdle(true);


        jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 10000, "123456", 15);
    }

    /**
     * 初始化切片池
     */
    private void initialShardedPool() {
        // 池基本配置
        // 池基本配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //连接超时时是否阻塞，false时报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(true);
        //逐出策略（默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)）
        jedisPoolConfig.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");

        //最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(8);

        //最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(8);

        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setMaxWaitMillis(-1);

        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setMinEvictableIdleTimeMillis(1800000);

        //最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(0);

        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(3);

        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(1800000);

        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
        // 默认-1
        jedisPoolConfig.setMaxWaitMillis(100);

        //对拿到的connection进行validateObject校验
        jedisPoolConfig.setTestOnBorrow(true);

        //在进行returnObject对返回的connection进行validateObject校验
        jedisPoolConfig.setTestOnReturn(true);

        //定时对线程池中空闲的链接进行validateObject校验
        jedisPoolConfig.setTestWhileIdle(true);
        // slave链接
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();

        shards.add(new JedisShardInfo("http://userInfo:123456@127.0.0.1:6379/15"));

        // 构造池
        shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, shards);
    }

    public void initialJedisClusterPool() {
        // 池基本配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //连接超时时是否阻塞，false时报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(true);
        //逐出策略（默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)）
        jedisPoolConfig.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");

        //最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(8);

        //最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(8);

        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setMaxWaitMillis(-1);

        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setMinEvictableIdleTimeMillis(1800000);

        //最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(0);

        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(3);

        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(1800000);

        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
        // 默认-1
        jedisPoolConfig.setMaxWaitMillis(100);

        //对拿到的connection进行validateObject校验
        jedisPoolConfig.setTestOnBorrow(true);

        //在进行returnObject对返回的connection进行validateObject校验
        jedisPoolConfig.setTestOnReturn(true);

        //定时对线程池中空闲的链接进行validateObject校验
        jedisPoolConfig.setTestWhileIdle(true);
        // Could not get a resource from the pool

        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("127.0.0.1", 6380));
        nodes.add(new HostAndPort("127.0.0.1", 6381));
        nodes.add(new HostAndPort("127.0.0.1", 6382));
        nodes.add(new HostAndPort("127.0.0.1", 6383));
        nodes.add(new HostAndPort("127.0.0.1", 6384));
        nodes.add(new HostAndPort("127.0.0.1", 6385));
        cluster = new JedisCluster(nodes, 1000, 1000, 1, "123456", jedisPoolConfig);
    }

    public void show() {
        KeyOperate();
        StringOperate();
        ListOperate();
        SetOperate();
        SortedSetOperate();
        HashOperate();
        jedisPool.returnResource(jedis);
        shardedJedisPool.returnResource(shardedJedis);
    }

    private void KeyOperate() {

    }

    private void StringOperate() {

    }

    private void ListOperate() {

    }

    private void SetOperate() {

    }

    private void SortedSetOperate() {

    }

    private void HashOperate() {

    }

    public Jedis getJedis() {
        return jedis;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public ShardedJedis getShardedJedis() {
        return shardedJedis;
    }

    public ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }

    public JedisCluster getCluster() {
        return cluster;
    }
}
