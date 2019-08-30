package javaThread;

/*
 * ���밴ü�� �����ϱ� ���� class�� ���� 
 */

class MyShared{
	
	//methodȣ��� �� Thread�� ������ ���鼭 ȣ���ϵ��� ����� �;��!
	public synchronized void printNum() {
		for(int i=0; i<10; i++) {
			try {
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName()
						+" : "+ i);
				notify(); //���� wait()���¿� �ִ� Thread�� ������
						  //runnable���·� ��ȯ
				wait();	  //�ڱⰡ ������ �ִ� monitor��ü�� ����
						  //������ wait block�� ����
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

class Exam05_Runnable implements Runnable{
	MyShared obj;
	
	public Exam05_Runnable(MyShared obj) {
		this.obj = obj;
	}
	
	@Override
	public void run() {
		obj.printNum();
	}
	
}

public class Exam05_ThreadWatiNotify {

	public static void main(String[] args) {
		//���밴ü �����
		MyShared shared = new MyShared();
		
		//Thread�� �����ϸ鼭 ���밴ü�� �־����!!
		Thread t1 = new Thread(new Exam05_Runnable(shared));
		Thread t2 = new Thread(new Exam05_Runnable(shared));
		
		t1.setPriority(10);
		t2.setPriority(1);
		
		t1.start();
		t2.start();
	}

}
