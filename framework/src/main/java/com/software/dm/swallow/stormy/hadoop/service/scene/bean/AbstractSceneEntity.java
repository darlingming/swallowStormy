package com.software.dm.swallow.stormy.hadoop.service.scene.bean;

import java.io.Serializable;

/**
 * 
 * @author DearM
 * 
 */
public abstract class AbstractSceneEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id = "";
	private String operTime = "";
	private String prov = "";
	private String netType = "";

	public AbstractSceneEntity(String id, String operTime, String prov, String netType) {
		super();
		this.id = id;
		this.operTime = operTime;
		this.prov = prov;
		this.netType = netType;
	}

	public AbstractSceneEntity() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getOperTime() {
		return operTime;
	}

	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

}
