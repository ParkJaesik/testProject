<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 메뉴바는 어느 페이지든 포함하고 있을 테니 여기서 contextPath 변수 값 선언-->
<c:set var="contextPath" value="${ pageContext.servletContext.contextPath }" scope="application"/>
	
<script src="http://code.jquery.com/jquery-3.4.1.min.js"></script> 
<link rel="stylesheet" href="${contextPath }/resources/css/menubar-style.css" type="text/css">
</head>
<body>
	<c:if test="${!empty msg}">
		<script>alert("${msg}")</script>
	</c:if>
	
	<h1 align="center">Finally Last Subject Spring!!</h1>
	태ㅔ슽ㄹ
	<br>
	
	<!-- 로그인유저가 있는 경우 없는 경우 동시에 작업 -->
	<div class="loginArea" align="right">
		<c:if test="${ empty sessionScope.loginUser }">
			<form action="login.kh" method="post">
				<table id="loginTable" style="text-align:center;">
					<tr>
						<td>아이디</td>
						<td><input type="text" name="id"></td>
						<td rowspan="2">
							<button id="loginBtn">로그인</button>
						</td>
					</tr>
					<tr>
						<td>비밀번호</td>
						<td><input type="password" name="pwd"></td>
					</tr>
					<tr>
						<td colspan="3">
							<a href="enrollView.kh">회원가입</a>
							<a href="#">아이디/비밀번호 찾기</a>
						</td>
					</tr>
				</table>
			</form>
		</c:if>
		<c:if test="${ !empty sessionScope.loginUser }">
			<h3 align="right">
				<c:out value="${ loginUser.name }님 환영합니다!!"/>
				<c:url var="myinfo" value="myinfo.kh"/>
				<c:url var="logout" value="logout.kh"/>
				<button onclick="location.href='${myinfo}'">정보수정</button>
				<button onclick="location.href='${logout}'">로그아웃</button>
			</h3>
		</c:if>
	</div>
	
	
	
	<c:url var="nlist" value="nlist.kh"/>
	<c:url var="blist" value="blist.bo"/>
	<div class="menubar">
		<div class="nav">
			<div class="menu"><a href="home.kh">HOME</a></div>
			<div class="menu"><a href="${ nlist }">공지사항</a></div>
			<div class="menu"><a href="${ blist }">게시판</a></div>
			<div class="menu">etc</div>
		</div>
	</div>
	
	
</body>
</html>