package com.software.dm.swallow.stormy.algoac.impl;

import com.software.dm.swallow.stormy.algoac.inter.AbstractAhoCorasick;
import com.software.dm.swallow.stormy.algoac.inter.AbstractState;
import com.software.dm.swallow.stormy.algoac.pojo.ResultSetEntity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description
 * @date 2016
 */
public class AhoCorasickCharacterTree extends AbstractAhoCorasick<Character> {

    public AhoCorasickCharacterTree() {
        super(new CharacterState());
    }

    /**
     *
     */
    public Collection<Object> search(Object keys) {
        if (!this.prepared) {
            throw new IllegalStateException("can't start search until prepare()");
        }
        Set<Object> set = new HashSet<Object>();
        this.search(keys, set);
        return set;
    }

    /**
     *
     */
    public AhoCorasickCharacterTree addKeyWord(Object keyword) {
        AbstractState<Character> tmpState = this.rootState;
        if (keyword instanceof String) {
            for (Character c : keyword.toString().toCharArray()) {
                tmpState = tmpState.addState(c);
            }
            tmpState.addOutput(keyword);
        } else if (keyword instanceof ResultSetEntity) {
            ResultSetEntity reg = (ResultSetEntity) keyword;
            for (Character c : reg.getKeyWord().toCharArray()) {
                if (c >= 65 && c <= 90) {
                    c = (char) (32 + c);
                }
                tmpState = tmpState.addState(c);
            }
            tmpState.addOutput(reg);
        }

        return this;
    }

    /**
     *
     */
    public void search(Object keys, Set<Object> set) {
        if (!this.prepared) {
            throw new IllegalStateException("can't start search until prepare()");
        }
        AbstractState<Character> currentState = this.rootState;
        for (Character c : keys.toString().toCharArray()) {
            if (c >= 65 && c <= 90) {
                c = (char) (32 + c);
            }
            currentState = this.getState(c, currentState);
            if (null != currentState.getOutput()) {
                set.addAll(currentState.getOutput());
            }
        }
    }

}
