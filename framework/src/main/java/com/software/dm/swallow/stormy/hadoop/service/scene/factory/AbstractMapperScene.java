package com.software.dm.swallow.stormy.hadoop.service.scene.factory;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.io.NullWritable;

import com.software.dm.swallow.stormy.hadoop.common.HadoopConstants;
import com.software.dm.swallow.stormy.hadoop.common.MaperdTools;
import com.software.dm.swallow.stormy.hadoop.service.common.AbstractConstants;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.basic.AbstractThirdEntity;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description
 * @date 2016-3-21
 */
public abstract class AbstractMapperScene implements Scene {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private MaperdTools maperdTools;

    public abstract void doAction(AbstractThirdEntity entity) throws Exception;

    public void doAction(String[] datas) throws Exception {
    }

    ;

    public MaperdTools getMaperdTools() {
        return maperdTools;
    }

    public void setMaperdTools(MaperdTools maperdTools) {
        this.maperdTools = maperdTools;
    }

    /**
     * @param flagPathName
     * @param midPathName
     * @param fileName
     * @param content
     * @throws IOException
     * @throws InterruptedException
     */
    public void mosWrite(String namedOutput, String baseOutputPath, String fileName, String content) throws IOException, InterruptedException {
        if (baseOutputPath == null || baseOutputPath.isEmpty()) {
            this.maperdTools.getMos().write(namedOutput, content, NullWritable.get(), fileName);
        } else {
            this.maperdTools.getMos().write(namedOutput, content, NullWritable.get(), baseOutputPath + AbstractConstants.FILE_SEPARATOR + fileName);
        }
    }

    /**
     * @param namedOutput
     * @param baseOutputPath
     * @param content
     * @throws IOException
     * @throws InterruptedException
     */
    public void mosWrite(String namedOutput, String baseOutputPath, String content) throws IOException, InterruptedException {
        this.mosWrite(namedOutput, baseOutputPath, HadoopConstants.MULTIPLE_OUTPUTS_FILENAME, content);
    }

    /**
     * @param namedOutput
     * @param midPathName
     * @param fileName
     * @param key
     * @param value
     * @throws IOException
     * @throws InterruptedException
     */
    public void mosWrite(String namedOutput, String baseOutputPath, String fileName, Object key, Object value) throws IOException, InterruptedException {
        if (baseOutputPath == null || baseOutputPath.isEmpty()) {
            this.maperdTools.getMos().write(namedOutput, key, value, fileName);
        } else {
            this.maperdTools.getMos().write(namedOutput, key, value, baseOutputPath + AbstractConstants.FILE_SEPARATOR + fileName);
        }
    }

    /**
     * @param namedOutput
     * @param baseOutputPath
     * @param key
     * @param value
     * @throws IOException
     * @throws InterruptedException
     */
    public void mosWrite(String namedOutput, String baseOutputPath, Object key, Object value) throws IOException, InterruptedException {
        this.mosWrite(namedOutput, baseOutputPath, HadoopConstants.MULTIPLE_OUTPUTS_FILENAME, key, value);
    }

    /**
     * @param groupName
     * @param counterName
     */
    public void addCounterMapper(String groupName, final String counterName) {
        if (null == groupName)
            groupName = AbstractConstants.TotalCountEnum.TOTAL_COUNT.name();
        this.maperdTools.addCounterMapper(groupName, counterName, 1);
    }

    /**
     * @param counterName
     */
    public void addCounterMapper(final String counterName) {
        this.addCounterMapper(null, counterName);
    }

    public void init() throws Exception {
        // TODO Auto-generated method stub

    }

    public void destroy() throws Exception {
        // TODO Auto-generated method stub

    }

    public void service(String key, Map<Object, Object> map) throws Exception {
        // TODO Auto-generated method stub

    }

}
