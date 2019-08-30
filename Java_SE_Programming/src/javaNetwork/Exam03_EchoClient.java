package javaNetwork;

import java.io.BufferedReader;

/*
 * Ŭ���̾�Ʈ�� ������ �����Ҷ�����
 * echo�۾��� ���������� �����ϴ� ���α׷� 
 * 
 * ���� ���α׷��� ������ Ŭ���̾�Ʈ 1�� ���� ����
 * �ټ��� Ŭ���̾�Ʈ�� ���� �Ϸ��� ��� �ؾ� �Ұ�?
 * ==> Thread�� �̿��ؼ� �� ������ �ذ�
 */

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam03_EchoClient extends Application {

	SharedObject_03 shared;
	ExecutorService executorService;
	TextArea textarea;
	TextField tf;
	Button startBtn;
	Button stopBtn;
	Socket socket;
	BufferedReader br;
	PrintWriter out;


	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		// BorderPane �� ũ�⸦ ���� => ȭ�鿡 ����window�� ũ�� ����
		root.setPrefSize(800, 500);

		// Component�����ؼ�
		textarea = new TextArea();
		root.setCenter(textarea);

		flowPane ch1 = new flowPane("1");
		flowPane ch2 = new flowPane("2");
		flowPane ch3 = new flowPane("3");
		flowPane ch4 = new flowPane("4");

		FlowPane sss = new FlowPane();
		sss.getChildren().add(ch1);
		sss.getChildren().add(ch2);
		sss.getChildren().add(ch3);
		sss.getChildren().add(ch4);

		root.setBottom(sss);

		// Scene��ü�� �ʿ��ؿ�
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread �����Դϴ�!");
		primaryStage.show();
		
		//Thread pool ����
		shared = new SharedObject_03();
		executorService = Executors.newFixedThreadPool(5); 

	}

	// Application�� �߻� Ŭ����
	public static void main(String[] args) {

		// ���� main method�� ȣ���� Thread�� �̸��� ���!
		// main method�� ȣ���ϴ� Thread�� main�̶� �̸��� thread
		//System.out.println(Thread.currentThread().getName());
		launch(); // launch()ȣ��Ǹ� startȣ���
	}

	
	
	
	class flowPane extends FlowPane {
		Socket socket;
		BufferedReader br;
		PrintWriter out;
		Button startBtn;
		Button stopBtn;
		TextField tf;
		TextArea ta;

		public flowPane(String num) {
			this.startBtn = new Button("Echo ���� ����!!");
			this.startBtn.setPrefSize(200, 50);
			startBtn.setOnAction(t -> {
				// ��ư���� action�� �߻�(Ŭ��)���� �� ȣ��
				// thread����
				makeThread(num);
				
				// ���ӹ�ư
				try {
					socket = new Socket("70.12.115.67", 7777);
					// ���࿡ ���ӿ� �����ϸ� socket��ü�� �ϳ� ����
					InputStreamReader isr = new InputStreamReader(socket.getInputStream());
					br = new BufferedReader(isr);
					out = new PrintWriter(socket.getOutputStream());
					// printMsg("Echo ���� ���� ����!!");

				} catch (Exception e) {
					System.out.println(e);
				}
			});

			this.stopBtn = new Button("Echo ���� ����!!");
			this.stopBtn.setPrefSize(200, 50);
			stopBtn.setOnAction(t -> {
				// ��ư���� action�� �߻�(Ŭ��)���� �� ȣ��
				// ���ӹ�ư
				try {
					String msg = "end";
					out.println(msg);
					out.flush();
					// �ڿ�����
					br.close();
					// isr.close();
					socket.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			});

			this.tf = new TextField();
			this.tf.setPrefSize(400, 40);
			tf.setOnAction(t -> {
				// �Է»���(TextField)���� enter key�� �ԷµǸ� ȣ��
				String msg = tf.getText();
				out.println(msg);
				out.flush();
				try {
					String result = br.readLine();
					shared.printMsg(result);
					tf.setText("");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			this.ta = new TextArea();
			this.ta.setPrefSize(400, 10);
			this.ta.setText(num);

			this.getChildren().add(ta);
			this.getChildren().add(startBtn);
			this.getChildren().add(stopBtn);
			this.getChildren().add(tf);
		}
		
		private void makeThread(String num) {
			final String k = num;
			Runnable runnable = () ->{
				//���ٳ����� �������� ����
				Thread.currentThread().setName("MYThread-"+ k);
				String msg = Thread.currentThread().getName()+
						" Pool�� ���� : "+((ThreadPoolExecutor)executorService).getPoolSize();
				shared.printMsg(msg);
				System.out.println(Thread.currentThread().getName()+"����");
				System.out.println(msg);
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
			executorService.execute(runnable); //���ڷ� runnable��ü �־��� , ���������� thread����� ����
												//executorService�� �� ������ ����
		}

	}
	
	class MyRunnable implements Runnable{
		SharedObject_03 shared;
		String input;
		
		public MyRunnable(SharedObject_03 shared, String input) {
			this.shared = shared;
			this.input = input;
		}

		@Override
		public void run() {
			shared.printMsg(input);
		}
	}
	
	// ���밴ü  class
	class SharedObject_03{
		private TextArea ta; //���밴ü�� ������ field

		// getter & setter (Thread�� ���� ���)
		
		
		//textarea�� ����ȭ������ ó��
		private void printMsg(String msg) {
			synchronized (this) {
				Platform.runLater(() -> {
					textarea.appendText(msg + "\n");
				});
			}
		}
	}
}

