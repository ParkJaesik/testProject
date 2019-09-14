<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:import url="../common/menubar.jsp"/>
	
	<br style="clear:both">
	
	<h1 align="center">공지글 목록 보기</h1>
	
	<br><br>
	
	<c:if test="${ loginUser.id == 'admin' }">
		<div align="center">
			<button onclick="location.href='nWriterView.kh'">글쓰기</button>
		</div>
	</c:if>
	<br style="clear:both">
	
	<table align="center" width="600" border="1" cellspacing="0" style="clear:right;" id="tb">
		<tr bgcolor="yellowgreen">
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>올린날짜</th>
			<th>첨부파일</th>
		</tr>
		
		<c:forEach var="n" items="${ list }">
		<tr>
			<td align="center">${ n.nId }</td>
			<td>
			
			<!-- 로그인 상태에서만 상세보기 가능 -->
			<c:if test="${ !empty loginUser }">
				<c:url var="ndetail" value="ndetail.kh">
					<c:param name="nId" value="${ n.nId }"/> 
				</c:url>
				<a href="${ ndetail }">${ n.nTitle }</a>
			</c:if>
			
			<!-- 로그인 상태가 아닌 경우 공지사항 제목만 출력 -->
			<c:if test="${ empty loginUser }">
				${ n.nTitle }
			</c:if>
			</td>
			
			<td align="center">${ n.nWriter }</td>
			<td align="center">${ n.nCreateDate }</td>
			<td align="center">
			<c:if test="${ !empty n.filePath }">
				◎
			</c:if>
			<c:if test="${ empty n.filePath }">
				&nbsp;
			</c:if>
			</td>
		</tr>
		</c:forEach>
	</table>
	
	<!-------------- 게시물 검색하기 --------------->
	<div id="searchArea" align="center">
		<form action="nsearch.kh" name="searchForm" method="get">
			
			<select id="searchCondition" name="searchCondition">
				<option value="all" <c:if test="${search.searchCondition == 'all'}">selected</c:if> >전체</option>
				<option value="writer" <c:if test="${search.searchCondition == 'writer'}">selected</c:if> >작성자</option>
				<option value="title" <c:if test="${search.searchCondition == 'title'}">selected</c:if> >제목</option>
				<option value="content" <c:if test="${search.searchCondition == 'content'}">selected</c:if> >내용</option>
			</select>
			
			<input type="search" name="searchValue" value="${search.searchValue}">
			<button>검색</button><br>
			첨부파일 있는 게시물만
			<input type="checkbox" name="existFile" <c:if test="${!empty search.existFile }">checked</c:if> >
			
		</form>
	</div>
	
	
	
	<p align="center">
		<c:url var="home" value="home.kh"/>
		<a href="${ home }">시작 페이지로 이동</a> &nbsp;
		<c:url var="nlist" value="nlist.kh"/>
		<a href="${ nlist }">목록 전체 보기</a>
	</p>
	<br><br><br><br><br><br><br><br><br><br><br><br>
	
</body>
</html>