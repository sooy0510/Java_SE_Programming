package javaLambda;
/*
 * Operator�� Function�� �ϴ����� ���� ���
 * �Է¸Ű������� �ְ� ���ϰ��� ����
 * Function�� ���ο뵵�� ���� ����(�Է¸Ű������� ����Ÿ������ ��ȭ, ������ �뵵)
 * Operator�� ����뵵�� ���� ����(�Է¸Ű������� ���꿡 �̿��Ͽ� ���� Ÿ���� 
 * ���ϰ��� �����ִ� ����)
 * 
 * �ִ밪�� �ּҰ��� ���ϴ� static method�� �ۼ�
 */

import java.util.function.IntBinaryOperator;

public class Exam07_LambdaUsingOperator {

	private static int arr[] = {100,92,50,89,34,27,99,3};
	
	//getMaxMin()�� static method�� ���鲨����
	//����ϴ� Operator�� IntBinaryOperator�� �̿�
	// IntBinaryOperator => interface Ÿ��
	private static int getMaxMin(IntBinaryOperator operator) {
		int result = arr[0];
		
		for(int k : arr) {
			result = operator.applyAsInt(result, k);
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println("�ִ밪 : "+ getMaxMin((a,b) -> {
			//�ִ밪
			return a>=b ? a : b;
		}));
		System.out.println("�ּҰ� : "+ getMaxMin((a,b) -> {
			//�ּҰ�
			return a<b ? a : b;
		}));
	}

}
