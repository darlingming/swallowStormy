package com.software.dm.swallow.stormy.hadoop.service.scene.bean.analysisBean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @author DearM
 * 
 */
public abstract class AbstractDtoEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String[] datas = null;

	public String[] getDatas() {
		return datas;
	}

	public void initData(String[] datas) {
		this.datas = datas;
	}

	public AbstractDtoEntity(String[] datas) {
		this.datas = datas;
	}

	public String getActionEntity() {
		return this.datas[0];
	}

	@Override
	public String toString() {
		return "AbstractDtoEntity [datas=" + Arrays.toString(datas) + "]";
	}

}
