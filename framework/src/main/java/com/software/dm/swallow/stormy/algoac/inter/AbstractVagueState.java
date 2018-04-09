package com.software.dm.swallow.stormy.algoac.inter;

import com.software.dm.swallow.stormy.algoac.pojo.VagueResultEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <T>
 * @author DM (darlingming@126.com)
 * @version v1.0.0.1
 * @Description
 * @date 2017
 */
public abstract class AbstractVagueState<T> implements VagueState<T> {

    /**
     * @param value
     */
    public AbstractVagueState(char value) {
        this.value = value;
    }

    /**
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

    // ?????
    protected char value;
    // ?????????
    protected Map<T, VagueState<T>> nextNormalStateMap;
    // ????????
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
     * @param value
     * @return
     */
    public abstract VagueState<T> getInstance(char value);

    /**
     * @return value
     */
    public char getValue() {
        return this.value;
    }

}
