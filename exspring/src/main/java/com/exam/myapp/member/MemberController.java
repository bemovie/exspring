package com.exam.myapp.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.view.JstlView;

@Controller //스프링한테 Controller로 등록하라고 알려줌
public class MemberController {
	
//	private MemberService memberService = MemberServiceImpl.getInstance();
	//singleton 객체를 하나만 만들어서 사용하려고
	//근데, spring을 사용하면, 기본이 singleton임
	
	@Autowired //@Resource @Inject 셋중 하나 사용하면 같은 역할
	private MemberService memberService;
	//spring이 MemberService 객체를 가지고 있어야 한다
	
	@RequestMapping(value = "/member/list.do", method = RequestMethod.GET)
	public String list(Model model) {
		//물론 요청객체, 응답객체로 받아도 되지만 가급적이면 평범한 얘들로 받음 (Model, Map, ModelMap)
		
		List<MemberVo> list = memberService.selectMemberList(); //db에서 회원목록 가져옴
		
		model.addAttribute("memberList", list); //가져온 회원목록을 model에 저장
		
		return "member/memList"; // memList.jsp로 forward
	
	}
	
	@RequestMapping(value = "/member/add.do", method = RequestMethod.GET)
	public String addform(MemberVo vo) {
//		req.getRequestDispatcher("/WEB-INF/views/member/memAdd.jsp").forward(req, resp);
		return "member/memAdd";
	}
	
	//스프링에 등록된 표준BeanValidator를 사용하여
	//저장된 값을 검증하고 싶은 객체 매개변수 앞에 @Valid 적용
	//@Valid 매개변수 다음 위치에 검증결과를 저장하기 위한
	//BindingResult 또는 Errors 타입의 매개변수를 추가
	@RequestMapping(value = "/member/add.do", method = RequestMethod.POST)
	public String add(@Valid /*@ModelAttribute("mv")*/ MemberVo vo, BindingResult result) {
		
		System.out.println(result.getAllErrors());
		
		if (result.hasErrors()) { //검증결과 오류가 있다면
////			List<FieldError> fieldErrors = result.getFieldErrors();
//			for	(FieldError fe : result.getFieldErrors()) {
//				System.out.println("** " + fe.getField() );
////				String[] codes = fe.getCodes();
//				for (String c : fe.getCodes()) {
//					System.out.println(c);
//				}
//			}
			return "member/memAdd"; //회원정보 입력 화면(JSP) 출력(실행)
		}
		
//		if (vo.getMemId().length() <=5 ) {
//			return "member/memAdd";
//		}
	
	//	req.setCharacterEncoding("UTF-8"); //필터로 이동
	//	MemberVo vo = new MemberVo();
	//	vo.setMemId(req.getParameter("memId"));
	//	vo.setMemPass(req.getParameter("memPass"));
	//	vo.setMemName(req.getParameter("memName"));
	//	vo.setMemPoint(Integer.parseInt(req.getParameter("memPoint")));
		
		int n = memberService.insertMember(vo);
		
		System.out.println(n + "명의 회원 추가");
		
	//	resp.sendRedirect(req.getContextPath() + "/member/list.do");
		return "redirect:/member/list.do";

	}
	
	@RequestMapping(value = "/member/edit.do", method = RequestMethod.GET)
	public String editform(/*@RequestParam("memId") */String memId, Model model) {
//		String memId = req.getParameter("memId");
		
		MemberVo vo = memberService.selectMember(memId);
		
//		req.setAttribute("mvo", vo);
		model.addAttribute("mvo", vo);
		
//		req.getRequestDispatcher("/WEB-INF/views/member/memEdit.jsp").forward(req, resp);
		return "member/memEdit";
	}

	@RequestMapping(value = "/member/edit.do", method = RequestMethod.POST)
	public String edit(MemberVo vo) {
	
//	req.setCharacterEncoding("UTF-8"); //필터로 이동, 필터에 있으니까 필요x
//	MemberVo vo = new MemberVo();
//	vo.setMemId(req.getParameter("memId"));
//	vo.setMemName(req.getParameter("memName"));
//	vo.setMemPoint(Integer.parseInt(req.getParameter("memPoint")));

	int n = memberService.updateMember(vo); //updateMember 구현 (MemberDao)
	
	System.out.println(n + "명의 회원 변경");

	//redirect : 지정한 주소로 이동하라는 명령을 담은 응답을 웹브라우저에게 전송
//	resp.sendRedirect(req.getContextPath() + "/member/list.do"); // 목록 화면으로 이동
	return "redirect:/member/list.do";
	
	}
	
	//1.삭제기능이 동작하도록 이 메서드 코드를 변경
	//2.회원정보변경 화면에 삭제버튼을 추가
	@RequestMapping(value = "/member/del.do", method = RequestMethod.GET)
	public String del(String memId) {
		
//		req.setCharacterEncoding("UTF-8"); //필터로 이동
		//~ 파라미터 값 읽어오기 ~
//		String memId = req.getParameter("memId");
		
		int n = memberService.deleteMember(memId);
		
		System.out.println(n + "명의 회원 삭제");
		
//		resp.sendRedirect(req.getContextPath() + "/member/list.do");
		return "redirect:/member/list.do";
	}
	
	//로그인 동작이 수행되도록 아래 메서드들을 변경
	@RequestMapping(value = "/member/login.do", method = RequestMethod.GET)
	public String loginform() {
	
		//req.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(req, resp);
		return "member/login";
	}
	
	
//	@SessionAttribute(name = "sa")
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public String login(MemberVo vo, /*HttpServletRequest req,*/ HttpSession session) {
		
//		MemberVo vo = new MemberVo();
//		vo.setMemId(req.getParameter("memId"));
//		vo.setMemPass(req.getParameter("memPass"));
		
		MemberVo mvo = memberService.selectLogin( vo ); 
		if (mvo==null) { //로그인 실패시, 로그인 화면으로 이동
//			resp.sendRedirect(req.getContextPath() + "/member/login.do"); // 로그인 화면으로 이동
			return "redirect:/member/login.do";
			
		}else { //로그인 성공
//			HttpSession session = req.getSession();
			session.setAttribute("loginUser", mvo); //세션에 로그인한 사용자정보 저장			
//			resp.sendRedirect(req.getContextPath() + "/member/list.do"); // 회원 목록 화면으로 이동
			return "redirect:/member/list.do";
		}
	}
	
	@RequestMapping(value = "/member/logout.do", method = RequestMethod.GET)
	public String logout(HttpSession session, @SessionAttribute("loginUser") MemberVo vo) {
//		HttpSession session = req.getSession(); //현재 사용자의 세션을 가져옴
		session.invalidate(); //세션객체를 제거(후 다시 생성), 모든 속성들도 함께 삭제
		
//		resp.sendRedirect(req.getContextPath() + "/member/login.do"); // 로그인 화면으로 이동
		return "redirect:/member/login.do";
	}
}


