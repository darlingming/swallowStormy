package com.software.dm.swallow.stormy.hbase.demo;

import java.util.List;

public class DataPutEntity {
    private String rowKey;
    private List<ColumnFamilyEntity> columnFamilyList;

    public DataPutEntity(String rowKey, List<ColumnFamilyEntity> columnFamilyList) {
        this.rowKey = rowKey;
        this.columnFamilyList = columnFamilyList;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public List<ColumnFamilyEntity> getColumnFamilyList() {
        return columnFamilyList;
    }

    public void setColumnFamilyList(List<ColumnFamilyEntity> columnFamilyList) {
        this.columnFamilyList = columnFamilyList;
    }
}
