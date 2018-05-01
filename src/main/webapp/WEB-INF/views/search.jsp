<%@ page import="com.springboot.springbootdemo.domain.ChampionDTO" %>
<%@ page import="java.util.*" %>
<%@ page import="com.mongodb.*" %>
<%@ page import="org.springframework.data.mongodb.core.query.Query" %>
<%@ page import="org.springframework.data.mongodb.core.query.Criteria" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include/header.jsp" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="dateValue" class="java.util.Date"/>
<!DOCTYPE html>
<html lang="kor">
<head>
    <title>Search</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
    <!-- 합쳐지고 최소화된 최신 CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

    <!-- 부가적인 테마 -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

    <!-- 합쳐지고 최소화된 최신 자바스크립트 -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

    <style type="text/css">
        .progress-bar-dealing {
            height: 30px;
            color: #fff;
            font-weight: bold;
            text-align: right;
            background: red;
            width: 0px;
            padding: 0.8em;
        }

        .progress-bar-taken {
            height: 30px;
            color: #fff;
            font-weight: bold;
            text-align: right;
            background: green;
            width: 0px;
            padding: 0.8em;
        }

        th, td {
            vertical-align: center;
            padding: 10px;
            border-bottom: 1px solid #444444;
        }

        table {
            border-top: 1px solid #444444;
            width: 60%;
            height: 100px;
        }

    </style>
</head>
<body>
<div id="wrapper">
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="col-md-12">
                <div class="box box-primary">

                    <div class="box-header">

                    </div>
                    <div class="box-body">
                        <div>
                            <form id="form" action="search" method="get" role="form">

                                소환사 : <input type="text" id="summonerName">
                                <button type="button" id="submit">검색</button>
                            </form>
                        </div>


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
                        <div>
                            <h3>챔피언 승률 통계(400 게임 이상)</h3>
                            <div>
                                <table>
                                    <tr>
                                        <th>챔피언</th>
                                        <th>표본</th>
                                        <th>승률</th>
                                    </tr>
                                    <c:forEach items="${championList}" var="champion">
                                        <tr>
                                            <c:if test="${champion.winCount + champion.lossCount >= 400}">
                                                <td><img width="20px" height="20px"
                                                         src="http://opgg-static.akamaized.net/images/lol/champion/<c:url value="${champion.image.full}"/>"/>${champion.name}
                                                </td>
                                                <td>${champion.winCount + champion.lossCount} 게임
                                                    (${champion.winCount}승 ${champion.lossCount}패)
                                                </td>
                                                <td><fmt:formatNumber
                                                        value="${champion.winCount / (champion.winCount + champion.lossCount) * 100}"
                                                        pattern="00"/>%
                                                </td>
                                            </c:if>
                                        </tr>
                                    </c:forEach>
                                </table>

                            </div>
                            <h3>소환사 정보</h3>
                            <div>
                                <img src="//opgg-static.akamaized.net/images/profile_icons/profileIcon<c:url value="${summoner.profileIconId}"/>.jpg"
                                     width="50px" height="50px"/><strong>${summoner.name}</strong> &nbsp;
                                Lv.${summoner.summonerLevel} <br>
                                <c:forEach items="${summoner.positions}" var="position">
                                    <c:if test="${position.queueType eq 'RANKED_SOLO_5x5'}">

                                        <c:set var="tier" value="${position.tier}"/>
                                        <c:set var="tierL" value="${fn:toLowerCase(tier)}"/>
                                        <c:if test="${position.rank eq 'V'}">
                                            <img src="//opgg-static.akamaized.net/images/medals/<c:url value="${tierL}"/>_5.png"
                                                 width="64px"
                                                 height="64px">
                                        </c:if>
                                        <c:if test="${position.rank eq 'IV'}">
                                            <img src="//opgg-static.akamaized.net/images/medals/<c:url value="${tierL}"/>_4.png"
                                                 width="64px"
                                                 height="64px">
                                        </c:if>
                                        <c:if test="${position.rank eq 'III'}">
                                            <img src="//opgg-static.akamaized.net/images/medals/<c:url value="${tierL}"/>_3.png"
                                                 width="64px"
                                                 height="64px">
                                        </c:if>
                                        <c:if test="${position.rank eq 'II'}">
                                            <img src="//opgg-static.akamaized.net/images/medals/<c:url value="${tierL}"/>_2.png"
                                                 width="64px"
                                                 height="64px">
                                        </c:if>
                                        <c:if test="${position.rank eq 'I'}">
                                            <img src="//opgg-static.akamaized.net/images/medals/<c:url value="${tierL}"/>_1.png"
                                                 width="64px"
                                                 height="64px">
                                        </c:if>
                                        <strong>솔랭</strong> - ${position.tier} : ${position.rank} : ${position.leaguePoints}p /   ${position.wins}승  ${position.losses}패 승률
                                        <fmt:formatNumber
                                                value="${position.wins / (position.wins + position.losses) * 100}"
                                                pattern="00"/>%
                                        <br>
                                    </c:if>

                                    <c:if test="${position.queueType eq 'RANKED_FLEX_SR'}">
                                        <c:set var="tier" value="${position.tier}"/>
                                        <c:set var="tierL" value="${fn:toLowerCase(tier)}"/>
                                        <c:if test="${position.rank eq 'V'}">
                                            <img src="//opgg-static.akamaized.net/images/medals/<c:url value="${tierL}"/>_5.png"
                                                 width="64px"
                                                 height="64px">
                                        </c:if>
                                        <c:if test="${position.rank eq 'IV'}">
                                            <img src="//opgg-static.akamaized.net/images/medals/<c:url value="${tierL}"/>_4.png"
                                                 width="64px"
                                                 height="64px">
                                        </c:if>
                                        <c:if test="${position.rank eq 'III'}">
                                            <img src="//opgg-static.akamaized.net/images/medals/<c:url value="${tierL}"/>_3.png"
                                                 width="64px"
                                                 height="64px">
                                        </c:if>
                                        <c:if test="${position.rank eq 'II'}">
                                            <img src="//opgg-static.akamaized.net/images/medals/<c:url value="${tierL}"/>_2.png"
                                                 width="64px"
                                                 height="64px">
                                        </c:if>
                                        <c:if test="${position.rank eq 'I'}">
                                            <img src="//opgg-static.akamaized.net/images/medals/<c:url value="${tierL}"/>_1.png"
                                                 width="64px"
                                                 height="64px">
                                        </c:if>
                                        <strong>자랭</strong> - ${position.tier} : ${position.rank} : ${position.leaguePoints}p /   ${position.wins}승  ${position.losses}패 승률
                                        <fmt:formatNumber
                                                value="${position.wins / (position.wins + position.losses) * 100}"
                                                pattern="00"/>%
                                        <br>
                                    </c:if>

                                </c:forEach>

                                <c:forEach items="${current.participants}" var="participant">
                                    ${participant.championId} <br>

                                </c:forEach>
                            </div>

                            <h3>플레이한 챔피언 통계(등록된 게임 중)</h3>
                            <div>
                                <table>
                                    <tr>
                                        <th>챔피언</th>
                                        <th>전적</th>
                                        <th>승률</th>
                                        <th>KDA</th>
                                    </tr>

                                    <c:forEach items="${mostList}" var="list">
                                        <tr>
                                            <td>
                                                <img width="30px" height="30px"
                                                     src="http://opgg-static.akamaized.net/images/lol/champion/<c:url value="${list.championName}"/>"/>
                                                <c:set var="name" value="${fn:split(list.championName, '.')}"/>
                                                    ${name[0]}</td>
                                            <td>${list.gameCount}전 ${list.winCount}승 ${list.looseCount}패</td>
                                            <td>${list.winRate}%</td>
                                            <td>${list.kdaRatio}</td>
                                        </tr>
                                    </c:forEach>
                                </table>


                            </div>
                        </div>
                        <c:forEach items="${matchList}" var="match">
                            <%--<h2>gameId : ${match.gameId}</h2>  ${match.gameCreation} / ${match.gameDuration} <br>--%>

                            <c:if test="${match.queueId eq '420'}">
                                <h3>
                                    <솔로 랭크>
                                </h3>
                            </c:if>
                            <c:if test="${match.queueId eq '440'}">
                                <h3>
                                    <자유 랭크>
                                </h3>
                            </c:if>
                            <c:if test="${match.queueId eq '430'}">
                                <h3>
                                    <일반 게임>
                                </h3>
                            </c:if>
                            <c:if test="${match.queueId eq '450'}">
                                <h3>
                                    <무작위 총력전>
                                </h3>
                            </c:if>
                            <c:if test="${match.queueId eq '850'}">
                                <h3>
                                    <Bot 전>
                                </h3>
                            </c:if>
                            <c:if test="${match.queueId eq '1020'}">
                                <h3>
                                    <이벤트 게임>
                                </h3>
                            </c:if>
                            <jsp:setProperty name="dateValue" property="time" value="${match.gameCreation}"/>
                            시작 시간 :<fmt:formatDate value="${dateValue}" pattern="yyyy년 MM월 dd일 HH시 mm분 ss초"/>
                            <br>
                            <jsp:setProperty name="dateValue" property="time" value="${match.gameDuration * 1000}"/>
                            경과 시간 :<fmt:formatDate value="${dateValue}" pattern="mm분 ss초"/>
                            <br>


                            <table class="table table-condensed">
                                <tr>
                                    <th>소환사</th>
                                    <th>전시즌</th>
                                    <th>KDA</th>
                                    <th>LV/G/CS</th>
                                    <th>스펠</th>
                                    <th>아이템</th>
                                    <th>딜량</th>
                                    <th>탱량</th>
                                    <th>와드</th>

                                </tr>

                                <c:forEach items="${match.participants}" var="participant" begin="0" end="4">


                                    <c:if test="${participant.stats.win eq true}">
                                        <tr style="background: #D4E4FE;">
                                    </c:if>
                                    <c:if test="${participant.stats.win eq false}">
                                        <tr style="background: #FFEEEE;">
                                    </c:if>
                                    <td><img width="30px" height="30px"
                                             src="http://opgg-static.akamaized.net/images/lol/champion/<c:url value="${participant.championName}"/>"/><a
                                            href="http://localhost:8080/search?summonerName=${match.participantIdentities.get(participant.participantId-1).player.summonerName}">${match.participantIdentities.get(participant.participantId-1).player.summonerName}</a>
                                    </td>
                                    <td>${participant.highestAchievedSeasonTier}</td>

                                    <td>${participant.stats.kills}/<strong
                                            style="color: #c6443e;">${participant.stats.deaths }</strong>/${participant.stats.assists}
                                        <c:set var="sum" value="${sum + participant.stats.kills}"/>

                                        <c:if test="${participant.stats.deaths eq 0 }">
                                            <strong>Perfect</strong>
                                        </c:if> <fmt:formatNumber value="${participant.stats.kdaRatio }"
                                                                  pattern="0.00"/>
                                    </td>

                                    <td> 레벨 ${participant.stats.champLevel} <br> <fmt:formatNumber
                                            value="${participant.stats.goldEarned}"
                                            type="number"/> G <br>
                                            ${participant.stats.totalMinionsKilled + participant.stats.neutralMinionsKilled}(
                                        <fmt:formatNumber
                                                value="${(participant.stats.totalMinionsKilled + participant.stats.neutralMinionsKilled)/(match.gameDuration / 60)}"
                                                pattern=".0"/>)
                                    </td>

                                    <td><img width="30px" height="30px"
                                             src="http://opgg-static.akamaized.net/images/lol/spell/<c:url value="${participant.spell1Name}"/>"/>
                                        <img width="30px" height="30px"
                                             src="http://opgg-static.akamaized.net/images/lol/spell/<c:url value="${participant.spell2Name}"/>"/>

                                        <img width="20px" height="20px"
                                             src="http://opgg-static.akamaized.net/images/lol/perk/<c:url value="${participant.stats.perk0}"/>.png"/>
                                        <img width="20px" height="20px"
                                             src="http://opgg-static.akamaized.net/images/lol/perkStyle/<c:url value="${participant.stats.perkSubStyle}"/>.png"/>
                                    </td>

                                    <td><img width="20px" height="20px"
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
                                    </td>

                                    <td><fmt:formatNumber
                                            value="${participant.stats.totalDamageDealtToChampions}" type="number"/>
                                        <c:set var="ratio"
                                               value="${participant.stats.totalDamageDealtToChampions / match.teams.get(0).totalDamageDealtToChampions * 100}"/>
                                        <fmt:formatNumber var="casting" value="${ratio}" pattern="#.##"/>
                                        <div class='progress-bar-holder'>
                                            <div class="progressbar">
                                                <div class="progress-bar-dealing">${casting}%</div>
                                            </div>
                                        </div>

                                    </td>

                                    <td><fmt:formatNumber value="${participant.stats.totalDamageTaken}" type="number"/>
                                        <c:set var="ratio"
                                               value="${participant.stats.totalDamageTaken / match.teams.get(0).totalDamageTaken * 100}"/>
                                        <fmt:formatNumber var="casting" value="${ratio}" pattern="#.##"/>
                                        <div class='progress-bar-holder'>
                                            <div class="progressbar">
                                                <div class="progress-bar-taken">${casting}%</div>
                                            </div>
                                        </div>

                                    </td>

                                    <td><img
                                            src="http://opgg-static.akamaized.net/images/site/summoner/icon-ward-red.png"
                                            alt="제어와드"
                                            style="width: 14px; border: 0; height: 14px; margin: 4px; vertical-align: middle;">${participant.stats.visionWardsBoughtInGame }<br/>${participant.stats.wardsPlaced}/${participant.stats.wardsKilled }
                                    </td>

                                    </tr>


                                </c:forEach>

                                <c:forEach items="${match.participants}" var="participant" begin="5" end="9">
                                    <c:if test="${participant.stats.win eq true}">
                                        <c:choose>
                                            <c:when test="${match.participantIdentities.get(participant.participantId-1).player.summonerName eq summonerName}">
                                                <tr style="background: #c6dbe9;">
                                            </c:when>
                                            <c:otherwise>
                                                <tr style="background: #D4E4FE;">
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>

                                    <c:if test="${participant.stats.win eq false}">
                                        <c:choose>
                                            <c:when test="${match.participantIdentities.get(participant.participantId-1).player.summonerName eq summonerName}">
                                                <tr style="background: #e1d1d0;">
                                            </c:when>
                                            <c:otherwise>
                                                <tr style="background: #FFEEEE;">
                                            </c:otherwise>
                                        </c:choose>


                                    </c:if>
                                    <td><img width="30px" height="30px"
                                             src="http://opgg-static.akamaized.net/images/lol/champion/<c:url value="${participant.championName}"/>"/><a
                                            href="http://localhost:8080/search?summonerName=${match.participantIdentities.get(participant.participantId-1).player.summonerName}">${match.participantIdentities.get(participant.participantId-1).player.summonerName}</a>
                                    </td>
                                    <td>${participant.highestAchievedSeasonTier}</td>

                                    <td>${participant.stats.kills}/<strong
                                            style="color: #c6443e;">${participant.stats.deaths }</strong>/${participant.stats.assists}
                                        <c:set var="sum" value="${sum + participant.stats.kills}"/>

                                        <c:if test="${participant.stats.deaths eq 0 }">
                                            <strong>Perfect</strong>
                                        </c:if> <fmt:formatNumber value="${participant.stats.kdaRatio }"
                                                                  pattern="0.00"/>
                                    </td>


                                    <td> 레벨 ${participant.stats.champLevel} <br> <fmt:formatNumber
                                            value="${participant.stats.goldEarned}"
                                            type="number"/> G <br>
                                            ${participant.stats.totalMinionsKilled + participant.stats.neutralMinionsKilled}(
                                        <fmt:formatNumber
                                                value="${(participant.stats.totalMinionsKilled + participant.stats.neutralMinionsKilled)/(match.gameDuration / 60)}"
                                                pattern=".0"/>)
                                    </td>

                                    <td><img width="30px" height="30px"
                                             src="http://opgg-static.akamaized.net/images/lol/spell/<c:url value="${participant.spell1Name}"/>"/>
                                        <img width="30px" height="30px"
                                             src="http://opgg-static.akamaized.net/images/lol/spell/<c:url value="${participant.spell2Name}"/>"/>

                                        <img width="20px" height="20px"
                                             src="http://opgg-static.akamaized.net/images/lol/perk/<c:url value="${participant.stats.perk0}"/>.png"/>
                                        <img width="20px" height="20px"
                                             src="http://opgg-static.akamaized.net/images/lol/perkStyle/<c:url value="${participant.stats.perkSubStyle}"/>.png"/>
                                    </td>

                                    <td><img width="20px" height="20px"
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
                                    </td>

                                    <td><fmt:formatNumber
                                            value="${participant.stats.totalDamageDealtToChampions}" type="number"/>
                                        <c:set var="ratio"
                                               value="${participant.stats.totalDamageDealtToChampions / match.teams.get(0).totalDamageDealtToChampions * 100}"/>
                                        <fmt:formatNumber var="casting" value="${ratio}" pattern="#.##"/>
                                        <div class='progress-bar-holder'>
                                            <div class="progressbar">
                                                <div class="progress-bar-dealing">${casting}%</div>
                                            </div>
                                        </div>

                                    </td>

                                    <td><fmt:formatNumber value="${participant.stats.totalDamageTaken}" type="number"/>
                                        <c:set var="ratio"
                                               value="${participant.stats.totalDamageTaken / match.teams.get(0).totalDamageTaken * 100}"/>
                                        <fmt:formatNumber var="casting" value="${ratio}" pattern="#.##"/>
                                        <div class='progress-bar-holder'>
                                            <div class="progressbar">
                                                <div class="progress-bar-taken">${casting}%</div>
                                            </div>
                                        </div>

                                    </td>

                                    <td><img
                                            src="http://opgg-static.akamaized.net/images/site/summoner/icon-ward-red.png"
                                            alt="제어와드"
                                            style="width: 14px; border: 0; height: 14px; margin: 4px; vertical-align: middle;">${participant.stats.visionWardsBoughtInGame }<br/>${participant.stats.wardsPlaced}/${participant.stats.wardsKilled }
                                    </td>
                                    </tr>

                                </c:forEach>

                            </table>
                        </c:forEach>
                    </div>
                    <div class="box-footer"></div>
                </div>
            </div>
        </div>

    </div>


</div>

<script>

    $(document).ready(function () {
        $(".progress-bar-dealing").each(function () {
            var value = parseInt($(this).html());
            // var value = $(this).html();
            console.log(value);
            $(this).animate({
                'width': '' + value + 'px'
            }, 800);
        });
        $(".progress-bar-taken").each(function () {
            var value = parseInt($(this).html());
            // var value = $(this).html();
            console.log(value);
            $(this).animate({
                'width': '' + value + 'px'
            }, 800);
        });
    });

    $("#submit").on("click", function () {
        var name = $("#summonerName").val();
        console.log(name);

        $.get("http://localhost:8080/search?summonerName=" + name);
        self.location = "http://localhost:8080/search?summonerName=" + name;

    });


</script>
</body>
</html>
