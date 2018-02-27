package com.software.dm.swallow.stormy.hadoop.exception;

/**
 * 
 * @author DearM
 *
 */
public class MapredException extends HadoopException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 687529081663176580L;

	public MapredException() {
		super();
	}

	public MapredException(String message) {
		super(message);
	}

	public MapredException(Throwable cause) {
		super(cause);
	}

	public MapredException(String message, Throwable cause) {
		super(message, cause);
	}

	public MapredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
