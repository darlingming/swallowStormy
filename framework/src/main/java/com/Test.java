package com;

import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils;
import com.software.dm.swallow.stormy.hadoop.tools.DataConvert;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @preserve
 */
public class Test {

    /**
     * @preserve
     */
    public Test() {
        // TODO Auto-generated constructor stub
    }

    // public static void main(String[] args) {
    // // TODO Auto-generated method stub
    //
    // }

    /**
     * @preserve
     */
    public static void main(String[] args) throws IOException {
        List<String> stringCollection = new ArrayList<String>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");

        String[] a = new String[]{"2132132"};
        String aa = new String(DataConvert.parseData("201620,279350,283443,345142,226147,12851,205667,280117,279364,145458,218212,79929,13620,144"), "gbk");
        System.out.println(aa);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        System.out.println(System.currentTimeMillis());
        Long s;
        System.out.println(sdf.format(1467170975132l));
        String regex = "(?:\\?|&)(?:.*=)([^&]+@[^&]+)(?:&?|&.*)$";
        regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p1 = Pattern.compile(regex);
        Matcher m = p1.matcher("ac.atpanel.com?email=darlingming@126.com.cn&dfad=darlingming@125.com");
        if (m.find()) {
            System.out.println(m.group(0));
        }

        Pattern p = Pattern.compile("(?:wx\\.qlogo\\.cn/mmhead/.*/)([\\w]{20,}).*");
        // Matcher m =
        // p.matcher("http://wx.qlogo.cn/mmhead/ver_1mmopen/mHbh0oVlzbTarFwiaA34QreB1jVSVtBRt31RcrgTjicaWY4VicocW2CRSLCLAbz9JpjiagcDoEfIbCWiaUiaM5KQVcicEOUJicTibr2Ql/0");
        m = p.matcher("http://wx.qlogo.cn/mmhead/ver_1/waWFJgkZsUDRLHiaicRUtciciajfjWYs51ia9rCrWRDFoiav4vIehxImHppzaB9tpF5wUyBVork0jR7DjaKJ4QSqXB1Q/0?randid=6772");
        if (m.find()) {
            System.out.println(m.group(1));
        }
        for (int i = 0; i < 10; i++) {
            int a1 = AbstractCommonUtils.encode(10 + i);
            int b = AbstractCommonUtils.decode(a1);
            System.out.println("" + a1 + "=" + b);
        }

        char[] chars = new char[100];
        chars[50] = '\n';
        String source = " A B C\t n                		 \rD EF G ";

        // System.out.println(source);
        chars[4] = 'C';
        chars[10] = '\r';
        chars[15] = '\t';
        chars[99] = ' ';
        System.out.println(1 + Character.MAX_VALUE);
        System.out.println("A" + Character.MAX_VALUE + "===" + AbstractCommonUtils.convertChar(source, chars) + "===");

        final char[] ch22ars = new char[Character.MAX_VALUE + 10000];
        System.out.println(fibonacci(9));
    }

    public static int fibonacci(int num) {
        if (num == 1 || num == 2) {
            return 1;
        }
        return fibonacci(num - 1) + fibonacci(num - 2);
    }

}
