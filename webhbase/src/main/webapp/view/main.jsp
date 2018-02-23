<%@ page language="java" contentType="text/html; charset=utf-8"   isELIgnored="false"  pageEncoding="utf-8" %>
<html>
<body>

<form  action="<%=request.getContextPath()%>/loginServlet" method="post"   name="form" >
    <input type="hidden" name="action" value="goLogin"/>

    <table  bgcolor="#a9a9a9" width="100%" style="height: 100%"   >
        <tr>
            <td colspan="2" align="center"><h2>main</h2></td>

        </tr>
        <tr>
            <td align="right">用户：</td>
            <td align="left">${username} </td>
        </tr>
        <tr>
            <td align="right">密码：</td>
            <td align="left"> ${passwd}  </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
               <input type="submit" value="退出">&nbsp;&nbsp;&nbsp;&nbsp;
            </td>

        </tr>
    </table>
</form>
</body>
</html>
