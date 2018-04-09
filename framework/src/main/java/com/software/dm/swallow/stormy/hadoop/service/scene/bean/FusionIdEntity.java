/**
 *
 */
package com.software.dm.swallow.stormy.hadoop.service.scene.bean;

import java.io.Serializable;

/**
 * @author DearM
 */
public class FusionIdEntity extends AbstractSceneEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param id
     * @param operTime
     * @param prov
     * @param netType
     */
    public FusionIdEntity(String id, String operTime, String prov, String netType) {
        super(id, operTime, prov, netType);
    }

    /**
     *
     */
    public FusionIdEntity() {

    }

}
