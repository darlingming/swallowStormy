package com.software.dm.swallow.stormy.hadoop.service.scene.factory;

import com.software.dm.swallow.stormy.hadoop.common.MaperdTools;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.basic.BasisResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.basic.ClearResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.basic.ExtractResultEntity;
import com.software.dm.swallow.stormy.hadoop.service.scene.bean.basic.ThemeResultEntity;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description
 * @date 2016-3-21
 */
public class DefaultDoMapper extends AbstractDoMapper {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public DefaultDoMapper(MaperdTools maperdTools) {
        super(maperdTools);
    }


    public void fusionid(ClearResultEntity clearResult) throws Exception {
        // TODO Auto-generated method stub
        super.fusionid(clearResult);
    }


    public void fusionid(ExtractResultEntity fusionid) throws Exception {
        // TODO Auto-generated method stub
        super.fusionid(fusionid);
    }


    public void lonlat(ExtractResultEntity lonlatExtract) throws Exception {
        // TODO Auto-generated method stub
        super.lonlat(lonlatExtract);
    }


    public void uaidhis(ClearResultEntity uaClearResult) throws Exception {
        // TODO Auto-generated method stub
        super.uaidhis(uaClearResult);
    }


    public void subject(ExtractResultEntity subjectExtractResult) throws Exception {
        // TODO Auto-generated method stub
        super.subject(subjectExtractResult);
    }


    public void theme(ThemeResultEntity themeResult) throws Exception {
        // TODO Auto-generated method stub
        super.theme(themeResult);
    }


    public void app(BasisResultEntity appResult) throws Exception {
        // TODO Auto-generated method stub
        super.app(appResult);
    }


    public void basic(BasisResultEntity basisResult) throws Exception {
        // TODO Auto-generated method stub
        super.basic(basisResult);
    }


    public void lacci(ClearResultEntity lacciClearResult) throws Exception {
        // TODO Auto-generated method stub
        super.lacci(lacciClearResult);
    }


    public void sword(ExtractResultEntity swordExtractResult) throws Exception {
        // TODO Auto-generated method stub
        super.sword(swordExtractResult);
    }


    public AbstractMapperScene getScene(String sceneCode) {
        // TODO Auto-generated method stub
        return super.getScene(sceneCode);
    }

}
