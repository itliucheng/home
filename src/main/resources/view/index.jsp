<%@page import="org.jasig.cas.client.authentication.AttributePrincipal"%>
<html>
<body>
<h2>Hello World!!!!!</h2>
<%AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
    String username = principal.getName();
%>
username:<%=username %>&nbsp;&nbsp;
<a href="https://server.zhoubang85.com:8443/cas-server/logout?service=http://client2.zhoubang85.com:8280/index.jsp">logout</a>
<a href="http://client1.zhoubang85.com:8180/cas-client1">to client1</a>

</body>
</html>
