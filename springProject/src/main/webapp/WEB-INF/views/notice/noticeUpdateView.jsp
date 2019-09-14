<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:import url="../common/menubar.jsp"/>
	
	<h1 align="center">공지글 수정 페이지</h1>
	
	<br><br>
	
	<form action="nupdate.kh" method="post" enctype="Multipart/form-data">
		<input type="hidden" name="nId" value="${ notice.nId }"/>
		<input type="hidden" name="filePath" value="${ notice.filePath }"/>
	
		<table align="center" border="1" cellspacing="0">
			<tr>
				<td>제목</td>
				<td>
					<input type="text" size="50" name="nTitle" value="${ notice.nTitle }">
				</td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>
					<input type="text" name="nWriter" readonly value="${ notice.nWriter }">
				</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>
					<!-- <br> 구문 없애기 -->
					<c:set var="nContent" value="${fn:replace(notice.nContent,'<br>','') }"/>
					<textarea rows="7" cols="50" name="nContent">${nContent}</textarea>
				</td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td>
					<input type="file" name="reloadFile">
					<c:if test="${ !empty notice.filePath }">
						<br>현재 업로드한 파일 : 
						
						<!--  download 속성 : ur로 이동 대신 다운로드 하라는 속성 -->
						<a href="${ contextPath }/resources/nuploadFiles/${ notice.filePath}" download>
							${ notice.filePath }
						</a>
					</c:if>
					<br>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="수정 하기"> 
				</td>
			</tr>
		</table>
	</form>
	<br><br>
	
	<p align="center">
		<c:url var="home" value="home.kh"/>
		<c:url var="list" value="nlist.kh"/>
		<c:url var="back" value="${header.referer }"/>
		<!-- header.referer : 이전 페이지의 url이 저장되어있음 -->
		
		<button onclick="location.href='${home}'">Home</button>
		<button onclick="location.href='${list}'">목록</button>
		<button onclick="location.href='${back}'">이전</button>
	</p>
</body>
</html>