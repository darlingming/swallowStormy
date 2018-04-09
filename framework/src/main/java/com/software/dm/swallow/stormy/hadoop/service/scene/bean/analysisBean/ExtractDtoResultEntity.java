package com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean;

import java.io.Serializable;

/**
 * @author DearM
 * @since 26 fields
 */
public class ExtractDtoResultEntity extends AbstractDtoEntity implements Serializable {

    public ExtractDtoResultEntity(String[] datas) {
        super(datas);
        // TODO Auto-generated constructor stub
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final int FIELDS_COUNT = 26;

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
    private final int ruleId = 10;
    private final int subjectId = 11;
    private final int platformId = 12;
    private final int actionId = 13;
    private final int contentType = 14;
    private final int result = 15;
    private final int lac = 16;
    private final int ci = 17;
    private final int crawlerFlag = 18;
    private final int cookieId = 19;
    private final int cookieResult = 20;
    private final int cookie = 21;
    private final int isRefer = 22;
    private final int dealTime = 23;
    private final int netType = 24;
    private final int provice = 25;

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

    public String getRuleId() {
        return this.datas[ruleId];
    }

    public String getSubjectId() {
        return this.datas[subjectId];
    }

    public String getPlatformId() {
        return this.datas[platformId];
    }

    public String getActionId() {
        return this.datas[actionId];
    }

    public String getContentType() {
        return this.datas[contentType];
    }

    public String getResult() {
        return this.datas[result];
    }

    public String getLac() {
        return this.datas[lac];
    }

    public String getCi() {
        return this.datas[ci];
    }

    public String getCrawlerFlag() {
        return this.datas[crawlerFlag];
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

}
