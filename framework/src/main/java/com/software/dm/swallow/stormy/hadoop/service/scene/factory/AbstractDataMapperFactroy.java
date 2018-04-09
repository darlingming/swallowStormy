package com.software.dm.swallow.stormy.hadoop.service.scene.factory;

import java.util.HashMap;
import java.util.Map;

import com.software.dm.swallow.stormy.hadoop.service.common.AbstractConstants;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.basic.AbstractThirdEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.basic.BasisResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.basic.ClearResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.basic.ExtractResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.basic.ThemeResultEntity;
import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description
 * @date 2014-3-21
 */
public abstract class AbstractDataMapperFactroy implements AnalysisMapperInterface {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final static Map<Integer, AbstractThirdEntity> storeMap = new HashMap<Integer, AbstractThirdEntity>();
    private AbstractDoMapper doMapper;

    public AbstractDataMapperFactroy(AbstractDoMapper doMapper) {
        this.doMapper = doMapper;
    }

    public AbstractThirdEntity doThirdData(String data, String delimiterStr) {
        String[] datas = AbstractCommonUtils.fastSplit(data, delimiterStr);
        return this.findData(datas);
    }

    public AbstractThirdEntity doThirdData(String data, char delimiterChar) {
        String[] datas = AbstractCommonUtils.fastSplit(data, delimiterChar);
        return this.findData(datas);
    }

    public boolean isBasic(BasisResultEntity appBasisResult) {
        if ("0".equals(appBasisResult.getIs_app_url())) {
            return true;
        }
        return false;
    }

    /**
     * @param datas
     * @return
     */
    private AbstractThirdEntity findData(String[] datas) {
        int data_len = datas.length;
        AbstractThirdEntity reEntity = storeMap.get(data_len);
        switch (data_len) {
            case AbstractConstants.COLUMN_LENGTH_THEMEOUT:
                if (null != reEntity) {
                    reEntity.initData(datas);
                } else {
                    reEntity = new ThemeResultEntity(datas[0], datas[1], datas[2], datas[3], datas[4], datas[5], datas[6], datas[7], datas[8], datas[9], datas[10], datas[11], datas[12], datas[13], datas[14], datas[15], datas[16], datas[17], datas[18], datas[19]);
                    storeMap.put(data_len, reEntity);
                }
                break;
            case AbstractConstants.COLUMN_LENGTH_EXTRACTOUT:
                if (null != reEntity) {
                    reEntity.initData(datas);
                } else {
                    reEntity = new ExtractResultEntity(datas[0], datas[1], datas[2], datas[3], datas[4], datas[5], datas[6], datas[7], datas[8], datas[9], datas[10], datas[11], datas[12], datas[13], datas[14], datas[15], datas[16], datas[17], datas[18], datas[19], datas[20], datas[21]);
                    storeMap.put(data_len, reEntity);
                }
                break;
            case AbstractConstants.COLUMN_LENGTH_CLEAROUT:
                if (null != reEntity) {
                    reEntity.initData(datas);
                } else {
                    reEntity = new ClearResultEntity(datas[0], datas[1], datas[2], datas[3], datas[4], datas[5], datas[6], datas[7], datas[8], datas[9], datas[10], datas[11], datas[12], datas[13], datas[14], datas[15], datas[16], datas[17], datas[18], datas[19], datas[20], datas[21], datas[22]);
                    storeMap.put(data_len, reEntity);
                }
                break;
            case AbstractConstants.COLUMN_LENGTH_BASISOUT:
                if (null != reEntity) {
                    reEntity.initData(datas);
                } else {
                    reEntity = new BasisResultEntity(datas[0], datas[1], datas[2], datas[3], datas[4], datas[5], datas[6], datas[7], datas[8], datas[9], datas[10], datas[11], datas[12], datas[13], datas[14], datas[15], datas[16], datas[17], datas[18], datas[19], datas[20], datas[21], datas[22], datas[23], datas[24], datas[25], datas[26], datas[27], datas[28], datas[29], datas[30]);
                    storeMap.put(data_len, reEntity);
                }
                break;

        }
        return reEntity;
    }

    private void doMapperThirdEntity(AbstractThirdEntity entity) throws Exception {
        if (entity instanceof BasisResultEntity) {
            BasisResultEntity basisResult = (BasisResultEntity) entity;
            if (this.isBasic(basisResult)) {
                doMapper.basic(basisResult);
            } else {
                doMapper.app(basisResult);
                if (!basisResult.getType_id().isEmpty())
                    doMapper.basic(basisResult);
            }
        } else if (entity instanceof ExtractResultEntity) {
            ExtractResultEntity extractResult = (ExtractResultEntity) entity;
            if (AbstractConstants.APP_ID_LONLAT.equals(extractResult.getSubject_id())) {
                doMapper.lonlat(extractResult);
            } else if (AbstractConstants.APP_ID_FUSIONID.equals(extractResult.getSubject_id())) {
                doMapper.fusionid(extractResult);
            } else if (AbstractConstants.APP_ID_SWORD.equals(extractResult.getSubject_id())) {
                doMapper.sword(extractResult);
            } else {
                doMapper.subject(extractResult);
            }
        } else if (entity instanceof ClearResultEntity) {
            ClearResultEntity clearResult = (ClearResultEntity) entity;
            doMapper.fusionid(clearResult);
            doMapper.uaidhis(clearResult);
            doMapper.lacci(clearResult);
        } else if (entity instanceof ThemeResultEntity) {
            ThemeResultEntity themeResult = (ThemeResultEntity) entity;
            doMapper.theme(themeResult);
        }
    }

    private void doMapperData(String data, Object delimiter) throws Exception {
        AbstractThirdEntity entity = null;
        if (delimiter instanceof String) {
            entity = this.doThirdData(data, (String) delimiter);
        } else if (delimiter instanceof Character) {
            entity = this.doThirdData(data, (Character) delimiter);
        }
        this.doMapperThirdEntity(entity);
    }

    private void doMapperData(String[] datas) throws Exception {
        AbstractThirdEntity entity = this.findData(datas);
        this.doMapperThirdEntity(entity);
    }

    /**
     * @param data
     * @param delimiter
     * @param normal    dto is true non false
     * @throws Exception
     */
    public void doMapperData(String[] datas, boolean normal) throws Exception {
        if (normal) {
            this.doMapperData(datas);
        } else {
            this.doMapper.mapred(datas);
        }
    }

    /**
     * @param datas
     * @param normal dto is true non false
     * @throws Exception
     */
    public void doMapperData(String data, Object delimiter, boolean normal) throws Exception {
        if (normal) {
            this.doMapperData(data, delimiter);
        } else {
            String[] datas = null;
            if (delimiter instanceof String) {
                datas = AbstractCommonUtils.fastSplit(data, (String) delimiter);
            } else if (delimiter instanceof Character) {
                datas = AbstractCommonUtils.fastSplit(data, (Character) delimiter);
            }
            this.doMapperData(datas, normal);
        }

    }

    public void init() throws Exception {
        this.doMapper.init();

    }

    public void destroy() throws Exception {
        this.doMapper.destroy();

    }

    public void service(String key, Map<Object, Object> map) throws Exception {
        this.doMapper.service(key, map);
    }

}
