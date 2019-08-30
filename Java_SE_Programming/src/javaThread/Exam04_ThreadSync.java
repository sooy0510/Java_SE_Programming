package javaThread;

/*
 * 2���� Thread�� �Ļ����Ѽ� ���밴ü�� �̿��ϵ��� ����� ���ƿ�
 * Thread�� ���밴ü�� ����ȭ�ؼ� ����ϴ� ���� �׷��� ���� ��츦
 * ���ؼ� �����غ���
 * 
 * ���밴ü�� ����� ���� class�� ������ ����
 */
// Runnable interface�� ������ class(Thread �������� ���ڷ� �̿��ϱ� ����)
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

// ���밴ü  class
class SharedObject{
	private int number;	//���밴ü�� ������ field

	// getter & setter (Thread�� ���� ���)
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	// Thread�� ���ؼ� ����� �Ǵ� business method
	// synchronized keyword�� ���� assignNumber�� 1���� Thread�� ���� ����..
	// 2���� thread�� �͵� monitor�� ù��° thread�� ������ �ֱ� ������ block
	// ������ method����ȭ�� ��ȿ����!
	// ����ȭ block�� �̿��ؼ� ó���ϴ°� �ѹ���
	//public synchronized void assignNumber(int number) { //����ȭ block
	public void assignNumber(int number) {
		// ����ȭ block, �ʿ��� �κи� ����ȭ
		synchronized (this) {
			this.number = number;
			try {
				Thread.sleep(3000);
				System.out.println("���� ���밴ü�� number : "
						+ this.number);
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
}
public class Exam04_ThreadSync {

	public static void main(String[] args) {
		// ���밴ü ����
		SharedObject shared = new SharedObject();
		
		//Thread ����(2��) - ���밴ü�� ������ Thread�� ����
		Thread t1 = new Thread(new MyRunnable(shared,100));
		Thread t2 = new Thread(new MyRunnable(shared,200));
		
		// Thread ����(runnable ���·� ��ȯ)
		t1.start();
		t2.start();
	}

}
