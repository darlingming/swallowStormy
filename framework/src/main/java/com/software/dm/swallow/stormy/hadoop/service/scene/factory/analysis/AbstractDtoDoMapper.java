package com.software.dm.swallow.stormy.hadoop.service.scene.factory.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.software.dm.swallow.stormy.hadoop.common.MaperdTools;
import com.software.dm.swallow.stormy.hadoop.service.common.AbstractConstants;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean.AppDtoResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean.BasisDtoResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean.ClearDtoResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean.ExtractDtoResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean.ThemeDtoResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.basic.KvTerminalDbEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.factory.Scene;
import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils;

/**
 * 
 * @Description
 * @author DearM
 * @date 2015-3-21
 * @version v1.0.0.1
 * 
 */
public abstract class AbstractDtoDoMapper implements Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MaperdTools maperdTools;
	private static final Map<String, AbstractDtoMapperScene> regeditMap = new HashMap<String, AbstractDtoMapperScene>();
	private static final Map<String, KvTerminalDbEntity> chkMap = new HashMap<String, KvTerminalDbEntity>();

	private static boolean bool_load_kv = false;

	public AbstractDtoDoMapper(MaperdTools maperdTools) {
		this.maperdTools = maperdTools;
	}

	public void fusionid(ClearDtoResultEntity clearResult) throws Exception {
		AbstractDtoMapperScene scene = this.getScene(AbstractConstants.SceneCode.FUSIONID_CLEAR.getCode());
		if (scene != null) {
			scene.doAction(clearResult);
		}
	};

	public void fusionid(ExtractDtoResultEntity fusionid) throws Exception {
		AbstractDtoMapperScene scene = this.getScene(AbstractConstants.SceneCode.FUSIONID_EXTRACT.getCode());
		if (scene != null) {
			scene.doAction(fusionid);
		}
	};

	public void lonlat(ExtractDtoResultEntity lonlatExtract) throws Exception {
		AbstractDtoMapperScene scene = this.getScene(AbstractConstants.SceneCode.LONLAT.getCode());
		if (scene != null) {
			scene.doAction(lonlatExtract);
		}
	};

	public void uaidhis(ClearDtoResultEntity uaClearResult) throws Exception {
		AbstractDtoMapperScene scene = this.getScene(AbstractConstants.SceneCode.UA.getCode());
		if (scene != null) {
			scene.doAction(uaClearResult);
		}
	};

	public void subject(ExtractDtoResultEntity subjectExtractResult) throws Exception {
		AbstractDtoMapperScene scene = this.getScene(AbstractConstants.SceneCode.SUBJECT.getCode());
		if (scene != null) {
			scene.doAction(subjectExtractResult);
		}
	};

	public void theme(ThemeDtoResultEntity themeResult) throws Exception {
		AbstractDtoMapperScene scene = this.getScene(AbstractConstants.SceneCode.THEME.getCode());
		if (scene != null) {
			scene.doAction(themeResult);
		}
	};

	public void app(AppDtoResultEntity appResult) throws Exception {
		AbstractDtoMapperScene scene = this.getScene(AbstractConstants.SceneCode.APP.getCode());
		if (scene != null) {
			scene.doAction(appResult);
		}
	};

	public void basic(BasisDtoResultEntity basisResult) throws Exception {
		AbstractDtoMapperScene scene = this.getScene(AbstractConstants.SceneCode.BASIC.getCode());
		if (scene != null) {
			scene.doAction(basisResult);
		}
	};

	public void lacci(ClearDtoResultEntity lacciClearResult) throws Exception {
		AbstractDtoMapperScene scene = this.getScene(AbstractConstants.SceneCode.LACCI.getCode());
		if (scene != null) {
			scene.doAction(lacciClearResult);
		}
	};

	public void sword(ExtractDtoResultEntity swordExtractResult) throws Exception {
		AbstractDtoMapperScene scene = this.getScene(AbstractConstants.SceneCode.SWORD.getCode());
		if (scene != null) {
			scene.doAction(swordExtractResult);
		}

	};

	public void mapred(String[] datas) throws Exception {
		AbstractDtoMapperScene scene = this.getScene(AbstractConstants.SceneCode.MAPRED.getCode());
		if (scene != null) {
			scene.doAction(datas);
		}
	};

	public final AbstractDtoDoMapper registerScene(String sceneCode, AbstractDtoMapperScene scene) {
		scene.setMaperdTools(maperdTools);
		regeditMap.put(sceneCode, scene);
		return this;
	}

	public AbstractDtoMapperScene getScene(String sceneCode) {
		return regeditMap.get(sceneCode);
	}

	private final static void loadKVAddr() throws IOException {
		InputStream is = AbstractDtoDoMapper.class.getResourceAsStream(AbstractConstants.KV_FILE);
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String line = "";
		KvTerminalDbEntity kv = null;
		while ((line = br.readLine()) != null) {
			String[] vals = AbstractCommonUtils.fastSplit(line, AbstractConstants.PUB_TAB_CHAR);
			vals[0] = vals[0].trim();
			kv = new KvTerminalDbEntity();
			kv.setTerminal_type(vals[1]);
			kv.setTerminal_brand(vals[3]);
			kv.setSystem(vals[4]);
			chkMap.put(vals[0], kv);
		}
		br.close();
		bool_load_kv = true;
	}

	public final static KvTerminalDbEntity getKvTerminal(String terminal_id) throws IOException {
		if (!bool_load_kv)
			loadKVAddr();
		return chkMap.get(terminal_id);
	}

	/**
	 * 
	 * @param ua
	 * @return
	 */
	public static String evaluate(String ua) {
		String ret = null;
		String[] regexs = { "\\(((?:iphone|ipad)[^\\);]*)(?:;)", "(?:\\(linux;)(?:[^\\)]*?)([^\\);]*)(?:miui|build)(?:[^\\)]*)(?:\\))", "(?:\\(compatible;)(?:[^\\)]*?)([^\\);]*)(?:\\))" };
		for (String regex : regexs) {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(ua.toLowerCase());
			if (m.find()) {
				ret = m.group(1).trim();
				break;
			}
		}
		return ret;
	}

	public void init() throws Exception {
		for (Map.Entry<String, AbstractDtoMapperScene> map : regeditMap.entrySet()) {
			map.getValue().init();
		}

	}

	public void destroy() throws Exception {
		for (Map.Entry<String, AbstractDtoMapperScene> map : regeditMap.entrySet()) {
			map.getValue().destroy();
		}
	}

	public void service(String key, Map<Object, Object> map) throws Exception {
		AbstractDtoMapperScene mapper = this.getScene(key);
		if (null != mapper)
			mapper.service(key, map);

	}
}
