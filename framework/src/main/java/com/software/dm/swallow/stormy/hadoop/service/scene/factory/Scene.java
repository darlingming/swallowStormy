package com.software.dm.swallow.stormy.hadoop.service.scene.factory;

import java.util.Map;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description
 * @date 2011-3-29
 */
public interface Scene extends java.io.Serializable {

    /**
     * @throws Exception
     */
    public void init() throws Exception;

    /**
     * @param key
     * @param map
     * @throws Exception
     */
    public void service(String key, Map<Object, Object> map) throws Exception;

    /**
     * @throws Exception
     */
    public void destroy() throws Exception;
}
