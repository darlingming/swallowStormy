<%@ page language="java" contentType="text/html; charset=utf-8"   isELIgnored="false"  pageEncoding="utf-8" %>
<html>
<title>注册</title>
<body>

<form  action="<%=request.getContextPath()%>/loginServlet" method="post"  name="form" >
<input type="hidden" name="action" value="register"/>

<table   bgcolor="#7fffd4" width="100%"  style="height: 100%"   >
    <tr >
        <td colspan="3" align="center"> <h2>REGISTER!</h2></td>

    </tr>
    <tr>
        <td>&nbsp;</td>
        <td align="right">用户：</td>
        <td><input type="text" name="username"   align="left"  /></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td align="right">密码：</td>
        <td><input type="password" name="passwd" align="left"/></td>
    </tr>
    <tr>
        <td colspan="3" align="center">
            <input type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;
        </td>

    </tr>
    <tr>
        <td colspan="3" align="center"> ${msg} </td>

    </tr>
</table>
</form>
</body>
</html>
