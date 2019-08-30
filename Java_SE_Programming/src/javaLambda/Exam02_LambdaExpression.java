package javaLambda;

/*
 * ���ٽ��� ǥ���ϴ� �����
 * (����1, ����2, ����3...)->{ �����ڵ�}
 *
 * 1. �Ű������� �̸��� �����ڰ� ������ �� ����
 * 2. �Ű������� Ÿ���� �����Ϸ��� Ÿ�����߿� ���ؼ� �� �� �ֱ� ������
 *    ���ٽĿ����� �Ϲ������� �Ű������� Ÿ���� �������� ����
 * 3. ���� �Ű������� 1���� ��� ()��������
 * 4. ���� ���๮�� 1���� ��� {}�� ��������
 * 5. �Ű������� ���ٸ� ()�� ���� �Ұ���
 * 6. ���๮�� �翬�� return ������ ���� �� ����
 * 7. ���๮�� return  ���� 1���� �����ϸ� return�̶�� Ű���嵵 ����������
 * 
 * ���ٽ��� interface type�� instance�� �����ϴ� expression
 * ���ٽ��� �ᱹ �͸� ��ü�� �����ϴ� �ڵ�
 * ���ٽ��� �����ϴ� ��ü�� �ᱹ � interface type�� ������ assign �� �Ǵ°��� �޷�����
 * �̷��� ���ٽ����� ��������� ��ü�� interface type�� ������ target type�̶�� ��
 * 
 * target type�� �ƹ� interface�� ����� �� ����
 * ������ target type�� �ɷ��� �ش�  interface�� �ݵ�� �߻� �޼ҵ尡 1���� 
 * �����ؾ� ��
 * �׷��� interface�� ����� �� ������̼��� �̿��ؼ� check�� �� �� ����
 * @FunctionalInterface�� �̿��ؼ� �ش� interface�� ������ target type�� �� �� �ִ���
 * compiler�� ���� check�� �� �� ����(�Լ��� interface)
 * 
 * Thread�� ������ �� Runnable interface�� ����ϴµ�
 * �� Runnable interface�� public void run()�̶�� �߻� method 1���� ������ ����
 * Runnable interface�� �Լ��� �������̽���� �� �� ����
 * - �̺�Ʈ�� ó���ϴ� interface�� ��ü�� �Լ��� interface
 */

@FunctionalInterface
interface Exam02_LambdaIF{
	int myFunc(int a, int b);
	//�Ű����� ���� ���
	//void myFunc();
}
public class Exam02_LambdaExpression {
	public static void main(String[] args) {
		Exam02_LambdaIF obj = (a,b)-> a+b;
		// �Ű����� ���� ���
		//Exam02_LambdaIF obj = ()-> System.out.println("�ڵ尡 ����ſ�");
		System.out.println(obj.myFunc(10,20));
	}
}
