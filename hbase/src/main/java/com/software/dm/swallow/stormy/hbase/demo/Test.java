package com.software.dm.swallow.stormy.hbase.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        String tableName = "dm:t_test";
        String[] columnFamilys = {"key", "value"};
        HbaseOpertion hbaseOpertion = null;
        try {
            hbaseOpertion = HbaseOpertion.getInstance();

            //HbaseOpertion.createTable(tableName, columnFamilys);

            String rowKey = "138";
            String columnFamily = "key";
            String column = "a4";
            String value = "456";
            //hbaseOpertion.addRow(tableName, rowKey, columnFamily, column, value);
            hbaseOpertion.getRow(tableName, rowKey);
            logger.info(" hbaseOpertion.getRow(");
            //hbaseOpertion.scanTable(tableName);
            //hbaseOpertion.delRow(tableName, rowKey);


            List<DataPutEntity> dataPutEntityList = new ArrayList<>();
            List<ColumnFamilyEntity> columnFamilyList = new ArrayList<>();

            List<ColumnEntity> columnList = new ArrayList<>();
            columnList.add(new ColumnEntity("name", "李明"));
            columnList.add(new ColumnEntity("sex", "男"));
            columnList.add(new ColumnEntity("jg", "中国"));

            columnFamilyList.add(new ColumnFamilyEntity("key", columnList));

            dataPutEntityList.add(new DataPutEntity("13804511234", columnFamilyList));
            dataPutEntityList.add(new DataPutEntity("13804510000", columnFamilyList));
            hbaseOpertion.bathInsert(tableName, dataPutEntityList);

            logger.info(" hbaseOpertion.bathInsert(");
            hbaseOpertion.scanTable(tableName);


        } catch (Exception e) {
            logger.error("HbaseOpertion Exception ", e);
        } finally {
            hbaseOpertion.closeConnection();
        }
        long end = System.currentTimeMillis();
        logger.info("consume -> " + (end - start));
    }
}
