<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>${sv}</h1>
	<%-- <h2>vo.x : ${x}, vo.y : ${y}</h2> --%>
	<h2>vo.x : ${mv.x}, vo.y : ${mv.y}</h2>
	<%-- <h2>vo.x : ${mv.getX()}, vo.y : ${mv.getY()}</h2> --%>
	<%-- <h2>vo.x : ${mv["x"]}, vo.y : ${mv["y"]}</h2> --%>
	<h2>v.x : ${myVo.x}, v.y : ${myVo.y}</h2>
</body>
</html>