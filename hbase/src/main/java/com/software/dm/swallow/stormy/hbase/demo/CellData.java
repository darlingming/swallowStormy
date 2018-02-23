package com.software.dm.swallow.stormy.hbase.demo;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;

/**
 * @author darlingming@126.com
 */
public class CellData {

    private String row;

    private String family;
    private String qualifier;
    private String value;
    private long timestamp;


    public CellData() {

    }

    public CellData(Cell cell) {
        this.row = new String(CellUtil.cloneRow(cell));
        this.family = new String(CellUtil.cloneFamily(cell));
        this.qualifier = new String(CellUtil.cloneQualifier(cell));
        this.value = new String(CellUtil.cloneValue(cell));
        this.timestamp = cell.getTimestamp();
    }

    public String getRow() {
        return row;
    }

    public String getFamily() {
        return family;
    }

    public String getQualifier() {
        return qualifier;
    }

    public String getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "CellData{" +
                "row='" + row + '\'' +
                ", family='" + family + '\'' +
                ", qualifier='" + qualifier + '\'' +
                ", value='" + value + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
