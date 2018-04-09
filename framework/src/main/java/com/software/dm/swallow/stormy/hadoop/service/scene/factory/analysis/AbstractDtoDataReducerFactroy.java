package com.software.dm.swallow.stormy.hadoop.service.scene.factory.analysis;

import java.util.Map;

import com.software.dm.swallow.stormy.hadoop.service.common.AbstractConstants;
import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description
 * @date 2016-3-21
 */
public abstract class AbstractDtoDataReducerFactroy implements AnalysisDtoReducerInterface {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private AbstractDtoDoReducer doReducer;

    public AbstractDtoDataReducerFactroy(AbstractDtoDoReducer doReducer) {
        this.doReducer = doReducer;
    }

    public void doReduceData(String keyData, Iterable<?> value, Object delimiter) throws Exception {
        String[] keyDatas = null;
        if (delimiter instanceof String) {
            keyDatas = AbstractCommonUtils.fastSplit(keyData, (String) delimiter);
        } else if (delimiter instanceof Character) {
            keyDatas = AbstractCommonUtils.fastSplit(keyData, (Character) delimiter);
        }
        this.doReduceData(keyDatas, value);

    }

    public void doReduceData(String[] keyDatas, Iterable<?> value) throws Exception {
        if (keyDatas == null || keyDatas.length <= 1)
            return;
        String sceneCode = keyDatas[keyDatas.length - 1];
        if (AbstractConstants.SceneCode.APP.getCode().equals(sceneCode)) {
            this.doReducer.app(keyDatas, value);
        } else if (AbstractConstants.SceneCode.BASIC.getCode().equals(sceneCode)) {
            this.doReducer.basic(keyDatas, value);
        } else if (AbstractConstants.SceneCode.THEME.getCode().equals(sceneCode)) {
            this.doReducer.theme(keyDatas, value);
        } else if (AbstractConstants.SceneCode.SWORD.getCode().equals(sceneCode)) {
            this.doReducer.sword(keyDatas, value);
        } else if (AbstractConstants.SceneCode.UA.getCode().equals(sceneCode)) {
            this.doReducer.uaidhis(keyDatas, value);
        } else if (AbstractConstants.SceneCode.FUSIONID_EXTRACT.getCode().equals(sceneCode)) {
            this.doReducer.fusionidExtract(keyDatas, value);
        } else if (AbstractConstants.SceneCode.FUSIONID_CLEAR.getCode().equals(sceneCode)) {
            this.doReducer.fusionidClear(keyDatas, value);
        } else if (AbstractConstants.SceneCode.FUSIONID.getCode().equals(sceneCode)) {
            this.doReducer.fusionid(keyDatas, value);
        } else if (AbstractConstants.SceneCode.LACCI.getCode().equals(sceneCode)) {
            this.doReducer.lacci(keyDatas, value);
        } else if (AbstractConstants.SceneCode.LONLAT.getCode().equals(sceneCode)) {
            this.doReducer.lonlat(keyDatas, value);
        } else if (AbstractConstants.SceneCode.SUBJECT.getCode().equals(sceneCode)) {
            this.doReducer.subject(keyDatas, value);
        } else if (AbstractConstants.SceneCode.MAPRED.getCode().equals(sceneCode)) {
            this.doReducer.mapred(keyDatas, value);
        }

    }

    public void init() throws Exception {
        this.doReducer.init();

    }

    public void destroy() throws Exception {
        this.doReducer.destroy();
        ;

    }

    public void service(String key, Map<Object, Object> map) throws Exception {
        this.doReducer.service(key, map);
    }

}
