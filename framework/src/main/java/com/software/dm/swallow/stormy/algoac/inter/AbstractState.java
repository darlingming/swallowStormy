package com.software.dm.swallow.stormy.algoac.inter;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @param <T>
 * @author DM
 */
public abstract class AbstractState<T> implements State<T, AbstractState<T>> {

    protected final int depth;
    protected final AbstractState<T> rootState;
    private AbstractState<T> failure = null;
    private Set<Object> output = null;
    protected Map<T, AbstractState<T>> success = new HashMap<T, AbstractState<T>>();
    protected boolean prepared = false;

    public AbstractState() {
        this(0);
    }

    public AbstractState(int depth) {
        this.depth = depth;
        rootState = this.depth == 0 ? this : null;
    }


    public AbstractState<T> nextState(T c) {
        return this.nextState(c, false);
    }


    public AbstractState<T> nextStateIgnoreRootState(T c) {
        return this.nextState(c, true);
    }

    public AbstractState<T> nextState(T c, boolean ignoreroot) {
        AbstractState<T> currentState = this.success.get(c);
        if (!ignoreroot && currentState == null && this.rootState != null) {
            return this.rootState;
        }
        return currentState;
    }


    public Collection<T> getValues() {
        return this.success.keySet();
    }


    public Collection<AbstractState<T>> getStates() {
        return this.success.values();
    }

    public void addOutput(Object output) {
        if (this.output == null)
            this.output = new HashSet<Object>();
        this.output.add(output);
    }

    public void addAllOutput(Set<Object> output) {
        if (output == null || output.isEmpty())
            return;
        if (this.output == null)
//			return;
            this.output = new HashSet<Object>();
        this.output.addAll(output);
    }

    public Set<Object> getOutput() {
//		return this.output == null ? new HashSet<Object>() : this.output;
        return this.output;
    }

    public AbstractState<T> getFailure() {
        return failure;
    }

    public void setFailure(AbstractState<T> failure) {
        this.failure = failure;
    }

    public int getDepth() {
        return depth;
    }

}
