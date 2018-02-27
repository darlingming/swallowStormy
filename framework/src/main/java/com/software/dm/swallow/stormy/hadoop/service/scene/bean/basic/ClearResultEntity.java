package com.software.dm.swallow.stormy.hadoop.service.scene.bean.basic;

import java.io.Serializable;

/**
 * 
 * @author DearM
 * @since 22 fields
 * 
 */
public class ClearResultEntity extends AbstractThirdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String phone_id;
	private String one_domain;
	private String all_domain;
	private String url;
	private String serverip;
	private String serverport;
	private String clienip;
	private String clienport;
	private String imsi;
	private String imei;
	private String ua;
	private String lac;
	private String ci;
	private String starttime;
	private String endtime;
	private String uplinkbytes;
	private String downlinkbytes;
	private String sword;
	private String dealflag;
	private String dealtime;

	public ClearResultEntity() {
	}

	public ClearResultEntity(String uuid, String phone_id, String one_domain, String all_domain, String url, String serverip, String serverport, String clienip, String clienport, String imsi, String imei, String ua, String lac, String ci, String starttime, String endtime, String uplinkbytes, String downlinkbytes, String sword, String dealflag, String dealtime, String netType) {
		super(netType);
		this.uuid = uuid;
		this.phone_id = phone_id;
		this.one_domain = one_domain;
		this.all_domain = all_domain;
		this.url = url;
		this.serverip = serverip;
		this.serverport = serverport;
		this.clienip = clienip;
		this.clienport = clienport;
		this.imsi = imsi;
		this.imei = imei;
		this.ua = ua;
		this.lac = lac;
		this.ci = ci;
		this.starttime = starttime;
		this.endtime = endtime;
		this.uplinkbytes = uplinkbytes;
		this.downlinkbytes = downlinkbytes;
		this.sword = sword;
		this.dealflag = dealflag;
		this.dealtime = dealtime;
	}

	public ClearResultEntity(String uuid, String phone_id, String one_domain, String all_domain, String url, String serverip, String serverport, String clienip, String clienport, String imsi, String imei, String ua, String lac, String ci, String starttime, String endtime, String uplinkbytes, String downlinkbytes, String sword, String dealflag, String dealtime, String netType, String prov) {
		super(netType, prov);
		this.uuid = uuid;
		this.phone_id = phone_id;
		this.one_domain = one_domain;
		this.all_domain = all_domain;
		this.url = url;
		this.serverip = serverip;
		this.serverport = serverport;
		this.clienip = clienip;
		this.clienport = clienport;
		this.imsi = imsi;
		this.imei = imei;
		this.ua = ua;
		this.lac = lac;
		this.ci = ci;
		this.starttime = starttime;
		this.endtime = endtime;
		this.uplinkbytes = uplinkbytes;
		this.downlinkbytes = downlinkbytes;
		this.sword = sword;
		this.dealflag = dealflag;
		this.dealtime = dealtime;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPhone_id() {
		return phone_id;
	}

	public void setPhone_id(String phone_id) {
		this.phone_id = phone_id;
	}

	public String getOne_domain() {
		return one_domain;
	}

	public void setOne_domain(String one_domain) {
		this.one_domain = one_domain;
	}

	public String getAll_domain() {
		return all_domain;
	}

	public void setAll_domain(String all_domain) {
		this.all_domain = all_domain;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getServerip() {
		return serverip;
	}

	public void setServerip(String serverip) {
		this.serverip = serverip;
	}

	public String getServerport() {
		return serverport;
	}

	public void setServerport(String serverport) {
		this.serverport = serverport;
	}

	public String getClienip() {
		return clienip;
	}

	public void setClienip(String clienip) {
		this.clienip = clienip;
	}

	public String getClienport() {
		return clienport;
	}

	public void setClienport(String clienport) {
		this.clienport = clienport;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getUa() {
		return ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	public String getLac() {
		return lac;
	}

	public void setLac(String lac) {
		this.lac = lac;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getUplinkbytes() {
		return uplinkbytes;
	}

	public void setUplinkbytes(String uplinkbytes) {
		this.uplinkbytes = uplinkbytes;
	}

	public String getDownlinkbytes() {
		return downlinkbytes;
	}

	public void setDownlinkbytes(String downlinkbytes) {
		this.downlinkbytes = downlinkbytes;
	}

	public String getSword() {
		return sword;
	}

	public void setSword(String sword) {
		this.sword = sword;
	}

	public String getDealflag() {
		return dealflag;
	}

	public void setDealflag(String dealflag) {
		this.dealflag = dealflag;
	}

	public String getDealtime() {
		return dealtime;
	}

	public void setDealtime(String dealtime) {
		this.dealtime = dealtime;
	}

	public void initData(String... args) {
		this.uuid = args[0];
		this.phone_id = args[1];
		this.one_domain = args[2];
		this.all_domain = args[3];
		this.url = args[4];
		this.serverip = args[5];
		this.serverport = args[6];
		this.clienip = args[7];
		this.clienport = args[8];
		this.imsi = args[9];
		this.imei = args[10];
		this.ua = args[11];
		this.lac = args[12];
		this.ci = args[13];
		this.starttime = args[14];
		this.endtime = args[15];
		this.uplinkbytes = args[16];
		this.downlinkbytes = args[17];
		this.sword = args[18];
		this.dealflag = args[19];
		this.dealtime = args[20];
		this.setNetType(args[21]);
		this.setProv(args[22]);
	}

}
