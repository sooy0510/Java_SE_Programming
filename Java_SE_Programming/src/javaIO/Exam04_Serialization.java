package javaIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

// Serializable을 구현해야 직렬화가 가능해진다
// 내부 데이터들도 모두 직렬화 가능한 타입이어야함
class MyClass implements Serializable{
	// 직렬화와 역직렬화를 할때 같은 타입인지를 비교하기 위해서 내부적으로 사용하는 static field
	// 없어도 문제는 없지만 있는게 낫다

	private static final long serialVersionUID = 1L;
	String name;	//직렬화가 가능한 형태여야 한다
					//String class는 이미 Serializable을 구현한 상태기 때문에 직렬화 가능
	int kor;		//직렬화가 가능
	
	//transient keyword : 직렬화 대상에서 제외하는 keyword
	transient Socket socket;	//직렬화가 안됨 => MyClass는 직렬화 불가능
								
				
	public MyClass(String name, int kor) {
		super();
		this.name = name;
		this.kor = kor;
	}
	
	
}

public class Exam04_Serialization {

	public static void main(String[] args) {
		// ObjectOutStream을 이용해서 File에 내가 만든 class의
		// instance를 저장
		// 1. 저장할 객체 생성
		MyClass obj = new MyClass("홍길동", 70);
		
		// 2. 객체를 저장할 파일객체를 생성
		File file = new File("asset/student.txt");
		
		try {
			// 3. 파일 객체를 이용해서 OjbectOutputStream을 생성
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			// 4. ObjectOutputStream을 이용해서 객체를 파일에 저장
			// 		저장될 객체는 반드시 직렬화가 가능한 객체이어야한다
			// 		우리가 생성한 객체는 직렬화가 가능한 객체가 아니다
			// 		객체직렬화가 가능하려면 어떻게 해야할가?
			//		=> Serializable interface를 구현하면 됨
			// 		=> class의 field가 직렬화가 가능한 형태여야함
			oos.writeObject(obj);
			// 5. 저장이 끝나면 Stream을 close
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
