package com.exam.myapp.bbs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.exam.myapp.member.MemberService;
import com.exam.myapp.member.MemberVo;

@Controller //스프링한테 Controller로 등록하라고 알려줌
@RequestMapping("/bbs/") //현재 컨트롤러 클래스 내부의 모든 메서드들의 공통 경로 설정
public class BbsController {
	@Autowired //@Resource @Inject 셋중 하나 사용하면 같은 역할
	private BbsService bbsService;
	//spring이 MemberService 객체를 가지고 있어야 한다
	
	@GetMapping("list.do") //@RequestMapping(value = "list.do", method = RequestMethod.GET)
	public String list(Model model , 
			/*@ModelAttribute("sinfo")*/ SearchInfo info // 첫글자를 소문자로 바꿔서 => searchInfo로 자동으로 model에 넣어줌
			//,PageInfo info2
			) 
	{
		int cnt = bbsService.selectBbsCount(info); //전체 레코드 수 조회 
		//아주 많은 값으로 하려면 long으로 하면 되고, 아니면 int
		info.setTotalRecordCount(cnt); //전체 레코드 수 정보 설정 
		//총 record의 값을 select한 갯수로 채워줌
		info.makePageHtml(); //페이지 처리에 필요한 값들 계산
		
		List<BbsVo> list = bbsService.selectBbsList(info); //db에서 회원목록 가져옴
		model.addAttribute("bbsList", list); //가져온 회원목록을 model에 저장
		return "bbs/bbsList"; // bbsList.jsp로 forward
	}
	
	@GetMapping("add.do") //@RequestMapping(value = "add.do", method = RequestMethod.GET)
	public String addform() {
		return "bbs/bbsAdd";
	}
	
	
	@PostMapping("add.do") //@RequestMapping(value = "add.do", method = RequestMethod.POST)
	public String add(BbsVo vo
//			, HttpSession session
			, @SessionAttribute("loginUser") MemberVo mvo //지정한 세션속성값을 이 변수에 주입(전달)
			) {
		
//		System.out.println("첨부파일명: " + vo.getBbsFile().getOriginalFilename()); //bbsFile의 파일 이름
		
		//로그인한사람의아이디 = session에 있는 ID를 꺼내오면 됨
		//세션에 로그인 정보를 꺼내와서
//		MemberVo mvo = (MemberVo) session.getAttribute("loginUser"); //세션에서 로그인정보를 꺼내와서
		
		vo.setBbsWriter(mvo.getMemId()); //로그인한 사용자아이디를 게시글 작성자로 설정
		int n = bbsService.insertBbs(vo); //db에 넣어주고,
		System.out.println(n + "개의 글 추가");
		return "redirect:/bbs/list.do";
	}
	
	
	@GetMapping("edit.do") //@RequestMapping(value = "edit.do", method = RequestMethod.GET)
	public String editform(int bbsNo, Model model) {
		BbsVo vo = bbsService.selectBbs(bbsNo);
		model.addAttribute("bbsVo", vo);
		return "bbs/bbsEdit";
	}

	@PostMapping("edit.do") //@RequestMapping(value = "edit.do", method = RequestMethod.POST)
	public String edit(BbsVo vo
			, @SessionAttribute("loginUser") MemberVo mvo
			/* , Model model */
			) { //파라미터 이름과 속성 이름이 똑같아서, vo에 알아서 담아준다 

		vo.setBbsWriter( mvo.getMemId() );
		
		/*
		if(!mvo.getMemId().equals(vo.getBbsWriter())) {
			model.addAttribute("message", "작성자 본인만 수정 가능합니다.");
			return "redirect:/bbs/list.do";
		}
		*/
		
		int n = bbsService.updateBbs(vo); //updateMember 구현 (BbsDao)
		System.out.println(n + "개의 게시글 변경");
		return "redirect:/bbs/list.do";
	}
	
	@GetMapping("del.do") //@RequestMapping(value = "del.do", method = RequestMethod.GET)
	public String del(
			/*int bbsNo, String bbsWriter, @SessionAttribute("loginUser") MemberVo mvo, Model model*/
			BbsVo vo
			, @SessionAttribute("loginUser") MemberVo mvo
			) {
		
		vo.setBbsWriter( mvo.getMemId() );
		
		/*
		if(!mvo.getMemId().equals(bbsWriter)) {
			model.addAttribute("message", "작성자 본인만 삭제 가능합니다.");
			return "redirect:/bbs/list.do";
		}
		System.out.println(mvo.getMemId());
		System.out.println(bbsWriter);
		*/
		
		int n = bbsService.deleteBbs(vo);
		System.out.println(n + "개의 게시글 삭제");
		return "redirect:/bbs/list.do";
	}
	
	//컨트롤러 메서드가 인자로 HttpServletResponse, OutputStream, Writer를 받고,
	//반환타입이 void 이면,
	//직접 응답을 처리(전송)했다고 판단하여 스프링은 뷰에 대한 처리를 하지 않는다
	@GetMapping("down.do")													/*resp.getOutputStream() : 응답객체에 바로 쓸 수 있는 출력용 스트림, resp.getWriter() : 응답객체에 바로 쓸 수 있는 출력용 라이터*/
	public void download(int attNo/*, AttachVo vo*/, HttpServletResponse resp/*, OutputStream os, Writer out*/) {
		AttachVo vo = bbsService.selectAttach(attNo); //DB에서 다운로드할 첨부파일 정보 조회
	
		File f = bbsService.getAttachFile(vo); //디스크 상에서 첨부파일의 위치 가져오기
		//폴더 위치는 컨트롤러는 모르고 서비스가 알고 있기 때문에 서비스에 위치 알려주는 메서드 추가함
		
		resp.setContentLength( (int) f.length() ); //응답메세지 본문(파일)의 크기 설정
//		resp.setContentLengthLong( f.length() ); //응답메세지 본문(파일)의 크기 설정
		
//		resp.setContentType( "application/octet-stream" ); //무조건 다운로드 팝업창을 띄우도록 설정
		resp.setContentType( MediaType.APPLICATION_OCTET_STREAM_VALUE ); //spring 것을 받아야함, 미디어타입 다른걸 import 해놔서 이름이 김, 지워줘야 함
		
		// 다운로드 파일을 저장할 때 사용할 디폴트 파일명 설정
		// 지원하는 브라우저에 따라서 다른 처리가 필요할 수도 있다
//		1)
//		try {
//			String fname = URLEncoder.encode(vo.getAttOrgName(), "UTF-8").replace("+", "%20");
//			resp.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fname);
//
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
		
//		2)
		String cdv = ContentDisposition.attachment().filename(vo.getAttOrgName(), StandardCharsets.UTF_8).build().toString();
		resp.setHeader(HttpHeaders.CONTENT_DISPOSITION, cdv);
		
		
		try {
			//파일 f의 내용을 응답 객체(의 출력 스트림)에 복사(전송)
			FileCopyUtils.copy( new FileInputStream(f), resp.getOutputStream() ); //= f파일을 출력용 파이프에다가 복사 => 1번째 인자를 2번째 인자에 복사
		
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
//		vo.getAttNewName() //파일의 하드디스크 상의 이름, 경로도 알아야 함 => 바뀔 수 있다 => 읽어서 getter 같은걸로 쓰던지, bbs 서비스에 있는 uploadPath 정보를 활용해야 한다
		
//		return "redirect:/bbs/list.do";
	}
	
}


