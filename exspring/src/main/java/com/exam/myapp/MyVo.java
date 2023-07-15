package com.exam.myapp;

public class MyVo { 
	
	private int x; //속성이름이 파라미터값과 같아야 한다, 속성이름은 x가 아니라 getX의 X임, 근데 일반적으로 변수이름과 속성이름을 맞춤
	private int y;
	
	public int getX() { 
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

}
