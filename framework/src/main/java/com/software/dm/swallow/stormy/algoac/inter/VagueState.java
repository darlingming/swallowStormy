package com.software.dm.swallow.stormy.algoac.inter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.software.dm.swallow.stormy.algoac.pojo.VagueResultEntity;

/**
 * 
 * @Description
 * @author DM
 * @date 2017
 * @version v1.0.0.1
 * @param <T>
 * 
 */
public interface VagueState<T> {

	/**
	 * 
	 */
	public static final char STAR_CHAR = '*';

	/**
	 * 
	 * @param t
	 * @param nextNormalState
	 */
	public void addNextNormalStateMap(T t, VagueState<T> nextNormalState);

	/**
	 * 
	 * @param t
	 * @param nextVagueState
	 */
	public void addNextVagueStateMap(T t, VagueState<T> nextVagueState);

	/**
	 * 
	 * @return
	 */
	public Map<T, VagueState<T>> getNextVagueStateMap();

	/**
	 * 
	 * @return
	 */
	public Map<T, VagueState<T>> getNextNormalStateMap();

	/**
	 * 
	 * @param vagueResultEntityList
	 */
	public void addVagueResultEntityList(VagueResultEntity vagueResultEntityList);

	/**
	 * 
	 * @return
	 */
	public List<VagueResultEntity> getVagueResultEntityList();

	/**
	 * 
	 */
	public Map<String, List<Object>> patternMap = new HashMap<String, List<Object>>();

	/**
	 * 
	 * @param pattern
	 * @param vagueResultEntity
	 */
	public void addState(final String pattern, final VagueResultEntity vagueResultEntity);

	/**
	 * 
	 * @param patterns
	 * @param resultDataSet
	 */
	public void serachVagueResult(final String patterns, final Set<Object> resultDataSet);

}
