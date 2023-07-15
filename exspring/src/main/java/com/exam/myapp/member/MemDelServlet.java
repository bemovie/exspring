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

//웹브라우저에서
//	http://localhost:8000/exweb/member/del.do?memId=삭제할회원아이디
//로 요청을 보내면, 지정한 회원 정보를 데이터베이스에서 삭제하고
//	"몇명의 회원 삭제 성공" 메시지와 "회원목록으로 이동" 링크를 화면에 출력
//MemDelServlet 클래스를 변경하세요.

//회원삭제 SQL문 :
//	DELETE FROM member WHERE mem_id = 'b003';

//@WebServlet("/member/del.do")
public class MemDelServlet extends HttpServlet{
	
//	{
//	//애플리케이션에 JDBC 사용 전에 최초 1번은 JDBC 드라이버 클래스를 메모리에 로드 필요 
//			try {
//				Class.forName("oracle.jdbc.OracleDriver");
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//}	
	
//	String url ="jdbc:oracle:thin:@localhost:1521:xe"; //데이터베이스 서버 주소
//	String user ="web"; //데이터베이스 접속 아이디
//	String password ="web01"; //데이터베이스 접속 비밀번호
	
//	private MemberDao memberDao = new MemberDaoBatis();
//	private MemberService memberService = new MemberServiceImpl();	// MemberService의 구현체
	private MemberService memberService = MemberServiceImpl.getInstance();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		req.setCharacterEncoding("UTF-8"); //필터로 이동
		//~ 파라미터 값 읽어오기 ~
		String memIdv = req.getParameter("memId");
//		String sql = "DELETE FROM member WHERE mem_id = memIdv";
//		로 할 수 있지만 SQL Injection 공격의 위험이 있으므로 보안을 위해 ?로 대체
		
//		int n = memberDao.deleteMember(memIdv);
		int n = memberService.deleteMember(memIdv);
		
		System.out.println(n + "명의 회원 삭제");
		
		resp.sendRedirect(req.getContextPath() + "/member/list.do");
		
//		resp.setCharacterEncoding("UTF-8");
//		resp.setContentType("text/html");
//		PrintWriter out = resp.getWriter();
//		out.println("<!DOCTYPE html>          ");
//		out.println("<html>                   ");
//		out.println("<head>                   ");
//		out.println("<meta charset=\"UTF-8\"> ");
//		out.println("<title>회원관리</title>     ");
////		out.println("<style> a{text-decoration-line: none;} a:visited {color:blue;} a:hover{color:pink;}");
////		out.println("</style>");
//		out.println("</head>                  ");
//		out.println("<body>                   ");			
//		out.println("<h1>" + n + "명의 회원 삭제 성공</h1>");
//		out.println("<a href=\"" + req.getContextPath() + "/member/list.do\">회원목록</a>");
//		out.println("</body>                  ");
//		out.println("</html>                  ");
			
	
//try() 사용하지 않을 경우의 close 방식		
//		finally {
//			try {
//				if(pstmt!=null)
//				pstmt.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} //명령문 객체가 사용하던 자원 반납
//			try {
//				if(conn!=null)
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} //데이터베이스와 연결 종료	
//		}
	
	
	}

	
	
}
