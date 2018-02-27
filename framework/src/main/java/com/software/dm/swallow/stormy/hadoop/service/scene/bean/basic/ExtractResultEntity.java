package com.software.dm.swallow.stormy.hadoop.service.scene.bean.basic;

import java.io.Serializable;

/**
 * 
 * @author DearM * @since 20 fields
 */
public class ExtractResultEntity extends AbstractThirdEntity implements Serializable {

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	private String rule_id;
	private String subject_id;
	private String client_id;
	private String productnum;
	private String action_id;
	private String type_id;
	private String result;
	private String uuid;
	private String phone_id;
	private String all_domain;
	private String url;
	private String imsi;
	private String imei;
	private String starttime;
	private String endtime;
	private String lac;
	private String ci;
	private String uplinkbytes;
	private String downlinkbytes;
	private String crawler_flag;

	public ExtractResultEntity() {
	}

	public ExtractResultEntity(String rule_id, String subject_id, String client_id, String productnum, String action_id, String type_id, String result, String uuid, String phone_id, String all_domain, String url, String imsi, String imei, String starttime, String endtime, String lac, String ci, String uplinkbytes, String downlinkbytes, String crawler_flag, String netType) {
		super(netType);
		this.rule_id = rule_id;
		this.subject_id = subject_id;
		this.client_id = client_id;
		this.productnum = productnum;
		this.action_id = action_id;
		this.type_id = type_id;
		this.result = result;
		this.uuid = uuid;
		this.phone_id = phone_id;
		this.all_domain = all_domain;
		this.url = url;
		this.imsi = imsi;
		this.imei = imei;
		this.starttime = starttime;
		this.endtime = endtime;
		this.lac = lac;
		this.ci = ci;
		this.uplinkbytes = uplinkbytes;
		this.downlinkbytes = downlinkbytes;
		this.crawler_flag = crawler_flag;
	}

	public ExtractResultEntity(String rule_id, String subject_id, String client_id, String productnum, String action_id, String type_id, String result, String uuid, String phone_id, String all_domain, String url, String imsi, String imei, String starttime, String endtime, String lac, String ci, String uplinkbytes, String downlinkbytes, String crawler_flag, String netType, String prov) {
		super(netType, prov);
		this.rule_id = rule_id;
		this.subject_id = subject_id;
		this.client_id = client_id;
		this.productnum = productnum;
		this.action_id = action_id;
		this.type_id = type_id;
		this.result = result;
		this.uuid = uuid;
		this.phone_id = phone_id;
		this.all_domain = all_domain;
		this.url = url;
		this.imsi = imsi;
		this.imei = imei;
		this.starttime = starttime;
		this.endtime = endtime;
		this.lac = lac;
		this.ci = ci;
		this.uplinkbytes = uplinkbytes;
		this.downlinkbytes = downlinkbytes;
		this.crawler_flag = crawler_flag;
	}

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

	public String getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getProductnum() {
		return productnum;
	}

	public void setProductnum(String productnum) {
		this.productnum = productnum;
	}

	public String getAction_id() {
		return action_id;
	}

	public void setAction_id(String action_id) {
		this.action_id = action_id;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public void initData(String... args) {
		this.rule_id = args[0];
		this.subject_id = args[1];
		this.client_id = args[2];
		this.productnum = args[3];
		this.action_id = args[4];
		this.type_id = args[5];
		this.result = args[6];
		this.uuid = args[7];
		this.phone_id = args[8];
		this.all_domain = args[9];
		this.url = args[10];
		this.imsi = args[11];
		this.imei = args[12];
		this.starttime = args[13];
		this.endtime = args[14];
		this.lac = args[15];
		this.ci = args[16];
		this.uplinkbytes = args[17];
		this.downlinkbytes = args[18];
		this.setCrawler_flag(args[19]);
		this.setNetType(args[20]);
		this.setProv(args[21]);
	}

	public String getCrawler_flag() {
		return crawler_flag;
	}

	public void setCrawler_flag(String crawler_flag) {
		this.crawler_flag = crawler_flag;
	}

}
