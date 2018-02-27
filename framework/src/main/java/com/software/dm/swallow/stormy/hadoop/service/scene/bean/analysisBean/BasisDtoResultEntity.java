package com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean;

import java.io.Serializable;

/**
 * 
 * @Description
 * @author DM
 * @date 2017
 * @version v1.0.0.1
 * @since 28 fields
 */
public class BasisDtoResultEntity extends AbstractDtoEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FIELDS_COUNT = 28;

	// public static final String ACTION_ENTITY =
	// AbstractConstants.BDIA_BAISCCODE;

	public BasisDtoResultEntity(String[] datas) {
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
	private final int n_id = 16;
	private final int bqzId = 17;
	private final int typeId = 18;// 26
	private final int brand = 19;// 3
	private final int serverName = 20;// 4
	private final int cookieId = 21;
	private final int cookieResult = 22;
	private final int cookie = 23;
	private final int isRefer = 24;
	private final int dealTime = 25;
	private final int netType = 26;
	private final int provice = 27;

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

	public String getN_id() {
		return this.datas[n_id];
	}

	public String getBqzId() {
		return this.datas[bqzId];
	}

	public String getTypeId() {
		return this.datas[typeId];
	}

	public String getBrand() {
		return this.datas[brand];
	}

	public String getServerName() {
		return this.datas[serverName];
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
