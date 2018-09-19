package com.software.dm.swallow.stormy.algoac.impl;

import com.software.dm.swallow.stormy.algoac.inter.AbstractVagueState;
import com.software.dm.swallow.stormy.algoac.inter.VagueState;
import com.software.dm.swallow.stormy.algoac.pojo.VagueResultEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author DM
 * @version v1.0.0.1
 * @Description
 * @date 2017
 *
 */
public class VagueStateCharacter extends AbstractVagueState<Character> implements Serializable {

    /**
     * @param nextNormalStateMap
     * @param nextVagueStateMap
     */
    public VagueStateCharacter(Map<Character, VagueState<Character>> nextNormalStateMap, Map<Character, VagueState<Character>> nextVagueStateMap) {
        super(nextNormalStateMap, nextVagueStateMap);
    }

    public VagueStateCharacter(char value) {
        super(value);
    }

    /**
     * @param pattern
     * @param vagueResultEntity
     */
    public void addState(final String pattern, final VagueResultEntity vagueResultEntity) {
        boolean isStar = false;
        VagueState<Character> currentVagueState = this;
        char[] values = pattern.toCharArray();
        for (int i = 0; i < values.length; i++) {
            if (values[i] == VagueState.STAR_CHAR) {
                if (isStar)
                    continue;
                isStar = true;
                continue;
            }
            VagueState<Character> newVagueState = null;
            if (isStar) {
                Map<Character, VagueState<Character>> vaguestateMap = currentVagueState.getNextVagueStateMap();

                if (null != vaguestateMap)
                    newVagueState = vaguestateMap.get(values[i]);
                if (null == vaguestateMap || null == newVagueState) {
                    newVagueState = this.getInstance(values[i]);
                    currentVagueState.addNextVagueStateMap(values[i], newVagueState);
                    currentVagueState = newVagueState;
                } else {
                    currentVagueState = newVagueState;
                }
                isStar = false;
            } else {
                //
                Map<Character, VagueState<Character>> normalStateMap = currentVagueState.getNextNormalStateMap();

                if (null != normalStateMap)
                    newVagueState = normalStateMap.get(values[i]);
                if (null == normalStateMap || null == newVagueState) {
                    newVagueState = this.getInstance(values[i]);
                    currentVagueState.addNextNormalStateMap(values[i], newVagueState);
                    currentVagueState = newVagueState;
                } else {
                    currentVagueState = newVagueState;
                }
            }
        }
        currentVagueState.addVagueResultEntityList(vagueResultEntity);
    }

    /**
     *
     */
    public void serachVagueResult(String patterns, Set<Object> resultDataSet) {
        if (null != patterns && !patterns.isEmpty()) {
            char[] c_patterns = patterns.toCharArray();
            for (int i = 0; i < c_patterns.length; i++) {
                if (c_patterns[i] >= 65 && c_patterns[i] <= 90) {
                    c_patterns[i] = (char) (32 + c_patterns[i]);
                }
            }
            this.serachResult(this, c_patterns, 0, resultDataSet, false);
        }

    }

    /**
     * @param serachVaguestate
     * @param c_patterns
     * @param poi
     * @param resultDataSet
     */
    private void serachResult(final VagueState<Character> serachVaguestate, final char[] c_patterns, final int poi, final Set<Object> resultDataSet, final boolean skipVague) {
        // try output
        if (!skipVague)
            this.tryOutputResult(serachVaguestate, c_patterns.length, poi, resultDataSet);
        if (poi >= c_patterns.length) {
            return;
        }
        VagueState<Character> vs = null;
        Map<Character, VagueState<Character>> normalStateMap = serachVaguestate.getNextNormalStateMap();
        if (null != normalStateMap && !skipVague) {
            vs = normalStateMap.get(c_patterns[poi]);
            if (null != vs) {
                this.serachResult(vs, c_patterns, poi + 1, resultDataSet, false);
            }
        }
        Map<Character, VagueState<Character>> vagueStateMap = serachVaguestate.getNextVagueStateMap();
        if (null != vagueStateMap) {
            vs = vagueStateMap.get(c_patterns[poi]);
            if (null != vs) {
                this.serachResult(vs, c_patterns, poi + 1, resultDataSet, false);
            }
            this.serachResult(serachVaguestate, c_patterns, poi + 1, resultDataSet, true);
        }

    }

    /**
     * @param vs
     * @param len
     * @param poi
     * @param resultDataSet
     */
    private void tryOutputResult(VagueState<Character> vs, int len, int poi, Set<Object> resultDataSet) {

        List<VagueResultEntity> vagueResultEntityList = vs.getVagueResultEntityList();
        if (null != vagueResultEntityList)
            for (VagueResultEntity vagueResultEntity : vagueResultEntityList) {
                if (vagueResultEntity.isMustEqual()) {
                    if (len == poi) {
                        resultDataSet.add(vagueResultEntity.getObj());
                    }
                } else {
                    resultDataSet.add(vagueResultEntity.getObj());
                }
                // System.out.println("vagueResultEntity===" +
                // vagueResultEntity);
            }
    }

    @Override
    public VagueState<Character> getInstance(char value) {
        return new VagueStateCharacter(value);
    }

}
