package com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean;

import java.io.Serializable;

/**
 * 
 * @author DearM
 * @since 19 fields
 * 
 */
public class ClearIpDtoResultEntity extends AbstractDtoEntity implements Serializable {

	public ClearIpDtoResultEntity(String[] datas) {
		super(datas);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FIELDS_COUNT = 19;

	private final int actionCode = 0;
	private final int uuid = 1;
	private final int phoneId = 2;
	private final int imei = 3;
	private final int imsi = 4;
	private final int account = 5;
	private final int ua = 6;
	private final int startTime = 7;
	private final int clienIp = 8;
	private final int clienPort = 9;// 13
	private final int serverIp = 10;
	private final int serverPort = 11;
	private final int upLinkBytes = 12;// 21
	private final int downLinkBytes = 13;// 22
	private final int lac = 14;
	private final int ci = 15;
	private final int dealTime = 16;
	private final int netType = 17;
	private final int provice = 18;

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
