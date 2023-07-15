package com.exam.ex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration //스프링 설정파일 역할을 하는 클래스임을 표시
//@ComponentScan(value = "com.exam.ex" ) 
@ComponentScan("com.exam.ex" ) //<context:component-scan base-package="com.exam.ex"></context:component-scan>와 같은 역할
public class MyConfig {
	
//	@Bean(name = "ma") 
	//@Bean//("ma") //이 메서드에서 반환하는 객체를 "ma"라는 이름으로 스프링에 등록
	public MyApp ma() { //이름을 생략하면 메서드명이 빈 이름으로 사용
		MyApp app = new MyApp();		
//		app.setMyService( this.msk() );
		app.setMyService( mse() );
//		return new MyApp();
		return app;
	}
	
	//@Bean//("msk") //이 메서드에서 반환하는 객체를 "msk"라는 이름으로 스프링에 등록
	public MyService msk() {
		return new MyServiceKo();
	}

	//@Bean//("mse") //이 메서드에서 반환하는 객체를 "mse"라는 이름으로 스프링에 등록
	public MyService mse() {
		return new MyServiceEn();
	}

}
