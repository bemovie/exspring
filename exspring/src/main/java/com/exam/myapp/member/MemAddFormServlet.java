package com.exam.myapp.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//웹브라우저에서 /member/addform.do 로 요청을 보내면
//웹브라우저 화면에 회원정보를 입력하는 폼을 출력하도록
//MemAddFormServlet을 변경하세요.

//@WebServlet("/member/addform.do")
public class MemAddFormServlet extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<!DOCTYPE html>          ");
		out.println("<html>                   ");
		out.println("<head>                   ");
		out.println("<meta charset=\"UTF-8\"> ");
		out.println("<title>회원관리</title>     ");
		out.println("</head>                  ");
		out.println("<body>                   ");		
		out.println("<h1>회원추가</h1>");
		out.println("<form action=\"" + req.getContextPath() + "/member/add.do\" method=\"post\">");
		out.println("아이디: <input type=\"text\" name=\"memId\" value=\"\" /><br>");
		out.println("비밀번호: <input type=\"password\" name=\"memPass\" value=\"\" /><br>");
		out.println("이름: <input type=\"text\" name=\"memName\" value=\"\" /><br>");
		out.println("포인트: <input type=\"number\" name=\"memPoint\" value=\"0\" /><br>");
		out.println("<input type=\"submit\" />");
		out.println("</form>                  ");	
		out.println("</body>                  ");
		out.println("</html>                  ");		
	
	}
	
}
