package javaNetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Echo program�� �ۼ��� ���� 
 */

public class Exam02_EchoServer {
	public static void main(String[] args) {
		ServerSocket server = null;
		Socket socket = null;
		
		try {
			server = new ServerSocket(5557);
			System.out.println("�������α׷� �⵿ - 5557");
			socket = server.accept();
			//stream����
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			PrintWriter out = 
					new PrintWriter(socket.getOutputStream());
			// br�κ��� �����͸� �о out�� ���� �ٽ� ����
			String msg = "";
			while(!((msg = br.readLine()).equals("end"))) {
				out.println(msg);
				out.flush();
			}
			// ���� ���ҽ��� ����
			out.close();
			br.close();
			socket.close();
			server.close();
			System.out.println("�������α׷� ����");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
