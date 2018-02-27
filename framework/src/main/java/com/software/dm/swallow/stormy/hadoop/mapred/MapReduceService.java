package com.software.dm.swallow.stormy.hadoop.mapred;

/**
 * 
 * @Description  
 * @author DearM
 * @date 2016��2��19��
 * @version v1.0.0.1 
 *
 */
public interface MapReduceService {
	public void initMapper();

	public void executeMapper();
	
	public void initReducer();

	public void executeReducer();

}
