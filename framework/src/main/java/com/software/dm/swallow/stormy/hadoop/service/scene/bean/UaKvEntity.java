package com.software.dm.swallow.stormy.hadoop.service.scene.bean;

import java.io.Serializable;

/**
 * 
 * @author DearM
 * 
 */
public class UaKvEntity extends AbstractSceneEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String terminal_id = "";
	private String ua = "";
	private String visit_date = "";
	private String pv = "";

	public UaKvEntity(String id, String terminal_id, String ua, String visit_date, String pv, String operTime, String prov, String netType) {
		super(id, operTime, prov, netType);
		this.terminal_id = terminal_id;
		this.ua = ua;
		this.visit_date = visit_date;
		this.pv = pv;
	}

	public UaKvEntity() {
		super();
	}

	public UaKvEntity(String id, String operTime, String prov, String netType) {
		super(id, operTime, prov, netType);
	}

	public String getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}

	public String getUa() {
		return ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	public String getVisit_date() {
		return visit_date;
	}

	public void setVisit_date(String visit_date) {
		this.visit_date = visit_date;
	}

	public String getPv() {
		return pv;
	}

	public void setPv(String pv) {
		this.pv = pv;
	}

}
