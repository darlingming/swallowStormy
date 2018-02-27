package com.software.dm.swallow.stormy.hadoop.service.scene.factory;

/**
 * 
 * @Description
 * @author DearM
 * @date 2016-3-21
 * @version v1.0.0.1
 * 
 */
public interface AnalysisReducerInterface extends Scene{
	/**
	 * 
	 * @param keyData
	 * @param value
	 * @param delimiter
	 * @throws Exception
	 */
	public void doReduceData(String keyData, Iterable<?> value, Object delimiter) throws Exception;

	/**
	 * 
	 * @param keyDatas
	 * @param value
	 * @throws Exception
	 */
	public void doReduceData(String[] keyDatas, Iterable<?> value) throws Exception;
}
