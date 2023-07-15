<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- session="false" : 처음 실행했을때, 세션 객체 안만들겠다(false), 굳이 안만들필요x이므로 지움 -->
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<%-- <P>  The time on the server is ${serverTime}. </P> --%>
<P>  The time on model is ${a}. </P>
<P>  The time on map is ${b}. </P>
<P>  The time on modelMap is ${c}. </P>
</body>
</html>
