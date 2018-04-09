package com.software.dm.swallow.stormy.hadoop.service.scene.factory;

import java.util.HashMap;
import java.util.Map;

import com.software.dm.swallow.stormy.hadoop.common.MaperdTools;
import com.software.dm.swallow.stormy.hadoop.service.common.AbstractConstants;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description
 * @date 2016
 */
public abstract class AbstractDoReducer implements Scene {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private MaperdTools maperdTools;
    private static final Map<String, AbstractReduceScene> regeditReduce = new HashMap<String, AbstractReduceScene>();

    public AbstractDoReducer(MaperdTools maperdTools) {
        this.maperdTools = maperdTools;
    }

    public final AbstractDoReducer registerScene(String sceneCode, AbstractReduceScene scene) {
        scene.setMaperdTools(maperdTools);
        regeditReduce.put(sceneCode, scene);
        return this;
    }

    public void fusionid(String[] keyDatas, Iterable<?> value) throws Exception {
        AbstractReduceScene scene = this.getScene(AbstractConstants.SceneCode.FUSIONID.getCode());
        if (scene != null) {
            scene.doAction(keyDatas, value);
        }
    }

    ;

    public void fusionidClear(String[] keyDatas, Iterable<?> value) throws Exception {
        AbstractReduceScene scene = this.getScene(AbstractConstants.SceneCode.FUSIONID_CLEAR.getCode());
        if (scene != null) {
            scene.doAction(keyDatas, value);
        }
    }

    public void fusionidExtract(String[] keyDatas, Iterable<?> value) throws Exception {
        AbstractReduceScene scene = this.getScene(AbstractConstants.SceneCode.FUSIONID_EXTRACT.getCode());
        if (scene != null) {
            scene.doAction(keyDatas, value);
        }
    }

    public void lonlat(String[] keyDatas, Iterable<?> value) throws Exception {
        AbstractReduceScene scene = this.getScene(AbstractConstants.SceneCode.LONLAT.getCode());
        if (scene != null) {
            scene.doAction(keyDatas, value);
        }
    }

    ;

    public void uaidhis(String[] keyDatas, Iterable<?> value) throws Exception {
        AbstractReduceScene scene = this.getScene(AbstractConstants.SceneCode.UA.getCode());
        if (scene != null) {
            scene.doAction(keyDatas, value);
        }
    }

    ;

    public void subject(String[] keyDatas, Iterable<?> value) throws Exception {
        AbstractReduceScene scene = this.getScene(AbstractConstants.SceneCode.SUBJECT.getCode());
        if (scene != null) {
            scene.doAction(keyDatas, value);
        }
    }

    ;

    public void theme(String[] keyDatas, Iterable<?> value) throws Exception {
        AbstractReduceScene scene = this.getScene(AbstractConstants.SceneCode.THEME.getCode());
        if (scene != null) {
            scene.doAction(keyDatas, value);
        }
    }

    ;

    public void app(String[] keyDatas, Iterable<?> value) throws Exception {
        AbstractReduceScene scene = this.getScene(AbstractConstants.SceneCode.APP.getCode());
        if (scene != null) {
            scene.doAction(keyDatas, value);
        }
    }

    ;

    public void basic(String[] keyDatas, Iterable<?> value) throws Exception {
        AbstractReduceScene scene = this.getScene(AbstractConstants.SceneCode.BASIC.getCode());
        if (scene != null) {
            scene.doAction(keyDatas, value);
        }
    }

    ;

    public void lacci(String[] keyDatas, Iterable<?> value) throws Exception {
        AbstractReduceScene scene = this.getScene(AbstractConstants.SceneCode.LACCI.getCode());
        if (scene != null) {
            scene.doAction(keyDatas, value);
        }
    }

    ;

    public void sword(String[] keyDatas, Iterable<?> value) throws Exception {
        AbstractReduceScene scene = this.getScene(AbstractConstants.SceneCode.SWORD.getCode());
        if (scene != null) {
            scene.doAction(keyDatas, value);
        }

    }

    ;

    public void mapred(String[] keyDatas, Iterable<?> value) throws Exception {
        AbstractReduceScene scene = this.getScene(AbstractConstants.SceneCode.MAPRED.getCode());
        if (scene != null) {
            scene.doAction(keyDatas, value);
        }

    }

    ;

    public AbstractReduceScene getScene(String sceneCode) {
        return regeditReduce.get(sceneCode);
    }

    public void init() throws Exception {
        for (Map.Entry<String, AbstractReduceScene> reduce : regeditReduce.entrySet()) {
            reduce.getValue().init();
        }

    }

    public void destroy() throws Exception {
        for (Map.Entry<String, AbstractReduceScene> reduce : regeditReduce.entrySet()) {
            reduce.getValue().destroy();
        }
    }

    public void service(String key, Map<Object, Object> map) throws Exception {
        AbstractReduceScene reduce = this.getScene(key);
        if (null != reduce)
            reduce.service(key, map);

    }

}
