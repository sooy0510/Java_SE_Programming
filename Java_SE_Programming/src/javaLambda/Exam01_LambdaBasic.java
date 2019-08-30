package javaLambda;
/*
 * �Լ��� ���α׷��� ������ ���� Java�� 8�������� Lambda�� ������
 * 
 * ���ٴ� �͸��Լ��� ���帯 ���� expression(��)
 * ==> ��ü������ٴ� �Լ������� ���� ���� => �ڹٽ�ũ��Ʈ�� ����
 * 
 * ���� �ڹ� �����ڵ��� Lambda��� ���信 �ͼ������� ����m��
 * �׷����� Lambda�� ������ ���� ũ�� 2����
 * 1. �ڵ尡 ����
 * 2. Java Stream�� �̿��ϱ� ���ؼ� ���ٸ� �̿�
 *    Java Stream�� collection(List, Map, Set, Array...)�� ó����
 *    ȿ�������� �� �� ����(����ó���� ����)
 *    
 *    ���ٽ��� ����
 *    (�Ű�����) -> {���� �ڵ�}
 *    �͸��Լ��� �����ϴ� ���·� �Ǿ� ������ �����δ� �͸�Ŭ������ �ν��Ͻ��� ���� 
 *    
 *    ���ٽ��� � ��ü�� �����ϴ��Ĵ� ���ٽ��� ���ԵǴ� interface ������
 *    � interface�ΰ��� �޷�����
 *    
 *    �Ϲ����� interface�� �����ؼ� ���ٽ����� ǥ��
 */

interface Exam01_LamdaIF{
	// �߻� method�� �� �� ����
	// method�� ���ǰ� ���� ���� �����ϴ� �� �߻�(abstract method) method
	void myFunc(int k);
	// lambda������ ǥ���Ϸ��� interface�� �߻�޼ҵ尡 1�����߸� ��
	// => lambda�� ���� ���ڰ� ��� �߻�޼ҵ��� �������� ��
}

//class MyThread extends Thread{
/*class MyRunnable implements Runnable{
	public void run() {
		System.out.println("�����尡 ����ſ�!");
	};
}*/
public class Exam01_LambdaBasic {

	public static void main(String[] args) {
		// Thread�� �����Ϸ��� �ؿ�!
		// 1. Thread�� subclass�� �̿��ؼ� Thread����
		//MyThread t = new MyThread();
		//t.start();
		// ���� �Ⱦ� -> ��ü���������� ����� ���� ����� �ƴ�, ���������� ���뼺�� ������
		// ������ interface�� �����ؼ� ���
		// 2. Runnable interface�� ������ class�� �̿��ؼ�
		// Thread�� ����(�� ���� ���)
		/*
		 * MyRunnable runnable = new MyRunnable(); Thread t = new Thread(runnable);
		 * t.start();
		 */
		// 3. Runnable interface�� ������ �͸� class�� �̿��ؼ� 
		// Thread����(�ȵ���̵忡�� �Ϲ����� ����)
		/*
		 * Runnable runnable = new Runnable() { //��ü�� ���� ���ϴ� ������ �߻� �޼ҵ尡 �����ϱ� �����ε� //��
		 * method�� overiding�ϸ� ��ü ���� ����
		 * 
		 * @Override public void run() {
		 * 
		 * } };
		 */
		// lambda������ ��ȯ

		
		//new Thread(()->{ System.out.println("������ ����!"); }).start();
		
		
		Exam01_LamdaIF sample = 
				(int k)-> {System.out.println("��µ�!");};
	
		sample.myFunc(100);
	}

}
