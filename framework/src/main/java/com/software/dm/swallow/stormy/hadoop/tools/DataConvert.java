package com.software.dm.swallow.stormy.hadoop.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description data type conversion
 * @date 2011-01-01
 */
public class DataConvert {
    private static final int KEY_CODE = 8888;

    private static final int CHECK_POI = 128;

    /**
     * Byte is converted to a length of 8 byte array, the array each value
     * represents bit
     */
    public static byte[] getBooleanArray(byte b) {
        byte[] array = new byte[8];
        for (int i = 7; i >= 0; i--) {
            array[i] = (byte) (b & 1);
            b = (byte) (b >> 1);
        }
        return array;
    }

    /**
     * Byte bit to the string
     */
    private static String byteToBit(byte b) {
        return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1) // 7
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1) // 5
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)// 3
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);// 1
    }

    /**
     * Binary string byte
     */
    private static byte decodeBinaryString(String byteStr) {
        long re, len;
        if (null == byteStr) {
            return 0;
        }
        len = byteStr.length();
        if (len != 4 && len != 8) {
            return 0;
        }
        if (len == 8) {// 8 bit
            if (byteStr.charAt(0) == '0') {//
                re = Long.parseLong(byteStr, 2);
            } else {//
                re = Long.parseLong(byteStr, 2) - 256;
            }
        } else {// 4 bit
            re = Long.parseLong(byteStr, 2);
        }
        return (byte) re;
    }

    /**
     * @param s
     * @param radix
     * @return @
     * @deprecated
     */
    public static String fromData(String s, int radix) {
        if (s == null || s.isEmpty())
            return "";
        if (radix < 8 || radix > 63)
            throw new NumberFormatException("radix more or less  63 or 8");
        StringBuilder sb = new StringBuilder();
        byte[] bs = s.getBytes();
        String tmp = "";
        for (byte c : bs) {
            tmp += byteToBit(c);
            if (tmp.length() > radix) {
                String strs = tmp.substring(0, radix);
                sb.append(Long.parseLong(strs, 2)).append(',');
                tmp = tmp.substring(radix);
            }
        }
        if (!tmp.isEmpty()) {
            sb.append(Long.parseLong(tmp, 2));
            sb.append(',').append(tmp.length() + CHECK_POI);
        } else {
            sb.append(',').append(CHECK_POI);
        }
        return sb.toString();
    }

    /**
     * @param bs
     * @param radix
     * @return @
     * @deprecated
     */
    public static String[] fromData(byte[] bs, int radix) {
        if (radix < 8 || radix > 63)
            throw new NumberFormatException("radix more or less  63 or 8");
        List<String> list = new ArrayList<String>();
        String tmp = "";
        for (byte c : bs) {
            tmp += byteToBit(c);
            if (tmp.length() > radix) {
                String strs = tmp.substring(0, radix);
                list.add(String.valueOf(Long.parseLong(strs, 2)));
                tmp = tmp.substring(radix);
            }
        }
        if (!tmp.isEmpty()) {
            list.add(String.valueOf(Long.parseLong(tmp, 2)));
            list.add(String.valueOf(tmp.length() + CHECK_POI));
        } else {
            list.add(String.valueOf(CHECK_POI));
        }
        return list.toArray(new String[]{});
    }

    /**
     * @param s
     * @return
     * @deprecated
     */
    public static String fromData(String s) {
        return fromData(s, 20);
    }

    /**
     * @param bs
     * @return @
     * @deprecated
     */
    public static String[] fromData(byte[] bs) {
        return fromData(bs, 20);
    }

    /**
     * @param s
     * @param radix
     * @return @
     * @deprecated
     */
    public static byte[] parseData(String s, int radix) {
        String[] tempList = AbstractCommonUtils.fastSplit(s, Constant.PUB_COMMA_CHAR);
        // String[] tempList = s.split(",");
        return parseData(tempList, radix);
    }

    /**
     * @param strs
     * @param radix
     * @return @
     * @deprecated
     */
    public static byte[] parseData(String[] strs, int radix) {
        StringBuilder sb = new StringBuilder();
        List<Byte> byte_list = new ArrayList<Byte>();
        int len = strs.length - 1;
        int real_poi = len - 1;
        for (int i = 0; i < len; i++) {
            String vas = Long.toBinaryString(Long.parseLong(strs[i]));
            if (vas.length() < radix) {
                int l = radix - vas.length();
                if (i == real_poi) {
                    l = Integer.parseInt(strs[len]) - CHECK_POI - vas.length();
                    l = l > radix ? 0 : l;
                }
                for (int j = 0; j < l; j++) {
                    sb.append("0");
                }
                sb.append(vas);
            } else {
                sb.append(vas);
            }
            while (sb.length() >= 8) {
                byte_list.add(decodeBinaryString(sb.substring(0, 8)));
                sb.delete(0, 8);
            }
        }
        byte[] re_b = new byte[byte_list.size()];
        for (int i = 0; i < byte_list.size(); i++) {
            re_b[i] = byte_list.get(i);
        }
        return re_b;
    }

    /**
     * @param strs
     * @param radix
     * @return
     * @deprecated
     */
    public static byte[] parseData_bak(String[] strs, int radix) {
        StringBuilder sb = new StringBuilder();
        int len = strs.length - 1;
        int real_poi = len - 1;

        for (int i = 0; i < len; i++) {
            String vas = Long.toBinaryString(Long.valueOf(strs[i]));
            if (vas.length() < radix) {
                int l = radix - vas.length();
                if (i == real_poi) {
                    l = Integer.valueOf(strs[len]) - CHECK_POI - vas.length();
                    l = l > radix ? 0 : l;
                }
                for (int j = 0; j < l; j++) {
                    vas = "0" + vas;
                }
            }
            sb.append(vas);
        }
        String result = sb.toString();
        byte[] b_v = new byte[result.length() / 8];
        for (int i = 0; i < b_v.length; i++) {
            b_v[i] = decodeBinaryString(result.substring(0, 8));
            result = result.substring(8);
        }
        return b_v;
    }

    /**
     * @param strs
     * @return @
     * @deprecated
     */
    public static byte[] parseData(String[] strs) {
        return parseData(strs, 20);
    }

    /**
     * @param s
     * @return
     * @deprecated
     */
    public static byte[] parseData(String s) {
        return parseData(s, 20);
    }

    /**
     * @param n
     * @param target
     * @return
     */
    public static String toBinary(long n, int target) {
        String s = "0";
        while (n != 0) {
            s = n % target + s;
            n = n / target;
        }
        return s;
    }

    /**
     * @param str
     * @param bit
     * @return
     */
    public static Long[] fromDataToLong(String str, int bit) {
        return fromDataToLong(str.getBytes(), bit);
    }

    /**
     * @param lon
     * @param bit
     * @return
     */
    public static byte[] parseDataToByte(Long[] lon, int bit) {
        int curr_len = bit;
        int len_count = lon.length * curr_len;
        int result_count = (len_count / 8) + (len_count % 8 == 0 ? 0 : 1);
        byte[] bs = new byte[result_count];
        int bs_i = 0;
        byte temp_b = 0x0;
        for (int i = 0; i < len_count; i++) {
            int x = i / curr_len;
            int y = i % curr_len;
            temp_b += (byte) (lon[x] >> (curr_len - 1 - y) & 0x1);
            int l = (i + 1) % 8;
            if (l == 0) {
                bs[bs_i++] = temp_b;
                temp_b = 0x0;
            } else {
                temp_b <<= 0x1;
            }
        }
        if (temp_b != 0x0) {
            bs[bs_i++] = temp_b;
        }
        return bs;
    }

    /**
     * @param strs
     * @param bit
     * @return
     */
    public static byte[] parseDataToByte(String[] strs, int bit) {
        Long[] lons = new Long[strs.length];
        for (int i = 0; i < strs.length; i++) {
            lons[i] = Long.valueOf(strs[i]);
        }
        return parseDataToByte(lons, bit);
    }

    /**
     * @param str
     * @param delimiter
     * @param bit
     * @return
     */
    public static byte[] parseDataToByte(String str, char delimiter, int bit) {
        String[] strs = AbstractCommonUtils.fastSplit(str, delimiter);
        Long[] lons = new Long[strs.length];
        for (int i = 0; i < strs.length; i++) {
            lons[i] = Long.valueOf(strs[i]);
        }
        return parseDataToByte(lons, bit);
    }

    public static String fromDataToString(byte[] bs, char delimiter, int bit) {
        Long[] lons = fromDataToLong(bs, bit);
        return AbstractCommonUtils.join(lons, delimiter);
    }

    /**
     * @param bs
     * @param bit
     * @return
     */
    public static Long[] fromDataToLong(byte[] bs, int bit) {
        int radix = bit;
        long result = 0;
        int len_count = bs.length * 8;
        int result_count = (len_count / radix) + (len_count % radix == 0 ? 0 : 1);
        Long[] ints = new Long[result_count];
        int z = 0;
        for (int j = 0; j < len_count; j++) {
            int poi = j / 8;
            int poi_s = j % 8;
            result += ((bs[poi] >> (7 - poi_s)) & 0x1);
            int y = (j + 1) % radix;
            if (y == 0) {
                ints[z++] = result;
                result = 0;
            } else if (j == len_count - 1) {
                int a = radix - y;
                while (a > 0) {
                    result <<= 0x1;
                    a--;
                }
                ints[z++] = result;
            } else {
                result <<= 0x1;
            }
        }
        return ints;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public static int dataConvert(int x, int y) {
        return x ^ y ^ KEY_CODE;
    }
}
