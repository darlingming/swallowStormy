package com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean;

import java.io.Serializable;

/**
 * @author DearM
 * @since 24 fields
 */
public class ClearDtoResultEntity extends AbstractDtoEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final int FIELDS_COUNT = 24;

    public ClearDtoResultEntity(String[] datas) {
        super(datas);
    }

    private final int actionCode = 0;
    private final int uuid = 1;
    private final int phoneId = 2;
    private final int imei = 3;
    private final int imsi = 4;
    private final int account = 5;
    private final int ua = 6;
    private final int startTime = 7;
    private final int domain = 8;
    private final int url = 9;
    private final int clienIp = 10;
    private final int clienPort = 11;// 13
    private final int serverIp = 12;
    private final int serverPort = 13;
    private final int upLinkBytes = 14;// 21
    private final int downLinkBytes = 15;// 22
    private final int lac = 16;
    private final int ci = 17;
    private final int refer = 18;
    private final int cookie = 19;
    private final int sceneFlag = 20;
    private final int dealTime = 21;
    private final int netType = 22;
    private final int provice = 23;

    public int getFieldsCount() {
        return FIELDS_COUNT;
    }

    public String getActionCode() {
        return this.datas[actionCode];
    }

    public String getUuid() {
        return this.datas[uuid];
    }

    public String getPhoneId() {
        return this.datas[phoneId];
    }

    public String getImei() {
        return this.datas[imei];
    }

    public String getImsi() {
        return this.datas[imsi];
    }

    public String getAccount() {
        return this.datas[account];
    }

    public String getUa() {
        return this.datas[ua];
    }

    public String getStartTime() {
        return this.datas[startTime];
    }

    public String getDomain() {
        return this.datas[domain];
    }

    public String getUrl() {
        return this.datas[url];
    }

    public String getClienIp() {
        return this.datas[clienIp];
    }

    public String getClienPort() {
        return this.datas[clienPort];
    }

    public String getServerIp() {
        return this.datas[serverIp];
    }

    public String getServerPort() {
        return this.datas[serverPort];
    }

    public String getUpLinkBytes() {
        return this.datas[upLinkBytes];
    }

    public String getDownLinkBytes() {
        return this.datas[downLinkBytes];
    }

    public String getLac() {
        return this.datas[lac];
    }

    public String getCi() {
        return this.datas[ci];
    }

    public String getRefer() {
        return this.datas[refer];
    }

    public String getCookie() {
        return this.datas[cookie];
    }

    public String getSceneFlag() {
        return this.datas[sceneFlag];
    }

    public String getDealTime() {
        return this.datas[dealTime];
    }

    public String getNetType() {
        return this.datas[netType];
    }

    public String getProvice() {
        return this.datas[provice];
    }

}
