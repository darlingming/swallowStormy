package com.software.dm.swallow.stormy.algoac.impl;

import com.software.dm.swallow.stormy.algoac.inter.AbstractState;

/**
 * 
 * @Description  
 * @author DearM
 * @date 2016 
 * @version v1.0.0.1 
 *
 */
public class ByteState extends AbstractState<Byte> {

	public ByteState() {
		super();
	}

	public ByteState(int depth) {
		super(depth);
	}

	 
	public AbstractState<Byte> addState(Byte c) {
		AbstractState<Byte> abstractState = this.nextStateIgnoreRootState(c);
		if (abstractState == null) {
			abstractState = new ByteState(this.depth + 1);
			this.success.put(c, abstractState);
		}
		return abstractState;
	}

 

}
