<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<title>반려동물 Dolbom</title>
<style>
#wrapper {
	max-width: 1400px;
	margin: 0 auto;
}

.container0 {
	display: flex;
	flex-direction: column;
	width: 1400px;
}

.container1 {
	height: 550px;
	padding-top: 20px;
	display: flex;
	flex-direction: row;
	flex-wrap: nowrap;
	justify-content: space-evenly;
	background-color: #ffcccb
}

.item1 {
	background-color: #ffecb3;
	height: 100%;
	width: 73%;
}

.loginForm {
	display: flex;
	flex-direction: column;
	align-items: center;
	background-color: #ffffe5;
	height: 100%;
	width: 23%;
	padding: 30px;
}

.container2 {
	height: 550px;
	padding-top: 20px;
	display: flex;
	flex-direction: row;
	flex-wrap: nowrap;
	justify-content: space-evenly;
	background-color: #ba6b6c;
	align-content: center;
}

.item3 {
	background-color: #ffecb3;
	height: 90%;
	width: 48%;
}

.item4 {
	background-color: #ffffe5;
	height: 90%;
	width: 48%;
}

#loginBtn {
	background-color: #ffffb3;
	color: black;
	height: 40px;
	width: 100%;
	border-color: #ffffb3;
	border-radius: 8px;
}
</style>
</head>
<body>
	<div id="wrapper">


		<div class="container0">
			<%@ include file="header.jsp"%>
			<%@ include file="navbar.jsp"%>

			<div class="container1">
				<div class="item1" style="padding: 30px 50px;">
					<div id="carouselExampleIndicators" class="carousel slide"
						data-ride="carousel">
						<ol class="carousel-indicators">
							<li data-target="#carouselExampleIndicators" data-slide-to="0"
								class="active"></li>
							<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
							<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
						</ol>
						<div class="carousel-inner">
							<div class="carousel-item active">
								<img src="c1.jpg" style="height: 500px; width: 500px;"
									class="w-100" alt="...">
							</div>
							<div class="carousel-item">
								<img src="c2.jpg" style="height: 500px; width: 500px;"
									class="w-100" alt="...">
							</div>
							<div class="carousel-item">
								<img src="c3.jpg" style="height: 500px; width: 500px;"
									class="w-100" alt="...">
							</div>
						</div>
						<a class="carousel-control-prev" href="#carouselExampleIndicators"
							role="button" data-slide="prev"> <span
							class="carousel-control-prev-icon" aria-hidden="true"></span> <span
							class="sr-only">Previous</span>
						</a> <a class="carousel-control-next"
							href="#carouselExampleIndicators" role="button" data-slide="next">
							<span class="carousel-control-next-icon" aria-hidden="true"></span>
							<span class="sr-only">Next</span>
						</a>
					</div>
				</div>


				<div class="loginForm">
					<form style="padding: 10px; background-color: #ffe082">
						<div class="form-group">
							아이디 <input type="text" name="id" class="form-control"
								id="exampleInputEmail1">
						</div>
						<div class="form-group">
							비밀번호 <input type="password" name="password" class="form-control" />
						</div>

						<button id="loginBtn" type="submit">로그인</button>
					</form>
					<div style="padding: 20px">
					<a href="https://www.animals.or.kr/"><img src="banner1.png" alt="HTML tutorial"
						style="width: 212px; height: 68px;"></a>
					</div>
				</div>


			</div>

			<div class="container2">
				<div class="item3"></div>
				<div class="item4"></div>
			</div>
		</div>

		<%@ include file="footer.jsp"%>

	</div>
</body>
</html>