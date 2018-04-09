package com.software.dm.swallow.stormy.hadoop.service.scene.factory.analysis;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.io.NullWritable;

import com.software.dm.swallow.stormy.hadoop.common.HadoopConstants;
import com.software.dm.swallow.stormy.hadoop.common.MaperdTools;
import com.software.dm.swallow.stormy.hadoop.service.common.AbstractConstants;
import com.software.dm.swallow.stormy.hadoop.service.scene.factory.Scene;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description
 * @date 2016-2-16
 */
public abstract class AbstractDtoReduceScene implements Scene {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private MaperdTools maperdTools;

    public abstract void doAction(String[] keyDatas, Iterable<?> value) throws Exception;

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
     * @param totalName
     */
    public void addCounterReducer(String groupName, final String totalName) {
        if (null == groupName)
            groupName = AbstractConstants.TotalCountEnum.TOTAL_COUNT.name();
        this.maperdTools.addCounterReducer(groupName, totalName, 1);
    }

    /**
     * @param totalName
     */
    public void addCounterReducer(final String totalName) {
        this.addCounterReducer(null, totalName);
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
