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

// 코드 확장
// 방이 여러개 있는 경우(대기실)
// 방을 골라서 들어가요
// 방안에서만 통신되는 multicast
// "외치기" => 모든 사용자에게 broadcast
// "귓말" => 단일 사용자에게 unicast
// "특정 사용자 강퇴"
// "특정 사용자 초대"
public class Exam05_ChatClient extends Application{

	TextArea textarea;
	Button connBtn,disConnBtn;	//서버접속, 접속끊기 버튼		
	TextField idTf, msgTf;		//아이디 입력칸, 메시지 입력칸
	
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	// 클라이언트쪽 Thread는 1개만 만들어져요. ThreadPool을 사용할 경우
	// overhead 발생
	ExecutorService executorService = Executors.newCachedThreadPool();
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg + "\n");
		});
	}
	
	// 서버로부터 들어오는 메시지를 계속 받아서 화면에 출력하기 위한 용도의
	// Thread
	class ReceiveRunnable implements Runnable{
		// 서버로부터 들어오는 메시지를 받아들이는 역할을 수행
		// 소켓에 대한 입력스트림만 있으면 된다
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
		//BorderPane 의 크기를 결정 => 화면에 띄우는window의 크기 설정
		root.setPrefSize(700, 500);
		
		//Component생성해서 
		textarea = new TextArea();
		root.setCenter(textarea);
		
		executorService = Executors.newCachedThreadPool();
		
		connBtn = new Button("채팅 서버 접속");
		connBtn.setPrefSize(250, 50);
		connBtn.setOnAction(t->{
			try {
				// 클라이언트는 버튼을 누르면 서버쪽에 Socket접속을 시도
				// Socket(url(서버쪽 ip주소), 접속할 port번호)
				// localhost = "127.0.0.1"
				socket = new Socket("70.12.115.67", 6789);
				// 만약에 접속에 성공하면 socket객체를 하나 얻어옴
				InputStreamReader isr = 
						new InputStreamReader(socket.getInputStream(),"EUC_KR");
				br = new BufferedReader(isr);
				out = new PrintWriter(socket.getOutputStream());
				printMsg("채팅 서버 접속 성공!!");
				
				// 접속을 성공했으니 이제 Thread를 만들어서 서버가 보내준
				// 데이터를 받을 준비를 해요!
				ReceiveRunnable runnable = 
						new ReceiveRunnable(br);
				executorService.execute(runnable);
			} catch (Exception e) {
				System.out.println(e);
			}
		});	
		
		disConnBtn = new Button("접속 종료");
		disConnBtn.setPrefSize(250, 50);
		disConnBtn.setOnAction(t->{
			//버튼에서 action이 발생(클릭)했을 때 호출
			//접속버튼
			try {
				// 내가 정한(protocol) 서버 접속 종료를 위한 문자열을 보내요
				String msg = "/EXIT/";
				out.println(msg);
				out.flush();
				//자원해제
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
			//입력상자(TextField)에서 enter key가 입력되면 호출
			//ex) 홍길동 > 안녕하세요
			String msg = idTf.getText() + ">" + msgTf.getText();
			out.println(msg);
			out.flush();
			msgTf.setText("");
		});
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane에 버튼을 올려요~
		flowpane.getChildren().add(connBtn);
		flowpane.getChildren().add(disConnBtn);
		flowpane.getChildren().add(idTf);
		flowpane.getChildren().add(msgTf);
		root.setBottom(flowpane);
		
		//Scene객체가 필요해요
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread 예제입니다!");
		primaryStage.show();
		
			}
	
	//Application은 추상 클래스
	public static void main(String[] args) {

		// 현재 main method를 호출한 Thread의 이름을 출력!
		// main method를 호출하느 Thread는 main이란 이름의 thread
		System.out.println(Thread.currentThread().getName());
		launch(); //launch()호출되면 start호출됨
	}

}
