package javaIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Exam03_ObjectStream {

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
//		map.put("1", "ȫ�浿");
//		map.put("2", "������");
//		map.put("3", "�Ż��Ӵ�");
//		map.put("4", "�ֱ浿");
		
		map.put("1", "�̼���");
		map.put("2", "������");
		map.put("3", "���̼�");
		map.put("4", "�̼�������");

		//�ϴ� ��ü�� ����� ���Ͽ� ���� File��ü���� ������!
		//�ش� ������ �����ϴ��� �׷��� �������� ��������
		File file = new File("asset/objectStream.txt"); //�����
		 
		//��ü�� ����� File�� outputStream���� �����
		try {
			FileOutputStream fos = new FileOutputStream(file);
			//��ü�� �������� stream���� �ٲ�
			ObjectOutputStream oos = new ObjectOutputStream(fos); 
			
			oos.writeObject(map);
			// 1. ���������� �ϴ� map��ü�� ������ �۾��� ���ؼ� ���¸� ��ȯ
			//	   ������ : ��ü�� ���ڿ��� ǥ���ϱ� ���ؼ��ϴ� ��ȭ�۾�
			//�����ϴ� �ڵ��̳� ����..close�� ����� ó���ؾ��Ѵ�
			//�׻� �������� stream�� �ݾƾ��Ѵ�
			oos.close();
			fos.close();
			
			// ��ü�� ����� ������ open�ؼ� �ش� ��ü�� ���α׷����� �о�鿩��
			// ���Ͽ��� �����͸� �б����� inputStream�� �ʿ�
			FileInputStream fis = new FileInputStream(file); 
			//�Ϲ� stream�̶� ��ü�� �Ӵٰ����Ҽ�����
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			Object obj = ois.readObject();
			//���ڿ��� ǥ���� ��ü�� �о�鿩�� ���� ��ü�� ����
			//�𸶼���
			HashMap<String, String> result = null;
			
			//���� obj�� Map��ü���� Ȯ��
			if(obj instanceof Map<?,?>) {
				result = (HashMap<String, String>)obj; //�������·� downcasting
			}
			System.out.println(result.get("3"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
