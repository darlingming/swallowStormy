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
        String[] columnFamilys = {"student","school","user"};
        HbaseOpertion hbaseOpertion = null;
        try {
            hbaseOpertion = HbaseOpertion.getInstance();

            hbaseOpertion.createTable(tableName, columnFamilys);

            //hbaseOpertion.alterTable(tableName,columnFamilys);
            String rowKey = "admin";
            String columnFamily = "user";
            String column = "passwd";
            String value = "123456";
            hbaseOpertion.addRow(tableName, rowKey, columnFamily, column, value);
            hbaseOpertion.getRow(tableName, rowKey);
            logger.info(" hbaseOpertion.getRow(");
            //hbaseOpertion.scanTable(tableName);
            //hbaseOpertion.delRow(tableName, rowKey);


            List<HbaseDataEntity> hbaseDataEntityList = new ArrayList<>();
            List<ColumnFamilyEntity> columnFamilyList = new ArrayList<>();

            List<ColumnEntity> columnList = new ArrayList<>();
            columnList.add(new ColumnEntity("name", "李明"));
            columnList.add(new ColumnEntity("sex", "男"));
            columnList.add(new ColumnEntity("jg", "中国"));

            columnFamilyList.add(new ColumnFamilyEntity(columnFamily, columnList));

            hbaseDataEntityList.add(new HbaseDataEntity("13804511234", columnFamilyList));
            hbaseDataEntityList.add(new HbaseDataEntity("13804510000", columnFamilyList));
            hbaseDataEntityList.add(new HbaseDataEntity("13804510000", columnFamilyList));
            hbaseOpertion.bathInsert(tableName, hbaseDataEntityList);

            logger.info(" hbaseOpertion.bathInsert(");

            //List<HbaseDataEntity> hbasedataList = hbaseOpertion.scanTableData(tableName);
            //for (HbaseDataEntity hbaseDataEntity : hbasedataList) {
            //    for (ColumnFamilyEntity columnFamilyEntity : hbaseDataEntity.getColumnFamilyList()  ) {
            //
            //        for (ColumnEntity columnEntity : columnFamilyEntity.getColumnList()  ) {
            //            logger.info( "行键:" + hbaseDataEntity.getRowKey() + "\t" +
            //                    "列族:" + columnFamilyEntity.getColumnFamily() + "\t" +
            //                    "列名:" + columnEntity.getColumn() + "\t" +
            //                    "值:" + columnEntity.getValue() + "\t" +
            //                    "时间戳:" + columnEntity.getTimestamp());
            //        }
            //    }
            //}
            column="js";
            long reslong = hbaseOpertion.incrementColumnValue(  tableName,   rowKey,   columnFamily,   column, 1);
            logger.info("计数器值："+reslong);
            hbaseOpertion.getRow(tableName, rowKey);
        } catch (Exception e) {
            logger.error("HbaseOpertion Exception ", e);
        } finally {
            hbaseOpertion.closeConnection();
        }
        long end = System.currentTimeMillis();
        logger.info("consume -> " + (end - start));
    }
}
