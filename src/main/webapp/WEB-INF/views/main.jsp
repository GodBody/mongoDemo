<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%--<%@page contentType="text/html" pageEncoding="UTF-8" %>--%>


<!DOCTYPE html>
<html lang="ko">
<title>Main</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta name="description" content="">
<meta name="author" content="">

<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>

<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>
<body>

	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">KNOC.GG</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<div class="navbar-form navbar-right">
					<div class="form-group">
						<input type="text" placeholder="소환사 입력..." class="form-control"
							id="summonerName">
					</div>

					<button type="button" id="submit" class="btn btn-success">.GG</button>
				</div>
			</div>
			<!--/.navbar-collapse -->
		</div>
	</nav>

	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="jumbotron">
		<div class="container"></div>
	</div>

	<div class="container">
		<!-- Example row of columns -->
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<h2>LeagueOfLegend match analytics Application</h2>

			</div>
		</div>

		<hr>

		<footer> </footer>
	</div>
	<!-- /container -->
</body>
<script>
	$("#submit").on("click", function() {
		var name = $("#summonerName").val();
		console.log(name);


		$.get("http://13.125.18.131:8080/lol-analytics-spring-boot/search?summonerName=" + name);
		self.location = "http://13.125.18.131:8080/lol-analytics-spring-boot/search?summonerName=" + name;


	});

	$('input[type="text"]').keydown(function() {
		if (event.keyCode === 13) {
			event.preventDefault();

			var name = $("#summonerName").val();
			console.log(name);


			$.get("http://13.125.18.131:8080/lol-analytics-spring-boot/search?summonerName=" + name);
			self.location = "http://13.125.18.131:8080/lol-analytics-spring-boot/search?summonerName=" + name;

		}
	});
</script>

</html>
