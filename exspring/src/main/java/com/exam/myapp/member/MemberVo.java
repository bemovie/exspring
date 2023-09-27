package com.exam.myapp.member;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

//표준 명세 Bean Validation 2.0부터 사용가능
//@NotEmpty, @NotBlank, @Email
//@Positive, @PositiveOrZero, @Negative, @NegativeOrZero
//@Future, @FutureOrPresent, @Past, @PastOrPresent

public class MemberVo {
	@NotNull @Size(min = 3, max = 50) @Email 
	private String memId;
	@NotNull @Size(min = 4, max = 50)
	private String memPass;
	@NotNull @Size(min = 1, max = 50, message = "회원이름은 1~50글자로 입력하세요")
	private String memName;
	@Digits(integer = 10, fraction = 0) /*@Positive*/
	private int memPoint;
	
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemPass() {
		return memPass;
	}
	public void setMemPass(String memPass) {
		this.memPass = memPass;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public int getMemPoint() {
		return memPoint;
	}
	public void setMemPoint(int memPoint) {
		this.memPoint = memPoint;
	}
	
}
