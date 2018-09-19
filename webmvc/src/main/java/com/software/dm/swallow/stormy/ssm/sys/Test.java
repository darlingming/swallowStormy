package com.software.dm.swallow.stormy.ssm.sys;

public class Test {

    public static void main(String[] args) {
        StringBuffer querySql = new StringBuffer();
        querySql.append("SELECT AREA.ID,                                                               ");
        querySql.append("DECODE(HEAD.PROVINCE || '-' || HEAD.MOBILE,                                   ");
        querySql.append("'-',                                                                          ");
        querySql.append("'云公司',                                                                     ");
        querySql.append("HEAD.PROVINCE || '-' || HEAD.MOBILE) PROVINCE,                                ");
        querySql.append("AREA.HEAD_ID,                                                                 ");
        querySql.append("AREA.JDK_VERSION,                                                             ");
        querySql.append("AREA.HADOOP_VERSION,                                                          ");
        querySql.append("AREA.CLUSTER_RESOURCE,                                                        ");
        querySql.append("AREA.HARDWARE_CONFIGURATION,                                                  ");
        querySql.append("AREA.USER_COUNT,                                                              ");
        querySql.append("AREA.RESTRICTION,                                                             ");
        querySql.append("AREA.DATA_FORMAT,                                                             ");
        querySql.append("AREA.ACCESS_TIME,                                                             ");
        querySql.append("AREA.MAIL_ID,                                                                 ");
        querySql.append("AREA.STATUS,                                                                  ");
        querySql.append("AREA.CREATE_TIME,                                                             ");
        // 新增解析版本字段
        querySql.append("AREA.PROGRAM,                                                         ");
        querySql.append("AREA.APPLICATION,                                                         ");
        querySql.append("AREA.USER_LIB_CATEGORY,                                                   ");
        querySql.append("(SELECT TO_CHAR(WM_CONCAT(IN_U.ID))                                           ");
        querySql.append("FROM T_CLOUDER_MAIL_CONFIG M, T_SYS_USER IN_U                                 ");
        querySql.append("WHERE M.ID = AREA.MAIL_ID                                                     ");
        querySql.append("AND M.SEND_MAIL_ADDRESS = IN_U.ID                                             ");
        querySql.append("AND M.SEND_TYPE = '00') PERSON_LIABLE,                                        ");
        querySql.append("(SELECT TO_CHAR(WM_CONCAT(IN_U.USER_NAME))                                    ");
        querySql.append("FROM T_CLOUDER_MAIL_CONFIG M, T_SYS_USER IN_U                                 ");
        querySql.append("WHERE M.ID = AREA.MAIL_ID                                                     ");
        querySql.append("AND M.SEND_MAIL_ADDRESS = IN_U.ID                                             ");
        querySql.append("AND M.SEND_TYPE = '00') PERSON_LIABLE_SHOW,                                   ");
        querySql.append("(SELECT TO_CHAR(WM_CONCAT(IN_U.USER_EMAIL))                                   ");
        querySql.append("FROM T_CLOUDER_MAIL_CONFIG M, T_SYS_USER IN_U                                 ");
        querySql.append("WHERE M.ID = AREA.MAIL_ID                                                     ");
        querySql.append("AND M.SEND_MAIL_ADDRESS = IN_U.ID                                             ");
        querySql.append("AND M.SEND_TYPE = '00') MAIN_MAIL_ADDRESS,                                    ");
        querySql.append("(SELECT TO_CHAR(WM_CONCAT(IN_U.ID))                                           ");
        querySql.append("FROM T_CLOUDER_MAIL_CONFIG M, T_SYS_USER IN_U                                 ");
        querySql.append("WHERE M.ID = AREA.MAIL_ID                                                     ");
        querySql.append("AND M.SEND_MAIL_ADDRESS = IN_U.ID                                             ");
        querySql.append("AND M.SEND_TYPE = '01') COPY_TO_MAIL,                                         ");
        querySql.append("(SELECT TO_CHAR(WM_CONCAT(IN_U.USER_EMAIL))                                   ");
        querySql.append("FROM T_CLOUDER_MAIL_CONFIG M, T_SYS_USER IN_U                                 ");
        querySql.append("WHERE M.ID = AREA.MAIL_ID                                                     ");
        querySql.append("AND M.SEND_MAIL_ADDRESS = IN_U.ID                                             ");
        querySql.append("AND M.SEND_TYPE = '01') COPY_TO_MAIL_ADDRESS,                                 ");
        querySql.append("(SELECT TO_CHAR(WM_CONCAT(IN_U.USER_NAME))                                    ");
        querySql.append("FROM T_CLOUDER_MAIL_CONFIG M, T_SYS_USER IN_U                                 ");
        querySql.append("WHERE M.ID = AREA.MAIL_ID                                                     ");
        querySql.append("AND M.SEND_MAIL_ADDRESS = IN_U.ID                                             ");
        querySql.append("AND M.SEND_TYPE = '01') COPY_TO_MAIL_SHOW,                                    ");
        querySql.append("HEAD.MOBILE,                                                                  ");
        querySql.append("U.USER_NAME CREATE_USER                                                       ");
        querySql.append("FROM T_CLOUDER_AREA_CONFIG AREA, T_CLOUDER_HEAD HEAD, T_SYS_USER U            ");
        querySql.append("WHERE AREA.HEAD_ID = HEAD.ID(+)                                               ");
        querySql.append("AND AREA.CREATE_USER = U.ID                                                   ");
        querySql.append("AND AREA.STATUS = '01'                                                          ");
        querySql.append("AND AREA.HEAD_ID = ?                                                            ");

        StringBuffer sql = new StringBuffer();
        sql.append("select * from T_CLOUDER2_MONITOR M where TO_NUMBER (M.RUN_START_DATE) <= TO_NUMBER (");
        sql.append(" TO_CHAR (SYSDATE, 'YYYYMMDD')");
        sql.append(" )");
        sql.append(" AND TO_NUMBER (M.RUN_END_DATE) >= TO_NUMBER (");
        sql.append(" TO_CHAR (SYSDATE, 'YYYYMMDD')");
        sql.append(" )");
        sql.append(" AND M.APPLICATION LIKE '%%'");
        sql.append(" AND M.CATEGORY like '%%'");
        sql.append(" AND M.STATUS = ''");
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM (");
        sb.append(" SELECT * FROM T_CLOUDER_ADDRESS_LEVEL START WITH PARENT_ID = ?");
        sb.append(" CONNECT BY PRIOR INNER_ID = PARENT_ID");
        sb.append(") T");
        sb.append(" WHERE T.id_type = ?");
        sb.append(" UNION ALL ");
        sb.append(" SELECT * FROM T_CLOUDER_ADDRESS_LEVEL WHERE inner_id = ? and id_type = ?");

        System.out.println(sb.toString());
    }
}
