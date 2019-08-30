package javaNetwork;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Exam01_DateServer {

	public static void main(String[] args) {
		// 서버쪽 프로그램은 클라이언트의 소켓 접속을 기다려야 해
		// ServerSocket class를 이용해서 기능을 구현
		ServerSocket server = null;
		// 클라이언트와 접속된 후 Socket 객체가 있어야지 클라이언트와
		// 데이터 통신이 가능
		Socket socket = null;
		try {
			// port번호를 가지고 ServerSocket객체를 생성
			// port번호는 0~65535사용가능, 0~1023까지는 예약되어 있음
			server = new ServerSocket(5554);
			System.out.println("클라이언트 접속 대기");
			socket = server.accept(); 	// 클라이언트의 접속을 기다려요!(block)]
			// 만약 클라이언트가 접속해 오면 Socket객체를 하나 리턴함
			// socket.getOutputStream은 성능도 안좋고 쓰기 불편해서 PrintWriter로 upgrade => 문자열 출력 가능
			// 실제 socket으로 연결된 stream은 in, out 두개로 이루어진 셈
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
			out.println(format.format(new Date()));
			// 일반적으로 Reader과 Writer은 내부 buffer을 가지고 있음(PrintWriter)
			// 내부 버퍼가 꽉 차거나 close했을때 전달됨
			// out.flush 사용
			out.flush();  		//명시적으로 내부 buffer를 비우고 데이터를 전달명령
			out.close();
			socket.close();
			// 서버(프로그램)는 클라이언트의 접속을 기다리는 serversocket도 가지고 있음
			server.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}
