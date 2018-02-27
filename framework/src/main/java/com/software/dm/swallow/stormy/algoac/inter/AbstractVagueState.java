package com.software.dm.swallow.stormy.algoac.inter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.software.dm.swallow.stormy.algoac.pojo.VagueResultEntity;

/**
 * 
 * @Description
 * @author DM (darlingming@126.com)
 * @date 2017
 * @version v1.0.0.1
 * @param <T>
 * 
 */
public abstract class AbstractVagueState<T> implements VagueState<T> {

	/**
	 * 
	 * @param value
	 */
	public AbstractVagueState(char value) {
		this.value = value;
	}

	/**
	 * 
	 * @param nextNormalStateMap
	 * @param nextVagueStateMap
	 */
	public AbstractVagueState(Map<T, VagueState<T>> nextNormalStateMap, Map<T, VagueState<T>> nextVagueStateMap) {
		this.nextNormalStateMap = nextNormalStateMap;
		this.nextVagueStateMap = nextVagueStateMap;
	}

	/**
	 * 
	 */
	private List<VagueResultEntity> vagueResultEntityList = null;

	/**
	 * 
	 */
	public List<VagueResultEntity> getVagueResultEntityList() {
		return vagueResultEntityList;
	}

	/**
	 * 
	 */
	public void addVagueResultEntityList(VagueResultEntity vagueResultEntityList) {
		if (this.vagueResultEntityList == null) {
			this.vagueResultEntityList = new ArrayList<VagueResultEntity>();
		}
		this.vagueResultEntityList.add(vagueResultEntityList);
	}

	// 状态的值
	protected char value;
	// 当前状态的状态
	protected Map<T, VagueState<T>> nextNormalStateMap;
	// 当前模糊状态
	protected Map<T, VagueState<T>> nextVagueStateMap;

	/**
	 * 
	 */
	public Map<T, VagueState<T>> getNextNormalStateMap() {
		return nextNormalStateMap;
	}

	/**
	 * 
	 */
	public void addNextNormalStateMap(T t, VagueState<T> nextNormalState) {
		if (null == this.nextNormalStateMap) {
			this.nextNormalStateMap = new HashMap<T, VagueState<T>>();
		}
		this.nextNormalStateMap.put(t, nextNormalState);
	}

	/**
	 * 
	 */
	public Map<T, VagueState<T>> getNextVagueStateMap() {
		return nextVagueStateMap;
	}

	/**
	 * 
	 */
	public void addNextVagueStateMap(T t, VagueState<T> nextVagueState) {
		if (null == this.nextVagueStateMap) {
			this.nextVagueStateMap = new HashMap<T, VagueState<T>>();
		}
		this.nextVagueStateMap.put(t, nextVagueState);
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public abstract VagueState<T> getInstance(char value);

	/**
	 * 
	 * @return value
	 */
	public char getValue() {
		return this.value;
	}

}
