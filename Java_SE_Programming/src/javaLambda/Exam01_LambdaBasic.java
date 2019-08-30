package javaLambda;
/*
 * 함수적 프로그래밍 패턴을 위해 Java는 8버전부터 Lambda를 지원함
 * 
 * 람다는 익명함수를 만드릭 위한 expression(식)
 * ==> 객체지향언어보다는 함수지향적 언어에서 사용됨 => 자바스크립트와 유사
 * 
 * 기존 자바 개발자들은 Lambda라는 개념에 익숙해지기 어려웟음
 * 그럼에도 Lambda를 도입한 이유 크게 2가지
 * 1. 코드가 간결
 * 2. Java Stream을 이용하기 위해서 람다를 이용
 *    Java Stream은 collection(List, Map, Set, Array...)의 처리를
 *    효율적으로 할 수 있음(병렬처리가 가능)
 *    
 *    람다식의 형태
 *    (매개변수) -> {실행 코드}
 *    익명함수를 정의하는 형태로 되어 있지만 실제로는 익명클래스의 인스턴스를 생성 
 *    
 *    람다식이 어떤 객체를 생성하느냐는 람다식이 대입되는 interface 변수가
 *    어떤 interface인가에 달려있음
 *    
 *    일반적인 interface를 정의해서 람다식으로 표현
 */

interface Exam01_LamdaIF{
	// 추상 method만 올 수 있음
	// method의 정의가 없고 선언만 존재하는 게 추상(abstract method) method
	void myFunc(int k);
	// lambda식으로 표현하려면 interface에 추상메소드가 1개여야만 함
	// => lambda식 사용시 인자가 어느 추상메소드의 인자인지 모름
}

//class MyThread extends Thread{
/*class MyRunnable implements Runnable{
	public void run() {
		System.out.println("쓰레드가 실행돼요!");
	};
}*/
public class Exam01_LambdaBasic {

	public static void main(String[] args) {
		// Thread를 생성하려고 해요!
		// 1. Thread의 subclass를 이용해서 Thread생성
		//MyThread t = new MyThread();
		//t.start();
		// 많이 안씀 -> 객체지향적으로 상속은 좋은 방법이 아님, 유지보수와 재사용성이 안좋음
		// 보통은 interface를 구현해서 사용
		// 2. Runnable interface를 구현한 class를 이용해서
		// Thread를 생성(더 좋은 방식)
		/*
		 * MyRunnable runnable = new MyRunnable(); Thread t = new Thread(runnable);
		 * t.start();
		 */
		// 3. Runnable interface를 구현한 익명 class를 이용해서 
		// Thread생성(안드로이드에서 일반적인 형태)
		/*
		 * Runnable runnable = new Runnable() { //객체를 생성 못하는 이유는 추상 메소드가 존재하기 때문인데 //이
		 * method를 overiding하면 객체 생성 가능
		 * 
		 * @Override public void run() {
		 * 
		 * } };
		 */
		// lambda식으로 변환

		
		//new Thread(()->{ System.out.println("쓰레드 실행!"); }).start();
		
		
		Exam01_LamdaIF sample = 
				(int k)-> {System.out.println("출력됨!");};
	
		sample.myFunc(100);
	}

}
