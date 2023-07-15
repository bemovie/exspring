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
import javax.servlet.http.HttpSession;
//import javax.websocket.Session;

// 1. 브라우저 주소창에
//	http://localhost:8000/exweb/memeber/login.do
//	를 입력하여 접속하면, LoginServlet 클래스와 login.jsp 파일이 순차적으로  실행되어
//	브라우저에 로그인 화면이 출력되도록 구현
// 2. 로그인 화면에서 submit 버튼을 클릭하면,
// LoginServlet 클래스의 doPost가 실행되도록 구현

//@WebServlet("/member/login.do")
public class LoginServlet extends HttpServlet{
	
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
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//			String memId = req.getParameter("memId"); //파라미터 불러옴
//			MemberVo vo = memberDao.selectMember(memId); //db에서 꺼내옴
//			req.setAttribute("mvo", vo); //요청객체에 저장
		
			req.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(req, resp);
		}
	
	@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		
//		}
//	
//	
//	@Override
//	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
//		req.setCharacterEncoding("UTF-8"); //필터로 이동
		MemberVo vo = new MemberVo();
		vo.setMemId(req.getParameter("memId"));
		vo.setMemPass(req.getParameter("memPass"));
//		vo.setMemName(req.getParameter("memName"));
//		vo.setMemPoint(Integer.parseInt(req.getParameter("memPoint")));
			
//		String memId = "a004";
//		String memPass = "1234";
//		String memName = "호랭이";
//		int memPoint = 90;
		
//		int n = memberDao.insertMember(vo);
//		int n = memberDao.updateMember(vo); //updateMember 구현 (MemberDao)
		
//		MemberVo mvo = memberDao.selectLogin( vo ); 
		MemberVo mvo = memberService.selectLogin( vo ); 
		
		//vo가 memId, memPass 둘 다 가지고 있음, 물론 vo.getMemId(), vo.getMemPass() 로 해도 된다	
		//변수 vo에 빨간줄은, 중복된 변수이기 때문, 물론 덮어써도 되지만 그냥 mvo로 바꿔준다
		
		//로그인 성공
		//로그인 실패
		
		if (mvo==null) { //로그인 실패시, 로그인 화면으로 이동
			resp.sendRedirect(req.getContextPath() + "/member/login.do"); // 로그인 화면으로 이동
			
		}else { //로그인 성공
			HttpSession session = req.getSession();
			session.setAttribute("loginUser", mvo); //세션에 로그인한 사용자정보 저장
			//id만 가지고 있으면 잃어버림, 사용자정보 자체(mvo)를 가지고 있어야함
			
//			요청객체(하나의 요청을 처리하는 얘들끼리 공유할때, 요청 다시 보내면 없어짐), 
//			세션객체(사용자가 접속을 유지할때까지 유지, 사용자 별로 구분), => 세션객체 사용 
//			서블릿컨텍스트객체(서버가 종료될 때까지 계속 유지, 모든 사용자가 공유 => 한사람만 로그인하면 모든 사용자가 로그인했다고 나옴)
			resp.sendRedirect(req.getContextPath() + "/member/list.do"); // 회원 목록 화면으로 이동
			
		}
		
		
		
		
		
//		System.out.println(n + "명의 회원 변경");
		
		//회원목록 출력
		//MemListServlet 실행!
		//forward : 요청객체와 응답객체를 전달하면서 다른 서블릿을 실행
		//		현재 서블릿에서는 더 이상 응답을 출력하지 않는다. (화면처리(print)는 더 이상 관여하지 못한다.)
//		req.getRequestDispatcher("/member/list.do").forward(req, resp); //req.getRequestDispatcher("이동할주소")
		//include : 요청객체와 응답객체를 전달하면서 다른 서블릿을 실행
		//		현재 서블릿의 출력 내용 중간에 지정한 서블릿의 출력 내용을 포함
//		req.getRequestDispatcher("/member/list.do").include(req, resp);
		//redirect : 지정한 주소로 이동하라는 명령을 담은 응답을 웹브라우저에게 전송
//		resp.sendRedirect(req.getContextPath() + "/member/list.do"); // 목록 화면으로 이동
		
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
//		out.println("<h1>" + n + "명의 회원 수정 성공</h1>");
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
