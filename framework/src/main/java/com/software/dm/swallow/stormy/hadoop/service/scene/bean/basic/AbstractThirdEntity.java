package com.software.dm.swallow.stormy.hadoop.service.scene.bean.basic;

import java.io.Serializable;

/**
 * 
 * @author DearM
 * 
 */
public abstract class AbstractThirdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String netType = "";
	private String prov = "";

	public abstract void initData(String... args);

	public AbstractThirdEntity(String netType) {
		this.netType = netType;
	}

	public AbstractThirdEntity(String netType, String prov) {
		this.netType = netType;
		this.prov = prov;
	}

	public AbstractThirdEntity() {
	}

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

}
