package practice;

public class Practice{

	public static void main(String[] args) {
		A temp = new B();
		temp.test();
		
		B temp2 = (B)temp;
		temp2.a();
	}
}