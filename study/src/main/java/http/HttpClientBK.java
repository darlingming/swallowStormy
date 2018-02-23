package http;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class HttpClientBK {
    public static final String baseUrl = "http://paas.ultradata.com";
    public static final String app_code = "bkdmtest";
    public static final String app_secret = "aaaa-bbbb";
    public static final String username = "admin";

    public static void main(String[] args) {
        HttpClientBK hcbk = new HttpClientBK();
        hcbk.get_task();
    }

    /**
     *
     */
    public void get_all_user() {
        final String app_url = "/api/c/compapi/bk_login/get_all_user/";
        String url = this.getUrl(app_url);
        String result = HttpClientTools.HttpGet(url);
        if (null != result)
            System.out.println(result);
    }

    private String getUrl(String app_url) {
        return baseUrl + "/" + app_url + "?app_code=" + app_code + "&app_secret=" + app_secret + "&username=" + username;
    }

    /**
     * 更新定时作业状态 ，如启动或暂停；操作者必须是业务的创建人或运维
     */
    public void change_cron_status() {
        String url = baseUrl + "/api/c/compapi/job/change_cron_status/";
        List<BasicNameValuePair> list = this.initBasicNameValue();
        list.add(new BasicNameValuePair("app_id", "2"));
        list.add(new BasicNameValuePair("status", "1"));
        list.add(new BasicNameValuePair("crontab_task_id", "1"));


        String result = HttpClientTools.httpPost(url, list);
        if (null != result)
            System.out.println(result);
    }

    private List<BasicNameValuePair> initBasicNameValue() {
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        list.add(new BasicNameValuePair("app_code", app_code));
        list.add(new BasicNameValuePair("app_secret", app_secret));
        list.add(new BasicNameValuePair("username", username));

        return list;
    }

    /**
     *
     */
    public void add_module() {
        String url = baseUrl + "/api/c/compapi/cc/add_module/";
        List<BasicNameValuePair> list = this.initBasicNameValue();
        list.add(new BasicNameValuePair("app_id", "2"));
        list.add(new BasicNameValuePair("set_id", "1"));

        String result = HttpClientTools.httpPost(url, list);
        if (null != result)
            System.out.println(result);

    }

    /**
     * 新建业务
     */
    public void add_app() {
        String url = baseUrl + "/api/c/compapi/cc/add_app/";
        url="http://paas.ultradata.com:80/api/c/compapi/cc/add_app/";
        List<BasicNameValuePair> list = this.initBasicNameValue();
        list.add(new BasicNameValuePair("app_id", "2"));
        list.add(new BasicNameValuePair("app_name", "业务名称"));

        list.add(new BasicNameValuePair("maintainers", "admin"));
        list.add(new BasicNameValuePair("company_name", "company_name"));
        list.add(new BasicNameValuePair("level", "3"));
        list.add(new BasicNameValuePair("life_cycle", "life_cycle"));


        String result = HttpClientTools.httpPost(url, list);
        if (null != result)
            System.out.println(result);

    }

    /**
     *
     */
    public void save_cron() {

        String url = baseUrl + "/api/c/compapi/job/save_cron/";
        List<BasicNameValuePair> list = this.initBasicNameValue();
        list.add(new BasicNameValuePair("app_id", "2"));
        list.add(new BasicNameValuePair("name", "sjgl5"));
        list.add(new BasicNameValuePair("task_id", "2"));
        list.add(new BasicNameValuePair("crontab_task_id", "2"));
        list.add(new BasicNameValuePair("cron_expression", "0 5 * * * ?"));


        String result = HttpClientTools.httpPost(url, list);
        if (null != result)
            System.out.println(result);

    }

    /**
     * 根据作业实例 ID 查询作业执行状态
     * 执行历史
     *
     * @param task_instance_id
     */
    public void get_task_result(String task_instance_id) {
        final String app_url = "/api/c/compapi/job/get_task_result/";

        task_instance_id = "1";
        String url = this.getUrl(app_url) + "&task_instance_id=" + task_instance_id;
        String result = HttpClientTools.HttpGet(url);
        if (null != result)
            System.out.println("get_task_result=" + result);
    }

    /**
     * 根据作业实例ID查询作业执行日志
     * <p>
     * 执行历史详情
     */
    public void get_task_ip_log() {
        final String app_url = "/api/c/compapi/job/get_task_ip_log/";

        String task_instance_id = "1";
        String url = this.getUrl(app_url) + "&task_instance_id=" + task_instance_id;
        String result = HttpClientTools.HttpGet(url);
        if (null != result)
            System.out.println("get_task_ip_log=" + result);
    }


    /**
     * 查询业务列表
     */
    public void get_app_list () {
        final String app_url = "/api/c/compapi/cc/get_app_list";
        String url = this.getUrl(app_url)  ;
        String result = HttpClientTools.HttpGet(url);
        if (null != result)
            System.out.println("get_app_list=" + result);
    }

    /**
     * 查询业务下定时作业信息
     */
    public void get_cron() {
        final String app_url = "/api/c/compapi/job/get_cron/";

        String app_id = "2";
        String url = this.getUrl(app_url) + "&app_id=" + app_id;
        String result = HttpClientTools.HttpGet(url);
        if (null != result)
            System.out.println("get_cron=" + result);
    }

    /**
     * 获取定时作业列表
     * 查询作业模板详情
     */
    public void get_task_detail() {
        final String app_url = "/api/c/compapi/job/get_cron/";

        String app_id = "2";
        String task_id = "1";
        String url = this.getUrl(app_url) + "&app_id=" + app_id + "&task_id=" + task_id;
        String result = HttpClientTools.HttpGet(url);
        if (null != result)
            System.out.println("get_task_detail=" + result);
    }

    /**
     *
     */
    public void get_task() {
        final String app_url = "/api/c/compapi/job/get_task/";

        String app_id = "2";

        String url = this.getUrl(app_url) + "&app_id=" + app_id;
        String result = HttpClientTools.HttpGet(url);
        if (null != result)
            System.out.println("get_task=" + result);
    }
    //public static void login (){
    //    List<BasicNameValuePair> list=getParam();
    //    String loginUrl=NewrankConfig.URL_ROOT+NewrankConfig.URL_LOGIN;
    //    String LoginResponse= HttpClientTools.httpPost(loginUrl, list);
    //    if(LoginResponse==null){
    //        logger.error("登录没有返回");
    //        return ;
    //    }
    //
    //    JSONObject json = JSONObject.fromObject(LoginResponse);
    //
    //    Object success=json.get("success");
    //    if(!(boolean)success){
    //        logger.error("登录失败："+json);
    //        return ;
    //    }
    //    JSONObject value = json.getJSONObject("value");
    //    XinbangUser ben = (XinbangUser) JSONObject.toBean(value, XinbangUser.class);
    //    setCookieStore(ben);
    //}
    //public static void setCookieStore(XinbangUser ben) {
    //    BasicClientCookie cookie = new BasicClientCookie("token", ben.getToken());
    //    cookie.setDomain("www.newrank.cn");
    //    cookie.setPath("/");
    //    cookie.setVersion(0);
    //    HttpClientTools.cookieStore.addCookie(cookie);
    //
    //}
}