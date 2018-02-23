package http;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author darlingming@126.com
 * @since HttpClinetConnection
 */
public final class HttpConnectionManager {

    //HttpConnectionManager.class
    private static final Logger logger = LoggerFactory.getLogger(HttpConnectionManager.class);
    //LoggerFactory.getLogger("http.HttpConnectionManager");
    private static HttpConnectionManager hcm = null;

    public org.apache.http.client.CookieStore cookieStore = new BasicCookieStore();
    private PoolingHttpClientConnectionManager connMgr = null;
    private HttpRequestRetryHandler myRetryHandler = null;
    // 配置超时时间
    private RequestConfig requestConfig = null;

    /**
     * 获取实例
     *
     * @return
     */
    public static HttpConnectionManager getInstance() {
        if (null == hcm) {
            synchronized (logger) {
                hcm = new HttpConnectionManager();
            }
        }
        return hcm;
    }

    /**
     * 自定义连接存活策略
     *
     * @param response
     * @param context
     * @return
     */
    private ConnectionKeepAliveStrategy myStrategy = null;
    private CloseableHttpClient closeableHttpClient = null;

    private synchronized CloseableHttpClient getHttpCilent() {

        if (null == closeableHttpClient) {
            synchronized (logger) {
                closeableHttpClient = HttpClients.custom() //aa
                        //.setDefaultCookieStore(cookieStore) //bb
                        .setKeepAliveStrategy(myStrategy) //cc
                        .setConnectionManager(connMgr) //cdd
                        .setRetryHandler(myRetryHandler) //fff
                        .build();
            }
        }


        return closeableHttpClient;
    }

    /**
     * 初始化构造函数
     */
    private HttpConnectionManager() {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", plainsf).register("https", sslsf).build();

        connMgr = new PoolingHttpClientConnectionManager(registry) {
            {
                setMaxTotal(5000);
                setDefaultMaxPerRoute(2001);

            }
        };

        myRetryHandler = (IOException exception, int executionCount, HttpContext context) -> {
            if (executionCount >= 5) {// 如果超过最大重试次数，那么就不要继续了
                return false;
            }
            if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                return true;
            }
            if (exception instanceof InterruptedIOException) {// 超时
                return false;
            }
            //if (exception instanceof UnknownHostException) {// 目标服务器不可达
            //    return false;
            //}
            if (exception instanceof ConnectTimeoutException) {// 连接被拒绝

                return false;
            }
            if (exception instanceof SSLException) {// SSL握手异常
                return false;
            }

            if (!(HttpClientContext.adapt(context).getRequest() instanceof HttpEntityEnclosingRequest)) { // 如果请求是幂等的，就再次尝试
                return true;
            }

            return false;

        };

        myStrategy = (HttpResponse response, HttpContext context) -> {
            // Honor 'keep-alive' header
            HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    try {
                        logger.warn("CONN_KEEP_ALIVE value=" + value);
                        return Long.parseLong(value) * 1000;
                    } catch (NumberFormatException ignore) {
                    }
                }
            }
            HttpHost target = (HttpHost) context.getAttribute(HttpClientContext.HTTP_TARGET_HOST);
            if ("domain".equalsIgnoreCase(target.getHostName())) {
                // Keep alive for 5 seconds only
                return 5 * 1000;
            } else {
                // otherwise keep alive for 30 seconds
                return 30 * 1000;
            }
        };

        requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)    // 请求超时时间
                .setConnectionRequestTimeout(10 * 1000) // 连接超时时间
                .setSocketTimeout(10000)   // 等待数据超时时间
                .setRedirectsEnabled(true) // 默认允许自动重定向
                .build();


    }


    /**
     * @param url
     * @param list
     * @return
     */
    public String httpPost(String url, List<BasicNameValuePair> list) {

        HttpPost httpPost = new HttpPost(url);
        httpPost.releaseConnection();
        // 设置超时时间
        httpPost.setConfig(requestConfig);

        String strResult = "";
        int StatusCode = 404;
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
            // 设置post求情参数
            httpPost.setEntity(entity);
            CloseableHttpResponse httpResponse = getHttpCilent().execute(httpPost);

            if (httpResponse != null) {
                StatusCode = httpResponse.getStatusLine().getStatusCode();
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                    //logger.info("post/" + StatusCode + ":" + strResult);
                    return strResult;
                } else {
                    strResult = "Error  Response: " + httpResponse.getStatusLine().toString();
                    logger.info("post/" + StatusCode + ":" + strResult);
                    strResult = null;
                }

                httpResponse.close();
            } else {

            }

        } catch (Exception e) {
            logger.error("httpPost", e);
        } finally {

        }
        httpPost.releaseConnection();
        return strResult;
    }

    /**
     * @param url
     * @return
     */
    public String HttpGet(String url) {

        HttpGet httpGet2 = new HttpGet(url);
        httpGet2.setConfig(requestConfig);
        String srtResult = null;
        int StatusCode = 404;
        try {
            CloseableHttpResponse httpResponse = getHttpCilent().execute(httpGet2);
            StatusCode = httpResponse.getStatusLine().getStatusCode();
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                srtResult = EntityUtils.toString(httpResponse.getEntity());// 获得返回的结果
                logger.info("get/ " + StatusCode + ":" + srtResult);
                return srtResult;
            } else {
                srtResult = EntityUtils.toString(httpResponse.getEntity());// 获得返回的结果
                logger.info("get/ " + StatusCode + ":" + srtResult);
                return null;
            }
        } catch (IOException e) {
            //e.printStackTrace();
            logger.error("HttpGet=", e);
        } finally {

        }
        return null;
    }


    /**
     * 上传文件
     */
    public void upload(String url, String filePath) {
        //CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpClient httpclient = getHttpCilent();
        try {
            HttpPost httpPost = new HttpPost(url);
            FileBody bin = new FileBody(new File(filePath));
            StringBody comment = new StringBody("A binary file  of some kind", ContentType.TEXT_PLAIN);
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("bin", bin).addPart("comment", comment).build();
            httpPost.setEntity(reqEntity);
            logger.info("executing  request " + httpPost.getRequestLine());
            String strResult = null;
            int StatusCode = 404;
            CloseableHttpResponse httpResponse = getHttpCilent().execute(httpPost);
            if (httpResponse != null) {
                StatusCode = httpResponse.getStatusLine().getStatusCode();
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                    logger.info("post/" + StatusCode + ":" + strResult);
                } else {
                    strResult = "Error  Response: " + httpResponse.getStatusLine().toString();
                    logger.info("post/" + StatusCode + ":" + strResult);
                }
                EntityUtils.consume(httpResponse.getEntity());
                httpResponse.close();
            } else {

            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setCookieStore(List<BasicClientCookie> cookielist) {
        for (BasicClientCookie cookie : cookielist) {
            cookieStore.addCookie(cookie);
        }
    }

    public void createCookie(List<BasicClientCookie> cookielist) {
        for (BasicClientCookie cookie : cookielist) {
            cookieStore.addCookie(cookie);
        }
    }

    public void close() throws IOException {
        this.connMgr.closeExpiredConnections(); // 关闭失效的连接
        // 可选的, 关闭30秒内不活动的连接
        this.connMgr.closeIdleConnections(50, TimeUnit.SECONDS);
    }


    private void setPostParams(HttpPost httpost, Map<String, Object> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
        }
        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void config(HttpRequestBase httpRequestBase) {
        // 设置Header等
        // httpRequestBase.setHeader("User-Agent", "Mozilla/5.0");
        // httpRequestBase
        // .setHeader("Accept",
        // "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        // httpRequestBase.setHeader("Accept-Language",
        // "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");// "en-US,en;q=0.5");
        // httpRequestBase.setHeader("Accept-Charset",
        // "ISO-8859-1,utf-8,gbk,gb2312;q=0.7,*;q=0.7");


        httpRequestBase.setConfig(requestConfig);
    }

    public String post(String url, Map<String, Object> params) throws IOException {
        HttpPost httppost = new HttpPost(url);
        config(httppost);
        setPostParams(httppost, params);
        CloseableHttpResponse response = null;
        try {
            response = this.getHttpCilent().execute(httppost, HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
//          e.printStackTrace();
            throw e;
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param url
     * @return
     */
    public String get(String url) {
        HttpGet httpget = new HttpGet(url);
        config(httpget);
        CloseableHttpResponse response = null;
        try {
            response = getHttpCilent().execute(httpget, HttpClientContext.create());
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity, "utf8");
            EntityUtils.consume(httpEntity);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    class GetRunnable implements Runnable {
        private CountDownLatch countDownLatch;
        private String url;

        public GetRunnable(String url, CountDownLatch countDownLatch) {
            this.url = url;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                //System.err.println("HttpConnectionManager=="+HttpConnectionManager.getInstance().getHttpCilent().toString());
                //HttpConnectionManager.getInstance().get(url);
                int i = 50;
                while (i > 0) {
                    logger.info(Thread.currentThread().getName() + "=" + HttpConnectionManager.getInstance().HttpGet(url + "&isReqStr=" + Thread.currentThread().getName() + ":" + +i));
                    i--;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            } finally {
                countDownLatch.countDown();
            }
        }
    }


}
