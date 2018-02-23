package http;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void aa() {
        // URL列表数组
        String[] urisToGet = {
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",

                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",

                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",

                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",

                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",

                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497",
                "http://blog.csdn.net/catoop/article/details/38849497"};

        long start = System.currentTimeMillis();
        try {
            int pagecount = urisToGet.length;
            pagecount = 0;
            System.out.println("pagecount==" + pagecount);
            ExecutorService executors = Executors.newFixedThreadPool(pagecount);
            CountDownLatch countDownLatch = new CountDownLatch(pagecount);
            for (int i = 0; i < pagecount; i++) {
                // 启动线程抓取
                executors.execute(HttpConnectionManager.getInstance().new GetRunnable(urisToGet[i], countDownLatch));
            }
            countDownLatch.await();
            executors.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("线程" + Thread.currentThread().getName() + ","
                    + System.currentTimeMillis() + ", 所有线程已完成，开始进入下一步！");
        }
        //HttpConnectionManager.getInstance().close();
        long end = System.currentTimeMillis();
        System.out.println("consume -> " + (end - start));
    }

    public static void main(String[] args) throws IOException {
        //uploadFile();

        execWebHbase();
        //exec();
    }

    public static void uploadFile() {
        String filePath = "D:\\DearM\\dm\\apache-maven-3.3.9.rar";

        String url = "http://127.0.0.1:9000/SAPI/servlet/UploadServlet";
        HttpConnectionManager.getInstance().upload(url, filePath);
    }

    /**
     * @throws IOException
     */
    public static void exec() throws IOException {
        long start = System.currentTimeMillis();
        try {
            int pagecount = 2;
            System.out.println("pagecount==" + pagecount);
            ExecutorService executors = Executors.newFixedThreadPool(pagecount);
            CountDownLatch countDownLatch = new CountDownLatch(pagecount);
            for (int i = 0; i < pagecount; i++) {
                // 启动线程抓取
                executors.execute(HttpConnectionManager.getInstance().new GetRunnable("http://127.0.0.1:9000/SAPI/api/tarzanApi/stateQuery?isSeNum=" + i + "", countDownLatch));
            }
            countDownLatch.await();
            executors.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("线程" + Thread.currentThread().getName() + ","
                    + System.currentTimeMillis() + ", 所有线程已完成，开始进入下一步！");
        }
        HttpConnectionManager.getInstance().close();
        long end = System.currentTimeMillis();
        System.out.println("consume -> " + (end - start));
    }
    //http://127.0.0.1:9000/SAPI/api/tarzanApi/stateQuery?isSeNum=2&isReqStr=2
    /**
     * @throws IOException
     */
    public static void execWebHbase() throws IOException {
        String url="http://localhost:8080/webhabse/loginServlet";
        long start = System.currentTimeMillis();
        try {

            int pagecount = 1000;
            System.out.println("pagecount==" + pagecount);
            ExecutorService executors = Executors.newFixedThreadPool(pagecount);
            CountDownLatch countDownLatch = new CountDownLatch(pagecount);

            for (int i = 0; i < pagecount; i++) {
                // 启动线程抓取
                executors.execute(new HbaseRunnable(url, countDownLatch,"DM["+i+"]",1000));
            }
            countDownLatch.await();
            executors.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("线程" + Thread.currentThread().getName() + ","
                    + System.currentTimeMillis() + ", 所有线程已完成，开始进入下一步！");
        }
        HttpConnectionManager.getInstance().close();
        long end = System.currentTimeMillis();
        System.out.println("consume -> " + (end - start));
    }

}
