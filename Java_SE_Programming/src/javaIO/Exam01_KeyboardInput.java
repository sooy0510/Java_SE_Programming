package javaIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Exam01_KeyboardInput {

	public static void main(String[] args) {
		System.out.println("키보드로 한줄을 입력해요"); //System.out(객체) stream을 통해 
								//표준출력 마지막단계인 monitor로 출력
		
		// 표준입력(keyboard)로부터 입력을 받기위해 keyboard와 
		// 연결된 stream객체가 필요
		// Java는 표준입력에 대한 Stream을 기본적으로 제공
		// System.in (객체의 형태로 제공)(inputStream) => byteStream형태
		// Stream은 이렇게 inputStream과 outputStream으로 구분
		// byteStream과 reader(기본데이터형), writer계열 stream(문자열)으로 구분  
		// 문자의 형태가 아닌 stream들도 존재 =  byteStream
		// InputStreamReader
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr); 
		// BufferedReader로 만들어 사용하는게 효율적이고 일반적
		
		// io시에는 예외발생 가능성이 높다
		try {
			String input = br.readLine();	//blocking method : 일단 데이터가 들어올 때까지 대기
			System.out.println("입력받은 데이터 : "+input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
