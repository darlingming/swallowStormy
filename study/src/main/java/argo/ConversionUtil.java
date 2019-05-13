package argo;


import org.apache.commons.lang.StringUtils;

/**
 * 10进制、62进制互转
 * author by DM
 */
public class ConversionUtil {
    /**
     * 初始化 62 进制数据，索引位置代表字符的数值，比如 A代表10，z代表61等
     */
    private static String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static int scale = 36;

    /**
     * 将数字转为62进制
     *
     * @param num    Long 型数字
     * @param length 转换后的字符串长度，不足则左侧补0
     * @return 62进制字符串
     */
    public static String encode(long num, int length) {
        StringBuilder sb = new StringBuilder();
        int remainder = 0;

        while (num > scale - 1) {
            /**
             * 对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转（reverse）字符串
             */
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));

            num = num / scale;
        }

        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
//        StringUtils.leftPad(value, length, '0');
        String vs = StringUtils.reverse(value);
        System.out.println(vs);
        return value;
    }

    /**
     * 62进制字符串转为数字
     *
     * @param str 编码后的62进制字符串
     * @return 解码后的 10 进制字符串
     */
    public static long decode(String str) {
        /**
         * 将 0 开头的字符串进行替换
         */
        str = str.replace("^0*", "");
        long num = 0;
        int index = 0;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            /**
             * 查找字符的索引位置
             */
            index = chars.indexOf(str.charAt(i));
            /**
             * 索引位置代表字符的数值
             */
            num += (long) (index * (Math.pow(scale, str.length() - i - 1)));
        }

        return num;
    }

    /**
     * @param str
     * @return
     */
    public static String reverseString(String str) {
        return str == null ? null : (new StringBuffer(str)).reverse().toString();
    }

    /**
     * @param value
     * @return
     */
    private static String encodeString(String value) {
//        System.out.println("encodeString===" + value);
        if (value.length() <= 2) {
            return reverseString(value);
        }
        StringBuffer odd = new StringBuffer(1024);

        StringBuffer even = new StringBuffer(1024);
        for (int i = 0; i < value.length(); i++) {
            if ((i & 1) == 0) {
                even.append(value.charAt(i));
            } else {
                odd.append(value.charAt(i));
            }
        }
        return encodeString(even.toString()) + encodeString(odd.toString());
    }

    /**
     * @param value
     * @return
     */
    private static String decodeString(String value) {
//        System.out.println("decodeString >>>>" + value + "========");
        int len = value.length();
        if (len <= 2) {
            return reverseString(value);
        }
        String leftStr = null;
        String rightStr = null;
        if ((len & 1) == 0) {
            leftStr = value.substring(0, len >> 1);
            rightStr = value.substring(len >> 1, len);
        } else {
            leftStr = value.substring(0, (len >> 1) + 1);
            rightStr = value.substring((len >> 1) + 1, len);
        }
        return unionString(decodeString(leftStr), decodeString(rightStr));
    }

    /**
     * @param left
     * @param right
     * @return
     */
    private static String unionString(String left, String right) {
//        System.out.println("a >>>>" + left + "========" + right);
        StringBuffer sb = new StringBuffer(1024);
        int lrLen = left.length() + right.length();
        int l = 0;
        int m = 0;
        for (int i = 0; i < lrLen; i++) {
            if ((i & 1) == 0) {
                sb.append(left.charAt(l++));
            } else {
                sb.append(right.charAt(m++));
            }
        }
//        System.out.println("sb====" + sb.toString());
        return sb.toString();
    }



    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("62进制：" + encode(19999999999l, 11));
        System.out.println("10进制：" + decode("96rherj"));

        String val = encodeString("我是王文博");
        String val1 = encodeString(val);
        String val_1 = decodeString(val);
        System.out.println(val + "===" + val_1 + "=="+val1+"=" + (19 >> 1));
    }

}
