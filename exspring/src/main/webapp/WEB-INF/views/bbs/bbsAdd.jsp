<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- <!DOCTYPE html>          
<html>                   
<head>                   
<meta charset=\"UTF-8\"> 
<title>게시판</title>     
</head>                  
<body> -->
<%-- <jsp:include page="/WEB-INF/views/menu.jsp" /> --%>

<!-- 글쓰기 화면에 첨부파일을 입력할 수 있도록 추가하고, -->
<!-- BbsVo 클래스에 첨부파일을 받을 수 있는 변수(필드)를 추가 -->
                   		
<h1>새글쓰기</h1>
<!-- 파일을 포함하여 전송하는 form 엘리먼트는 enctype="multipart/form-data"으로 설정 -->
<form action="${pageContext.request.contextPath}/bbs/add.do" method="post" enctype="multipart/form-data">
	제목: <input type="text" name="bbsTitle" value="" /><br> <!-- 파라미터값을 받을 때 사용할 객체의 변수이름하고 속성이름하고 맞춰줘야함(정확히는 getBbsNo()에서 BbsNo가 속성이름, 어려우면 그냥 변수이름으로 알아라) -->
	내용: <textarea name="bbsContent" rows="5" cols="30" ></textarea><br><!-- <input type="text" name="bbsContent" value="" /> -->
	첨부파일1: <input type="file" name="bbsFile" /><br>
	첨부파일2: <input type="file" name="bbsFile" /><br>
<input type="submit" />
</form> 
                 	
<!-- </body>                  
</html> -->                      