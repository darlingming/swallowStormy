package com.software.dm.swallow.stormy.algoac.impl;

import com.software.dm.swallow.stormy.algoac.inter.AbstractState;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description
 * @date 2016
 */
public class CharacterState extends AbstractState<Character> {

    public CharacterState() {
        super();
    }

    public CharacterState(int depth) {
        super(depth);
    }


    public AbstractState<Character> addState(Character c) {
        AbstractState<Character> abstractState = this.nextStateIgnoreRootState(c);
        if (abstractState == null) {
            abstractState = new CharacterState(this.depth + 1);
            this.success.put(c, abstractState);
        }
        return abstractState;
    }


}
