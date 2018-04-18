<%--
  Created by IntelliJ IDEA.
  User: won
  Date: 2018. 4. 10.
  Time: PM 3:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Hello, Spring Boot !

<form style="text-align: center;" role="form"
      action="<c:url value = "/lol/infoPage"/>" method="get">
    <strong>소환사</strong> : <input type="text" name="summonerName"
                                  id="summonerName"> <input type="submit" value="검색"
                                                            id="submitBtn" placeholder="소환사명...">
</form>
</body>
</html>
