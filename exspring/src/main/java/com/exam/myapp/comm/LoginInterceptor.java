package com.exam.myapp.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.exam.myapp.member.MemberVo;

//다수의 컨트롤러 실행 전후에 수행해야하는 공통작업들은
//핸들러인터셉터를 사용하여 구현 가능

//@WebFilter()로 등록하거나 설정파일(web.xml)에 등록
public class LoginInterceptor /*implements Filter*/ /*extends HandlerInterceptorAdapter*/ 
								implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 컨트롤러 실행 전에 실행되는 메서드
		// handler : 현재 인터셉터 이후에 실행될 인터셉터 또는 컨트롤러
		// 반환값 : 이후에 실행될 인터셉터 또는 컨트롤러의 실행 여부
		
		//요청보낸 사용자의 세션을 가져와서 
		HttpSession session = request.getSession();
		
		//세션에 로그인 정보를 꺼내와서
		MemberVo vo = (MemberVo) session.getAttribute("loginUser"); 
		
		//로그인 정보가 없다면,
		if (vo==null) {
		
			//로그인 페이지로 이동
			response.sendRedirect(request.getContextPath() + "/member/login.do"); // 로그인 화면으로 이동
			return false; //컨트롤러 실행하지 않음
		}
		
		return true; //컨트롤러 실행 //else는 굳이 안 적어줘도 됨, if에서 이미 return 해버렸으므로,
	}
	
	
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		// 컨트롤러 실행 후, 뷰(JSP) 실행 전에 실행되는 메서드	
//	}
//	
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		// 뷰 렌더링(JSP 실행 및 출력) 완료 후에 실행되는 메서드
//	}
	
	
	
	
/*	
	//로그인 없이 사용가능한 요청경로들을 저장할 목록
	private List<String> whiteList = new ArrayList<String>();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		whiteList.add("/member/login.do");
		whiteList.add("/member/add.do");
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 필터의 경로에 맞는 요청이 올때마다 한번씩 실행
		// ~ 1) 필터의 설정이 들어가는 부분 (요청 파라미터 읽기전에 인코딩 설정,) ~
		// ~ 1)에 해야 서블릿 실행 전에 막을 수 있다 ~
		
		HttpServletRequest req = (HttpServletRequest) request; // HttpServletRequest로 형변환을 시켜준다
		HttpServletResponse resp = (HttpServletResponse) response; // // HttpServletResponse로 형변환을 시켜준다
		
		System.out.println("URI: " + req.getRequestURI());
		System.out.println("URL: " + req.getRequestURL());
		String reqPath = req.getRequestURI().substring( req.getContextPath().length() );
		System.out.println("reqPath : " + reqPath);
		
//		if (!req.getRequestURI().equals( req.getContextPath() + "/member/login.do")) { //요청 주소가 login.do면 그냥 통과하도록 설정 
//		if ( whiteList.contains( req.getRequestURI() ) == false ) { // whiteList에 등록된 주소는 통과하도록 설정 (contextpath 포함해서 비교)
//		if ( !whiteList.contains( req.getRequestURI() ) ) { // whiteList에 등록된 주소는 통과하도록 설정 (contextpath 포함해서 비교)
		if ( !whiteList.contains( reqPath ) ) { // whiteList에 등록된 주소(contextpath를 제외한 주소로 비교)는 통과하도록 설정
			
			//요청보낸 사용자의 세션을 가져와서 
			HttpSession session = req.getSession();
			
			//세션에 로그인 정보를 꺼내와서
			MemberVo vo = (MemberVo) session.getAttribute("loginUser"); 
			//세션에 로그인한 사용자정보 꺼내옴, 
			//빨간줄 뜨는 이유는 MemberVo 타입이라는 보장이 없어서 => 하지만, 우리는 알고 있으므로 강제 형변환해줌
			
			//로그인 정보가 없다면,
			if (vo==null) { //로그인 실패시, 로그인 화면으로 이동
			
			//로그인 페이지로 이동
				resp.sendRedirect(req.getContextPath() + "/member/login.do"); // 로그인 화면으로 이동
				return; 
				// 아래쪽 코드 else로 감싸도 되지만, 보기가 안 좋음(들여쓰기도 해야되고)
				// => if가 실행되면 아래쪽 코드는 실행 안 되게 해야함 
				// => method 함수의 실행을 그만두고 돌아가라	=> return;
			}
			
		}
		
		
		
		
		//이후 실행될 필터 또는 서블릿들을 실행
		chain.doFilter(request, response);
		
		// 2) 서블릿이 어떤 응답을 만들어냈을때 그 응답을 조작하고 싶을때 여기 코딩
	}

//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		// 필터가 처음 생성됐을 때 1번 실행
//		System.out.println("CharEncFilter init() 실행");
//		// FilterConfig : 필터의 초기설정을 불러옴
//		// 필터의 초기화 파라미터 값 읽기
//		enc = filterConfig.getInitParameter("encoding");
//	}

	
	@Override
	public void destroy() {
		// 필터 객체가 소멸(삭제)되기 전에 1번 실행
		System.out.println("CharEncFilter destroy() 실행");
	}
*/


}
