package com.software.dm.swallow.stormy.hbase.demo;

import java.util.List;

public class HbaseCommonUtils  {

    public static boolean contains(List<HbaseDataEntity> list, String rowKey) {
        if (null != list && !list.isEmpty())
            for (HbaseDataEntity hbaseDataEntity : list) {
                if (hbaseDataEntity.getRowKey().equals(rowKey)) {
                    return true;
                } else {
                    return false;
                }
            }
        return false;
    }

    public static Object get(List list, String dataType) {
        if (null != list && !list.isEmpty())
            for (Object obj : list) {
                if (obj instanceof ColumnFamilyEntity) {
                    ColumnFamilyEntity columnFamilyEntity = (ColumnFamilyEntity) obj;
                    if (columnFamilyEntity.equals(dataType)) {
                        return obj;
                    } else {
                        return null;
                    }
                }else if (obj instanceof  HbaseDataEntity){
                    HbaseDataEntity hbaseDataEntity = (HbaseDataEntity) obj;
                    if (hbaseDataEntity.getRowKey().equals(dataType)) {
                        return hbaseDataEntity;
                    } else {
                        return null;
                    }
                }

            }
        return null;
    }
}
