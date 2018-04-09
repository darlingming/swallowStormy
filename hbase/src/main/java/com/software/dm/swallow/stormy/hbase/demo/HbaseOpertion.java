package com.software.dm.swallow.stormy.hbase.demo;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * HbaseOpertion
 */
public class HbaseOpertion {

    private static final Logger logger = LoggerFactory.getLogger(HbaseOpertion.class);
    // 声明静态配置
    private Configuration conf = null;
    // 建立一个数据库的连接
    private Connection conn = null;
    // 单例类
    private static HbaseOpertion hbaseOpertion = null;


    /**
     * @throws IOException
     */
    private HbaseOpertion() throws IOException {
        InputStream is = HbaseOpertion.class.getResourceAsStream("/hbase.properties");
        Properties prop = new Properties();
        prop.load(is);
        is.close();
        logger.info(prop.values().toString());
        conf = HBaseConfiguration.create();

        conf.set("hbase.zookeeper.quorum", prop.getProperty("hbase.zookeeper.quorum"));
        conf.set("hbase.zookeeper.property.clientPort", prop.getProperty("hbase.zookeeper.property.clientPort"));
        conf.set("hive.exec.submitviachild", prop.getProperty("hive.exec.submitviachild"));
        // 建立一个数据库的连接
        conn = getConnection();
    }

    /**
     * @return
     */
    public static HbaseOpertion getInstance() throws IOException {
        if (null == hbaseOpertion) {
            synchronized (logger) {
                hbaseOpertion = new HbaseOpertion();
            }
        }

        return hbaseOpertion;
    }


    /**
     * @return
     */
    private Connection getConnection() {

        if (null == conn) {
            synchronized (logger) {
                try {
                    conn = ConnectionFactory.createConnection(conf);
                } catch (IOException e) {
                    logger.error("createConnection->", e);
                }
            }

        }

        return conn;
    }

    /**
     *
     */
    public void closeConnection() {
        try {
            if (null != conn)
                conn.close();
        } catch (IOException e) {
            logger.error("closeConnection ->", e);
        }
    }

    /**
     * 创建数据库表
     *
     * @param tableName
     * @param columnFamilys
     * @throws IOException
     */
    public void createTable(String tableName, String[] columnFamilys) throws IOException {
        // 创建一个数据库管理员
        HBaseAdmin hAdmin = (HBaseAdmin) getConnection().getAdmin();


        if (hAdmin.tableExists(tableName)) {
            logger.info(tableName + "表已存在");

        } else {
            // 新建一个表描述
            HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));
            // 在表描述里添加列族
            for (String columnFamily : columnFamilys) {
                tableDesc.addFamily(new HColumnDescriptor(columnFamily));
            }
            // 根据配置好的表描述建表
            hAdmin.createTable(tableDesc);
            logger.info("创建" + tableName + "表成功");
        }
    }

    /**
     * 添加列族
     *
     * @param tableName
     * @param columnFamilys
     * @throws IOException
     */
    public void alterTable(String tableName, String[] columnFamilys) throws IOException {
        // 创建一个数据库管理员
        HBaseAdmin hAdmin = (HBaseAdmin) getConnection().getAdmin();

        ClusterStatus clusterStatus = hAdmin.getClusterStatus();
        for (ServerName serverName : clusterStatus.getServers()) {
            ServerLoad serverLoad = clusterStatus.getLoad(serverName);
            for (Map.Entry<byte[], RegionLoad> regionload : serverLoad.getRegionsLoad().entrySet()) {
                String regionName = Bytes.toString(regionload.getKey());
                logger.info(regionName + "====" + regionload.getValue());
                //result.put(regionName, regionload.getValue());
            }
        }

        if (hAdmin.tableExists(tableName)) {
            if (hAdmin.isTableEnabled(tableName))
                hAdmin.disableTable(tableName);
            // 获取一个表描述
            HTableDescriptor tableDesc = hAdmin.getTableDescriptor(TableName.valueOf(tableName));
            // 在表描述里添加列族
            for (String columnFamily : columnFamilys) {

                if (tableDesc.hasFamily(columnFamily.getBytes())) {
                    HColumnDescriptor family = new HColumnDescriptor(columnFamily);

                    //tableDesc.modifyFamily(family);
                } else {
                    tableDesc.addFamily(new HColumnDescriptor(columnFamily));
                }

            }

            // 根据配置好的表描述建表
            hAdmin.modifyTable(tableName, tableDesc);
            hAdmin.enableTable(tableName);
            logger.info("添加" + tableName + "表成功");

        }


    }

    /**
     * 批量添加数据
     *
     * @param tableName
     * @param hbaseDataEntitySet
     * @throws IOException
     */
    public void bathInsert(String tableName, List<HbaseDataEntity> hbaseDataEntitySet)
            throws IOException {
        // 获取表
        HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
        List<Put> putList = new ArrayList<Put>();
        for (HbaseDataEntity hbaseDataEntity : hbaseDataEntitySet) {
            for (ColumnFamilyEntity columnFamilyEntity : hbaseDataEntity.getColumnFamilyList()) {
                // 通过rowkey创建一个put对象
                Put put = new Put(Bytes.toBytes(hbaseDataEntity.getRowKey()));
                for (ColumnEntity columnEntity : columnFamilyEntity.getColumnList()) {
                    // 在put对象中设置列族、列、值
                    put.addColumn(Bytes.toBytes(columnFamilyEntity.getColumnFamily()), Bytes.toBytes(columnEntity.getColumn()), Bytes.toBytes(columnEntity.getValue()));
                }
                putList.add(put);
            }
        }
        // 插入数据,可通过put(List<Put>)批量插入
        table.put(putList);
        // 关闭资源
        table.close();

    }


    /**
     * 添加一条数据
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @param value
     * @throws IOException
     */
    public void addRow(String tableName, String rowKey, String columnFamily, String column, String value)
            throws IOException {
        // 获取表
        HTable updtable = (HTable) getConnection().getTable(TableName.valueOf(tableName));
        // 通过rowkey创建一个put对象
        Put putData = new Put(Bytes.toBytes(rowKey));
        // 在put对象中设置列族、列、值
        putData.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
        // 插入数据,可通过put(List<Put>)批量插入
        updtable.put(putData);

        // 关闭资源
        updtable.close();

    }

    /**
     * 通过rowkey获取一条数据
     *
     * @param tableName
     * @param rowKey
     * @throws IOException
     */
    public void getRow(String tableName, String rowKey) throws IOException {

        // 获取表
        HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
        // 通过rowkey创建一个get对象
        Get get = new Get(Bytes.toBytes(rowKey));
        //get.addFamily(Bytes.toBytes("student"));
        // 输出结果
        Result result = table.get(get);
        for (Cell cell : result.rawCells()) {
            logger.info(
                    "行键:" + new String(CellUtil.cloneRow(cell)) + "\t" +
                            "列族:" + new String(CellUtil.cloneFamily(cell)) + "\t" +
                            "列名:" + new String(CellUtil.cloneQualifier(cell)) + "\t" +
                            "值:" + new String(CellUtil.cloneValue(cell)) + "\t" +
                            "时间戳:" + cell.getTimestamp());
        }
        // 关闭资源
        table.close();
    }

    /**
     * 通过rowkey获取一条数据
     *
     * @param tableName
     * @param rowKey
     * @throws IOException
     */
    public CellData getRowByKey(String tableName, String rowKey) throws IOException {
        CellData cd = null;
        // 获取表
        HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
        // 通过rowkey创建一个get对象
        Get get = new Get(Bytes.toBytes(rowKey));

        // 输出结果
        Result result = table.get(get);
        for (Cell cell : result.rawCells()) {
            logger.info(
                    "行键:" + new String(CellUtil.cloneRow(cell)) + "\t" +
                            "列族:" + new String(CellUtil.cloneFamily(cell)) + "\t" +
                            "列名:" + new String(CellUtil.cloneQualifier(cell)) + "\t" +
                            "值:" + new String(CellUtil.cloneValue(cell)) + "\t" +
                            "时间戳:" + cell.getTimestamp());
            cd = new CellData(cell);
        }
        // 关闭资源
        table.close();
        return cd;
    }

    /**
     * 通过rowkey获取一条数据
     *
     * @param tableName
     * @param rowKey
     * @throws IOException
     */
    public List<CellData> getRow(String tableName, String rowKey, String familyName, String qualifier) throws IOException {
        List<CellData> listCD = null;
        // 获取表
        HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
        // 通过rowkey创建一个get对象
        Get get = new Get(Bytes.toBytes(rowKey));
        //get.addFamily(Bytes.toBytes(familyName));
        get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(qualifier));

        // 输出结果
        Result result = table.get(get);
        if (!result.isEmpty()) {
            listCD = new ArrayList<CellData>();
            for (Cell cell : result.rawCells()) {
                listCD.add(new CellData(cell));
                logger.info(
                        "行键:" + new String(CellUtil.cloneRow(cell)) + "\t" +
                                "列族:" + new String(CellUtil.cloneFamily(cell)) + "\t" +
                                "列名:" + new String(CellUtil.cloneQualifier(cell)) + "\t" +
                                "值:" + new String(CellUtil.cloneValue(cell)) + "\t" +
                                "时间戳:" + cell.getTimestamp());
            }
        }

        // 关闭资源
        table.close();

        return listCD;
    }

    /**
     * 全表扫描
     *
     * @param tableName
     * @throws IOException
     */
    public void scanTable(String tableName) throws IOException {

        // 获取表
        HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
        // 创建一个扫描对象
        Scan scan = new Scan();
        // 扫描全表输出结果
        ResultScanner results = table.getScanner(scan);
        for (Result result : results) {
            for (Cell cell : result.rawCells()) {
                logger.info(
                        "行键:" + new String(CellUtil.cloneRow(cell)) + "\t" +
                                "列族:" + new String(CellUtil.cloneFamily(cell)) + "\t" +
                                "列名:" + new String(CellUtil.cloneQualifier(cell)) + "\t" +
                                "值:" + new String(CellUtil.cloneValue(cell)) + "\t" +
                                "时间戳:" + cell.getTimestamp());
            }
        }

        // 关闭资源
        results.close();
        table.close();

    }

    /**
     * 全表扫描
     *
     * @param tableName
     * @throws IOException
     */
    public List<HbaseDataEntity> scanTableData(String tableName) throws IOException {
        List<HbaseDataEntity> hbaseDataEntityList = new ArrayList<HbaseDataEntity>();
        // 获取表
        HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
        // 创建一个扫描对象
        Scan scan = new Scan();
        // 扫描全表输出结果
        ResultScanner results = table.getScanner(scan);


        for (Result result : results) {
            for (Cell cell : result.rawCells()) {
                String rowKey = new String(CellUtil.cloneRow(cell));
                String columnFamily = new String(CellUtil.cloneFamily(cell));

                final String column = new String(CellUtil.cloneQualifier(cell));
                final String value = new String(CellUtil.cloneValue(cell));
                long timestamp = cell.getTimestamp();

                Object hbaseDataEntity = HbaseCommonUtils.get(hbaseDataEntityList, rowKey);
                if (null != hbaseDataEntity) {
                    List<ColumnFamilyEntity> columnFamilyList = ((HbaseDataEntity) hbaseDataEntity).getColumnFamilyList();
                    Object columnFamilyEntity = HbaseCommonUtils.get(columnFamilyList, columnFamily);

                    if (null != columnFamilyEntity) {
                        ((ColumnFamilyEntity) columnFamilyEntity).getColumnList().add(new ColumnEntity(column, value));
                    } else {
                        List<ColumnEntity> columnList = new ArrayList<ColumnEntity>() {
                            {
                                this.add(new ColumnEntity(column, value));
                            }
                        };

                        columnFamilyList.add(new ColumnFamilyEntity(columnFamily, columnList));
                    }
                } else {
                    List<ColumnFamilyEntity> columnFamilyList = new ArrayList<>();
                    hbaseDataEntityList.add(new HbaseDataEntity(rowKey, columnFamilyList));
                    List<ColumnEntity> columnList = new ArrayList<>();
                    ColumnEntity columnEntity = new ColumnEntity(column, value);
                    columnEntity.setTimestamp(timestamp);
                    columnList.add(columnEntity);
                    ColumnFamilyEntity columnFamilyEntity = new ColumnFamilyEntity(columnFamily, columnList);
                    columnFamilyList.add(columnFamilyEntity);
                }
            }
        }


        // 关闭资源
        results.close();
        table.close();
        return hbaseDataEntityList;

    }

    /**
     * 删除一条数据
     *
     * @param tableName
     * @param rowKey
     * @throws IOException
     */
    public void delRow(String tableName, String rowKey) throws IOException {

        // 获取表
        HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
        // 删除数据
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        table.delete(delete);
        // 关闭资源
        table.close();
    }


    /**
     * 删除多条数据
     *
     * @param tableName
     * @param rows
     * @throws IOException
     */
    public void delRows(String tableName, String[] rows) throws IOException {

        // 获取表
        HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
        // 删除多条数据
        List<Delete> list = new ArrayList<Delete>();
        for (String row : rows) {
            Delete delete = new Delete(Bytes.toBytes(row));
            list.add(delete);
        }
        table.delete(list);
        // 关闭资源
        table.close();
    }


    /**
     * 删除列族
     *
     * @param tableName
     * @param columnFamily
     * @throws IOException
     */
    public void delColumnFamily(String tableName, String columnFamily) throws IOException {

        // 创建一个数据库管理员
        HBaseAdmin hAdmin = (HBaseAdmin) getConnection().getAdmin();
        // 删除一个表的指定列族
        hAdmin.deleteColumn(tableName, columnFamily);
    }


    /**
     * 删除数据库表
     *
     * @param tableName
     * @throws IOException
     */
    public void deleteTable(String tableName) throws IOException {
        // 创建一个数据库管理员
        HBaseAdmin hAdmin = (HBaseAdmin) getConnection().getAdmin();
        if (hAdmin.tableExists(tableName)) {
            // 失效表
            hAdmin.disableTable(tableName);
            // 删除表
            hAdmin.deleteTable(tableName);
            logger.info("删除" + tableName + "表成功");
            //getConnection().close();
        } else {
            logger.info("需要删除的" + tableName + "表不存在");
            //getConnection().close();
            System.exit(0);
        }
    }


    /**
     * 追加插入(将原有value的后面追加新的value，如原有value=a追加value=bc则最后的value=abc)
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @param value
     * @throws IOException
     */
    public void appendData(String tableName, String rowKey, String columnFamily, String column, String value)
            throws IOException {
        // 获取表
        HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
        // 通过rowkey创建一个append对象
        Append append = new Append(Bytes.toBytes(rowKey));
        // 在append对象中设置列族、列、值
        append.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
        // 追加数据
        table.append(append);
        // 关闭资源
        table.close();
    }


    /**
     * 符合条件后添加数据(只能针对某一个rowkey进行原子操作)
     *
     * @param tableName
     * @param rowKey
     * @param columnFamilyCheck
     * @param columnCheck
     * @param valueCheck
     * @param columnFamily
     * @param column
     * @param value
     * @return
     * @throws IOException
     */
    public boolean checkAndPut(String tableName, String rowKey, String columnFamilyCheck, String columnCheck, String valueCheck, String columnFamily, String column, String value) throws IOException {

        // 获取表
        HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
        // 设置需要添加的数据
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
        // 当判断条件为真时添加数据
        boolean result = table.checkAndPut(Bytes.toBytes(rowKey), Bytes.toBytes(columnFamilyCheck),
                Bytes.toBytes(columnCheck), Bytes.toBytes(valueCheck), put);
        // 关闭资源
        table.close();

        return result;
    }


    /**
     * 符合条件后刪除数据(只能针对某一个rowkey进行原子操作)
     *
     * @param tableName
     * @param rowKey
     * @param columnFamilyCheck
     * @param columnCheck
     * @param valueCheck
     * @param columnFamily
     * @param column
     * @return
     * @throws IOException
     */
    public boolean checkAndDelete(String tableName, String rowKey, String columnFamilyCheck, String columnCheck,
                                  String valueCheck, String columnFamily, String column) throws IOException {
        // 获取表
        HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
        // 设置需要刪除的delete对象
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        delete.addColumn(Bytes.toBytes(columnFamilyCheck), Bytes.toBytes(columnCheck));
        // 当判断条件为真时添加数据
        boolean result = table.checkAndDelete(Bytes.toBytes(rowKey), Bytes.toBytes(columnFamilyCheck), Bytes.toBytes(columnCheck),
                Bytes.toBytes(valueCheck), delete);
        // 关闭资源
        table.close();

        return result;
    }


    /**
     * 计数器(amount为正数则计数器加，为负数则计数器减，为0则获取当前计数器的值)
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @param amount
     * @return
     * @throws IOException
     */
    public long incrementColumnValue(String tableName, String rowKey, String columnFamily, String column, long amount)
            throws IOException {
        // 获取表
        HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
        // 计数器
        long result = table.incrementColumnValue(Bytes.toBytes(rowKey), Bytes.toBytes(columnFamily), Bytes.toBytes(column), amount);

        // 关闭资源
        table.close();

        return result;
    }
}
