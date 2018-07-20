package com.software.dm.swallow.stormy.web.action;

import com.software.dm.swallow.stormy.hbase.demo.CellData;
import com.software.dm.swallow.stormy.hbase.demo.HbaseOpertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LoginServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(LoginServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //1.设置response的编码为utf-8
        response.setCharacterEncoding("utf-8");
        //2.通知浏览器，以UTF-8的编码打开
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("controller");
        action = action == null ? "" : action;
        switch (action) {
            case "goLogin":
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            case "login":
                this.login(request, response);
                break;
            case "register":
                this.register(request, response);
                break;
            case "goRegister":
                request.getRequestDispatcher("view/register.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("index.jsp").forward(request, response);

        }


    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String passwd = request.getParameter("passwd");
        logger.info("username=" + username + " passwd=" + passwd);
        HbaseOpertion hbaseOpertion = null;

        String urljsp = "index.jsp";
        try {
            hbaseOpertion = HbaseOpertion.getInstance();
            String tableName = "dm:t_test";
            String rowKey = username;
            String columnFamily = "user";
            String column = "passwd";
            String value = passwd;
            CellData cd = hbaseOpertion.getRowByKey(tableName, rowKey);
            if (cd == null) {
                request.setAttribute("msg", "注册成功!!!");
                hbaseOpertion.addRow(tableName, rowKey, columnFamily, column, value);
                logger.info("注册成功!!!");
            } else {
                logger.warn("已存在注册用户");
                request.setAttribute("msg", "已存在注册用户!!!");
                urljsp = "view/register.jsp";
            }


        } catch (IOException e) {
            logger.error("register:", e);
        }

        request.getRequestDispatcher(urljsp).forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String passwd = request.getParameter("passwd");
        logger.info("username=" + username + " passwd=" + passwd);
        request.setAttribute("username", username);

        request.setAttribute("passwd", passwd);
        boolean bool = login(username, passwd);
        String urljsp = "index.jsp";
        if (bool) {
            logger.info("登陆成功");
            urljsp = "view/main.jsp";
        } else {
            logger.info("登陆失败！！");
            request.setAttribute("msg", "登陆失败!!!");
        }
        request.getRequestDispatcher(urljsp).forward(request, response);
    }

    private boolean login(String username, String passwd) {
        boolean rebool = false;
        long start = System.currentTimeMillis();
        String tableName = "dm:t_test";
        HbaseOpertion hbaseOpertion = null;
        try {
            hbaseOpertion = HbaseOpertion.getInstance();

            String rowKey = username;
            String columnFamily = "user";
            String column = "passwd";
            String value = passwd;

            List<CellData> listCD = hbaseOpertion.getRow(tableName, rowKey, columnFamily, column);
            if (null != listCD)
                for (CellData cd : listCD) {
                    if (passwd.equals(cd.getValue())) {
                        rebool = true;
                        break;
                    }
                }

            column = "js";
            long reslong = hbaseOpertion.incrementColumnValue(tableName, rowKey, columnFamily, column, 1);
            logger.info("计数器值：" + reslong);

        } catch (Exception e) {
            logger.error("HbaseOpertion Exception ", e);
        } finally {
            //hbaseOpertion.closeConnection();
        }
        long end = System.currentTimeMillis();
        logger.info("consume -> " + (end - start));

        return rebool;
    }
}
