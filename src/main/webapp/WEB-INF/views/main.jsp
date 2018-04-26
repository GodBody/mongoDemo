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
    <title>Main</title>
    <script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<body>
Main Page
<form id="form" action="search" method="get" role="form">

    소환사 : <input type="text" id="summonerName">
    <input type="submit" value="search">
</form>

<button type="button" id="submit">검색</button>
<button type="button" id="update">업데이트</button>
</body>
<script>
    $("#submit").on("click", function () {
        var name = $("#summonerName").val();
        console.log(name);

        $.get("http://localhost:8080/search?summonerName=" + name);
           self.location = "http://localhost:8080/search?summonerName=" + name;

    });

    $("#update").on("click", function () {
        var name = $("#summonerName").val();
        console.log(name);

        $.get("http://localhost:8080/update?summonerName=" + name);
        self.location = "http://localhost:8080/search?summonerName=" + name;

    });

</script>

</html>
