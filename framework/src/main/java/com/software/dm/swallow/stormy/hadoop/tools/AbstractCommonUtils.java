package com.software.dm.swallow.stormy.hadoop.tools;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author DearM
 * @version v1.0.0.1
 * @Description
 * @date 2010-03-30
 */
public abstract class AbstractCommonUtils {
    /**
     * StringBuilder
     */
    public static final StringBuilder public_sb = new StringBuilder();
    public final static Random random = new Random();
    public final static String[] dictionarys = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public AbstractCommonUtils() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param num
     * @return
     * @since Whether it is odd odd even numbers, 0 is even number
     */
    public static boolean isOdd(int num) {
        return (num & 1) == 1;
    }

    /**
     * @param delimiter_str
     * @return
     * @since Whether it is digital
     */
    public static boolean isNum(String delimiter_str) {
        boolean bool = true;
        for (char c : delimiter_str.toCharArray()) {
            if (c < '0' || c > '9') {
                bool = false;
                break;
            }
        }
        return bool;
    }

    /**
     * @param poi
     * @return
     * @since Random n number , the first is not 0 examples：poi IS 5, result
     * 3520
     */
    public static String getRandomNum(int poi) {
        String result = "";
        if (poi < 1)
            return result;
        for (int i = 0; i < poi; i++) {
            int rand = random.nextInt(10);
            if (i == 0)
                rand = rand == 0 ? 1 : rand;
            result += dictionarys[rand];
        }
        return result;
    }

    /**
     * @param poi
     * @return String
     * @since N number of bits Fluctuate.examples：poi IS 5,result 0~50000 ֵ
     */
    public static String getRandomFluctuateNum(int poi) {
        String result = "";
        if (poi < 0)
            return result;
        int num = (int) Math.pow(10, poi);
        result = String.valueOf(random.nextInt(num));
        return result;
    }

    /**
     * @param poi
     * @return int
     * @since N number of bits Fluctuate ֵ examples：poi IS 5,result 0~50000
     */
    public static int getRandomInt(int poi) {
        if (poi < 0)
            return 0;
        int num = (int) Math.pow(10, poi);
        return random.nextInt(num);
    }

    /**
     * @param poi
     * @return
     * @since Numerical random number. examples：poi IS 5,result 0~5
     */
    public static int getRandomLessInt(int poi) {
        if (poi < 0)
            return 0;
        return random.nextInt(poi);
    }

    /**
     * @param str
     * @param delimiterChar
     * @return
     */
    public static String[] fastSplit(String str, char delimiterChar) {
        if (str == null)
            return null;
        int startPos = 0;
        String[] result = new String[str.length() + 1];
        int resultIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == delimiterChar) {
                result[resultIndex] = str.substring(startPos, i);
                startPos = i + 1;
                resultIndex++;
            }
        }
        result[resultIndex] = str.substring(startPos);
        return Arrays.copyOfRange(result, 0, resultIndex + 1);
    }

    /**
     * @param str
     * @param delimiterChar
     * @return
     */
    public static String[] fastSplit(String str, String delimiterChar) {
        if (str == null || delimiterChar == null || delimiterChar.isEmpty())
            return null;
        int index_len = delimiterChar.length();
        if (index_len == 1) {
            return fastSplit(str, delimiterChar.charAt(0));
        }
        int startPos = 0;
        int resultIndex = 0;
        int total_len = str.length();
        String[] result = new String[total_len];
        int index = str.indexOf(delimiterChar);
        while (index != -1) {
            result[resultIndex++] = str.substring(startPos, index);
            if (total_len != index + index_len) {
                startPos = index + index_len;
            } else {
                startPos = total_len;
            }
            index = str.indexOf(delimiterChar, startPos);
        }
        result[resultIndex] = str.substring(startPos);
        return Arrays.copyOfRange(result, 0, resultIndex + 1);
    }

    /**
     * clear public_sb
     *
     * @return StringBuilder
     */
    public static StringBuilder clear_public_sb() {
        return public_sb.delete(0, public_sb.length());
    }

    private static StringBuffer hexValue = new StringBuffer();

    /**
     * @param inStr
     * @return
     */
    public static String string2MD5(String inStr) {
        inStr = inStr == null ? "" : inStr;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        hexValue.delete(0, hexValue.length());
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString().toUpperCase();
    }

    /**
     * @param array
     * @param separator
     * @return
     */
    public static String join(Object[] array, char separator) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : array) {
            sb.append(separator).append(obj);
        }
        return sb.deleteCharAt(0).toString();
    }

    /**
     * @param array
     * @param separator
     * @return
     */
    public static String join(Object[] array, String separator) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : array) {
            sb.append(separator).append(obj);
        }
        return sb.delete(0, separator.length()).toString();
    }

    /**
     * @param i
     * @param poi
     * @return 2, 3 >> 002
     */
    public static String getequalsnum(int i, int poi) {
        String result = "" + i;
        if (result.length() < poi) {
            for (int j = result.length(); j < poi; j++) {
                result = '0' + result;
            }
        }
        return result;
    }

    /**
     * char)0
     *
     * @param source
     * @param sep
     * @return
     */
    public static String convertChar(String source, char sep) {
        final char[] c = source.toCharArray();
        boolean bool = false;
        for (int i = 0; i < c.length; i++) {
            if (c[i] == sep) {
                c[i] = 'M';
                bool = true;
            }
        }
        return bool ? String.valueOf(c) : source;
    }

    /**
     * char)0
     *
     * @param source
     * @param seps
     * @return
     */
    public final static String convertChar(final String source, final char[] seps) {
        String reStr = source;
        final char[] cs = source.toCharArray();
        int l = 0;
        for (char c : cs) {
            for (char sep : seps) {
                if (c == sep) {
                    l++;
                    break;
                }
            }
        }
        if (l > 0) {
            final char[] chars = new char[cs.length - l];
            int j = 0;
            for (char c : cs) {
                boolean bool = false;
                for (char sep : seps) {
                    if (c == sep) {
                        bool = true;
                        break;
                    }
                }
                if (!bool)
                    chars[j++] = c;
            }
            reStr = String.valueOf(chars);
        }
        return reStr;
    }

    /**
     * @param intVal
     * @return
     */
    public static int encode(int intVal) {
        int r = new Random().nextInt(4);
        int rs = (intVal << 0x02) + r;
        return rs;
    }

    /**
     * @param intVal
     * @return
     */
    public static int decode(int intVal) {
        return intVal >> 0x02;
    }

    /**
     * @param ua
     * @return
     */
    public static String evaluate(String ua) {
        String ret = null;
        for (Pattern p : uaList) {
            Matcher m = p.matcher(ua);
            if (m.find()) {
                ret = m.group(1).trim();
                break;
            }
        }
        return ret;
    }

    public final static List<Pattern> uaList = new ArrayList<Pattern>();

    static {
        // String[] regexs = { "\\(((?:iphone|ipad)[^\\);]*)(?:;)",
        // "(?:\\(linux;)(?:[^\\)]*?)([^\\);]*)(?:miui|build)(?:[^\\)]*)(?:\\))",
        // "(?:\\(compatible;)(?:[^\\)]*?)([^\\);]*)(?:\\))" };

        String[] regexs = {"(?:\\(((?:iphone|ipad)[\\w ,]*)(?:;))", "(?:\\(Linux;)(?:[^\\)]*?;)([\\w \\-\\+\\/]{4,35})(?:miui|Build|\\()(?:[^\\)]*)(?:\\))", "(?:\\(compatible;)(?:[^\\)]*?)(?:windows phone[\\w /;.\\-]*;)([\\w\\- );]*)(?:\\))"};
        for (String regex : regexs) {
            Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            uaList.add(p);
        }

    }

    public static byte SLASH_BYTE = '/';
    public static byte EYE_BYTE = ':';
    public static byte DOT_BYTE = '.';

    public static int skipHttpOrHttps(byte[] targetBytes) {
        int offset = 0;
        for (int i = 4; i < targetBytes.length && i < 6; i++) {
            if (targetBytes[i] == EYE_BYTE && i + 2 < targetBytes.length && targetBytes[i + 1] == SLASH_BYTE && targetBytes[i + 2] == SLASH_BYTE) {
                offset = i + 3;
                break;
            }
        }
        // ����http://����https://���ͷ���0
        return offset;
    }

    /**
     */
    public static boolean isDomainOver(byte b) {
        return b == 47 || b == 58 || b == 63;
    }

    public static boolean isNormalByte(byte b) {
        return b >= 97 && b <= 122 || b >= 48 && b <= 57 || b >= 65 && b <= 90;
    }

    public static boolean isNormalByteOrHorizon(byte b) {
        return b >= 97 && b <= 122 || b >= 48 && b <= 57 || b >= 65 && b <= 90 || b == 45 || b == 95;
    }

    /**
     * "[a-zA-Z0-9][-a-zA-Z0-9_]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9_]{0,62})+\\.?";
     *
     * @param urlBytes
     * @return String[]
     */
    public static String[] getFullDomainWithBareUrl(byte[] urlBytes) {
        int start = skipHttpOrHttps(urlBytes);
        if (urlBytes.length - start < 3) {
            return null;
        }
        int fullDomainEndAt = urlBytes.length;
        boolean atLeastOneDot = false;
        boolean metDot = true;
        boolean afterMetDot = true;
        int sinceDot = 0;
        for (int i = start; i < urlBytes.length; i++) {
            byte b = urlBytes[i];
            if (isDomainOver(b)) {
                fullDomainEndAt = i;
                break;
            } else if (metDot && !isNormalByte(b) || afterMetDot && b != 46 && !isNormalByteOrHorizon(b) || metDot && b == 46 || sinceDot > 63) {
                return null;
            }
            if (!metDot) {
                afterMetDot = true;
            }
            metDot = b == 46;
            if (metDot) {
                atLeastOneDot = true;
                sinceDot = 0;
            } else {
                sinceDot++;
            }
        }
        if (atLeastOneDot) {
            return new String[]{new String(Arrays.copyOfRange(urlBytes, start, fullDomainEndAt)), new String(Arrays.copyOfRange(urlBytes, start, urlBytes.length))};
        } else {
            return null;
        }
    }

    /**
     * @param character
     * @return
     * @see
     */
    public static String toLowerCase(String character) {
        char[] characters = character.toCharArray();

        for (int i = 0; i < characters.length; i++) {
            if (characters[i] >= 65 && characters[i] <= 90) {
                characters[i] = (char) (32 + characters[i]);
            }
        }
        return String.valueOf(characters);
    }

    /**
     * @param value
     * @return null?"":val
     */
    public static String trimAsterisk(String value) {
        if (value == null)
            return "";
        char[] tca = value.toCharArray();
        int i = 0, j = tca.length - 1;
        while (i <= j && tca[i] == Constant.PUB_ASTERISK_CHAR) {
            i++;
        }
        while (j > i && tca[j] == Constant.PUB_ASTERISK_CHAR) {
            j--;
        }
        return (i == 0 && j == tca.length - 1) ? value : value.substring(i, ++j);
    }

}
