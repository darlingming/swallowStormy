package com.software.dm.swallow.stormy.algoac.inter;

import java.util.Collection;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @param <T>
 * @author DearM
 */
public abstract class AbstractAhoCorasick<T> {
    protected boolean prepared = false;
    protected final AbstractState<T> rootState;

    public AbstractAhoCorasick(AbstractState<T> rootState) {
        this.rootState = rootState;
    }

    public void prepare() {
        if (!this.prepared) {
            this.prepareFailTransitions();
        }

    }

    /**
     * DANGER DANGER: dense algorithm code ahead. Very order dependent.
     * Initializes the fail transitions of all states except for the root.
     */
    private void prepareFailTransitions() {
        Queue<AbstractState<T>> queue = new LinkedBlockingQueue<AbstractState<T>>();
        for (AbstractState<T> abstractState : this.rootState.getStates()) {
            queue.add(abstractState);
            abstractState.setFailure(this.rootState);
        }
        this.prepared = true;
        while (!queue.isEmpty()) {
            AbstractState<T> currentstate = queue.remove();
            for (T c : currentstate.getValues()) {
                AbstractState<T> targetState = currentstate.nextState(c);
                queue.add(targetState);
                AbstractState<T> failurestate = currentstate.getFailure();
                while (failurestate.nextState(c) == null) {
                    failurestate = failurestate.getFailure();
                }
                AbstractState<T> newState = failurestate.nextState(c);
                targetState.setFailure(newState);
                targetState.addAllOutput(newState.getOutput());
            }

        }
    }

    /**
     * @param c
     * @param currentState
     * @return
     */
    protected AbstractState<T> getState(T c, AbstractState<T> currentState) {
        AbstractState<T> newCurrentState = currentState.nextState(c);
        while (newCurrentState == null) {
            currentState = currentState.getFailure();
            newCurrentState = currentState.nextState(c);
        }
        return newCurrentState;
    }

    /**
     * @param c
     * @param currentState
     * @return
     */
    protected AbstractState<T> getStateIgnore(T c, AbstractState<T> currentState) {
        AbstractState<T> newState = currentState.nextStateIgnoreRootState(c);
        if (newState == null) {
            for (AbstractState<T> tmp : currentState.getStates()) {
                newState = this.getStateIgnore(c, tmp);
                if (newState != null)
                    return newState;
            }
        }
        return newState;
    }

    /**
     * @param keyword
     * @return
     */
    public abstract AbstractAhoCorasick<T> addKeyWord(Object keyword);

    /**
     * @param keys
     * @return
     */
    public abstract Collection<Object> search(Object keys);

    /**
     * @param keys
     * @param set
     */
    public abstract void search(Object keys, Set<Object> set);
}
