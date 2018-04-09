package com.software.dm.swallow.stormy.hadoop.service.scene.factory.analysis;

import com.software.dm.swallow.stormy.hadoop.common.MaperdTools;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean.AppDtoResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean.BasisDtoResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean.ClearDtoResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean.ExtractDtoResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean.ThemeDtoResultEntity;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description
 * @date 2016-3-21
 */
public class DefaultDtoDoMapper extends AbstractDtoDoMapper {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public DefaultDtoDoMapper(MaperdTools maperdTools) {
        super(maperdTools);
    }


    public void fusionid(ClearDtoResultEntity clearResult) throws Exception {
        // TODO Auto-generated method stub
        super.fusionid(clearResult);
    }


    public void fusionid(ExtractDtoResultEntity fusionid) throws Exception {
        // TODO Auto-generated method stub
        super.fusionid(fusionid);
    }


    public void lonlat(ExtractDtoResultEntity lonlatExtract) throws Exception {
        // TODO Auto-generated method stub
        super.lonlat(lonlatExtract);
    }


    public void uaidhis(ClearDtoResultEntity uaClearResult) throws Exception {
        // TODO Auto-generated method stub
        super.uaidhis(uaClearResult);
    }


    public void subject(ExtractDtoResultEntity subjectExtractResult) throws Exception {
        // TODO Auto-generated method stub
        super.subject(subjectExtractResult);
    }


    public void theme(ThemeDtoResultEntity themeResult) throws Exception {
        // TODO Auto-generated method stub
        super.theme(themeResult);
    }


    public void app(AppDtoResultEntity appResult) throws Exception {
        // TODO Auto-generated method stub
        super.app(appResult);
    }


    public void basic(BasisDtoResultEntity basisResult) throws Exception {
        // TODO Auto-generated method stub
        super.basic(basisResult);
    }


    public void lacci(ClearDtoResultEntity lacciClearResult) throws Exception {
        // TODO Auto-generated method stub
        super.lacci(lacciClearResult);
    }


    public void sword(ExtractDtoResultEntity swordExtractResult) throws Exception {
        // TODO Auto-generated method stub
        super.sword(swordExtractResult);
    }


    public AbstractDtoMapperScene getScene(String sceneCode) {
        // TODO Auto-generated method stub
        return super.getScene(sceneCode);
    }

}
