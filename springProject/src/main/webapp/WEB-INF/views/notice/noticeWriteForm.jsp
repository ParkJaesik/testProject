<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:import url="../common/menubar.jsp"/>
	
	<h1 align="center">공지글 등록 페이지</h1>
	
	<br><br>
	
	<!-- 파일 업로드를 위해 encType="Multipart/form-data" 지정 -->
	<form action="ninsert.kh" method="post" enctype="Multipart/form-data">
		<table align="center" border="1" cellspacing="0">
			<tr>
				<td>제목</td>
				<td>
					<input type="text" size="50" name="nTitle">
				</td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>
					<input type="text" name="nWriter" readonly value="${ loginUser.id }">
				</td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea rows="7" cols="50" name="nContent"></textarea></td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td><input type="file" name="uploadFile"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="등록 하기"> &nbsp;
					<input type="reset" value="등록 취소">
				</td>
			</tr>
		</table>
	</form>
	<br><br>
	
	<p align="center">
		<a href="home.kh">시작 페이지로 이동</a>
		<a href="nlist.kh">목록 보기로 이동</a>
	</p>
</body>
</html>