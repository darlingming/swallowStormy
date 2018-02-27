package com.software.dm.swallow.stormy.hadoop.common;

import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import com.software.dm.swallow.stormy.hadoop.tools.Constant;

/**
 * 
 * @author DearM
 * 
 */
@SuppressWarnings("rawtypes")
public class MaperdTools {

	private org.apache.hadoop.mapreduce.Mapper.Context mapContext;
	private org.apache.hadoop.mapreduce.Reducer.Context reducerContext;
	private MultipleOutputs<?, ?> mos;
	private String split_fields = Constant.PUB_FIELD_STR;

	public MaperdTools(org.apache.hadoop.mapreduce.Mapper.Context mapContext, MultipleOutputs<?, ?> mos, String split_fields) {
		this.mapContext = mapContext;
		this.mos = mos;
		this.split_fields = split_fields;
	}

	public MaperdTools(org.apache.hadoop.mapreduce.Mapper.Context mapContext, MultipleOutputs<?, ?> mos) {
		this.mapContext = mapContext;
		this.mos = mos;
	}

	public MaperdTools(org.apache.hadoop.mapreduce.Mapper.Context mapContext) {
		this.mapContext = mapContext;

	}

	public MaperdTools(org.apache.hadoop.mapreduce.Mapper.Context mapContext, String split_fields) {
		this.mapContext = mapContext;
		this.split_fields = split_fields;
	}

	public MaperdTools(org.apache.hadoop.mapreduce.Reducer.Context reducerContext, MultipleOutputs<?, ?> mos, String split_fields) {
		this.reducerContext = reducerContext;
		this.mos = mos;
		this.split_fields = split_fields;
	}

	public MaperdTools(org.apache.hadoop.mapreduce.Reducer.Context reducerContext, MultipleOutputs<?, ?> mos) {
		this.reducerContext = reducerContext;
		this.mos = mos;
	}

	public MaperdTools(MultipleOutputs<?, ?> mos, String split_fields) {
		this.mos = mos;
		this.split_fields = split_fields;
	}

	public MaperdTools(org.apache.hadoop.mapreduce.Reducer.Context reducerContext, String split_fields) {
		this.reducerContext = reducerContext;
		this.split_fields = split_fields;
	}

	public MaperdTools(org.apache.hadoop.mapreduce.Reducer.Context reducerContext) {
		this.reducerContext = reducerContext;
	}

	public MultipleOutputs<?, ?> getMos() {
		return mos;
	}

	public void setMos(MultipleOutputs<?, ?> mos) {
		this.mos = mos;
	}

	public String getSplit_fields() {
		return split_fields;
	}

	public void setSplit_fields(String split_fields) {
		this.split_fields = split_fields;
	}

	public org.apache.hadoop.mapreduce.Mapper.Context getMapContext() {
		return mapContext;
	}

	public void setMapContext(org.apache.hadoop.mapreduce.Mapper.Context mapContext) {
		this.mapContext = mapContext;
	}

	public org.apache.hadoop.mapreduce.Reducer.Context getReducerContext() {
		return reducerContext;
	}

	public void setReducerContext(org.apache.hadoop.mapreduce.Reducer.Context reducerContext) {
		this.reducerContext = reducerContext;
	}

	/**
	 * 
	 * @param groupName
	 * @param counterName
	 */
	public void addCounterReducer(String groupName, final String counterName, final long i) {
		if (null == groupName || null == counterName)
			return;
		this.getReducerContext().getCounter(groupName, counterName).increment(i);
	}

	/**
	 * 
	 * @param groupName
	 * @param counterName
	 */
	public void addCounterMapper(final String groupName, final String counterName, final long i) {
		if (null == groupName || null == counterName)
			return;
		this.getMapContext().getCounter(groupName, counterName).increment(i);
	}
}
