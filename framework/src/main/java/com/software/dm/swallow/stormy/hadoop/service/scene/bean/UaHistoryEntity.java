/**
 *
 */
package com.software.dm.swallow.stormy.hadoop.service.scene.bean;

import java.io.Serializable;

/**
 * @author DearM
 */
public class UaHistoryEntity extends AbstractSceneEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String erminal_type = "";
    private String terminal_brand = "";
    private String system = "";
    private String pv = "1";
    private String visit_date = "";

    public UaHistoryEntity(String id, String erminal_type, String terminal_brand, String system, String visit_date, String operTime, String prov, String netType) {
        super(id, operTime, prov, netType);
        this.erminal_type = erminal_type;
        this.terminal_brand = terminal_brand;
        this.system = system;
        this.visit_date = visit_date;
    }

    /**
     * @param id
     * @param operTime
     * @param prov
     * @param netType
     */
    public UaHistoryEntity(String id, String operTime, String prov, String netType) {
        super(id, operTime, prov, netType);
    }

    /**
     *
     */
    public UaHistoryEntity() {
    }

    public String getErminal_type() {
        return erminal_type;
    }

    public void setErminal_type(String erminal_type) {
        this.erminal_type = erminal_type;
    }

    public String getTerminal_brand() {
        return terminal_brand;
    }

    public void setTerminal_brand(String terminal_brand) {
        this.terminal_brand = terminal_brand;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

}
