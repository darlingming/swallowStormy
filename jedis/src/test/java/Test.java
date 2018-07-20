import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class Test {

    public static void main(String[] args) {
        try {
            //Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
            //jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7379));
            //JedisCluster jc = new JedisCluster(jedisClusterNodes);
            ////jc.persist()
            //jc.set("foo", "bar");
            //String value = jc.get("foo");
            //RedisClient redisClient = new RedisClient();
            //Jedis jedis = redisClient.getJedis();
            //
            //jedis.set("foo", "bar");
            //String value = jedis.get("foo");
            //
            //System.out.println(value);
            //
            //ShardedJedis shardedJedis = redisClient.getShardedJedis();
            //shardedJedis.set("aa", "bb");
            //
            //value = shardedJedis.get("aa");
            //
            //System.out.println(value);
            //JedisCluster jedisCluster = redisClient.getCluster();
            //for (int i = 0; i < 10; i++) {
            //
            //    jedisCluster.set("a" + i, i + "");
            //}
            //jedisCluster.set("a", "liming");
            //
            //value = jedisCluster.get("a");
            //
            //System.out.println(value);
            //jedisCluster.close();


            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring-redis.xml");

            RedisTemplate jc = context.getBean(RedisTemplate.class);

            jc.execute(new RedisCallback<Long>() {

                public Long doInRedis(RedisConnection connection)
                        throws DataAccessException {

                    byte[] keyb = "liming".getBytes();
                    byte[] valueb =  "liming_vale".getBytes();
                    // 判断当前值是否已经存在
                    if (connection.exists(keyb)) {
                        // 删除原数据
                        connection.del(keyb);
                    }
                    connection.set(keyb, valueb);
                    return 1L;
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            System.exit(0);
        }


    }
}
