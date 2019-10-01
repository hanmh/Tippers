package practice;

public class Test {

	private int a;
	private int b;
	private int c;
	
	public Test(int a, int b, int c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public Test(int a, int b) {
		Test(a, b, 10);
	}
	
	public Test(int a) {
		Test(a, 10);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test t = new Test(1, 2);
	}

}
