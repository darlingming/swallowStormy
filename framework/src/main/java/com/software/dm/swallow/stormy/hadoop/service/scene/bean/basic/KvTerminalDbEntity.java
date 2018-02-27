package com.software.dm.swallow.stormy.hadoop.service.scene.bean.basic;

import java.io.Serializable;

/**
 * 
 * @author DearM
 *
 */
public class KvTerminalDbEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ua_analyze;//0
	private String terminal_type;//1
	private String terminal_name;
	private String terminal_brand;//3
	private String system;//4
	private String version;
	private String screen_size;
	private String ram;
	private String cpu;
	private String pigment;
	private String gps_support;
	private String nfc_support;
	private String update_time;
	private String remark;

	public KvTerminalDbEntity() {
		// TODO Auto-generated constructor stub
	}

	public String getUa_analyze() {
		return ua_analyze;
	}

	public void setUa_analyze(String ua_analyze) {
		this.ua_analyze = ua_analyze;
	}

	public String getTerminal_type() {
		return terminal_type;
	}

	public void setTerminal_type(String terminal_type) {
		this.terminal_type = terminal_type;
	}

	public String getTerminal_name() {
		return terminal_name;
	}

	public void setTerminal_name(String terminal_name) {
		this.terminal_name = terminal_name;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getScreen_size() {
		return screen_size;
	}

	public void setScreen_size(String screen_size) {
		this.screen_size = screen_size;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getPigment() {
		return pigment;
	}

	public void setPigment(String pigment) {
		this.pigment = pigment;
	}

	public String getGps_support() {
		return gps_support;
	}

	public void setGps_support(String gps_support) {
		this.gps_support = gps_support;
	}

	public String getNfc_support() {
		return nfc_support;
	}

	public void setNfc_support(String nfc_support) {
		this.nfc_support = nfc_support;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
