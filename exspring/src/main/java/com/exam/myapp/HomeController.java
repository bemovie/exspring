package com.exam.myapp;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */

@Controller //컨트롤러(요청을 받았을 때 실행되는 객체)로서 스프링(DispatcherServlet)에 등록
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	// "/" 경로로 GET 방식 요청이 오면 실행
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, Map map, ModelMap modelMap) {
		logger.info("Welcome home! The client locale is {}.", locale);
		//locale : 지역이나 언어, 인자로 받은 locale 정보를 로그로 출력(logger는 system.out과 비슷)
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		//현재시각을 담은 객체를 문자열로 바꾸는데, 현재 지역에 맞게끔(미국이면 미국, 한국이면 한국)
		
		String formattedDate = dateFormat.format(date);
		
		//모델 : 응답/화면(JSP) 출력시 포함할 데이터 (서블릿에서는 setAttribute로 넣었지만, spring에서는 dispatcher가 처리해주므로 model에다가 집어넣으면 된다)
		//모델에 데이터를 추가(저장)하기 위해서는,
		//인자로 받은 Model, Map, ModelMap (셋 중에 하나) 객체에 모델이름-값 형식으로 데이터를 저장
		//지금은 Model로 받고 있음 (Model model)
		//JSP에서는 ${모델이름} 으로 값을 꺼내서 사용 가능
		
		model.addAttribute("a", formattedDate ); //JSP에서는 ${serverTime}으로 사용 가능
		//model은 요청객체 넣는것
		map.put("b", formattedDate ); //map은 원래 java에 있는 class, put으로 저장, model과 modelmap은 spring에서 제공 
		modelMap.addAttribute("c", formattedDate );
		
		return "home"; //컨트롤러가 문자열을 반환하면, 스프링은 뷰이름으로 인식
		//servlet-context.xml의 InternalResourceViewResolver 설정대로
		//문자열 앞에 "/WEB-INF/views/"를 추가하고
		//문자열 뒤에 ".jsp"를 추가한 주소(경로)로 이동(forward)
		//즉, "/WEB-INF/views/home.jsp" 파일을 실행
	}
	
	//브라우저에서 "http://localhost:8000/myapp/t"로 접속하면,
	//test()메서드와 test.jsp가 순서대로 실행되어
	//test.jsp의 h1 태그 내용에 변수 s 값("JSP에서 출력할 문자열")이 출력되도록 구현 
	@RequestMapping(value = "/t", method = RequestMethod.GET)
//	@GetMapping(value = "/test") // 요청방식별 @RequestMapping 애노테이션도 사용 가능
//	@GetMapping("/test") // 애노테이션에서 value 값 하나일때는 value 생략 가능
	public String m(@RequestParam(value = "x") String xv //이름이 x인 요청파라미터 값을 변수에 저장 (dispatcherServlet이 getParameter해서 자동적으로 처리)
//					,@RequestParam(value = "y") int y //파라미터 이름과 변수 이름이 동일하면 생략을 해도 된다
					,int y //변수이름이 파라미터이름과 동일하면 @RequestParam 생략 가능
					//객체의 속성명과 동일한 이름의 파라미터 값을 객체의 속성에 자동 저장
//					,@ModelAttribute(value = "mv") MyVo vo
					,@ModelAttribute("mv") MyVo vo //@ModelAttribute("모델명") 을 적용하여
					//매개변수 값을 지정한 이름으로 모델에 저장(추가)가능
					,MyVo v //파라미터를 받기 위해서 배치한 매개변수는 자동으로 모델에 추가 
					//MyVo객체를 받으려고 놨을때, 모델에는 들어가 있음,but 꺼내 쓰고 싶은데 이름을 모름
					//@ModelAttribute에서 모델명을 생략한 경우, 모델이름은 타입명의 첫글자를 소문자로 변환하여 사용
					//타입이름이 MyVo이므로 M의 소문자인 m으로 사용 => myVo
					,Model model) { //이름을 반환
		logger.info("xv : {}, y : {}", xv, y); // system.out.print처럼 +로 이어붙이는게 아니라, {}하면 뒤에 나열한 변수값들을 끼워 넣어서 출력(로그 라이브러리가 지원, 스프링과 상관x)
		logger.info("vo.x : {}, vo.y : {}", vo.getX(), vo.getY());
		String s = "JSP에서 출력할 문자열";
		model.addAttribute("sv", s); //모델에 값을 추가
//		model.addAttribute("mv", vo); //이렇게 손수 manual(수동)으로 넣어주지 않아도 된다 => 애노테이션을 써서 지시 가능
		
//		model.addAttribute("x" , vo.getX());
//		model.addAttribute("y" , vo.getY());
		
		
		return "test"; //뷰를 반환 (스프링에서는 /WEB-INF/views/test.jsp에서 앞뒤 생략)
	}
	
}
