package com.software.dm.swallow.stormy.algoac.pojo;

/**
 * 
 * @Description
 * @author DM
 * @date 2017
 * @version v1.0.0.1
 * 
 */
public final class VagueResultEntity {

	/**
	 * 
	 * @param obj
	 * @param mustEqual
	 */
	public VagueResultEntity(Object obj, boolean mustEqual) {
		this.obj = obj;
		this.mustEqual = mustEqual;
	}

	// obj
	private Object obj;
	// string is endding
	private boolean mustEqual = false;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isMustEqual() {
		return mustEqual;
	}

	public void setMustEqual(boolean mustEqual) {
		this.mustEqual = mustEqual;
	}

	@Override
	public String toString() {
		return "VagueResultEntity [obj=" + obj + ", mustEqual=" + mustEqual + "]";
	}

}
