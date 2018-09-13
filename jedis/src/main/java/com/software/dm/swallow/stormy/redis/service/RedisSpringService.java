package com.software.dm.swallow.stormy.redis.service;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.ReactiveListCommands;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisSpringService {
    /**
     * 前缀
     */
    String KEY_PREFIX_VALUE = "DM:VALUE:";
    String KEY_PREFIX_SET = "DM:SET:";
    String KEY_PREFIX_LIST = "DM:LIST:";
    String KEY_PREFIX_HASH = "DM:HASH:";
    String KEY_PREFIX_GEO = "DM:GEO:";

    /**
     * @param k
     * @param v
     * @param time(单位秒) <=0 不过期
     * @return
     * @MethodName：cacheValue
     * @ReturnType：boolean
     * @Description：缓存value操作
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public boolean cacheValue(String k, String v, long time);

    /**
     * @param k
     * @param v
     * @return
     * @MethodName：cacheValue
     * @ReturnType：boolean
     * @Description：缓存value操作
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public boolean cacheValue(String k, String v);

    /**
     * @param k
     * @return
     * @MethodName：getValue
     * @ReturnType：String
     * @Description：获取缓存
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:21:37
     * @Modifier：
     * @ModifyTime：
     */
    public String getValue(String k);

    /**
     * @param time
     * @param k
     * @param v
     * @return
     * @MethodName：cacheSet
     * @ReturnType：boolean
     * @Description：缓存set操作
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:20:00
     * @Modifier：
     * @ModifyTime：
     */
    public boolean cacheSet(long time, String k, String... v);

    /**
     * @param k
     * @param v
     * @return
     * @MethodName：cacheSet
     * @ReturnType：boolean
     * @Description：缓存set
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public boolean cacheSet(String k, String... v);


    /**
     * @param k
     * @return
     * @MethodName：getSet
     * @ReturnType：Set<String>
     * @Description：获取缓存set数据
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:17:45
     * @Modifier：
     * @ModifyTime：
     */
    public Set<String> getSet(String k);

    /**
     * @param time
     * @param k
     * @param v
     * @return
     * @MethodName：cacheList
     * @ReturnType：boolean
     * @Description：list缓存
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:17:23
     * @Modifier：
     * @ModifyTime：
     */
    public boolean cacheList(long time, String k, String... v);

    /**
     * @param k
     * @param v
     * @return
     * @MethodName：cacheList
     * @ReturnType：boolean
     * @Description：缓存list
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:17:10
     * @Modifier：
     * @ModifyTime：
     */
    public boolean cacheList(String k, String... v);


    /**
     * @param k
     * @param start
     * @param end
     * @return
     * @MethodName：getList
     * @ReturnType：List<String>
     * @Description：获取list缓存
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:14:45
     * @Modifier：
     * @ModifyTime：
     */
    public List<String> getList(String k, long start, long end);

    /**
     * @param k
     * @return
     * @MethodName：getListSize
     * @ReturnType：long
     * @Description：获取总条数
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:13:39
     * @Modifier：
     * @ModifyTime：
     */
    public long getListSize(String k);

    /**
     * @param k
     * @return
     * @MethodName：removeOneOfList
     * @ReturnType：boolean
     * @Description：移除list缓存
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public boolean removeOneOfList(String k);

    /**
     * @param
     * @param x
     * @param y
     * @param member
     * @param time(单位秒) <=0 不过期
     * @return
     * @MethodName：cacheGeo
     * @ReturnType：boolean
     * @Description：缓存地理位置信息
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public boolean cacheGeo(String k, double x, double y, String member, long time);

    /**
     * @param k
     * @param locations
     * @param time(单位秒) <=0 不过期
     * @return
     * @MethodName：cacheGeo
     * @ReturnType：boolean
     * @Description：
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public boolean cacheGeo(String k, Iterable<RedisGeoCommands.GeoLocation<String>> locations, long time);

    /**
     * @param k
     * @param members
     * @return
     * @MethodName：removeGeo
     * @ReturnType：boolean
     * @Description：移除地理位置信息
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public boolean removeGeo(String k, String... members);

    /**
     * @param k
     * @param member1
     * @param member2
     * @return Distance
     * @MethodName：distanceGeo
     * @ReturnType：Distance
     * @Description：根据两个成员计算两个成员之间距离
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public Distance distanceGeo(String k, String member1, String member2);

    /**
     * @param k
     * @param members
     * @return
     * @MethodName：getGeo
     * @ReturnType：List<Point>
     * @Description：根据key和member获取这些member的坐标信息
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public List<Point> getGeo(String k, String... members);

    /**
     * @param k
     * @param x
     * @param y
     * @param distance km
     * @return
     * @MethodName：radiusGeo
     * @ReturnType：GeoResults<GeoLocation<String>>
     * @Description：通过给定的坐标和距离(km)获取范围类其它的坐标信息
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> radiusGeo(String k, double x, double y, double distance, ReactiveListCommands.Direction direction, long limit);

    /**
     * @param k
     * @return
     * @MethodName：containsListKey
     * @ReturnType：boolean
     * @Description：判断缓存是否存在
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public boolean containsValueKey(String k);

    /**
     * @param k
     * @return
     * @MethodName：containsListKey
     * @ReturnType：boolean
     * @Description：判断缓存是否存在
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public boolean containsSetKey(String k);

    /**
     * @param k
     * @return
     * @MethodName：containsListKey
     * @ReturnType：boolean
     * @Description：判断缓存是否存在
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public boolean containsListKey(String k);

    /**
     * @param k
     * @return
     * @MethodName：containsGeoKey
     * @ReturnType：boolean
     * @Description：判断缓存是否存在
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public boolean containsGeoKey(String k);


    /**
     * @param k
     * @return
     * @MethodName：remove
     * @ReturnType：boolean
     * @Description：移除key中所有缓存
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:20:19
     * @Modifier：
     * @ModifyTime：
     */
    public boolean removeValue(String k);

    /**
     * @param k
     * @return
     * @MethodName：remove
     * @ReturnType：boolean
     * @Description：移除key中所有缓存
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:20:19
     * @Modifier：
     * @ModifyTime：
     */
    public boolean removeSet(String k);

    /**
     * @param k
     * @return
     * @MethodName：remove
     * @ReturnType：boolean
     * @Description：移除key中所有缓存
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public boolean removeList(String k);

    /**
     * @param k
     * @return
     * @MethodName：removeGeo
     * @ReturnType：boolean
     * @Description：移除key中所有缓存
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public boolean removeGeo(String k);


    /**
     * 缓存一个hash键值对到hash表
     *
     * @param k
     * @param hk
     * @param hv
     * @param time
     * @return
     * @MethodName：cacheHash
     * @ReturnType：boolean
     * @Description：
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public boolean cacheHash(String k, String hk, String hv, long time);

    /**
     * @param k
     * @param hk
     * @param hv
     * @return
     */
    public boolean cacheHash(String k, String hk, String hv);

    /**
     * 缓存一个map到hash表
     *
     * @param k
     * @param map
     * @param time
     * @return
     * @MethodName：cacheHash
     * @ReturnType：boolean
     * @Description：
     * @Creator：DM
     * @CreateTime：2018年5月20日上午10:45:27
     * @Modifier：
     * @ModifyTime：
     */
    public boolean cacheHash(String k, Map<String, String> map, long time);

    /**
     * @param k
     * @param map
     * @return
     */
    public boolean cacheHash(String k, Map<String, String> map);

    /**
     * 通过key获取一个map
     *
     * @param k
     * @return
     * @MethodName：getHash
     * @ReturnType：Map<String,String>
     * @Description：
     * @Creator：DM
     * @CreateTime：2018年5月20日上午10:48:21
     * @Modifier：
     * @ModifyTime：
     */
    public Map<String, String> getHash(String k);

    /**
     * 获取key对应map中所有的keys
     *
     * @param k
     * @return
     * @MethodName：getHashKey
     * @ReturnType：Set<String>
     * @Description：
     * @Creator：DM
     * @CreateTime：2018年5月20日上午10:49:16
     * @Modifier：
     * @ModifyTime：
     */
    public Set<String> getHashKey(String k);

    /**
     * 获取key对应map中所有的values
     *
     * @param k
     * @return
     * @MethodName：getHashValues
     * @ReturnType：List<String>
     * @Description：
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public List<String> getHashValues(String k);

    /**
     * 删除key对应hashMap中key的值.或删除整个key对应map
     *
     * @param k
     * @param hks
     * @return
     * @MethodName：delete
     * @ReturnType：List<String>
     * @Description：
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public boolean deleteHash(String k, String... hks);

    /**
     * 获取key对应的过期时间, 秒
     *
     * @param k
     * @return
     * @MethodName：getExpireTime
     * @ReturnType：Long
     * @Description：
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public Long getExpireTimeValue(String k);

    /**
     * @param alias
     * @param k
     * @return
     */
    public Long getExpireTime(String alias, String k);


    /**
     * @param time
     * @param k
     * @param v
     * @return
     * @MethodName：cacheZSet
     * @ReturnType：boolean
     * @Description：缓存set操作
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public boolean cacheZSet(long time, String k, String v, double dv);

    public boolean cacheZSet(long time, String k, Set<ZSetOperations.TypedTuple<String>> sets);

    /**
     * @param k
     * @param v
     * @return
     * @MethodName：cacheSet
     * @ReturnType：boolean
     * @Description：缓存set
     * @Creator：DM
     * @CreateTime：2018年5月20日
     * @Modifier：
     * @ModifyTime：
     */
    public boolean cacheZSet(String k, String v, double dv);

    /**
     * @param keys
     * @return
     */
    public long delKey(final String... keys);

    /**
     * @param pattern
     * @return
     */
    public Set keys(String pattern);

    /**
     * @param key
     * @return
     */
    public boolean exists(final String key);

    /**
     * @return
     */
    public String flushDB();

    /**
     * @return
     */
    public long dbSize();

    /**
     * @return
     */
    public String ping();

    /**
     * @param match_pattern
     * @param count
     * @return
     */
    public Set<Object> scan(String match_pattern, long count);

    /**
     * @param key
     * @return
     */
    public String getDataType(String key);
}
