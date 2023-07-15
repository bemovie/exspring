<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>          
<html>                   
<head>                   
<meta charset=\"UTF-8\"> 
<title>게시판</title>     
<style> a{text-decoration-line: none;} a:visited {color:red;} a:hover{color:pink;}
</style>
</head>                  
<body>
<%-- <jsp:include page="/WEB-INF/views/menu.jsp" /> --%>

<hr> <!-- 수평선 -->
                  		
<h1>게시글목록</h1>
<button><a href="${pageContext.request.contextPath}/bbs/add.do">글쓰기</a></button>

<table>
	<thead>
		<tr><th>번호</th>	<th>제목</th>	<th>작성자</th><th>등록일시</th><!-- <th>조회수</th> --></tr>
	</thead>
	<tbody>
		<c:forEach var="vo" items="${bbsList}"> <!-- 게시글 하나가 하나의 tr임 => 한줄 차지 -->
			<tr>
				<td>${vo.bbsNo}</td>
				<td>
					<a href="${pageContext.request.contextPath}/bbs/edit.do?bbsNo=${vo.bbsNo}">
					<c:out value="${vo.bbsTitle}" />
					</a>
				</td>
				<td><c:out value="${vo.bbsWriter}" /></td>
				<%-- <td><c:out value="${vo.bbsRegDate}" /></td> --%>
				<td><fmt:formatDate value="${vo.bbsRegDate}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
				<!-- 등록일시가 2023/06/29 14:00:12 형식으로 출력되도록 변경 -->
			</tr>
		</c:forEach>
	</tbody>
</table>

<form id="searchForm" action="${pageContext.request.contextPath}/bbs/list.do">
	<select name="searchType"> <%-- value="${searchInfo.searchType} --%>
		<%-- <option value="title" ${searchInfo.searchType =='title'? 'selected':''}>제목</option>
		<option value="content" ${searchInfo.searchType =='content'? 'selected':''}>내용</option>
		<option value="total" ${searchInfo.searchType =='total'? 'selected':''}>제목+내용</option> --%>
		<option value="title">제목</option>
		<option value="content">내용</option>
		<option value="total">제목+내용</option>
	</select>
	<script type="text/javascript">
		//if('${searchInfo.searchType}' != "") {
		if('${searchInfo.searchType}') {			
		document.querySelector('[name="searchType"]').value = '${searchInfo.searchType}';
		}
		//$('[name="searchType"]').prop('value', '${searchInfo.searchType}');
		//$('[name="searchType"]').val('${searchInfo.searchType}');
	</script>
	<input type="text" name="searchWord" value="${searchInfo.searchWord}"/> <!-- 입력된 값을 보낼꺼니까, value는 안 넣어도 됨 -->
	<input type="hidden" name="currentPageNo" value="1" />
	<input type="submit" value="검색" />
</form>

${searchInfo.pageHtml}
<script type="text/javascript">
	function goPage(n) {
		document.querySelector('[name="currentPageNo"]').value = n;
		document.querySelector('#searchForm').submit();
		//document.forms[0]
		//location.href = 'http://localhost:8000/myapp/bbs/list.do?currentPageNo=' + n;
		//location.href = location.pathname + '?currentPageNo=' + n;
	}
</script>



<script>
<c:if test="${not empty param.message}">
	alert("${param.message}");
</c:if>
</script>

</body>                 
</html>                  

