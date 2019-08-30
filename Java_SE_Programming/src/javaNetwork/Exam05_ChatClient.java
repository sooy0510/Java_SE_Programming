package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

// �ڵ� Ȯ��
// ���� ������ �ִ� ���(����)
// ���� ��� ����
// ��ȿ����� ��ŵǴ� multicast
// "��ġ��" => ��� ����ڿ��� broadcast
// "�Ӹ�" => ���� ����ڿ��� unicast
// "Ư�� ����� ����"
// "Ư�� ����� �ʴ�"
public class Exam05_ChatClient extends Application{

	TextArea textarea;
	Button connBtn,disConnBtn;	//��������, ���Ӳ��� ��ư		
	TextField idTf, msgTf;		//���̵� �Է�ĭ, �޽��� �Է�ĭ
	
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	// Ŭ���̾�Ʈ�� Thread�� 1���� ���������. ThreadPool�� ����� ���
	// overhead �߻�
	ExecutorService executorService = Executors.newCachedThreadPool();
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg + "\n");
		});
	}
	
	// �����κ��� ������ �޽����� ��� �޾Ƽ� ȭ�鿡 ����ϱ� ���� �뵵��
	// Thread
	class ReceiveRunnable implements Runnable{
		// �����κ��� ������ �޽����� �޾Ƶ��̴� ������ ����
		// ���Ͽ� ���� �Է½�Ʈ���� ������ �ȴ�
		private BufferedReader br;
		
		public ReceiveRunnable(BufferedReader br) {
			super();
			this.br = br;
		}

		@Override
		public void run() {
			String line = "";
			try {
				while((line = br.readLine()) != null) {
					printMsg(line);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {	
		BorderPane root = new BorderPane();
		//BorderPane �� ũ�⸦ ���� => ȭ�鿡 ����window�� ũ�� ����
		root.setPrefSize(700, 500);
		
		//Component�����ؼ� 
		textarea = new TextArea();
		root.setCenter(textarea);
		
		executorService = Executors.newCachedThreadPool();
		
		connBtn = new Button("ä�� ���� ����");
		connBtn.setPrefSize(250, 50);
		connBtn.setOnAction(t->{
			try {
				// Ŭ���̾�Ʈ�� ��ư�� ������ �����ʿ� Socket������ �õ�
				// Socket(url(������ ip�ּ�), ������ port��ȣ)
				// localhost = "127.0.0.1"
				socket = new Socket("70.12.115.67", 6789);
				// ���࿡ ���ӿ� �����ϸ� socket��ü�� �ϳ� ����
				InputStreamReader isr = 
						new InputStreamReader(socket.getInputStream(),"EUC_KR");
				br = new BufferedReader(isr);
				out = new PrintWriter(socket.getOutputStream());
				printMsg("ä�� ���� ���� ����!!");
				
				// ������ ���������� ���� Thread�� ���� ������ ������
				// �����͸� ���� �غ� �ؿ�!
				ReceiveRunnable runnable = 
						new ReceiveRunnable(br);
				executorService.execute(runnable);
			} catch (Exception e) {
				System.out.println(e);
			}
		});	
		
		disConnBtn = new Button("���� ����");
		disConnBtn.setPrefSize(250, 50);
		disConnBtn.setOnAction(t->{
			//��ư���� action�� �߻�(Ŭ��)���� �� ȣ��
			//���ӹ�ư
			try {
				// ���� ����(protocol) ���� ���� ���Ḧ ���� ���ڿ��� ������
				String msg = "/EXIT/";
				out.println(msg);
				out.flush();
				//�ڿ�����
				br.close();
				//isr.close();
				socket.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		});	
		
		idTf = new TextField();
		idTf.setPrefSize(100, 40);
		msgTf = new TextField();
		msgTf.setPrefSize(200, 40);
		msgTf.setOnAction(t->{
			//�Է»���(TextField)���� enter key�� �ԷµǸ� ȣ��
			//ex) ȫ�浿 > �ȳ��ϼ���
			String msg = idTf.getText() + ">" + msgTf.getText();
			out.println(msg);
			out.flush();
			msgTf.setText("");
		});
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane�� ��ư�� �÷���~
		flowpane.getChildren().add(connBtn);
		flowpane.getChildren().add(disConnBtn);
		flowpane.getChildren().add(idTf);
		flowpane.getChildren().add(msgTf);
		root.setBottom(flowpane);
		
		//Scene��ü�� �ʿ��ؿ�
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread �����Դϴ�!");
		primaryStage.show();
		
			}
	
	//Application�� �߻� Ŭ����
	public static void main(String[] args) {

		// ���� main method�� ȣ���� Thread�� �̸��� ���!
		// main method�� ȣ���ϴ� Thread�� main�̶� �̸��� thread
		System.out.println(Thread.currentThread().getName());
		launch(); //launch()ȣ��Ǹ� startȣ���
	}

}
