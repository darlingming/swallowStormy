package com.software.dm.swallow.stormy.algoac;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.software.dm.swallow.stormy.algoac.impl.VagueStateCharacter;
import com.software.dm.swallow.stormy.algoac.inter.VagueState;
import com.software.dm.swallow.stormy.algoac.pojo.VagueResultEntity;

/**
 * @author DM
 * @version v1.0.0.1
 * @Description
 * @date 2017
 * @preserve public
 */

public final class VagueAnalysisFactroy {

    /**
     *
     */
    public VagueAnalysisFactroy() {
        // TODO Auto-generated constructor stub
    }

    /**
     *
     */
    private final VagueState<Character> vagueState = new VagueStateCharacter(new HashMap<Character, VagueState<Character>>(), new HashMap<Character, VagueState<Character>>());

    /**
     * @param pattern
     * @param obj
     */
    public void addData(final String pattern, final Object obj) {
        if (null == pattern || pattern.isEmpty())
            return;
        boolean bool = pattern.charAt(pattern.length() - 1) != VagueState.STAR_CHAR;
        VagueResultEntity vagueResultEntity = new VagueResultEntity(obj, bool);
        this.vagueState.addState(pattern.toLowerCase(), vagueResultEntity);
    }

    /**
     * HashSet
     */
    private final Set<Object> resultDataSet = new HashSet<Object>();

    @SuppressWarnings("unused")
    private void print(VagueState<Character> vagueState) {
        if (vagueState.getVagueResultEntityList() != null)
            System.out.println(Arrays.toString(vagueState.getVagueResultEntityList().toArray()));

        Map<Character, VagueState<Character>> vaguestateMap = vagueState.getNextNormalStateMap();
        if (null != vaguestateMap) {
            for (Map.Entry<Character, VagueState<Character>> iterable_element : vaguestateMap.entrySet()) {
                print(iterable_element.getValue());
            }
        }
        vaguestateMap = vagueState.getNextVagueStateMap();
        if (null != vaguestateMap) {
            for (Map.Entry<Character, VagueState<Character>> iterable_element : vaguestateMap.entrySet()) {
                print(iterable_element.getValue());
            }
        }
    }

    /**
     * @param value
     * @return Set<Object>
     * @preserve
     */
    public Set<Object> serachResult(String value) {
        resultDataSet.clear();
        // this.print(vagueState);
        this.vagueState.serachVagueResult(value, resultDataSet);
        return resultDataSet;
    }
}
