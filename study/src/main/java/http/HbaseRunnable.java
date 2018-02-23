package http;

import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author darlingming@126.com
 */
public class HbaseRunnable implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(HbaseRunnable.class);
    private CountDownLatch countDownLatch;
    private String url;
    private String flag;
    private long count = 0;
    public HbaseRunnable(String url, CountDownLatch countDownLatch,String flag,long count ) {
        this.url = url;
        this.countDownLatch = countDownLatch;
        this.flag = flag;
        this.count = count;

    }
    @Override
    public void run() {

        try {
            //HttpConnectionManager.getInstance().get(url);

            while (count > 0) {
                List<BasicNameValuePair> list = new ArrayList();
                list.add(new BasicNameValuePair("username",flag + Thread.currentThread().getName() + ":"+count)) ;
                list.add(new BasicNameValuePair("passwd","1")) ;
                list.add(new BasicNameValuePair("action","register")) ;
                HttpConnectionManager.getInstance().httpPost(url,list);

                count--;
            }


        } finally {
            countDownLatch.countDown();
        }

    }
}
