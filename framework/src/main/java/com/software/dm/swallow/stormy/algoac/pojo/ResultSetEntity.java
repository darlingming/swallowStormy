package com.software.dm.swallow.stormy.algoac.pojo;

import java.util.Arrays;

/**
 * 
 * @Description
 * @author DM
 * @date 2017
 * @version v1.0.0.1
 * 
 */
public final class ResultSetEntity {

	private int regnum = 1;
	private String keyWord;
	private String[] values;
	private int type;
	private Object source;

	public ResultSetEntity(int regnum, String keyWord, String[] values, int type, Object source) {

		this.regnum = regnum;
		this.keyWord = keyWord;
		this.values = values;
		this.type = type;
		this.source = source;
	}

	public Object getSource() {
		return source;
	}

	public int getRegnum() {
		return regnum;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public String[] getValues() {
		return values;
	}

	public int getType() {
		return type;
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + regnum;
//		result = prime * result + type;
//		result = prime * result + Arrays.hashCode(values);
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		ResultSetEntity other = (ResultSetEntity) obj;
//		if (regnum != other.regnum)
//			return false;
//		if (type != other.type)
//			return false;
//		if (!Arrays.equals(values, other.values))
//			return false;
//		return true;
//	}

	@Override
	public String toString() {
		return "ResultSetEntity [regnum=" + regnum + ", keyWord=" + keyWord + ", values=" + Arrays.toString(values) + ", type=" + type + "]";
	}

}
