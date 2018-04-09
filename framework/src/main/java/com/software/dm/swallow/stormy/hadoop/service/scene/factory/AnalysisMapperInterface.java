package com.software.dm.swallow.stormy.hadoop.service.scene.factory;


/**
 * @author DearM
 * @version v1.0.0.1
 * @Description
 * @date 2016-3-17
 */
public interface AnalysisMapperInterface extends Scene {
    /**
     * @param data
     * @param delimiter
     * @param normal    dto is true non false
     * @throws Exception
     */
    public void doMapperData(String data, Object delimiter, boolean normal) throws Exception;

    /**
     * @param datas
     * @param normal dto is true non false
     * @throws Exception
     */
    public void doMapperData(String[] datas, boolean normal) throws Exception;
}
