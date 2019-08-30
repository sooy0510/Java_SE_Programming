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
//		map.put("1", "홍길동");
//		map.put("2", "강감찬");
//		map.put("3", "신사임당");
//		map.put("4", "최길동");
		
		map.put("1", "이수연");
		map.put("2", "수연이");
		map.put("3", "연이수");
		map.put("4", "이수연수이");

		//일단 객체가 저장될 파일에 대한 File객체부터 만들어요!
		//해당 파일이 존재하는지 그렇지 않은지는 상관없어요
		File file = new File("asset/objectStream.txt"); //상대경로
		 
		//객체가 저장될 File에 outputStream부터 열어요
		try {
			FileOutputStream fos = new FileOutputStream(file);
			//객체를 내보내는 stream으로 바꿈
			ObjectOutputStream oos = new ObjectOutputStream(fos); 
			
			oos.writeObject(map);
			// 1. 내보내려고 하는 map객체를 마샬링 작업을 통해서 형태를 변환
			//	   마샬링 : 객체를 문자열로 표현하기 위해서하는 변화작업
			//저장하는 코드이나 보니..close를 제대로 처리해야한다
			//항상 역순으로 stream을 닫아야한다
			oos.close();
			fos.close();
			
			// 객체가 저장된 파일을 open해서 해당 객체를 프로그램으로 읽어들여요
			// 파일에서 데이터를 읽기위해 inputStream이 필요
			FileInputStream fis = new FileInputStream(file); 
			//일반 stream이라서 객체가 왓다갓다할수없음
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			Object obj = ois.readObject();
			//문자열로 표현된 객체를 읽어들여서 원래 객체를 복원
			//언마샬링
			HashMap<String, String> result = null;
			
			//들어온 obj가 Map객체인지 확인
			if(obj instanceof Map<?,?>) {
				result = (HashMap<String, String>)obj; //원래상태로 downcasting
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
