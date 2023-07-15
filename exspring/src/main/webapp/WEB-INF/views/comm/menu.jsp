<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  

<!-- 로그인이 된 경우 -->
<c:if test="${loginUser!=null}">
	${loginUser.memName}
	<button><a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a></button>|
	<button><a href="${pageContext.request.contextPath}/member/list.do">회원관리</a></button>|
	<button><a href="${pageContext.request.contextPath}/bbs/list.do">게시판</a></button>
</c:if>

<!-- 로그인이 되지 않은 경우 -->
<c:if test="${loginUser==null}">
<%-- <c:redirect url="<c:url value="/member/login.do" />"></c:redirect> --%>
<button><a href="${pageContext.request.contextPath}/member/login.do">로그인</a></button>|
<%-- <button><a href="<%=request.getContextPath()%>/member/add.do">회원추가</a></button> --%>
<button><a href="${pageContext.request.contextPath}/member/add.do">회원추가</a></button>|
<button><a href="<c:url value="/member/add.do" />">회원추가</a></button>
</c:if>

