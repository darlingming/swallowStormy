package com.software.dm.swallow.stormy.hadoop.service.scene.bean.basic;

import java.io.Serializable;

/**
 * 
 * @author DearM
 * @since 19 fields
 */
public class ThemeResultEntity extends AbstractThirdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TABLE_FLAG = "THEME";
	private String n_id;
	private String phone_id;
	private String imei;
	private String serverip;
	private String serverport;
	private String all_domain;
	private String url;
	private String starttime;
	private String ua;
	private String lac;
	private String ci;
	private String bsid;
	private String region;
	private String totalbytes;
	private String type_id;
	private String type_name;
	private String type_level;
	private String bqz_id;

	public ThemeResultEntity() {
	}

	public ThemeResultEntity(String n_id, String phone_id, String imei, String serverip, String serverport, String all_domain, String url, String starttime, String ua, String lac, String ci, String bsid, String region, String totalbytes, String type_id, String type_name, String type_level, String bqz_id, String netType) {
		super(netType);
		this.n_id = n_id;
		this.phone_id = phone_id;
		this.imei = imei;
		this.serverip = serverip;
		this.serverport = serverport;
		this.all_domain = all_domain;
		this.url = url;
		this.starttime = starttime;
		this.ua = ua;
		this.lac = lac;
		this.ci = ci;
		this.bsid = bsid;
		this.region = region;
		this.totalbytes = totalbytes;
		this.type_id = type_id;
		this.type_name = type_name;
		this.type_level = type_level;
		this.bqz_id = bqz_id;
	}

	public ThemeResultEntity(String n_id, String phone_id, String imei, String serverip, String serverport, String all_domain, String url, String starttime, String ua, String lac, String ci, String bsid, String region, String totalbytes, String type_id, String type_name, String type_level, String bqz_id, String netType, String prov) {
		super(netType, prov);
		this.n_id = n_id;
		this.phone_id = phone_id;
		this.imei = imei;
		this.serverip = serverip;
		this.serverport = serverport;
		this.all_domain = all_domain;
		this.url = url;
		this.starttime = starttime;
		this.ua = ua;
		this.lac = lac;
		this.ci = ci;
		this.bsid = bsid;
		this.region = region;
		this.totalbytes = totalbytes;
		this.type_id = type_id;
		this.type_name = type_name;
		this.type_level = type_level;
		this.bqz_id = bqz_id;
	}

	public void initData(String... args) {
		this.n_id = args[0];
		this.phone_id = args[1];
		this.imei = args[2];
		this.serverip = args[3];
		this.serverport = args[4];
		this.all_domain = args[5];
		this.url = args[6];
		this.starttime = args[7];
		this.ua = args[8];
		this.lac = args[9];
		this.ci = args[10];
		this.bsid = args[11];
		this.region = args[12];
		this.totalbytes = args[13];
		this.type_id = args[14];
		this.type_name = args[15];
		this.type_level = args[16];
		this.bqz_id = args[17];
		this.setNetType(args[18]);
		this.setProv(args[19]);
	}

	public String getN_id() {
		return n_id;
	}

	public void setN_id(String n_id) {
		this.n_id = n_id;
	}

	public String getPhone_id() {
		return phone_id;
	}

	public void setPhone_id(String phone_id) {
		this.phone_id = phone_id;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
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

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
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

	public String getBsid() {
		return bsid;
	}

	public void setBsid(String bsid) {
		this.bsid = bsid;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getTotalbytes() {
		return totalbytes;
	}

	public void setTotalbytes(String totalbytes) {
		this.totalbytes = totalbytes;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getType_level() {
		return type_level;
	}

	public void setType_level(String type_level) {
		this.type_level = type_level;
	}

	public String getBqz_id() {
		return bqz_id;
	}

	public void setBqz_id(String bqz_id) {
		this.bqz_id = bqz_id;
	}

}
