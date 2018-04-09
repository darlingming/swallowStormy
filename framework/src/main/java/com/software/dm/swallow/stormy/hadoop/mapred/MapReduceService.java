package com.software.dm.swallow.stormy.hadoop.mapred;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description
 * @date 2016��2��19��
 */
public interface MapReduceService {
    public void initMapper();

    public void executeMapper();

    public void initReducer();

    public void executeReducer();

}
