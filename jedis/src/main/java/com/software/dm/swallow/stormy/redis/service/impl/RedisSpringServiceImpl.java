package com.software.dm.swallow.stormy.redis.service.impl;

import com.software.dm.swallow.stormy.redis.service.RedisSpringService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.ReactiveListCommands;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * BoundValueOperations：字符串类型操作
 * BoundListOperations：列表类型操作
 * BoundSetOperations：集合类型操作
 * BoundZSetOperations：有序集合类型操作
 * BoundHashOperations：散列操作
 */
public class RedisSpringServiceImpl implements RedisSpringService {


    /**
     * 日志记录
     */
    private static final Logger logger = LogManager.getLogger(RedisSpringServiceImpl.class);

    public RedisTemplate<String, String> redisTemplate;

    public RedisSpringServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


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
    public boolean cacheValue(String k, String v, long time) {
        String key = KEY_PREFIX_VALUE + k;
        try {

            BoundValueOperations<String, String> valueOps = redisTemplate.boundValueOps(key);

            if (time > 0) {
                valueOps.set(v, time, TimeUnit.SECONDS);
            } else {
                valueOps.set(v);
            }

            return true;
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + v + "]", t);
        }
        return false;
    }

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
    public boolean cacheValue(String k, String v) {
        return cacheValue(k, v, -1);
    }

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
    public String getValue(String k) {
        String key = KEY_PREFIX_VALUE + k;
        try {
            BoundValueOperations<String, String> valueOps = redisTemplate.boundValueOps(key);
            return valueOps.get();
        } catch (Throwable t) {
            logger.error("获取缓存失败key[" + key + ", error[" + t + "]");
        }
        return null;
    }

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
    public boolean cacheSet(long time, String k, String... v) {
        String key = KEY_PREFIX_SET + k;
        try {
            BoundSetOperations<String, String> setOps = redisTemplate.boundSetOps(key);
            setOps.add(v);
            if (time > 0) {
                setOps.expire(time, TimeUnit.SECONDS);
            }
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + v + "]", t);
        }
        return true;
    }

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
    public boolean cacheSet(String k, String... v) {
        return cacheSet(-1, k, v);
    }


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
    public Set<String> getSet(String k) {
        String key = KEY_PREFIX_SET + k;
        try {
            BoundSetOperations<String, String> setOps = redisTemplate.boundSetOps(key);
            return setOps.members();
        } catch (Throwable t) {
            logger.error("获取set缓存失败key[" + key + ", error[" + t + "]");
        }
        return null;
    }

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
    public boolean cacheList(long time, String k, String... v) {
        String key = KEY_PREFIX_LIST + k;
        try {
            BoundListOperations<String, String> listOps = redisTemplate.boundListOps(key);
            listOps.rightPushAll(v);
            if (time > 0)
                listOps.expire(time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + v + "]", t);
        }
        return false;
    }

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
    public boolean cacheList(String k, String... v) {
        return cacheList(-1, k, v);
    }


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
    public List<String> getList(String k, long start, long end) {
        String key = KEY_PREFIX_LIST + k;
        try {
            BoundListOperations<String, String> listOps = redisTemplate.boundListOps(key);
            return listOps.range(start, end);

        } catch (Throwable t) {
            logger.error("获取list缓存失败key[" + key + "]" + ", error[" + t + "]");
        }
        return null;
    }

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
    public long getListSize(String k) {
        String key = KEY_PREFIX_LIST + k;
        try {
            BoundListOperations<String, String> listOps = redisTemplate.boundListOps(key);
            return listOps.size();
        } catch (Throwable t) {
            logger.error("获取list长度失败key[" + key + "], error[" + t + "]");
        }
        return 0;
    }

    /**
     * @param k
     * @return
     * @MethodName：removeOneOfList
     * @ReturnType：boolean
     * @Description：移除list缓存
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:11:16
     * @Modifier：
     * @ModifyTime：
     */
    public boolean removeOneOfList(String k) {
        String key = KEY_PREFIX_LIST + k;
        try {
            BoundListOperations<String, String> listOps = redisTemplate.boundListOps(key);
            listOps.rightPop();
            return true;
        } catch (Throwable t) {
            logger.error("移除list缓存失败key[" + key + ", error[" + t + "]");
        }
        return false;
    }

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
     * @CreateTime：2018年5月20日 上午11:30:23
     * @Modifier：
     * @ModifyTime：
     */
    public boolean cacheGeo(String k, double x, double y, String member, long time) {
        String key = KEY_PREFIX_GEO + k;
        try {
            BoundGeoOperations<String, String> geoOps = redisTemplate.boundGeoOps(key);
            geoOps.add(new Point(x, y), member);
            if (time > 0) geoOps.expire(time, TimeUnit.SECONDS);
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]" + "失败, point[" + x + "," +
                    y + "], member[" + member + "]" + ", error[" + t + "]");
        }
        return true;
    }

    /**
     * @param k
     * @param locations
     * @param time(单位秒) <=0 不过期
     * @return
     * @MethodName：cacheGeo
     * @ReturnType：boolean
     * @Description：
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:31:33
     * @Modifier：
     * @ModifyTime：
     */
    public boolean cacheGeo(String k, Iterable<RedisGeoCommands.GeoLocation<String>> locations, long time) {
        try {
            for (RedisGeoCommands.GeoLocation<String> location : locations) {
                cacheGeo(k, location.getPoint().getX(), location.getPoint().getY(), location.getName(), time);
            }
        } catch (Throwable t) {
            logger.error("缓存[" + KEY_PREFIX_GEO + k + "]" + "失败" + ", error[" + t + "]");
        }
        return true;
    }

    /**
     * @param k
     * @param members
     * @return
     * @MethodName：removeGeo
     * @ReturnType：boolean
     * @Description：移除地理位置信息
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午10:53:01
     * @Modifier：
     * @ModifyTime：
     */
    public boolean removeGeo(String k, String... members) {
        String key = KEY_PREFIX_GEO + k;
        try {
            BoundGeoOperations<String, String> geoOps = redisTemplate.boundGeoOps(key);
            geoOps.remove(members);
        } catch (Throwable t) {
            logger.error("移除[" + key + "]" + "失败" + ", error[" + t + "]");
        }
        return true;
    }

    /**
     * @param k
     * @param member1
     * @param member2
     * @return Distance
     * @MethodName：distanceGeo
     * @ReturnType：Distance
     * @Description：根据两个成员计算两个成员之间距离
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午10:58:33
     * @Modifier：
     * @ModifyTime：
     */
    public Distance distanceGeo(String k, String member1, String member2) {
        String key = KEY_PREFIX_GEO + k;
        try {
            BoundGeoOperations<String, String> geoOps = redisTemplate.boundGeoOps(key);
            return geoOps.distance(member1, member2);
        } catch (Throwable t) {
            logger.error("计算距离[" + key + "]" + "失败, member[" + member1 + "," + member2 + "], error[" + t + "]");
        }
        return null;
    }

    /**
     * @param k
     * @param members
     * @return
     * @MethodName：getGeo
     * @ReturnType：List<Point>
     * @Description：根据key和member获取这些member的坐标信息
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:02:01
     * @Modifier：
     * @ModifyTime：
     */
    public List<Point> getGeo(String k, String... members) {
        String key = KEY_PREFIX_GEO + k;
        try {
            BoundGeoOperations<String, String> geoOps = redisTemplate.boundGeoOps(key);
            return geoOps.position(members);
        } catch (Throwable t) {
            logger.error("获取坐标[" + key + "]" + "失败]" + ", error[" + t + "]");
        }
        return null;
    }

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
     * @CreateTime：2018年5月20日 上午11:09:10
     * @Modifier：
     * @ModifyTime：
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> radiusGeo(String k, double x, double y, double distance, ReactiveListCommands.Direction direction, long limit) {
        try {
            String key = KEY_PREFIX_GEO + k;
            BoundGeoOperations<String, String> geoOps = redisTemplate.boundGeoOps(key);

            //设置geo查询参数
            RedisGeoCommands.GeoRadiusCommandArgs geoRadiusArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs();
            geoRadiusArgs = geoRadiusArgs.includeCoordinates().includeDistance();//查询返回结果包括距离和坐标
            if (Sort.Direction.ASC.equals(direction)) {//按查询出的坐标距离中心坐标的距离进行排序
                geoRadiusArgs.sortAscending();
            } else if (Sort.Direction.DESC.equals(direction)) {
                geoRadiusArgs.sortDescending();
            }
            geoRadiusArgs.limit(limit);//限制查询数量
            GeoResults<RedisGeoCommands.GeoLocation<String>> radiusGeo = geoOps.radius(new Circle(new Point(x, y), new Distance(distance, RedisGeoCommands.DistanceUnit.METERS)), geoRadiusArgs);

            return radiusGeo;
        } catch (Throwable t) {
            logger.error("通过坐标[" + x + "," + y + "]获取范围[" + distance + "km的其它坐标失败]" + ", error[" + t + "]");
        }
        return null;
    }

    /**
     * @param k
     * @return
     * @MethodName：containsListKey
     * @ReturnType：boolean
     * @Description：判断缓存是否存在
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:23:37
     * @Modifier：
     * @ModifyTime：
     */
    public boolean containsValueKey(String k) {
        return containsKey(KEY_PREFIX_VALUE + k);
    }

    /**
     * @param k
     * @return
     * @MethodName：containsListKey
     * @ReturnType：boolean
     * @Description：判断缓存是否存在
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:23:37
     * @Modifier：
     * @ModifyTime：
     */
    public boolean containsSetKey(String k) {
        return containsKey(KEY_PREFIX_SET + k);
    }

    /**
     * @param k
     * @return
     * @MethodName：containsListKey
     * @ReturnType：boolean
     * @Description：判断缓存是否存在
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:23:37
     * @Modifier：
     * @ModifyTime：
     */
    public boolean containsListKey(String k) {
        return containsKey(KEY_PREFIX_LIST + k);
    }

    /**
     * @param k
     * @return
     * @MethodName：containsGeoKey
     * @ReturnType：boolean
     * @Description：判断缓存是否存在
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:34:14
     * @Modifier：
     * @ModifyTime：
     */
    public boolean containsGeoKey(String k) {
        return containsKey(KEY_PREFIX_GEO + k);
    }

    private boolean containsKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Throwable t) {
            logger.error("判断缓存存在失败key[" + key + ", error[" + t + "]");
        }
        return false;
    }

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
    public boolean removeValue(String k) {
        return remove(KEY_PREFIX_VALUE + k);
    }

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
    public boolean removeSet(String k) {
        return remove(KEY_PREFIX_SET + k);
    }

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
    public boolean removeList(String k) {
        return remove(KEY_PREFIX_LIST + k);
    }

    /**
     * @param k
     * @return
     * @MethodName：removeGeo
     * @ReturnType：boolean
     * @Description：移除key中所有缓存
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:36:23
     * @Modifier：
     * @ModifyTime：
     */
    public boolean removeGeo(String k) {
        return remove(KEY_PREFIX_GEO + k);
    }

    private boolean remove(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Throwable t) {
            logger.error("移除缓存失败key[" + key + ", error[" + t + "]");
        }
        return false;
    }

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
    public boolean cacheHash(String k, String hk, String hv, long time) {
        String key = KEY_PREFIX_HASH + k;
        try {
            BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(key);
            hashOps.put(hk, hv);
            if (time > 0) hashOps.expire(time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            logger.error("缓存失败key[" + key + ", error[" + e + "]");
        }
        return false;
    }

    /**
     * @param k
     * @param hk
     * @param hv
     * @return
     */
    public boolean cacheHash(String k, String hk, String hv) {
        return this.cacheHash(k, hk, hv, -1);
    }

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
    public boolean cacheHash(String k, Map<String, String> map, long time) {
        String key = KEY_PREFIX_HASH + k;
        try {
            BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(key);
            hashOps.putAll(map);
            if (time > 0) hashOps.expire(time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            logger.error("缓存失败key[" + key + ", error[" + e + "]");
        }
        return false;
    }

    /**
     * @param k
     * @param map
     * @return
     */
    public boolean cacheHash(String k, Map<String, String> map) {

        return this.cacheHash(k, map, -1);
    }

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
    public Map<String, String> getHash(String k) {
        String key = KEY_PREFIX_HASH + k;
        try {
            BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(key);
            return hashOps.entries();
        } catch (Exception e) {
            logger.error("获取缓存失败key[" + key + ", error[" + e + "]");
        }
        return null;
    }

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
    public Set<String> getHashKey(String k) {
        String key = KEY_PREFIX_HASH + k;
        try {
            BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(key);
            return hashOps.keys();
        } catch (Exception e) {
            logger.error("获取缓存失败key[" + key + ", error[" + e + "]");
        }
        return null;
    }

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
    public List<String> getHashValues(String k) {
        String key = KEY_PREFIX_HASH + k;
        try {
            BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(key);
            return hashOps.values();
        } catch (Exception e) {
            logger.error("获取缓存失败key[" + key + ", error[" + e + "]");
        }
        return null;
    }

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
    public boolean deleteHash(String k, String... hks) {
        String key = KEY_PREFIX_HASH + k;
        try {

            if (hks == null || hks.length == 0) {
                redisTemplate.delete(key);
            } else {
                BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(key);
                hashOps.delete(hks);
            }
            return true;
        } catch (Exception e) {
            logger.error("获取缓存失败key[" + key + ", error[" + e + "]");
        }
        return false;
    }

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
    public Long getExpireTimeValue(String k) {
        String key = KEY_PREFIX_VALUE + k;
        Long expire = -2L;
        try {
            expire = redisTemplate.getExpire(key);

        } catch (Exception e) {
            logger.error("获取缓存剩余时间失败key[" + key + ", error[" + e + "]");
        }
        return expire;
    }

    /**
     * @param alias
     * @param k
     * @return
     */
    public Long getExpireTime(String alias, String k) {
        String key = alias + k;
        Long expire = -2L;
        try {
            expire = redisTemplate.getExpire(key);

        } catch (Exception e) {
            logger.error("获取缓存剩余时间失败key[" + key + ", error[" + e + "]");
        }
        return expire;
    }
    //---


    /**
     * @param time
     * @param k
     * @param v
     * @return
     * @MethodName：cacheZSet
     * @ReturnType：boolean
     * @Description：缓存set操作
     * @Creator：DM
     * @CreateTime：2018年5月20日 上午11:20:00
     * @Modifier：
     * @ModifyTime：
     */
    public boolean cacheZSet(long time, String k, String v, double dv) {
        String key = KEY_PREFIX_SET + k;
        try {
            BoundZSetOperations<String, String> zSetOps = redisTemplate.boundZSetOps(key);
            zSetOps.add(v, dv);
            if (time > 0) {
                zSetOps.expire(time, TimeUnit.SECONDS);
            }
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + v + "]", t);
        }
        return true;
    }

    public boolean cacheZSet(long time, String k, Set<ZSetOperations.TypedTuple<String>> sets) {
        String key = KEY_PREFIX_SET + k;
        try {
            BoundZSetOperations<String, String> zSetOps = redisTemplate.boundZSetOps(key);
            zSetOps.add(sets);
            if (time > 0) {
                zSetOps.expire(time, TimeUnit.SECONDS);
            }
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + sets + "]", t);
        }
        return true;
    }


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
    public boolean cacheZSet(String k, String v, double dv) {
        return cacheZSet(-1, k, v, dv);
    }


    /**
     * @param keys
     * @return
     */
    public long delKey(final String... keys) {

        return (long) redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                long result = 0;
                for (int i = 0; i < keys.length; i++) {
                    result = connection.del(keys[i].getBytes());
                }
                return result;
            }
        });

    }

    /**
     * @param pattern
     * @return
     */
    public Set keys(String pattern) {
        return redisTemplate.keys(pattern);

    }

    /**
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return (boolean) redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }

    /**
     * @return
     */
    public String flushDB() {
        return (String) redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    /**
     * @return
     */
    public long dbSize() {
        return (long) redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

    /**
     * @return
     */
    public String ping() {
        return (String) redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ping();
            }
        });
    }

    /**
     * @param match_pattern
     * @param count
     * @return
     */
    public Set<Object> scan(String match_pattern, long count) {

        return (Set<Object>) redisTemplate.execute(new RedisCallback<Set<Object>>() {
            @Override
            public Set<Object> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                Set<Object> binaryKeys = new HashSet<Object>();
                Cursor<byte[]> cursor = redisConnection.scan(ScanOptions.scanOptions().match(match_pattern).count(count).build());
                while (cursor.hasNext()) {
                    binaryKeys.add(new String(cursor.next()));
                }
                return binaryKeys;

            }
        });
    }

    /**
     * @param key
     * @return
     */
    public String getDataType(String key) {
        return redisTemplate.type(key).code();
    }
}
