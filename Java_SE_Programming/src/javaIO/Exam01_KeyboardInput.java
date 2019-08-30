package javaIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Exam01_KeyboardInput {

	public static void main(String[] args) {
		System.out.println("Ű����� ������ �Է��ؿ�"); //System.out(��ü) stream�� ���� 
								//ǥ����� �������ܰ��� monitor�� ���
		
		// ǥ���Է�(keyboard)�κ��� �Է��� �ޱ����� keyboard�� 
		// ����� stream��ü�� �ʿ�
		// Java�� ǥ���Է¿� ���� Stream�� �⺻������ ����
		// System.in (��ü�� ���·� ����)(inputStream) => byteStream����
		// Stream�� �̷��� inputStream�� outputStream���� ����
		// byteStream�� reader(�⺻��������), writer�迭 stream(���ڿ�)���� ����  
		// ������ ���°� �ƴ� stream�鵵 ���� =  byteStream
		// InputStreamReader
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr); 
		// BufferedReader�� ����� ����ϴ°� ȿ�����̰� �Ϲ���
		
		// io�ÿ��� ���ܹ߻� ���ɼ��� ����
		try {
			String input = br.readLine();	//blocking method : �ϴ� �����Ͱ� ���� ������ ���
			System.out.println("�Է¹��� ������ : "+input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
