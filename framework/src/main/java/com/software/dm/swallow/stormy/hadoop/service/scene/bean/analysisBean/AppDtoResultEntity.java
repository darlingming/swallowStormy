package com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean;

import java.io.Serializable;

/**
 * @author DM
 * @version v1.0.0.1
 * @Description
 * @date 2017
 * @since 21 fields
 */
public class AppDtoResultEntity extends AbstractDtoEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final int FIELDS_COUNT = 21;

    public AppDtoResultEntity(String[] datas) {
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
    private final int n_id = 10;
    private final int bqzId = 11;
    private final int bqzName = 12;
    private final int dataFlag = 13;
    private final int cookieId = 14;
    private final int cookieResult = 15;
    private final int cookie = 16;
    private final int isRefer = 17;
    private final int dealTime = 18;
    private final int netType = 19;
    private final int provice = 20;

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

    public String getN_id() {
        return this.datas[n_id];
    }

    public String getBqzId() {
        return this.datas[bqzId];
    }

    public String getBqzName() {
        return this.datas[bqzName];
    }

    public String getDataFlag() {
        return this.datas[dataFlag];
    }

    public String getCookieId() {
        return this.datas[cookieId];
    }

    public String getCookieResult() {
        return this.datas[cookieResult];
    }

    public String getCookie() {
        return this.datas[cookie];
    }

    public String getIsRefer() {
        return this.datas[isRefer];
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

    public int getFieldsCount() {
        return FIELDS_COUNT;
    }
}
