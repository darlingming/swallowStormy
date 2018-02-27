package com.software.dm.swallow.stormy.hadoop.service.scene.bean.basic;

import java.io.Serializable;

/**
 * 
 * @Description 30 fields
 * @author DearM
 * @date 2016��2��19��
 * @version v1.0.0.1
 * 
 */
public class BasisResultEntity extends AbstractThirdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String n_id;// 0
	private String is_app_url;// 1
	private String bqz_id;// 2
	private String brand;// 3
	private String server_name;// 4
	private String uuid;// 5
	private String phone_id;// 6
	private String one_domain;// 7
	private String all_domain;// 8
	private String url;// 9
	private String serverip;// 10
	private String serverport;// 11
	private String clienip;// 12
	private String clienport;// 13
	private String imsi;// 14
	private String imei;// 15
	private String ua;// 16
	private String lac;// 17
	private String ci;// 18
	private String starttime;// 19
	private String endtime;// 20
	private String uplinkbytes;// 21
	private String downlinkbytes;// 22
	private String sword;// 23
	private String dealflag;// 24
	private String dealtime;// 25
	private String type_id;// 26
	private String type_name;// 27
	private String bqz_name;// 28

	public BasisResultEntity() {
	}

	public BasisResultEntity(String n_id, String is_app_url, String bqz_id, String brand, String server_name, String uuid, String phone_id, String one_domain, String all_domain, String url, String serverip, String serverport, String clienip, String clienport, String imsi, String imei, String ua, String lac, String ci, String starttime, String endtime, String uplinkbytes, String downlinkbytes, String sword, String dealflag, String dealtime, String type_id, String type_name, String bqz_name, String netType) {
		super(netType);
		this.n_id = n_id;
		this.is_app_url = is_app_url;
		this.bqz_id = bqz_id;
		this.brand = brand;
		this.server_name = server_name;
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
		this.type_id = type_id;
		this.type_name = type_name;
		this.bqz_name = bqz_name;
	}

	public BasisResultEntity(String n_id, String is_app_url, String bqz_id, String brand, String server_name, String uuid, String phone_id, String one_domain, String all_domain, String url, String serverip, String serverport, String clienip, String clienport, String imsi, String imei, String ua, String lac, String ci, String starttime, String endtime, String uplinkbytes, String downlinkbytes, String sword, String dealflag, String dealtime, String type_id, String type_name, String bqz_name, String netType, String prov) {
		super(netType, prov);
		this.n_id = n_id;
		this.is_app_url = is_app_url;
		this.bqz_id = bqz_id;
		this.brand = brand;
		this.server_name = server_name;
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
		this.type_id = type_id;
		this.type_name = type_name;
		this.bqz_name = bqz_name;
	}

	public String getN_id() {
		return n_id;
	}

	public void setN_id(String n_id) {
		this.n_id = n_id;
	}

	public String getIs_app_url() {
		return is_app_url;
	}

	public void setIs_app_url(String is_app_url) {
		this.is_app_url = is_app_url;
	}

	public String getBqz_id() {
		return bqz_id;
	}

	public void setBqz_id(String bqz_id) {
		this.bqz_id = bqz_id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getServer_name() {
		return server_name;
	}

	public void setServer_name(String server_name) {
		this.server_name = server_name;
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

	public String getBqz_name() {
		return bqz_name;
	}

	public void setBqz_name(String bqz_name) {
		this.bqz_name = bqz_name;
	}

	public void initData(String... args) {
		this.n_id = args[0];
		this.is_app_url = args[1];
		this.bqz_id = args[2];
		this.brand = args[3];
		this.server_name = args[4];
		this.uuid = args[5];
		this.phone_id = args[6];
		this.one_domain = args[7];
		this.all_domain = args[8];
		this.url = args[9];
		this.serverip = args[10];
		this.serverport = args[11];
		this.clienip = args[12];
		this.clienport = args[13];
		this.imsi = args[14];
		this.imei = args[15];
		this.ua = args[16];
		this.lac = args[17];
		this.ci = args[18];
		this.starttime = args[19];
		this.endtime = args[20];
		this.uplinkbytes = args[21];
		this.downlinkbytes = args[22];
		this.sword = args[23];
		this.dealflag = args[24];
		this.dealtime = args[25];
		this.type_id = args[26];
		this.type_name = args[27];
		this.bqz_name = args[28];
		this.setNetType(args[29]);
		this.setProv(args[30]);

	}

}
