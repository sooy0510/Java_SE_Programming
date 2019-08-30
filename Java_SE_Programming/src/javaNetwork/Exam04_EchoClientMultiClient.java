package javaNetwork;

/*
 * Ŭ���̾�Ʈ�� ������ �����Ҷ�����
 * echo�۾��� ���������� �����ϴ� ���α׷� 
 * 
 * ���� ���α׷��� ������ Ŭ���̾�Ʈ 1�� ���� ����
 * �ټ��� Ŭ���̾�Ʈ�� ���� �Ϸ��� ��� �ؾ� �Ұ�?
 * ==> Thread�� �̿��ؼ� �� ������ �ذ�
 */

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

public class Exam04_EchoClientMultiClient extends Application{

	ExecutorService executorService;
	TextArea textarea;
	TextField tf;
	Button startBtn;
	Button stopBtn;
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg + "\n");
		});
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {	
		//ȭ���� ���� thread�� JavaFX Application Thr1ead
		//�� thread�� ��� ������ start��� method�� ȣ��
		//System.out.println(Thread.currentThread().getName());
		//JavaFX�� ���������� ȭ���� �����ϴ� Thread�� �����ؼ� ����ؿ�
		//primaryStage�� ������ ȭ�鿡 ���� â�� ��Ī�ϴ� reference
		//ȭ�鱸���ؼ� window ���� �ڵ�
		//ȭ��⺻ layout�� ���� => ȭ���� ���������߾�(5���� ����)���� �и�
		//BorderPane�� layout �Ŵ���
		BorderPane root = new BorderPane();
		//BorderPane �� ũ�⸦ ���� => ȭ�鿡 ����window�� ũ�� ����
		root.setPrefSize(700, 500);
		
		//Component�����ؼ� 
		textarea = new TextArea();
		root.setCenter(textarea);
		
		executorService = Executors.newCachedThreadPool();
		
		startBtn = new Button("Echo ���� ����!!");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			//��ư���� action�� �߻�(Ŭ��)���� �� ȣ��
			//���ӹ�ư
			try {
				// Ŭ���̾�Ʈ�� ��ư�� ������ �����ʿ� Socket������ �õ�
				// Socket(url(������ ip�ּ�), ������ port��ȣ)
				// localhost = "127.0.0.1"
				socket = new Socket("70.12.115.61", 7777);
				// ���࿡ ���ӿ� �����ϸ� socket��ü�� �ϳ� ����
				InputStreamReader isr = 
						new InputStreamReader(socket.getInputStream());
				br = new BufferedReader(isr);
				out = new PrintWriter(socket.getOutputStream());
				printMsg("Echo ���� ���� ����!!");
//				String msg = br.readLine();
//				printMsg(msg);
//				br.close();
//				isr.close();
//				socket.close();
				Runnable runnable = () ->{
					while(true) {
						try {
							String result = br.readLine();
							printMsg(result);
							tf.setText("");
						} catch (IOException e) {
							e.printStackTrace();
						}	
					}
				};
				executorService.execute(runnable);
			} catch (Exception e) {
				System.out.println(e);
			}
		});	
		
		stopBtn = new Button("Echo ���� ����!!");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t->{
			//��ư���� action�� �߻�(Ŭ��)���� �� ȣ��
			//���ӹ�ư
			try {
				String msg = "end";
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
		
		tf = new TextField();
		tf.setPrefSize(200, 40);
		tf.setOnAction(t->{
			//�Է»���(TextField)���� enter key�� �ԷµǸ� ȣ��
			String msg = tf.getText();
			out.println(msg);
			out.flush();
			
		});
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane�� ��ư�� �÷���~
		flowpane.getChildren().add(startBtn);
		flowpane.getChildren().add(stopBtn);
		flowpane.getChildren().add(tf);
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
