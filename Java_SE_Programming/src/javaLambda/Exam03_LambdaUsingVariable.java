package javaLambda;
/*
 * ���ٽ��� �����ؼ� ����� �� �����ؾ� �� ��
 * Ŭ������ ���(�ʵ� + �޼ҵ�)
 * 
 * Ư�� this keyword�� ����� �� �����ؾ� ��
 * this : ���� ���Ǵ� ��ü�� reference
 * ���ٽ��� �͸� ��ü�� ����� ���� �ڵ�. 
 * ���ٽ��� �����ڵ峻���� this keyword�� ���� �͸�ü�� ��Ī���� ����
 * ���ٽľȿ����� ���������� readonly ���·� ����ؾ� ��
 */

@FunctionalInterface
interface Exam03_LambdaIF{
	public void myFunc();
}

class OuterClass{
	//Field( �⺻������ class�� field�� private)
	public int outerField = 100;
	public OuterClass() {
		System.out.println(this.getClass().getName());
	}
	
	//class�ȿ� �ٸ� class�� �����Ұſ���(inner class)
	class InnerClass{
		int innerField = 200;
		
		  Exam03_LambdaIF fieldLambda = ()->{
		  System.out.println("outerField: "+outerField);
		  System.out.println("OuterClass�� ��ü�� ã�ƿ� : "+OuterClass.this.outerField);
		  System.out.println("innerField : "+innerField);
		  System.out.println("this.innerFeild : "+this.innerField); };
		 
		
//		  Exam03_LambdaIF fieldLambda = new Exam03_LambdaIF() { 
//			  public void myFunc() {
//				  System.out.println(this.getClass().getName()); } };
		 
		public InnerClass() {
			System.out.println(this.getClass().getName());
		}
		public void innerMethod() {
			int localVal = 100;	//��������
								//���������� stack������ �����̵ǰ�
								//method�� ȣ��Ǹ� �����
								//method�� ������ ������
			Exam03_LambdaIF localLambda = ()->{
				System.out.println(localVal); //���ٿ��� ���������� readonly
				//localVal = 50;
			};
			
			localLambda.myFunc();
		}
	}
}

public class Exam03_LambdaUsingVariable {
	
	public static void main(String[] args) {
		// ���ٽ��� ����Ϸ��� InnerClass�� instance�� �����ؾ���
		// �׷��� �����̸� �� InnerClass�� inner class��??
		// inner class�� instance�� �����Ϸ��� outer class�� instance���� �����ؾ���
		OuterClass outer = new OuterClass(); //�ܺ� Ŭ������ ��ü ����
		OuterClass.InnerClass inner = outer.new InnerClass();
		inner.fieldLambda.myFunc();
		inner.innerMethod();
	}
}
