package com.software.dm.swallow.stormy.hbase.demo;

import java.util.List;

/**
 * 列族
 */
public class ColumnFamilyEntity {
    private String columnFamily;
    private List<ColumnEntity> columnList;

    public ColumnFamilyEntity(String columnFamily, List<ColumnEntity> columnList) {
        this.columnFamily = columnFamily;
        this.columnList = columnList;
    }

    public String getColumnFamily() {
        return columnFamily;
    }

    public void setColumnFamily(String columnFamily) {
        this.columnFamily = columnFamily;
    }

    public List<ColumnEntity> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ColumnEntity> columnList) {
        this.columnList = columnList;
    }
}
