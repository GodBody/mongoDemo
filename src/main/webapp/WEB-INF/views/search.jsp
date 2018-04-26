<%@ page import="java.util.Collections" %>
<%@ page import="com.springboot.springbootdemo.domain.ChampionDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Comparator" %><%--
  Created by IntelliJ IDEA.
  User: won
  Date: 2018. 4. 10.
  Time: PM 9:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="dateValue" class="java.util.Date"/>
<html>
<head>
    <title>Search</title>
    <script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<body>

<%
    List<ChampionDTO> list = (List<ChampionDTO>) request.getAttribute("championList");
    Collections.sort(list, new Comparator<ChampionDTO>() {
        @Override
        public int compare(ChampionDTO o1, ChampionDTO o2) {
            double winRate1 = (double) o1.getWinCount() / (double) (o1.getWinCount() + (double) o1.getLossCount());
            double winRate2 = (double) o2.getWinCount() / (double) (o2.getWinCount() + (double) o2.getLossCount());

            if (winRate1 > winRate2)
                return -1;
            else if (winRate1 < winRate2)
                return 1;
            else {
                if (o1.getWinCount() > o2.getWinCount())
                    return -1;
                else
                    return 1;
            }
        }
    });
%>

<%--<c:forEach items="<%=list%>" var="champion">--%>
    <%--<img width="20px" height="20px"--%>
         <%--src="http://opgg-static.akamaized.net/images/lol/champion/<c:url value="${champion.image.full}"/>"/> ${champion.winCount}승 ${champion.lossCount}패 / 승률--%>
    <%--<fmt:formatNumber value="${champion.winCount / (champion.winCount + champion.lossCount) * 100}" pattern="00"/>% <br>--%>
<%--</c:forEach>--%>
<h3>챔피언 승률 통계</h3>
<c:forEach items="${championList}" var="champion">
    <img width="20px" height="20px"
         src="http://opgg-static.akamaized.net/images/lol/champion/<c:url value="${champion.image.full}"/>"/> ${champion.winCount}승 ${champion.lossCount}패 / 승률
    <fmt:formatNumber value="${champion.winCount / (champion.winCount + champion.lossCount) * 100}" pattern="00"/>% <br>
</c:forEach>

<br>
<br>

<h3>소환사 정보</h3>
<strong>${summoner.name}</strong> <br>
<c:forEach items="${summoner.positions}" var="position">
    <c:if test="${position.queueType eq 'RANKED_SOLO_5x5'}">
        <strong>솔랭</strong> - ${position.tier} : ${position.rank} : ${position.leaguePoints}p /   ${position.wins}승  ${position.losses}패 승률
        <fmt:formatNumber value="${position.wins / (position.wins + position.losses) * 100}" pattern="00"/>%
        <br>
    </c:if>

    <c:if test="${position.queueType eq 'RANKED_FLEX_SR'}">
        <strong>자랭</strong> - ${position.tier} : ${position.rank} : ${position.leaguePoints}p /   ${position.wins}승  ${position.losses}패 승률
        <fmt:formatNumber value="${position.wins / (position.wins + position.losses) * 100}" pattern="00"/>%
        <br>
    </c:if>

</c:forEach>

<c:forEach items="${current.participants}" var="participant">
    ${participant.championId} <br>

</c:forEach>


<c:forEach items="${mostList}" var="list">
    <img width="30px" height="30px"
         src="http://opgg-static.akamaized.net/images/lol/champion/<c:url value="${list.championName}"/>"/> : ${list.gameCount}전 ${list.winCount}승 ${list.looseCount}패 / 승률 : ${list.winRate}% / KDA : ${list.kdaRatio}
    <br>
</c:forEach>


<c:forEach items="${matchList}" var="match">
    <%--<h2>gameId : ${match.gameId}</h2>  ${match.gameCreation} / ${match.gameDuration} <br>--%>

    <jsp:setProperty name="dateValue" property="time" value="${match.gameCreation}"/>
    <fmt:formatDate value="${dateValue}" pattern="yyyy년 MM월 dd일 HH시 mm분 ss초"/>
    <br>
    <jsp:setProperty name="dateValue" property="time" value="${match.gameDuration * 1000}"/>
    <fmt:formatDate value="${dateValue}" pattern="mm분 ss초"/>

    <c:if test="${match.queueId eq '420'}">
        <h3>솔랭</h3>
    </c:if>
    <c:if test="${match.queueId eq '440'}">
        <h3>자랭</h3>
    </c:if>
    <c:if test="${match.queueId eq '430'}">
        <h3>일반</h3>
    </c:if>
    <c:if test="${match.queueId eq '450'}">
        <h3>무작위</h3>
    </c:if>
    <c:if test="${match.queueId eq '850'}">
        <h3>Bot전</h3>
    </c:if>
    <c:if test="${match.queueId eq '1020'}">
        <h3>이벤트</h3>
    </c:if>
    <br>

    <c:forEach items="${match.participants}" var="participant">
        <c:if test="${participant.stats.win eq true}">
            <strong>승리</strong> <br>
        </c:if>

        <c:if test="${participant.stats.win eq false}">
            <strong>패배</strong> <br>
        </c:if>
        <img width="30px" height="30px"
             src="http://opgg-static.akamaized.net/images/lol/champion/<c:url value="${participant.championName}"/>"/>
        <%--cName : ${participant.championName} &nbsp;--%>
        <%--pId : ${participant.participantId} &nbsp;--%>
        <c:if test="${match.participantIdentities.get(participant.participantId-1).player.summonerName eq summonerName}">
            <a href="http://localhost:8080/search?summonerName=${match.participantIdentities.get(participant.participantId-1).player.summonerName}"><strong>${match.participantIdentities.get(participant.participantId-1).player.summonerName}</strong></a> &nbsp;
        </c:if>
        <c:if test="${match.participantIdentities.get(participant.participantId-1).player.summonerName ne summonerName}">
            <a href="http://localhost:8080/search?summonerName=${match.participantIdentities.get(participant.participantId-1).player.summonerName}">${match.participantIdentities.get(participant.participantId-1).player.summonerName}</a> &nbsp;
        </c:if>
        ${participant.highestAchievedSeasonTier}
        <%--tId : ${participant.teamId}--%>
        win : ${participant.stats.win} &nbsp;
        <%--championId : <p id="cId" on="getImage(${participant.championId})">${participant.championId}</p> &nbsp;--%>
        <%--spell1Id : ${participant.spell1Id} &nbsp;--%>
        <%--spell2Id : ${participant.spell2Id} &nbsp;--%>
        CS : ${participant.stats.totalMinionsKilled + participant.stats.neutralMinionsKilled}(분당 <fmt:formatNumber
            value="${(participant.stats.totalMinionsKilled + participant.stats.neutralMinionsKilled)/(match.gameDuration / 60)}"
            pattern=".0"/>)

        <img width="30px" height="30px"
             src="http://opgg-static.akamaized.net/images/lol/spell/<c:url value="${participant.spell1Name}"/>"/>
        <img width="30px" height="30px"
             src="http://opgg-static.akamaized.net/images/lol/spell/<c:url value="${participant.spell2Name}"/>"/>

        <img width="20px" height="20px"
             src="http://opgg-static.akamaized.net/images/lol/perk/<c:url value="${participant.stats.perk0}"/>.png"/>
        <img width="20px" height="20px"
             src="http://opgg-static.akamaized.net/images/lol/perkStyle/<c:url value="${participant.stats.perkSubStyle}"/>.png"/>

        <img width="20px" height="20px"
             src="http://opgg-static.akamaized.net/images/lol/item/<c:url value="${participant.stats.item0}"/>.png"/>
        <img width="20px" height="20px"
             src="http://opgg-static.akamaized.net/images/lol/item/<c:url value="${participant.stats.item1}"/>.png"/>
        <img width="20px" height="20px"
             src="http://opgg-static.akamaized.net/images/lol/item/<c:url value="${participant.stats.item2}"/>.png"/>
        <img width="20px" height="20px"
             src="http://opgg-static.akamaized.net/images/lol/item/<c:url value="${participant.stats.item3}"/>.png"/>
        <img width="20px" height="20px"
             src="http://opgg-static.akamaized.net/images/lol/item/<c:url value="${participant.stats.item4}"/>.png"/>
        <img width="20px" height="20px"
             src="http://opgg-static.akamaized.net/images/lol/item/<c:url value="${participant.stats.item5}"/>.png"/>
        <img width="20px" height="20px"
             src="http://opgg-static.akamaized.net/images/lol/item/<c:url value="${participant.stats.item6}"/>.png"/>


        ${participant.stats.kills}/<strong style="color: #c6443e;">${participant.stats.deaths }</strong>/${participant.stats.assists}

        <c:if test="${participant.stats.deaths eq 0 }">
            <strong>Perfect</strong>
        </c:if> <fmt:formatNumber value="${participant.stats.kdaRatio }"
                                  pattern="0.00"/>
        <p style="color: #c6443e;">킬관여 ${participant.stats.killInvolvement }%</p>
        <br>

    </c:forEach>
    <br>
</c:forEach>
<script>
    function getImage(param) {
        var cId = param;

        // var cId = $("#cId").text();
        console.log(cId);
        var url = "https://kr.api.riotgames.com/lol/static-data/v3/champions/" + cId + "?locale=en_US&api_key=RGAPI-296b0cb7-bd55-4315-b160-fd29327b64b6";
        $.getJSON(url, '', function (data) {
            console.log(data.name);
            $("#imgTag").attr("src", "https://ddragon.leagueoflegends.com/cdn/8.8.1/img/champion/" + Lux + ".png");
        });
    }


</script>
</body>
</html>
