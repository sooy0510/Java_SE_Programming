package javaNetwork;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Exam01_DateServer {

	public static void main(String[] args) {
		// ������ ���α׷��� Ŭ���̾�Ʈ�� ���� ������ ��ٷ��� ��
		// ServerSocket class�� �̿��ؼ� ����� ����
		ServerSocket server = null;
		// Ŭ���̾�Ʈ�� ���ӵ� �� Socket ��ü�� �־���� Ŭ���̾�Ʈ��
		// ������ ����� ����
		Socket socket = null;
		try {
			// port��ȣ�� ������ ServerSocket��ü�� ����
			// port��ȣ�� 0~65535��밡��, 0~1023������ ����Ǿ� ����
			server = new ServerSocket(5554);
			System.out.println("Ŭ���̾�Ʈ ���� ���");
			socket = server.accept(); 	// Ŭ���̾�Ʈ�� ������ ��ٷ���!(block)]
			// ���� Ŭ���̾�Ʈ�� ������ ���� Socket��ü�� �ϳ� ������
			// socket.getOutputStream�� ���ɵ� ������ ���� �����ؼ� PrintWriter�� upgrade => ���ڿ� ��� ����
			// ���� socket���� ����� stream�� in, out �ΰ��� �̷���� ��
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
			out.println(format.format(new Date()));
			// �Ϲ������� Reader�� Writer�� ���� buffer�� ������ ����(PrintWriter)
			// ���� ���۰� �� ���ų� close������ ���޵�
			// out.flush ���
			out.flush();  		//��������� ���� buffer�� ���� �����͸� ���޸��
			out.close();
			socket.close();
			// ����(���α׷�)�� Ŭ���̾�Ʈ�� ������ ��ٸ��� serversocket�� ������ ����
			server.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}
