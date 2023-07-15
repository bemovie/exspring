package com.exam.myapp.reply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.exam.myapp.bbs.BbsService;
import com.exam.myapp.bbs.BbsVo;
import com.exam.myapp.member.MemberVo;

//@RequestMapping("/reply/")
//@Controller
@RestController //현재 클래스의 모든 요청처리 메서드에 @ResponseBody를 적용
public class ReplyController {
	
	@Autowired //@Resource @Inject 셋중 하나 사용하면 같은 역할 => 주입을 해달라고, => replyServiceImpl 객체가 주입이 됨
	private ReplyService replyService;
	
	@GetMapping("/reply/list.do") 
//	@ResponseBody //메서드의 반환값을 그대로 응답메시지 내용으로 전송
	public List<ReplyVo> list(int repBbsNo) {
		
		List<ReplyVo> repList = replyService.selectReplyList(repBbsNo);
		return repList;
		
	}
//		Map<String, Object> map = new HashMap<String, Object>();
//		for ( ReplyVo vo : repList ) {
//			map.put("repNo", vo.getRepNo());
//			map.put("repContent", vo.getRepContent());
//			map.put("repWriter", vo.getRepWriter());
//			map.put("repRegDate", vo.getRepRegDate());
//			map.put("repBbsNo", vo.getRepBbsNo());
//		}
//		return map;
//	}

	
	
	
	
	
//	@RequestMapping(path = "/reply/add.do", method = RequestMethod.POST)
	@PostMapping("/reply/add.do") 
//	@ResponseBody //메서드의 반환값을 그대로 응답메시지 내용으로 전송
	public Map<String, Object> add(ReplyVo vo
			, @SessionAttribute("loginUser") MemberVo mvo //지정한 세션속성값을 이 변수에 주입(전달)
//			, HttpSession session
			) {
//		MemberVo mvo = (MemberVo) session.getAttribute("loginUser");
		vo.setRepWriter(mvo.getMemId()); //로그인한 사용자아이디를 게시글 작성자로 설정
		int num = replyService.insertReply(vo); //db에 넣어주고,
//		System.out.println(num + "개의 댓글 추가");
		
//		MemberVo v = new MemberVo();
//		v.setMemId("a001");
//		v.setMemName("고길동");
//		v.setMemPoint(10);
		
		//자바스크립트로 위의 v와 동일한 데이터를 저장한 객체를 정의
//		var v = { memId : "a001", memName : "고길동", memPoint : 10 }; // JS에서는, {}가 객체 표현
		
		//JSON은 자바스크립트 객체 표현과 동일하지만 2가지 차이점 존재
		//(1)문자열은 반드시 큰따옴표만 가능(작은따옴표사용불가)
		//(2)객체의 속성이름은 반드시 문자열로 표현
//		String jsonStr = "{ \"memId\" : \"a001\", \"memName\" : \"고길동\", \"memPoint\" : 10 }";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ok", true);
		map.put("result", num);
		
//		단순 문자열이 아니라, 복잡한 데이터 (데이터 2개 이상) 보내려면, 데이터를 객체에 담아서 그걸 JSON 형태로 보냄
		
		
		
//		return "redirect:/bbs/edit.do?bbsNo="+vo.getRepBbsNo();
//		return num+"개의 댓글 저장"; //"/WEB-INF/views/1개의 댓글 저장.jsp"로 forwarding 시켜버림
//		return num+"reply add";
//		return "{ \"ok\" : true, \"result\" : " + num + " }";
		return map;
		
	}
	
	
	@GetMapping("/reply/del.do") 
//	@ResponseBody //메서드의 반환값을 그대로 응답메시지 내용으로 전송
	public Map<String, Object> del( /*int repNo,*/ ReplyVo vo
			, @SessionAttribute("loginUser") MemberVo mvo //지정한 세션속성값을 이 변수에 주입(전달)
//			, HttpSession session
			) {
//		MemberVo mvo = (MemberVo) session.getAttribute("loginUser");
		vo.setRepWriter( mvo.getMemId() ); //로그인한 사용자아이디를 게시글 작성자로 설정
		
		int num = replyService.deleteReply(vo); //db에 넣어주고,
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ok", true);
		map.put("result", num);
		//사실 ok는 없어도 됨, 추가로 보내고 싶은 정보가 있으면, 넣어서 반환
		
		return map;
		
	}
	
	
	
	
	
	
	
	
	/*
	@GetMapping("/reply/del.do")
	@ResponseBody
	public Map<String, Object> del(int RepNo) {
		int num = replyService.deleteReply(RepNo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ok", true);
		map.put("result", num);
		
		return map;
	}
	*/
	
	
	
	/*
	@GetMapping(value="/reply/del.do", produces = "application/text; charset=utf8")
	@ResponseBody
	public String del(int RepNo, @SessionAttribute("loginUser") MemberVo mvo) {
		
		ReplyVo rvo = replyService.selectReply(RepNo);
		
		if(mvo.getMemId().equals(rvo.getRepWriter())) {
			int n = replyService.deleteReply(RepNo);
			return n + "개의 댓글 삭제 성공";
		}
		return "오류 발생";
	}
	*/
	

}
