package javaNetwork;

import java.io.BufferedReader;

/*
 * 클라이언트가 접속을 종료할때까지
 * echo작업이 지속적으로 동작하는 프로그램 
 * 
 * 현재 프로그램은 서버가 클라이언트 1명만 서비스 가능
 * 다수의 클라이언트를 서비스 하려면 어떻게 해야 할가?
 * ==> Thread를 이용해서 이 문제를 해결
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
		// BorderPane 의 크기를 결정 => 화면에 띄우는window의 크기 설정
		root.setPrefSize(800, 500);

		// Component생성해서
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

		// Scene객체가 필요해요
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread 예제입니다!");
		primaryStage.show();
		
		//Thread pool 생성
		shared = new SharedObject_03();
		executorService = Executors.newFixedThreadPool(5); 

	}

	// Application은 추상 클래스
	public static void main(String[] args) {

		// 현재 main method를 호출한 Thread의 이름을 출력!
		// main method를 호출하느 Thread는 main이란 이름의 thread
		//System.out.println(Thread.currentThread().getName());
		launch(); // launch()호출되면 start호출됨
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
			this.startBtn = new Button("Echo 서버 접속!!");
			this.startBtn.setPrefSize(200, 50);
			startBtn.setOnAction(t -> {
				// 버튼에서 action이 발생(클릭)했을 때 호출
				// thread생성
				makeThread(num);
				
				// 접속버튼
				try {
					socket = new Socket("70.12.115.67", 7777);
					// 만약에 접속에 성공하면 socket객체를 하나 얻어옴
					InputStreamReader isr = new InputStreamReader(socket.getInputStream());
					br = new BufferedReader(isr);
					out = new PrintWriter(socket.getOutputStream());
					// printMsg("Echo 서버 접속 성공!!");

				} catch (Exception e) {
					System.out.println(e);
				}
			});

			this.stopBtn = new Button("Echo 서버 종료!!");
			this.stopBtn.setPrefSize(200, 50);
			stopBtn.setOnAction(t -> {
				// 버튼에서 action이 발생(클릭)했을 때 호출
				// 접속버튼
				try {
					String msg = "end";
					out.println(msg);
					out.flush();
					// 자원해제
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
				// 입력상자(TextField)에서 enter key가 입력되면 호출
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
				//람다내에서 지역변수 못씀
				Thread.currentThread().setName("MYThread-"+ k);
				String msg = Thread.currentThread().getName()+
						" Pool의 개수 : "+((ThreadPoolExecutor)executorService).getPoolSize();
				shared.printMsg(msg);
				System.out.println(Thread.currentThread().getName()+"입장");
				System.out.println(msg);
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
			executorService.execute(runnable); //인자로 runnable객체 넣어줌 , 내부적으로 thread만들고 실행
												//executorService가 이 모든것을 관리
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
	
	// 공용객체  class
	class SharedObject_03{
		private TextArea ta; //공용객체가 가지는 field

		// getter & setter (Thread에 의해 사용)
		
		
		//textarea는 동기화블럭으로 처리
		private void printMsg(String msg) {
			synchronized (this) {
				Platform.runLater(() -> {
					textarea.appendText(msg + "\n");
				});
			}
		}
	}
}

