package com.software.dm.swallow.stormy.hadoop.service.scene.factory.analysis;

import com.software.dm.swallow.stormy.hadoop.service.scene.factory.Scene;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description
 * @date 2016-3-17
 */
public interface AnalysisDtoMapperInterface extends Scene {
    /**
     * @param data
     * @param delimiter
     * @param normal    dto is true non false
     * @throws Exception
     */
    public void doMapperData(String data, Object delimiter) throws Exception;

    /**
     * @param datas
     * @param normal dto is true non false
     * @throws Exception
     */
    public void doMapperData(String[] datas) throws Exception;

    /**
     * @param data
     * @param delimiter
     * @param normal    dto is true non false
     * @throws Exception
     */
    public void doMapperDataFix(String data, Object delimiter) throws Exception;

    /**
     * @param datas
     * @param normal dto is true non false
     * @throws Exception
     */
    public void doMapperDataFix(String[] datas) throws Exception;

}
