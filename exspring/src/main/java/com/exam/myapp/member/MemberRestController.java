package com.exam.myapp.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController //클래스 내부의 모든 요청처리 메서드에 @ResponseBody 일괄 적용
@RequestMapping("/rest")
public class MemberRestController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/members")
	public List<MemberVo> list() {
		List<MemberVo> list = memberService.selectMemberList(); //db에서 회원목록 가져옴
		return list;
	}
	
	@PostMapping("/members")
	public ResponseEntity<Map<String, Object>> add(@RequestBody @Valid MemberVo vo, BindingResult result) { //@RequestBody : 요청메시지 본문의 내용을 그대로 저장
		
		System.out.println(result.getAllErrors());
		
		if (result.hasErrors()) { //검증결과 오류가 있다면
			throw new RuntimeException("회원정보검증오류"); //->에러를 던져서 실행 중단시키겠음
		}
		
		int n = memberService.insertMember(vo);
		
		System.out.println(n + "명의 회원 추가");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", n);
		
		//return map; //{num:0} or {num:1}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED); //제너릭에다가는 body 타입을 적어줌
	}
	
	//경로내에서 경로변수로 저장하고 싶은 부분을 {경로변수명}으로 지정
	//@GetMapping("/members/조회하고싶은회원의아이디")
	@GetMapping("/members/{memId}") //인자로 받지 않는다면 *를 사용하면 모든 아이디가 통용된다, 
	public MemberVo editform(@PathVariable("memId") String memId) {
		MemberVo vo = memberService.selectMember(memId);
		return vo;
	}
	
	@PatchMapping("/members/{memId}") //Put : 전체수정, Patch : 일부수정
	//@RequestBody 요청메시지의 본문 내용을 자바 객체로 변환하여 매개변수에 저장, HttpConverter가 Content-type 헤더 본문을 보고, json인지, xml인지, 판단해서 변환해줌 
	public Map<String, Object> edit(@RequestBody MemberVo vo) { 

		int n = memberService.updateMember(vo); //updateMember 구현 (MemberDao)
		
		System.out.println(n + "명의 회원 변경");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", n);
		return map; //{num:0} or {num:1}
	
	}
	
	@DeleteMapping("/members/{memId}")
	//경로변수명과 매개변수명이 동일하면, @PathVariable()에서 경로변수명 생략 가능
	//public Map<String, Object> del(@PathVariable("memId") String memId) {
	public Map<String, Object> del(@PathVariable String memId) {
		int n = memberService.deleteMember(memId);
		
		System.out.println(n + "명의 회원 삭제");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", n);
		return map; //{num:0} or {num:1}
	}

}


