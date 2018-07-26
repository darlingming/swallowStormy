package com.software.dm.swallow.stormy.redis;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * BoundValueOperations：字符串类型操作
 * BoundListOperations：列表类型操作
 * BoundSetOperations：集合类型操作
 * BoundZSetOperations：有序集合类型操作
 * BoundHashOperations：散列操作
 */
public class RedisOperationsTest {
    private RedisTemplate stringRedisTemplate;

    @Before
    public void init() {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/spring-redis.xml");
        stringRedisTemplate = appContext.getBean(RedisTemplate.class);

    }

    /**
     * 需要指定hashtable的名字
     *
     * @param
     */
    @Test
    public void boundHashOps() {
        String tableName = "t_dm_test";

        //stringRedisTemplate.opsForGeo()
        System.out.println("==================Hash==============");
        BoundHashOperations<String, String, String> ops = stringRedisTemplate
                .boundHashOps(tableName);
        stringRedisTemplate.delete("student");
        stringRedisTemplate.delete("student:1");
        ops.put("cs01", "123");// 存入数据 ops.putAll(maps); 存入多条数据
        String key1 = ops.getKey();// tableName的名字
        System.out.println("key1:" + key1);
        String key11 = ops.get("cs01");
        System.out.println("key11:" + key11);// 获取key的值

        ops.putIfAbsent("cs02", "456");
        String key2 = ops.getKey();
        System.out.println("ops.getKey()-key2:" + key2);
        String key21 = ops.get("cs02");
        System.out.println("ops.get(cs02)-key21:" + key21);

        Map<String, String> maps = ops.entries();// 获取所有的key-value值
        for (String key : maps.keySet()) {
            System.out.println("map-key:" + key + "map-value:" + maps.get(key));
        }
        // ops.persist();//删除过期(如果有的话)的数据。
        System.out.println("ops.getExpire():" + ops.getExpire());// -1
        System.out.println("ops.expireAt(new Date()):"
                + ops.expireAt(new Date()));// true 设置生存过期时间
        System.out.println("ops.getType():" + ops.getType());// Hash
        System.out.println("ops.hasKey(cs01):" + ops.hasKey("cs01"));// true
        System.out.println("ops.hasKey(cs02):" + ops.hasKey("cs02"));// true
        System.out.println("ops.size():" + ops.size());// 2

        Set<String> keys = ops.keys();// 获取所有的key
        for (String string : keys) {
            System.out.println("ops.keys():" + string);
        }

        System.out.println("ops.values():" + ops.values());// 获取所有的value
        System.out.println("ops.size():" + ops.size());// 2 获取数量

        ops.delete("cs01");// 删除key为cs01的数据
    }

    /**
     * 未指定hashtable的名字
     *
     * @param tableName
     */
    public void opsForHash(String tableName) {
        System.out.println("==================Hash==============");
        HashOperations<String, Object, Object> ops = stringRedisTemplate
                .opsForHash();
        stringRedisTemplate.delete("student");
        stringRedisTemplate.delete("student:1");
        ops.put(tableName, "cs01", "123");// 存入数据 ops.putAll(maps); 存入多条数据
        Object key11 = ops.get(tableName, "cs01");
        System.out.println("key11:" + key11);// 获取key的值

        ops.putIfAbsent(tableName, "cs02", "456");
        Object key21 = ops.get(tableName, "cs02");
        System.out.println("ops.get(cs02)-key21:" + key21);

        Map<Object, Object> maps = ops.entries(tableName);// 获取所有的key-value值
        for (Object key : maps.keySet()) {
            System.out.println("map-key:" + key + "map-value:" + maps.get(key));
        }
        // ops.persist();//删除过期(如果有的话)的数据。
        System.out.println("ops.hasKey(cs01):"
                + ops.hasKey(tableName, "cs01"));// true
        System.out.println("ops.hasKey(cs02):"
                + ops.hasKey(tableName, "cs02"));// true
        System.out.println("ops.size():" + ops.size(tableName));// 2

        Set<Object> keys = ops.keys(tableName);// 获取所有的key
        for (Object string : keys) {
            System.out.println("ops.keys():" + string);
        }

        System.out.println("ops.values():"
                + ops.values(tableName));// 获取所有的value
        System.out.println("ops.size():"
                + ops.size(tableName));// 2 获取数量

        ops.delete("cs01");// 删除key为cs01的数据
    }

    /**
     * List 里面有重复数据
     *
     * @param tableName
     */
    public void boundListOps(String tableName) {
        System.out.println("==================List==============");
        BoundListOperations<String, String> ops = stringRedisTemplate
                .boundListOps(tableName);
        ops.leftPush("cs01");// left push 左侧入栈，先进后出，和右侧相比，左侧先出
        ops.leftPushIfPresent("cs011");// 不知道和上面的有什么区别
        ops.leftPush("cs01", "cs0111");// 在cs01的左侧入栈
        ops.leftPushAll("cs01111", "cs011111");

        List<String> values = ops.range(0, -1);
        for (String string : values) {
            System.out.println("letf push:" + string);
        }

        ops.rightPush("cs02");// right push 右侧入栈 先进先出 ，和左侧相比，左侧先出
        ops.rightPushIfPresent("cs022");
        ops.rightPush("cs02", "cs0222");// 在cs02的右侧入栈
        ops.rightPushAll("cs02222", "cs022222");

        ops.set(0, "cs04");// 把第一个数据替换成cs04
        // ops.trim(0, 3);//从第一个数据到第4个数据删除

        List<String> values1 = ops.range(0, -1);// 查出所有数据
        for (String string : values1) {
            System.out.println("right push:" + string);
        }

        List<String> values2 = ops.range(1, 2);// 查出从第二个到第三个
        for (String string : values2) {
            System.out.println("right push1:" + string);
        }
        System.out.println("ops.index(1):" + ops.index(0));// 获得第一个数据
        System.out.println("ops.remove(0, cs01):"
                + ops.remove(0, "cs01"));// 1,删除"cs01"
        System.out.println("ops.leftPop():" + ops.leftPop());// 左侧出栈
        System.out.println("ops.rightPop():" + ops.rightPop());// 右侧出栈
        System.out.println("ops.remove(0, cs01)1:"
                + ops.remove(0, "cs01"));// 0 ,如果"cs01"不存在返回0

        // ops.persist();//删除过期(如果有的话)的数据。
    }

    public void boundSetOps() {
        System.out.println("==================Set==============");
        String tableName2 = "caoshuai03";
        BoundSetOperations<String, String> ops = stringRedisTemplate
                .boundSetOps(tableName2);

        String[] values = {"cs03", "cs04"};
        System.out.println("ops.add(values):"
                + ops.add(values));// 添加多条数据到set里,返回添加的数量

        Set<String> sets1 = ops.members();// 获取set里的所有数据,每次显示的顺序可能不一样
        for (String string : sets1) {
            System.out.println("ops.members()1:" + string);
        }

        // 获取随机的数
        System.out.println("ops.randomMember():" + ops.randomMember());
        // 获取一个随机数
        System.out.println("ops.randomMembers(1):" + ops.randomMembers(1));
        // 获取两个随机数，值可能一样
        System.out.println("ops.randomMembers(2):" + ops.randomMembers(2));

        System.out.println("ops.distinctRandomMembers(1):"
                + ops.distinctRandomMembers(1));// 获取一个随机数
        System.out.println("ops.distinctRandomMembers(2):"
                + ops.distinctRandomMembers(2));// 获取两个不一样的随机数

        System.out.println(ops.isMember("cs04"));// 是否含有cs04,有的话返回true
        System.out.println(ops.isMember("cs01"));// 没有返回false

        System.out.println(ops.size());// set里数据量

        System.out.println(ops.getKey());// 获取set的名字

        System.out.println(ops.getType());// 获取类型

        Set<String> set7 = ops.diff("caoshuai02");// 获取和另一set里不一样的数据，差集
        for (String string : set7) {
            System.out.println("ops.diff(caoshuai02):" + string);
        }

        // 获取和另一set里一样的数据，交集
        Set<String> set8 = ops.intersect("caoshuai02");
        for (String string : set8) {
            System.out.println("ops.intersect(caoshuai02):" + string);
        }

        // 获取另一set所有的数据，和自己合并起来，去掉重复的数据，并集
        Set<String> set6 = ops.union("caoshuai02");
        for (String string : set6) {
            System.out.println("ops.union(caoshuai02):" + string);
        }

        // 获取和其它set里一样的数据，交集
        List<String> keys = new ArrayList<String>();
        keys.add("caoshuai02");
        Set<String> set = ops.intersect(keys);
        for (String string : set) {
            System.out.println("ops.intersect(keys):" + string);
        }

        // 获取和其它set里不一样的数据，差集
        List<String> keys1 = new ArrayList<String>();
        keys1.add("caoshuai02");
        Set<String> set3 = ops.diff(keys);
        for (String string : set3) {
            System.out.println("ops.diff(keys)3:" + string);
        }

        // 获取其它set所有的数据，和自己合并起来，去掉重复的数据，并集
        List<String> keys2 = new ArrayList<String>();
        keys2.add("caoshuai02");
        Set<String> set4 = ops.union(keys2);
        for (String string : set4) {
            System.out.println("ops.union(keys2):" + string);
        }

        // 获取和另一set里不一样的数据，差集,存入到另一set里
        ops.diffAndStore("caoshuai02", "caoshuai04");
        // ops.diffAndStore(keys, destKey);

        // 获取和另一set里一样的数据，交集，存入到另一set里
        ops.intersectAndStore("caoshuai02", "caoshuai05");
        // ops.intersectAndStore(keys, destKey);

        // 获取另一set里的所有数据，并集，存入到另一set里
        ops.unionAndStore("caoshuai02", "caoshuai06");
        // ops.unionAndStore(keys, destKey);

        // 把指定的数据移到另一set里，移动成功返回true
        System.out.println(ops.move("caoshuai07", "cs03"));
        // 把指定的数据移到另一set里，移动失败，返回false，注：当前set里没有cs01
        System.out.println(ops.move("caoshuai07", "cs01"));

        System.out.println(ops.pop());// pop出一个数据,第一个数据被pop出

        System.out.println(ops.remove("cs03"));// 删除多个数据，返回删除的个数
        System.out.println(ops.remove("cs01"));// 删除数据，返回删除的个数
    }

    public void boundValueOps() {
        System.out.println("==================String==============");
        String tableName2 = "LiMing01";
        BoundValueOperations<String, String> ops = stringRedisTemplate.boundValueOps(tableName2);

        System.out.println(ops.append("stu01"));// 添加数据，返回添加数据字符的个数

        // 先获取原先存在的数据，再添加数据覆盖原先的
        System.out.println(ops.getAndSet("stu02"));

        System.out.println(ops.get());// 获取添加的数据

        System.out.println(ops.get(0, 1));//获取从第一个开始到第二个结束的字符
        System.out.println(ops.get(0, 5));//获取从第一个开始到第六个结束的数据，注实际只有两个数据

        System.out.println(ops.size());//获取数据字符的个数
        ops.set("stu03");//添加数据
        System.out.println("ops.set(stu03):" + ops.get());// 获取添加的数据
        ops.set("stu04", 0);//在位置0处添加数据
        System.out.println("ops.set(stu04, 0):" + ops.get());// 获取添加的数据

        //如果原先的string里有数据，则使用此方法set新数据会失败，并返回false
        System.out.println(ops.setIfAbsent("stu04"));
        System.out.println("ops.setIfAbsent(stu04):" + ops.get());// 获取添加的数据

        stringRedisTemplate.delete(tableName2);//删除此string
        //如果原先的string里没有数据，则使用此方法set新数据会成功并返回true
        System.out.println(ops.setIfAbsent("stu06"));
        System.out.println("ops.setIfAbsent(stu06):" + ops.get());// 获取添加的数据
        ops.set("stu05", 30, TimeUnit.SECONDS);//设置30秒过期
    }

    @Test
    public void boundZSetOps() {
        System.out.println("==================Zset==============");
        String tableName2 = "LiMing03";
        BoundZSetOperations<String, String> ops = stringRedisTemplate
                .boundZSetOps(tableName2);

        System.out.println(ops.add("stu01", 1));//Zset里添加数据
        System.out.println(ops.add("stu03", 1));//Zset里添加数据

        System.out.println(ops.count(0, 1));//返回score在给定区间的数量

        //添加数据和score,返回score的大小，如果stu04存在，则只需score相加即可
        //例如：原先存在数据stu04 score是2.0，执行下面语句后变为:stu04， score:6.0
        System.out.println(ops.incrementScore("LiMing02", 4));

        ops.intersectAndStore("LiMing02", "LiMing04");
        //ops.intersectAndStore(otherKeys, destKey);
        ops.unionAndStore("LiMing02", "LiMing05");
        //ops.unionAndStore(otherKeys, destKey);

        Set<ZSetOperations.TypedTuple<String>> sets = new HashSet<ZSetOperations.TypedTuple<String>>();
        ZSetOperations.TypedTuple<String> typedTuple = new ZSetOperations.TypedTuple<String>() {

            @Override
            public int compareTo(ZSetOperations.TypedTuple<String> o) {
                // TODO Auto-generated method stub
                return 0;
            }


            @Override
            public String getValue() {
                // TODO Auto-generated method stub
                return "stu06";
            }

            @Override
            public Double getScore() {
                // TODO Auto-generated method stub
                return 6.0;
            }
        };
        sets.add(typedTuple);
        ops.add(sets);//添加数据

        Set<String> set1 = ops.range(0, -1); //返回指定区间的元素
        for (String string : set1) {
            System.out.println("ops.range(0, 1):" + string);
        }

        Set<String> set2 = ops.rangeByScore(1, 4);//返回指定score区间的元素，包含1和4
        for (String string : set2) {
            System.out.println("ops.rangeByScore(0, 4):" + string);
        }

        //返回指定位置的元素
        Set<ZSetOperations.TypedTuple<String>> set3 = ops.rangeWithScores(0, 4);
        for (ZSetOperations.TypedTuple<String> string : set3) {
            System.out.println("ops.rangeByScore(0, 4):" + string.getValue()
                    + "score:" + string.getScore());
        }

        System.out.println(ops.remove("stu01"));//删除数据，返回删除数据的个数

        Set<String> set5 = ops.range(0, -1); //返回指定区间的元素
        for (String string : set5) {
            System.out.println("ops.range(0, 1)5:" + string);
        }

        ops.removeRangeByScore(1, 4);//删除score的范围是1和4的数，包含1和4

        Set<String> set6 = ops.range(0, -1); //返回指定区间的元素
        for (String string : set6) {
            System.out.println("ops.range(0, 1)6:" + string);
        }

        ops.removeRange(0, 1);//删除懂第一个开始到第二个结束的数据

        Set<String> set7 = ops.range(0, -1); //返回指定区间的元素
        for (String string : set7) {
            System.out.println("ops.range(0, 1)7:" + string);
        }


    }
}
