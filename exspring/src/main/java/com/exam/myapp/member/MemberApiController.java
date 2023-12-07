package com.exam.myapp.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.management.RuntimeErrorException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.view.JstlView;

//@Controller //스프링한테 Controller로 등록하라고 알려줌
@RestController //클래스 내부의 모든 요청처리 메서드에 @ResponseBody 일괄 적용
@RequestMapping("/api")
public class MemberApiController {
	
//	private MemberService memberService = MemberServiceImpl.getInstance();
	//singleton 객체를 하나만 만들어서 사용하려고
	//근데, spring을 사용하면, 기본이 singleton임
	
	@Autowired //@Resource @Inject 셋중 하나 사용하면 같은 역할
	private MemberService memberService;
	//spring이 MemberService 객체를 가지고 있어야 한다
	
	@GetMapping("/member/list")
	//@RequestMapping(value = "/member/list", method = RequestMethod.GET)
	//@ResponseBody //->문자 그대로 인식, 이게 없으면 모델로 인식함,
	public List<MemberVo> list(/*Model model*/) {
		
		List<MemberVo> list = memberService.selectMemberList(); //db에서 회원목록 가져옴
		
		//model.addAttribute("memberList", list); //가져온 회원목록을 model에 저장
		//->jsp에서 쓰려고 model에 담았는데 지금 jsp 안 쓰므로, 필요없음
		
		//return "member/memList"; // memList.jsp로 forward
		return list;
	}
	
	
	/*
	@RequestMapping(value = "/member/add.do", method = RequestMethod.GET)
	public String addform(MemberVo vo) {
//		req.getRequestDispatcher("/WEB-INF/views/member/memAdd.jsp").forward(req, resp);
		return "member/memAdd";
	}
	*/
	
	
	//스프링에 등록된 표준BeanValidator를 사용하여
	//저장된 값을 검증하고 싶은 객체 매개변수 앞에 @Valid 적용
	//@Valid 매개변수 다음 위치에 검증결과를 저장하기 위한
	//BindingResult 또는 Errors 타입의 매개변수를 추가
	@PostMapping("/member/add")
	//@RequestMapping(value = "/member/add", method = RequestMethod.POST)
	//@ResponseBody
	public Map<String, Object> add(@Valid /*@ModelAttribute("mv")*/ MemberVo vo, BindingResult result) {
		
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
			//return "member/memAdd"; //회원정보 입력 화면(JSP) 출력(실행)
			throw new RuntimeException("회원정보검증오류"); //->에러를 던져서 실행 중단시키겠음
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
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", n);
		
	//	resp.sendRedirect(req.getContextPath() + "/member/list.do");
		//return "redirect:/member/list.do";
		return map; //{num:0} or {num:1}

	}
	
	@GetMapping("/member/edit")
	//@RequestMapping(value = "/member/edit", method = RequestMethod.GET)
	//@ResponseBody
	public MemberVo editform(/*@RequestParam("memId") */String memId/*, Model model*/) {
//		String memId = req.getParameter("memId");
		
		MemberVo vo = memberService.selectMember(memId);
		
//		req.setAttribute("mvo", vo);
		//model.addAttribute("mvo", vo);
		
//		req.getRequestDispatcher("/WEB-INF/views/member/memEdit.jsp").forward(req, resp);
		//return "member/memEdit";
		return vo;
	}
	
	@PostMapping("/member/edit")
	//@RequestMapping(value = "/member/edit", method = RequestMethod.POST)
	//@ResponseBody
	public Map<String, Object> edit(MemberVo vo) {
	
//	req.setCharacterEncoding("UTF-8"); //필터로 이동, 필터에 있으니까 필요x
//	MemberVo vo = new MemberVo();
//	vo.setMemId(req.getParameter("memId"));
//	vo.setMemName(req.getParameter("memName"));
//	vo.setMemPoint(Integer.parseInt(req.getParameter("memPoint")));

	int n = memberService.updateMember(vo); //updateMember 구현 (MemberDao)
	
	System.out.println(n + "명의 회원 변경");

	//redirect : 지정한 주소로 이동하라는 명령을 담은 응답을 웹브라우저에게 전송
//	resp.sendRedirect(req.getContextPath() + "/member/list.do"); // 목록 화면으로 이동
	//return "redirect:/member/list.do";
	//return "redirect:/member/list.do";
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("num", n);
	return map; //{num:0} or {num:1}
	
	}
	
	//1.삭제기능이 동작하도록 이 메서드 코드를 변경
	//2.회원정보변경 화면에 삭제버튼을 추가
	@GetMapping("/member/del")
	//@RequestMapping(value = "/member/del", method = RequestMethod.GET)
	//@ResponseBody
	public Map<String, Object> del(String memId) {
		
//		req.setCharacterEncoding("UTF-8"); //필터로 이동
		//~ 파라미터 값 읽어오기 ~
//		String memId = req.getParameter("memId");
		
		int n = memberService.deleteMember(memId);
		
		System.out.println(n + "명의 회원 삭제");
		
//		resp.sendRedirect(req.getContextPath() + "/member/list.do");
		//return "redirect:/member/list.do";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", n);
		return map; //{num:0} or {num:1}
	}

}


