package javaThread;

/*
 * 2개의 Thread를 파생시켜서 공용객체를 이용하도록 만들어 보아요
 * Thread가 공용객체를 동기화해서 사용하는 경우와 그렇지 않은 경우를
 * 비교해서 이해해보자
 * 
 * 공용객체를 만들기 위한 class를 정의해 보자
 */
// Runnable interface를 구현한 class(Thread 생성자의 인자로 이용하기 위해)
class MyRunnable implements Runnable{
	SharedObject shared;
	int input;
	
	public MyRunnable(SharedObject shared, int input) {
		this.shared = shared;
		this.input = input;
	}

	@Override
	public void run() {
		shared.assignNumber(input);
	}
}

// 공용객체  class
class SharedObject{
	private int number;	//공용객체가 가지는 field

	// getter & setter (Thread에 의해 사용)
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	// Thread에 의해서 사용이 되는 business method
	// synchronized keyword에 의해 assignNumber은 1개의 Thread만 점령 가능..
	// 2번재 thread가 와도 monitor를 첫번째 thread가 가지고 있기 때문에 block
	// 하지만 method동기화는 비효율적!
	// 동기화 block을 이용해서 처리하는게 읾반적
	//public synchronized void assignNumber(int number) { //동기화 block
	public void assignNumber(int number) {
		// 동기화 block, 필요한 부분만 동기화
		synchronized (this) {
			this.number = number;
			try {
				Thread.sleep(3000);
				System.out.println("현재 공용객체의 number : "
						+ this.number);
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
}
public class Exam04_ThreadSync {

	public static void main(String[] args) {
		// 공용객체 생성
		SharedObject shared = new SharedObject();
		
		//Thread 생성(2개) - 공용객체를 가지는 Thread를 생성
		Thread t1 = new Thread(new MyRunnable(shared,100));
		Thread t2 = new Thread(new MyRunnable(shared,200));
		
		// Thread 실행(runnable 상태로 전환)
		t1.start();
		t2.start();
	}

}
