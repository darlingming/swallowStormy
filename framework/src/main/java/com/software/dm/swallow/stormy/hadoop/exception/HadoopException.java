package com.software.dm.swallow.stormy.hadoop.exception;

public class HadoopException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 7732648819938252547L;

    public HadoopException() {
        super();
    }

    public HadoopException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause);
    }

    public HadoopException(String message, Throwable cause) {
        super(message, cause);
    }

    public HadoopException(String message) {
        super(message);
    }

    public HadoopException(Throwable cause) {
        super(cause);
    }

}
