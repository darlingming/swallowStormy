import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @Description
 * @author DM
 * @date 2017
 * @version v1.0.0.1
 *
 */
public class C3 {

    public C3() {
        // TODO Auto-generated constructor stub
    }

    public static void addScripture(List<String> st) {
        st.add("罗  8:12-17");
        st.add("罗  8:18-25");
        st.add("罗  8:26-30");
        st.add("罗  8:31-39");
        st.add("罗  9:1-13");
    }

    // 赞美人员
    public static void addGlorifyUser(List<String> gu) {
        gu.add("江珊珊");
        gu.add("许崇月");
        gu.add("周晨光");
        gu.add("韩珊珊");
        gu.add("席莹莹");
    }

    // 主持
    public static void addEmceeUser(List<String> eu) {
        eu.add("李  明");//
//		eu.add("张来鑫");
        eu.add("韩珊珊");
        eu.add("席莹莹");
        eu.add("许崇月");//
        eu.add("江珊珊");
    }

    public static void checkSort(List<String> gu, List<String> eu) {
        int m = 0;
        for (int i = 0; i < gu.size(); i++) {
//			System.out.println(gu.get(i)+"=="+eu.get(i));
            if (gu.get(i).compareTo(eu.get(i)) == 0) {
                Collections.shuffle(gu);
                Collections.shuffle(eu);
                i = -1;
                m++;
            }
            if (m > 10000) {
                System.out.println("重复次数过多！！！");
                break;
            }
        }

    }

    public static void getEuNeq(String gu, int poi, List<String> eu) {
        int m = 0;
        for (int i = poi; i < eu.size(); i++) {
            if (gu.compareTo(eu.get(i)) == 0) {

                i = -1;
                m++;
            }
            if (m > 10000) {
                System.out.println("重复次数过多！！！");
                break;
            }
        }

    }

    public static void outputC3msg() {
        List<String> st = new ArrayList<String>();
        addScripture(st);
        // 添加赞美人员
        List<String> gu = new ArrayList<String>();
        addGlorifyUser(gu);
        Collections.shuffle(gu);
        // 添加主持人员
        List<String> eu = new ArrayList<String>();
        addEmceeUser(eu);
        Collections.shuffle(eu);
        // 校验是否主和攒一致
        checkSort(gu, eu);

        DateFormat dft = new SimpleDateFormat("yyyy年MM月dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + 1);
        int week = date.get(Calendar.WEEK_OF_MONTH);
        Calendar calendar = (Calendar) date.clone();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 8);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(dft.format(date.getTime()) + "至" + day + "日(5:50-6:30)灵修计划 ");
        System.out.println();
        for (int i = 0; i < gu.size(); i++) {
            switch (i) {
                case 0:
                    System.out.println("星期一 赞美:" + gu.get(i) + ",主持:" + eu.get(i) + " " + st.get(i));
                    break;
                case 1:
                    System.out.println("星期二 赞美:" + gu.get(i) + ",主持:" + eu.get(i) + " " + st.get(i));
                    break;
                case 2:
                    System.out.println("星期三 赞美:" + gu.get(i) + ",主持:" + eu.get(i) + " " + st.get(i));
                    break;
                case 3:
                    System.out.println("星期四 赞美:" + gu.get(i) + ",主持:" + eu.get(i) + " " + st.get(i));
                    break;
                case 4:
                    System.out.println("星期五 赞美:" + gu.get(i) + ",主持:" + eu.get(i) + " " + st.get(i));
                    break;
                default:
                    System.out.println("ok");

            }
        }
        System.out.println();
        System.out.println("大家看看,谁又不方便的可以调换,收到请回复");
    }

    public static void main(String[] args) {
        outputC3msg();
//		String aa ="dsaf|dsaf|ddd";
//		String[] as =aa.split( "\\|" );
//		for (int i = 0; i < as.length; i++) {
//			System.err.println(as[i]);
//		}


    }

}
