package com.software.dm.swallow.stormy.hadoop.tools;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description Constant
 * @date 2011
 */
public interface Constant {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String CURRENT_DIR = System.getProperty("user.dir");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * char '@'
     */
    public static final char PUB_AT_CHAR = '@';
    /**
     * char '*'
     */
    public static final char PUB_ASTERISK_CHAR = '*';
    /**
     * str "@"
     */
    public static final String PUB_AT = "@";
    /**
     * space " "
     */
    public static final String PUB_SPACE = " ";
    /**
     * , str
     */
    public static final String PUB_COMMA = ",";
    /**
     * , char
     */
    public static final char PUB_COMMA_CHAR = ',';

    /**
     * char .
     */
    public static final char PUB_POINT_CHAR = '.';

    /**
     * string .
     */
    public static final String PUB_POINT_STR = ".";

    /**
     * string .
     */
    public static final String PUB_UNDERLINE_STR = "_";

    /**
     * char .
     */
    public static final char PUB_UNDERLINE_CHAR = '_';

    /**
     * tab \t string
     */
    public static final String PUB_TAB = "\t";

    /**
     * \t char
     */
    public static final char PUB_TAB_CHAR = '\t';
    /**
     * \u0001 str
     */
    public final static String PUB_FIELD_STR = "\u0001";
    /**
     * \u0001 char
     */
    public final static char PUB_FIELD_CHAR = '\u0001';

    /**
     * \u0002 str
     */
    public final static String PUB_LINE_STR = "\u0002";
    /**
     * \u0002 char
     */
    public final static char PUB_LINE_CHAR = '\u0002';
    /**
     * \u0003 char
     */
    public final static char PUB_FLAG_CHAR = '\u0003';
    /**
     * \u0003 str
     */
    public final static String PUB_FLAG_STR = "\u0003";
    /**
     * \u0004 char
     */
    public final static char PUB_EVE_LINE_CHAR = '\u0004';

    /**
     * | char
     */
    public final static char PUB_VERTICAL_LINE_CHAR = '|';

    /**
     * | String
     */
    public final static String PUB_VERTICAL_LINE_STR = "|";

    public void init() throws Exception;

    public final static String MAP_KEY = "MAP_KEY";
    public final static String MAP_VALUE = "MAP_VALUE";
    public final static String REDUCE_KEY = "REDUCE_KEY";
    public final static String REDUCE_VALUE = "REDUCE_VALUE";
    public final static String MAP_REDUCE_FLAG = "MAP_REDUCE_FLAG";

}
