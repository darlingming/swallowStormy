package com.software.dm.swallow.stormy.hadoop.service.common;

import com.software.dm.swallow.stormy.hadoop.mapred.AbstractMapreduce;
import com.software.dm.swallow.stormy.hadoop.tools.Constant;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description
 * @date 2012-3-7
 */
public abstract class AbstractConstants implements Constant {

    public String INPUT_FIELD = PUB_FIELD_STR;
    public String OUTPUT_FIELD = PUB_FIELD_STR;
    public static final String KV_FILE = "/kv_terminal_db.txt";
    /**
     * 90:pv2 91:coordinates 99:word 1 MALL
     */
    public final static int COLUMN_LENGTH_EXTRACTOUT = 22;
    /**
     * LACCI lacci,KV,PV "HttpNormal"
     */
    public final static int COLUMN_LENGTH_CLEAROUT = 23;
    /**
     * ר thematic
     */
    public final static int COLUMN_LENGTH_THEMEOUT = 20;
    /**
     * app and base 6 "Sort"
     */
    public final static int COLUMN_LENGTH_BASISOUT = 31;

    /**
     * UA KV:COLUMN_LENGTH_CLEAROUT "HttpNormal"
     */
    public final static int COLUMN_LENGTH_KV_RESULT = 10;
    /**
     * pv1 FusionIdupidpv "HttpNormal"
     */
    public final static int COLUMN_LENGTH_PV_RESULT = 8;

    // public final static int COLUMN_LENGTH_COORDINATES = 20;//
    // public final static int COLUMN_LENGTH_PV2 = 20;// PV2
    // public final static int COLUMN_LENGTH_MALL = 20;//
    // public final static int COLUMN_LENGTH_WORD = 20;//
    public final static String APP_ID_LONLAT = "91";//
    public final static String APP_ID_FUSIONID = "90";//
    public final static String APP_ID_SWORD = "99";//
    public final static String APP_ID_SUBJECT = "1";//

    public static boolean TOTAL_COUNT_BOOL = true;

    public static boolean TOTAL_DATE_BOOL = false;

    /**
     * @author DearM
     * @version v1.0.0.1
     * @Description
     * @date 2015-3-1
     */
    public enum TotalCountEnum {
        TOTAL_COUNT, // TOTAL
        SCENE_OUTPUT_COUNT, //
        SCENE_INPUT_COUNT, //
        APP_IN_COUNT("ULTRA_A_IN_COUNT"), //
        APP_OUT_COUNT("ULTRA_A_OUT_COUNT"), //
        BASIC_IN_COUNT("ULTRA_B_IN_COUNT"), //
        BASIC_OUT_COUNT("ULTRA_B_OUT_COUNT"), //
        THEME_IN_COUNT("ULTRA_T_IN_COUNT"), //
        THEME_OUT_COUNT("ULTRA_T_OUT_COUNT"), //
        SUBJECT_IN_COUNT("ULTRA_SJ_IN_COUNT"), //
        SUBJECT_OUT_COUNT("ULTRA_SJ_OUT_COUNT"), //
        SWORD_IN_COUNT("ULTRA_SW_IN_COUNT"), //
        SWORD_OUT_COUNT("ULTRA_SW_OUT_COUNT"), //
        UA_IN_COUNT("ULTRA_U_IN_COUNT"), //
        UA_OUT_COUNT("ULTRA_U_OUT_COUNT"), //
        FUSIONID_IN_COUNT("ULTRA_F_IN_COUNT"), //
        FUSIONID_OUT_COUNT("ULTRA_F_OUT_COUNT"), //
        FUSIONID_CLEAR_IN_COUNT("ULTRA_F_IN_COUNT"), //
        FUSIONID_CLEAR_OUT_COUNT("ULTRA_F_OUT_COUNT"), //
        FUSIONID_EXTRACT_IN_COUNT("ULTRA_F_IN_COUNT"), //
        FUSIONID_EXTRACT_OUT_COUNT("ULTRA_F_OUT_COUNT"), //
        LACCI_IN_COUNT("ULTRA_LC_IN_COUNT"), //
        LACCI_OUT_COUNT("ULTRA_LC_OUT_COUNT"), //
        LONLAT_IN_COUNT("ULTRA_LL_IN_COUNT"), //
        LONLAT_OUT_COUNT("ULTRA_LL_OUT_COUNT");

        private String aliasCode;

        private TotalCountEnum() {
        }

        private TotalCountEnum(String aliasCode) {
            this.aliasCode = aliasCode;
        }

        public String getAliasCode() {
            return this.aliasCode == null ? this.name() : this.aliasCode;
        }

    }

    /**
     * @author DearM
     * @version v1.0.0.1
     * @Description
     * @date 2015-3-21
     */
    public enum SceneCode implements java.io.Serializable {
        APP("app", "ultra-a"), //
        BASIC("basic", "ultra-b"), //
        THEME("theme", "ultra-t"), //
        SWORD("sword", "ultra-sw"), //
        SUBJECT("subject", "ultra-sj"), //
        LONLAT("lonlat", "ultra-ll"), //
        FUSIONID("fusionid", "ultra-f"), //
        FUSIONID_CLEAR("fusionid_clear", "ultra-fc"), //
        FUSIONID_EXTRACT("fusionid_extract", "ultra-fe"), //
        LACCI("lacci", "ultra-lc"), //
        UA("ua", "ultra-u"), MAPRED("mapred", "ultra-mapred"); //

        private String code;
        private String pathName;

        private SceneCode(String code, String pathName) {
            this.code = code;
            this.pathName = pathName;
        }

        public String getCode() {
            return code;
        }

        public String getPathName() {
            return pathName;
        }

    }

    public enum AppCode implements java.io.Serializable {
        PV1("pv1", "FusionIdupidpvMapper", "FusionIdupidpvReducer"), // pv1
        PV2("pv2", "FusionidClientPvCpMapper", "FusionidClientPvCpReducer"), // pv2
        PV2_1("pv2_1", null, null), // pv2_1
        LACCI("lacci", "CleaningResultMapper", "CleaningResultReducer"), // lacci
        COORDINATES("coordinates", "LongitudelatitudeMapper", null), // coordinates
        UA("ua", "UaidhistoryMapper", "UaidhistoryReducer"), // ua
        MALL("mall", "SupplierMapper", "SupplierReducer"), // mall
        WORD("word", null, null), // a
        THEMATIC("thematic", null, null), // themetic
        APP("app", null, null), // b
        BASIC("basic", null, null);

        private String code;
        private String mapperName;
        private String reducerName;

        private AppCode(String code, String mapperName, String reducerName) {
            this.code = code;
            this.mapperName = mapperName;
            this.reducerName = reducerName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMapperName() {
            return mapperName;
        }

        public void setMapperName(String mapperName) {
            this.mapperName = mapperName;
        }

        public String getReducerName() {
            return reducerName;
        }

        public void setReducerName(String reducerName) {
            this.reducerName = reducerName;
        }
    }

    /**
     * @param splits
     * @return
     */
    protected String getFieldSplit(String delimiter_str) {
        return AbstractMapreduce.splitConvert(delimiter_str);
    }

    /**
     * 多文输出清洗的输出名
     */
    public static final String FILEPATH_CLEAR = "clearOut";
    /**
     * 多文输出基础匹配的输出名
     */
    public static final String FILEPATH_BASIS = "basisOut";

    public static final String FILEPATH_BASIS_RESULT = "dmb";
    public static final String FILEPATH_APP_RESULT = "dma";
    /**
     * 多文输出主题匹配的输出名
     */
    public static final String FILEPATH_THEME = "themeOut";
    public static final String FILEPATH_THEME_RESULT = "dmt";

    public static final String FILEPATH_EXTRACT = "extractOut";

    public static final String FILEPATH_CLEAR_FLAG = "1";
    public static final String FILEPATH_BASIS_FLAG = "2";
    public static final String FILEPATH_THEME_FLAG = "3";
    public static final String FILEPATH_EXTRACT_FLAG = "4";
    public static final String FILEPATH_OTHER_FLAG = "0";

    /**
     */
    public static final String FILENAME_NOISE = "Noise";
    /**
     * http扩展名文件名
     */
    public static final String FILENAME_HTTP_EXT = "HttpExt";
    /**
     * http正常待解析数据文件名
     */
    public static final String FILENAME_IP_NORMAL = "IpNormal";

    /**
     * http正常待解析数据文件名
     */
    public static final String FILENAME_HTTP_NORMAL = "HttpNormal";

    /**
     */
    public static final String FILENAME_NOHTTP_NORMAL = "NoHttpNormal";
    /**
     */
    public static final String FILENAME_URL_MATCH = "UrlMatch";
    /**
     */
    public static final String FILENAME_EXT_MATCH = "ExtMatch";
    /**
     */
    public static final String FILENAME_IP_MATCH = "IpMatch";
    /**
     */
    public static final String FILENAME_SWORD_MATCH = "SwordMatch";
    /**
     */
    public static final String FILENAME_SORT = "Sort";

    public static final String FILENAME_URL_NOT_MATCH = "UrlNotMatch";

    public static final String FILENAME_IP_NOT_MATCH = "IpNotMatch";

    public final static String SCENE_SJ = "mr.java.opts1";
    public final static String SCENE_F = "mr.java.opts2";
    public final static String SCENE_U = "mr.java.opts3";
    public final static String SCENE_LL = "mr.java.opts4";
    public final static String SCENE_LC = "mr.java.opts5";
    public final static String SCENE_A = "mr.java.opts6";
    public final static String SCENE_B = "mr.java.opts7";
    public final static String SCENE_T = "mr.java.opts8";
    public final static String SCENE_SW = "mr.java.opts9";
    /**
     * clearOut f
     * 9020
     */
    public final static String SCENE_F_C = "mr.java.opts10";
    //9016
    public final static String SCENE_F_C_S = "mr.java.opts11";

    // YW
    public static final String BDIA_CLEARCODE = "BDIA-100";
    public static final String BDIA_SUBJECTCODE = "BDIA-101";
    public static final String BDIA_FUSIONIDCODE = "BDIA-102";
    // public static final String BDIA_lonlatcode= "BDIA-103";
    public static final String BDIA_LONLATCODE = "BDIA-104";
    public static final String BDIA_IPCLEARCODE = "BDIA-105";
    public static final String BDIA_APPCODE = "BDIA-106";
    public static final String BDIA_BAISCCODE = "BDIA-107";
    public static final String BDIA_THEMECODE = "BDIA-108";
    public static final String BDIA_SWORDCODE = "BDIA-109";

    // FIX
    public static final String BDIA_CLEARCODE_FIX = "BDIA-300";
    public static final String BDIA_SUBJECTCODE_FIX = "BDIA-301";
    public static final String BDIA_FUSIONIDCODE_FIX = "BDIA-302";
    public static final String BDIA_LONLATCODE_FIX = "BDIA-304";
    public static final String BDIA_IPCLEARCODE_FIX = "BDIA-305";
    public static final String BDIA_APPCODE_FIX = "BDIA-306";
    public static final String BDIA_BAISCCODE_FIX = "BDIA-307";
    public static final String BDIA_THEMECODE_FIX = "BDIA-308";
    public static final String BDIA_SWORDCODE_FIX = "BDIA-309";

}