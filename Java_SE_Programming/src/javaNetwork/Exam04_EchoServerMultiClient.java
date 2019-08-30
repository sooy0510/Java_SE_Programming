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

class Exam04_ShardObject{
	private List<EchoRunnable> clients = new ArrayList<EchoRunnable>();
			
	public void addClient(EchoRunnable client) {
		clients.add(client);
		System.out.println("add����");
	}

	public void broadCast(String msg) {
		System.out.println("broadcast���Դϴ�");
		for(EchoRunnable client : clients) {
			client.out(msg);
		}
	}
}

class EchoRunnable implements Runnable{
	// ������ �־�� �ϴ� field
	Exam04_ShardObject shared;
	Socket socket; //Ŭ���̾�Ʈ�� ����� ����
	BufferedReader br; //�Է��� ���� ��Ʈ��
	PrintWriter out;
	
	
	
	public EchoRunnable(Socket socket, Exam04_ShardObject shared) {
		super();
		this.socket = socket;
		this.shared = shared;
		try {
			this.br = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			this.out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void out(String str) {
		out.println(str);
		out.flush();
	}


	@Override
	public void run() {
		//Ŭ���̾�Ʈ�� echoó�� ����
		//Ŭ���̾�Ʈ�� ���ڿ��� ������ �ش� ���ڿ��� �޾� �ٽ� Ŭ���̾�Ʈ���� ����
		//�ѹ��ϰ� �����ϴ°� �ƴ϶� Ŭ���̾�Ʈ�� "/EXIT"��� ���ڿ��� ���������� ����
		String line = "";
		try {
			while((line = br.readLine()) != null) {
				if(line.equals("/EXIT/")) {
					break;	//���� ������ loop�� Ż��
				}else {
					//out.println(line);
					//out.flush();
					shared.broadCast(line);
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

public class Exam04_EchoServerMultiClient extends Application{

	Exam04_ShardObject shared;
	TextArea textarea;
	Button startBtn;
	Button stopBtn;
	// ThreadPool�� ����
	ExecutorService executorService = 
			Executors.newCachedThreadPool();
	ServerSocket server;
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg + "\n");
		});
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {	
		BorderPane root = new BorderPane();
		//BorderPane �� ũ�⸦ ���� => ȭ�鿡 ����window�� ũ�� ����
		root.setPrefSize(700, 500);
		
		//Component�����ؼ� 
		textarea = new TextArea();
		root.setCenter(textarea);
		
		startBtn = new Button("Echo ���� ����!!");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			shared = new Exam04_ShardObject();
			//��ư���� action�� �߻�(Ŭ��)���� �� ȣ��
			//�������α׷��� ����
			//Ŭ���̾�Ʈ�� ������ ��ٷ��� -> ������ �Ǹ� Thread�� �ϳ� ����
			// -> Thread�� �����ؼ� Ŭ���̾�Ʈ�� Thread�� ����ϵ��� ������
			//������ �ٽ� �ٸ� Ŭ���̾�Ʈ�� ������ ��ٷ���
			Runnable runnable = ()->{
				try {
					server = new ServerSocket(6652);
					printMsg("Echo ���� ����!!");
					while(true) {
						printMsg("Ŭ���̾�Ʈ ���� ���"); //blocking
						// Ŭ���̾�Ʈ�� ������ ������ blocking
						Socket s = server.accept();
						printMsg("Ŭ���̾�Ʈ ���� ����");
						//Ŭ���̾�Ʈ�� ���������� Thread����� �����ؾ��ؿ�
						EchoRunnable r = new EchoRunnable(s, shared);
						shared.addClient(r);
						executorService.execute(r); //thread����
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
			executorService.execute(runnable);
		});	
		
		stopBtn = new Button("Echo ���� ����!!");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t->{
			//��ư���� action�� �߻�(Ŭ��)���� �� ȣ��
			//���ӹ�ư
//			try {
//				String msg = "end";
//				out.println(msg);
//				out.flush();
//				//�ڿ�����
//				br.close();
//				//isr.close();
//				socket.close();
//			} catch (Exception e) {
//				System.out.println(e);
//			}
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
		primaryStage.setTitle("���� Ŭ���̾�Ʈ Echo Server!");
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
