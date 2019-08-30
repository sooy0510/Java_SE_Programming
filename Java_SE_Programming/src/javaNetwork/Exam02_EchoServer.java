package javaNetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Echo program을 작성해 보자 
 */

public class Exam02_EchoServer {
	public static void main(String[] args) {
		ServerSocket server = null;
		Socket socket = null;
		
		try {
			server = new ServerSocket(5557);
			System.out.println("서버프로그램 기동 - 5557");
			socket = server.accept();
			//stream생성
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			PrintWriter out = 
					new PrintWriter(socket.getOutputStream());
			// br로부터 데이터를 읽어서 out을 통해 다시 전달
			String msg = "";
			while(!((msg = br.readLine()).equals("end"))) {
				out.println(msg);
				out.flush();
			}
			// 사용된 리소스들 해제
			out.close();
			br.close();
			socket.close();
			server.close();
			System.out.println("서버프로그램 종료");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
