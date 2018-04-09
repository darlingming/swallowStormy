package com.software.dm.swallow.stormy.algoac.inter;

import java.util.Collection;

/**
 * @param <T>
 * @param <D>
 * @author DM
 */
public interface State<T, D> {

    public D nextState(T c);

    public D addState(T c);

    public D nextStateIgnoreRootState(T c);

    public Collection<T> getValues();

    public Collection<D> getStates();

}
