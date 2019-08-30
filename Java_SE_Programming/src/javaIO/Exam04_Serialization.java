package javaIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

// Serializable�� �����ؾ� ����ȭ�� ����������
// ���� �����͵鵵 ��� ����ȭ ������ Ÿ���̾����
class MyClass implements Serializable{
	// ����ȭ�� ������ȭ�� �Ҷ� ���� Ÿ�������� ���ϱ� ���ؼ� ���������� ����ϴ� static field
	// ��� ������ ������ �ִ°� ����

	private static final long serialVersionUID = 1L;
	String name;	//����ȭ�� ������ ���¿��� �Ѵ�
					//String class�� �̹� Serializable�� ������ ���±� ������ ����ȭ ����
	int kor;		//����ȭ�� ����
	
	//transient keyword : ����ȭ ��󿡼� �����ϴ� keyword
	transient Socket socket;	//����ȭ�� �ȵ� => MyClass�� ����ȭ �Ұ���
								
				
	public MyClass(String name, int kor) {
		super();
		this.name = name;
		this.kor = kor;
	}
	
	
}

public class Exam04_Serialization {

	public static void main(String[] args) {
		// ObjectOutStream�� �̿��ؼ� File�� ���� ���� class��
		// instance�� ����
		// 1. ������ ��ü ����
		MyClass obj = new MyClass("ȫ�浿", 70);
		
		// 2. ��ü�� ������ ���ϰ�ü�� ����
		File file = new File("asset/student.txt");
		
		try {
			// 3. ���� ��ü�� �̿��ؼ� OjbectOutputStream�� ����
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			// 4. ObjectOutputStream�� �̿��ؼ� ��ü�� ���Ͽ� ����
			// 		����� ��ü�� �ݵ�� ����ȭ�� ������ ��ü�̾���Ѵ�
			// 		�츮�� ������ ��ü�� ����ȭ�� ������ ��ü�� �ƴϴ�
			// 		��ü����ȭ�� �����Ϸ��� ��� �ؾ��Ұ�?
			//		=> Serializable interface�� �����ϸ� ��
			// 		=> class�� field�� ����ȭ�� ������ ���¿�����
			oos.writeObject(obj);
			// 5. ������ ������ Stream�� close
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
