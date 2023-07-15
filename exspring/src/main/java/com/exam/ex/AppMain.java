package com.exam.ex;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppMain {

	public static void main(String[] args) {
		//Myapp, MyServiceKo 를 사용하여,
		//콘솔에 "안녕"이 출력되도록 구현
		
//		MyApp app = new MyApp();
//		MyApp.say(); //MyApp 객체가 있어야 method가 실행된다
//		방금만든MyApp객체.say(); 이렇게 할 수는 없으니까, MyApp app = new MyApp();
		
//		MyServiceKo msk = new MyServiceKo();
//		app.setMyService(msk);
		
//		MyServiceEn mse = new MyServiceEn();
//		app.setMyService(mse);		

		//스프링 == (IoC/DI 기능을 가진) 객체컨테이너(객체들을 여러개 담고 있는 상자, 바구니) == Bean(spring에 등록되서 사용되는 객체)Factory == ApplicationContext(애플리케이션이 실행되고 있는 환경)
		
		//클래스패스 상의 XML 파일로부터 설정을 읽어서, 스프링 객체 컨테이너를 생성
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("/com/exam/ex/context.xml");
		
		//JAVA 클래스로부터 설정을 읽어서, 스프링 객체 컨테이너를 생성
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfig.class);
		
		//스프링에 "ma"라는 이름으로 등록되어 있는 객체를 가져오기
		MyApp app = (MyApp) ctx.getBean("ma");
//		MyApp app = ctx.getBean("ma", MyApp.class);
		
		app.say();
		
//		MyApp myApp = new MyApp();
//		myApp.setMyService(new MyServiceKo());
//		myApp.say();
		
	}
}
