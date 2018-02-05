package com.software.dm.swallow.stormy.hbase.demo;


/**
 * 列名类
 */
public class ColumnEntity {
    private String column;
    private String value;
    private long timestamp;

    public ColumnEntity(String column, String value) {
        this.column = column;
        this.value = value;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

