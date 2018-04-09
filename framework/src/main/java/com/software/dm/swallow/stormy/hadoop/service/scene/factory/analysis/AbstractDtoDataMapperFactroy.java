package com.software.dm.swallow.stormy.hadoop.service.scene.factory.analysis;

import java.util.HashMap;
import java.util.Map;

import com.software.dm.swallow.stormy.hadoop.service.common.AbstractConstants;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean.AbstractDtoEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean.AppDtoResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean.BasisDtoResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean.ClearDtoResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean.ExtractDtoResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean.ThemeDtoResultEntity;
import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description
 * @date 2014-3-21
 */
public abstract class AbstractDtoDataMapperFactroy implements AnalysisDtoMapperInterface {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final static Map<String, AbstractDtoEntity> storeMap = new HashMap<String, AbstractDtoEntity>();
    private AbstractDtoDoMapper doMapper;

    public AbstractDtoDataMapperFactroy(AbstractDtoDoMapper doMapper) {
        this.doMapper = doMapper;
    }

    /**
     * @param data
     * @param delimiterStr
     * @return
     */
    public AbstractDtoEntity doDtoData(String data, String delimiterStr) {
        String[] datas = AbstractCommonUtils.fastSplit(data, delimiterStr);
        return this.findData(datas);
    }

    /**
     * @param data
     * @param delimiterChar
     * @return
     */
    public AbstractDtoEntity doDtoData(String data, char delimiterChar) {
        String[] datas = AbstractCommonUtils.fastSplit(data, delimiterChar);
        return this.findData(datas);
    }

    /**
     * @param data
     * @param delimiterStr
     * @return
     */
    public AbstractDtoEntity doDtoDataFix(String data, String delimiterStr) {
        String[] datas = AbstractCommonUtils.fastSplit(data, delimiterStr);
        return this.findDataFix(datas);
    }

    /**
     * @param data
     * @param delimiterChar
     * @return
     */
    public AbstractDtoEntity doDtoDataFix(String data, char delimiterChar) {
        String[] datas = AbstractCommonUtils.fastSplit(data, delimiterChar);
        return this.findDataFix(datas);
    }

    /**
     * @param datas
     * @return
     */
    private AbstractDtoEntity findData(String[] datas) {
        String actionEntity = datas[0];
        AbstractDtoEntity reEntity = storeMap.get(actionEntity);
        if (null != reEntity) {
            reEntity.initData(datas);
        } else if (AbstractConstants.BDIA_BAISCCODE.equals(actionEntity)) {
            reEntity = new BasisDtoResultEntity(datas);
            storeMap.put(actionEntity, reEntity);
        } else if (AbstractConstants.BDIA_APPCODE.equals(actionEntity)) {
            reEntity = new AppDtoResultEntity(datas);
            storeMap.put(actionEntity, reEntity);
        } else if (AbstractConstants.BDIA_THEMECODE.equals(actionEntity)) {
            reEntity = new ThemeDtoResultEntity(datas);
            storeMap.put(actionEntity, reEntity);
        } else if (AbstractConstants.BDIA_CLEARCODE.equals(actionEntity)) {
            reEntity = new ClearDtoResultEntity(datas);
            storeMap.put(actionEntity, reEntity);
        } else if (AbstractConstants.BDIA_FUSIONIDCODE.equals(actionEntity)) {
            reEntity = new ExtractDtoResultEntity(datas);
            storeMap.put(actionEntity, reEntity);
        } else if (AbstractConstants.BDIA_LONLATCODE.equals(actionEntity)) {
            reEntity = new ExtractDtoResultEntity(datas);
            storeMap.put(actionEntity, reEntity);
        } else if (AbstractConstants.BDIA_SUBJECTCODE.equals(actionEntity)) {
            reEntity = new ExtractDtoResultEntity(datas);
            storeMap.put(actionEntity, reEntity);
        } else if (AbstractConstants.BDIA_SWORDCODE.equals(actionEntity)) {
            reEntity = new ExtractDtoResultEntity(datas);
            storeMap.put(actionEntity, reEntity);
        }

        return reEntity;
    }

    /**
     * @param datas
     * @return
     */
    private AbstractDtoEntity findDataFix(String[] datas) {
        String actionEntity = datas[0];
        AbstractDtoEntity reEntity = storeMap.get(actionEntity);
        if (null != reEntity) {
            reEntity.initData(datas);
        } else if (AbstractConstants.BDIA_BAISCCODE_FIX.equals(actionEntity)) {
            reEntity = new BasisDtoResultEntity(datas);
            storeMap.put(actionEntity, reEntity);
        } else if (AbstractConstants.BDIA_APPCODE_FIX.equals(actionEntity)) {
            reEntity = new AppDtoResultEntity(datas);
            storeMap.put(actionEntity, reEntity);
        } else if (AbstractConstants.BDIA_THEMECODE_FIX.equals(actionEntity)) {
            reEntity = new ThemeDtoResultEntity(datas);
            storeMap.put(actionEntity, reEntity);
        } else if (AbstractConstants.BDIA_CLEARCODE_FIX.equals(actionEntity)) {
            reEntity = new ClearDtoResultEntity(datas);
            storeMap.put(actionEntity, reEntity);
        } else if (AbstractConstants.BDIA_FUSIONIDCODE_FIX.equals(actionEntity)) {
            reEntity = new ExtractDtoResultEntity(datas);
            storeMap.put(actionEntity, reEntity);
        } else if (AbstractConstants.BDIA_LONLATCODE_FIX.equals(actionEntity)) {
            reEntity = new ExtractDtoResultEntity(datas);
            storeMap.put(actionEntity, reEntity);
        } else if (AbstractConstants.BDIA_SUBJECTCODE_FIX.equals(actionEntity)) {
            reEntity = new ExtractDtoResultEntity(datas);
            storeMap.put(actionEntity, reEntity);
        } else if (AbstractConstants.BDIA_SWORDCODE_FIX.equals(actionEntity)) {
            reEntity = new ExtractDtoResultEntity(datas);
            storeMap.put(actionEntity, reEntity);
        }

        return reEntity;
    }

    /**
     * @param entity
     * @throws Exception
     */
    private void doMapperDtoEntity(AbstractDtoEntity entity) throws Exception {
        if (entity instanceof BasisDtoResultEntity) {
            BasisDtoResultEntity basisResult = (BasisDtoResultEntity) entity;
            doMapper.basic(basisResult);
        } else if (entity instanceof AppDtoResultEntity) {
            AppDtoResultEntity appResult = (AppDtoResultEntity) entity;
            doMapper.app(appResult);
        } else if (entity instanceof ExtractDtoResultEntity) {
            ExtractDtoResultEntity extractResult = (ExtractDtoResultEntity) entity;
            if (AbstractConstants.BDIA_LONLATCODE.equals(extractResult.getActionCode())) {
                doMapper.lonlat(extractResult);
            } else if (AbstractConstants.BDIA_FUSIONIDCODE.equals(extractResult.getActionCode())) {
                doMapper.fusionid(extractResult);
            } else if (AbstractConstants.BDIA_SWORDCODE.equals(extractResult.getActionCode())) {
                doMapper.sword(extractResult);
            } else {
                doMapper.subject(extractResult);
            }
        } else if (entity instanceof ClearDtoResultEntity) {
            ClearDtoResultEntity clearResult = (ClearDtoResultEntity) entity;
            doMapper.fusionid(clearResult);
            doMapper.uaidhis(clearResult);
            doMapper.lacci(clearResult);
        } else if (entity instanceof ThemeDtoResultEntity) {
            ThemeDtoResultEntity themeResult = (ThemeDtoResultEntity) entity;
            doMapper.theme(themeResult);
        }
    }

    /**
     * @param entity
     * @throws Exception
     */
    private void doMapperDtoEntityFix(AbstractDtoEntity entity) throws Exception {
        if (entity instanceof BasisDtoResultEntity) {
            BasisDtoResultEntity basisResult = (BasisDtoResultEntity) entity;
            doMapper.basic(basisResult);
        } else if (entity instanceof AppDtoResultEntity) {
            AppDtoResultEntity appResult = (AppDtoResultEntity) entity;
            doMapper.app(appResult);
        } else if (entity instanceof ExtractDtoResultEntity) {
            ExtractDtoResultEntity extractResult = (ExtractDtoResultEntity) entity;
            if (AbstractConstants.BDIA_LONLATCODE_FIX.equals(extractResult.getActionCode())) {
                doMapper.lonlat(extractResult);
            } else if (AbstractConstants.BDIA_FUSIONIDCODE_FIX.equals(extractResult.getActionCode())) {
                doMapper.fusionid(extractResult);
            } else if (AbstractConstants.BDIA_SWORDCODE_FIX.equals(extractResult.getActionCode())) {
                doMapper.sword(extractResult);
            } else {
                doMapper.subject(extractResult);
            }
        } else if (entity instanceof ClearDtoResultEntity) {
            ClearDtoResultEntity clearResult = (ClearDtoResultEntity) entity;
            doMapper.fusionid(clearResult);
            doMapper.uaidhis(clearResult);
            doMapper.lacci(clearResult);
        } else if (entity instanceof ThemeDtoResultEntity) {
            ThemeDtoResultEntity themeResult = (ThemeDtoResultEntity) entity;
            doMapper.theme(themeResult);
        }
    }

    public void doMapperData(String data, Object delimiter) throws Exception {
        AbstractDtoEntity entity = null;
        if (delimiter instanceof String) {
            entity = this.doDtoData(data, (String) delimiter);
        } else if (delimiter instanceof Character) {
            entity = this.doDtoData(data, (Character) delimiter);
        }
        this.doMapperDtoEntity(entity);
    }

    /**
     * @param data
     * @param delimiter
     * @throws Exception
     */
    public void doMapperDataFix(String data, Object delimiter) throws Exception {
        AbstractDtoEntity entity = null;
        if (delimiter instanceof String) {
            entity = this.doDtoDataFix(data, (String) delimiter);
        } else if (delimiter instanceof Character) {
            entity = this.doDtoDataFix(data, (Character) delimiter);
        }
        this.doMapperDtoEntityFix(entity);
    }

    /**
     *
     */
    public void doMapperData(String[] datas) throws Exception {
        AbstractDtoEntity entity = this.findData(datas);
        this.doMapperDtoEntity(entity);
    }

    /**
     * @param datas
     * @throws Exception
     */
    public void doMapperDataFix(String[] datas) throws Exception {
        AbstractDtoEntity entity = this.findDataFix(datas);
        this.doMapperDtoEntityFix(entity);
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
