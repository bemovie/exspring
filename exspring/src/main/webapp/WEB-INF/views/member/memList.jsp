<%-- <%@page import="com.exam.member.MemberVo"%>
<%@page import="java.util.List"%>
<%@page import="com.exam.member.MemberDaoBatis"%>
<%@page import="com.exam.member.MemberDao"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
    
<%-- <%! //선언부
private MemberDao memberDao = new MemberDaoBatis();
%>


<% //java code 입력
List<MemberVo> list = memberDao.selectMemberList();
request.setAttribute("memberList", list);
%> --%>

<!-- <!DOCTYPE html>          
<html>                   
<head>                   
<meta charset=\"UTF-8\"> 
<title>회원관리</title>     
<style> a{text-decoration-line: none;} a:visited {color:red;} a:hover{color:pink;}
</style>
</head>                  
<body> -->
로그인이 된 경우, 로그인한 사용자 이름과 로그아웃 링크를 출력
로그인이 되지 않은 경우, 로그인과 회원가입(추가) 링크를 출력
<br>

<%-- <jsp:include page="/WEB-INF/views/menu.jsp" /> --%>

<!-- 조건문 : if, switch
core 태그는 c:if나 c:choose 사용  -->

<%-- <!-- 로그인이 된 경우 -->
<c:if test="${loginUser!=null}">
${loginUser.memName}
<button><a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a></button>
</c:if>

<!-- 로그인이 되지 않은 경우 -->
<c:if test="${loginUser==null}">
<button><a href="${pageContext.request.contextPath}/member/login.do">로그인</a></button>
<button><a href="<%=request.getContextPath()%>/member/add.do">회원추가</a></button>
<button><a href="${pageContext.request.contextPath}/member/add.do">회원추가</a></button>
<button><a href="<c:url value="/member/add.do" />">회원추가</a></button>
</c:if> --%>


<%-- <c:choose>
<c:when test="${loginUser!=null}">
<c:out value="${loginUser.memName}" /> 님 | <a href="<c:url value="/member/logout.do" />">로그아웃</a>
</c:when>
<c:otherwise>
<a href="<c:url value="/member/login.do" />">로그인</a> <a href="<c:url value="/member/add.do" />">회원가입</a>
</c:otherwise>
</c:choose> --%>

<hr> <!-- 수평선 -->  
                  		
<h1>회원목록</h1>
<%-- <button><a href="<%=request.getContextPath()%>/member/add.do">회원추가</a></button> --%>
<%-- <button><a href="${pageContext.request.contextPath}/member/add.do">회원추가</a></button>
<button><a href="<c:url value="/member/add.do" />">회원추가</a></button> --%>


<button><a href="<%=request.getContextPath()%>/member/delform.do">회원삭제</a></button>


<%-- <% 
for (MemberVo vo : list) {
%> --%>

<c:forEach var="vo" items="${memberList}">
	<p><%-- ${vo.memId} : ${vo.memPass} : ${vo.memName} : ${vo.memPoint} --%>
	 <a href="${pageContext.request.contextPath}/member/edit.do?memId=<c:out value="${vo.memId}" />"><c:out value="${vo.memId}" /></a> :
	 <c:out value="${vo.memName}" /> :
	 ${vo.memPoint}	 
	<a href="${pageContext.request.contextPath}/member/del.do?memId=<c:out value="${vo.memId}" />"><button type='button'>삭제</button></a>
	<!-- JSTL 태그의 scope와 var 속성을 사용하면, -->
	<!-- JSTL 태그의 실행 별과를 현재 위치에 출력하지 않고, -->
	<!-- 지정한 scope에 지정한 이름(var)의 속성을 저장한 후, -->
	<!-- EL에서 읽어서 사용 가능 -->
	<c:url value="/member/del.do" var="delUrl" >
		<c:param name="memId">${vo.memId}</c:param>
	</c:url>
	<a href="${delUrl}"><button type='button'>삭제</button></a>
	</p>
</c:forEach>

<%-- <%
}
%> --%>

<!-- </body>                 
</html> -->
