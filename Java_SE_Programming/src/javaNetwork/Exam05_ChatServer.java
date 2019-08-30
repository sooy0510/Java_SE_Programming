package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam05_ChatServer extends Application{

	TextArea textarea;
	Button startBtn;
	Button stopBtn;
	ServerSocket server; //Ŭ���̾�Ʈ�� ������ �޾Ƶ��̴� ��
	ExecutorService executorService	
		= Executors.newCachedThreadPool(); //ThreadPool
	// singleton������ ������ü�� ����
	SharedObject sharedObject = new SharedObject();
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg + "\n");
		});
	}
	
	// Ŭ���̾�Ʈ�� ����� Thread�� ����ϴ� ������ü�� ����� ���� Ŭ������ ����
	// �ܺ� Ŭ������ ���� ���� -> ������ ����
	// Ŭ���������� ����ҰŸ� inner class�� ����
	// Thread�� ������ü�� Thread�� ������ �־�� �ϴ� �ڷᱸ��,
	// ����� ������ ���� ��ü�� ��Ī!
	class SharedObject{
		// Ŭ���̾�Ʈ Thread�� �����ϰ� �־�� �ؿ�! => ������ �������
		List<ClientRunnable> clients = 
				new ArrayList<ClientRunnable>();
		
		
		// �츮�� �ʿ��� �����..Broadcast.
		// Thread�� Ŭ���̾�Ʈ�κ��� �����͸� �޾Ƽ� 
		// ��� Ŭ���̾�Ʈ Thread���� �����͸� �����ϴ� ����� ����
		// ������ü�� method�� ���� Thread�� ���ؼ� ���ÿ� ���� �� ����
		// �̷���쿡�� ����ȭ ó���� �� ����� �������� ��µ� �� ����
		public void broadcast(String msg) {
			clients.stream().forEach(t ->{ //arraylist�� ���� stream�� ����
				t.out.println(msg);
				t.out.flush();
			});
		}
	}
	
	// Ŭ���̾�Ʈ�� ���εǴ� Thread�� ��������� Runnable class
	// �ᱹ client��� ���� ��
	class ClientRunnable implements Runnable{

		private SharedObject sharedObject; //������ü
		private Socket socket;	//Ŭ���̾�Ʈ�� ����� socket
		private BufferedReader br;
		private PrintWriter out;
		
		// Ŭ���̾�Ʈ�� ������ �����ؼ� �ش� Ŭ���̾�Ʈ�� ���� Thread�� 
		// ������ �� Thread���� 2���� ��ü�� ���޵Ǿ�� ��
		// �����ڸ� 2���� ����(������ü�� ����)�� �޴� ���·� �ۼ�
		// �Ϲ������� �����ڴ� �ʵ��ʱ�ȭ�� ����ϱ� ������ ���⿡�� Stream��
		// ����
		public ClientRunnable(SharedObject sharedObject, Socket socket) {
			super();
			this.sharedObject = sharedObject;
			this.socket = socket;
			try {
				this.br = new BufferedReader(
						new InputStreamReader(
								socket.getInputStream()));
				
				this.out = new PrintWriter(socket.getOutputStream());
			}catch (Exception e) {
				System.out.println(e);
			}
		} 
		
		// Ŭ���̾�Ʈ�� Thread�� ���۵Ǹ� run method()�� ȣ��Ǽ�
		// Ŭ���̾�Ʈ�� ������ ����� ����
		// �ݺ������� Ŭ���̾�Ʈ�� �����͸� �޾Ƽ� ������ü�� �̿��ؼ� broadcasting
		
		@Override
		public void run() {
			String msg = "";
			try {
				while((msg = br.readLine()) != null) {
					// Ŭ���̾�Ʈ�� ä���� �����ϸ�
					if(msg.equals("/EXIT/")) {
						break;
					}
					// �Ϲ����� ä�� �޽����ΰ�� ��� Ŭ���̾�Ʈ���� ����
					sharedObject.broadcast(msg);
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
		
		startBtn = new Button("ä�� ���� ����");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			textarea.clear();
			printMsg("[ä�ü����⵿ - 6789]");
			// ���������� ���� Ŭ���̾�Ʈ ������ ��ٷ����ؿ�
			// JavaFX thread�� blocking���� �ʵ��� ���ο� Thread��
			// ���� Ŭ���̾�Ʈ ������ ��ٷ����ؿ�
			Runnable runnable = new Runnable() {
				public void run() {
					try {
						server = new ServerSocket(6789);
						while(true) {
							printMsg("[Ŭ���̾�Ʈ ���� �����]");
							Socket socket = server.accept();
							printMsg("[Ŭ���̾�Ʈ ���� ����!]");
							// Ŭ���̾�Ʈ�� ���������� Thread�� �ϳ�
							// �����ϰ� �����ؾ��ؿ�
							ClientRunnable cRunnable =
									new ClientRunnable(sharedObject, socket);
							// ���ο� Ŭ���̾�Ʈ�� ���ӵǾ�����
							// ���밴ü�� List�ȿ� ����
							// Thread�� ���� ���밴ü�� �����Ͱ� ���ɶ�
							// ����ȭ ó���� �� ��� ����
							sharedObject.clients.add(cRunnable);
							printMsg("[���� Ŭ���̾�Ʈ �� : ]"+sharedObject.clients.size());
							executorService.execute(cRunnable);
						}
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			};
			executorService.execute(runnable);
		});	
		
		stopBtn = new Button("ä�� ���� ����");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t->{
		});	
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane�� ��ư�� �÷���~
		flowpane.getChildren().add(startBtn);
		flowpane.getChildren().add(stopBtn);
		root.setBottom(flowpane);
		
		//Scene��ü�� �ʿ��ؿ�
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("�� 1��¥�� ä���Դϴ�!");
		primaryStage.show();
		
			}
	
	//Application�� �߻� Ŭ����
	public static void main(String[] args) {
		
		launch(); //launch()ȣ��Ǹ� startȣ���
	}

}

