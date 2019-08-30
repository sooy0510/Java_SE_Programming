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
		System.out.println("add성공");
	}

	public void broadCast(String msg) {
		System.out.println("broadcast안입니다");
		for(EchoRunnable client : clients) {
			client.out(msg);
		}
	}
}

class EchoRunnable implements Runnable{
	// 가지고 있어야 하는 field
	Exam04_ShardObject shared;
	Socket socket; //클라이언트와 연결된 소켓
	BufferedReader br; //입력을 위한 스트림
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
		//클라이언트와 echo처리 구현
		//클라이언트가 문자열을 보내면 해당 문자열을 받아 다시 클라이언트에게 전달
		//한번하고 종료하는게 아니라 클라이언트가 "/EXIT"라는 문자열을 보낼때까지 지속
		String line = "";
		try {
			while((line = br.readLine()) != null) {
				if(line.equals("/EXIT/")) {
					break;	//가장 근접한 loop를 탈출
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
	// ThreadPool을 생성
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
		//BorderPane 의 크기를 결정 => 화면에 띄우는window의 크기 설정
		root.setPrefSize(700, 500);
		
		//Component생성해서 
		textarea = new TextArea();
		root.setCenter(textarea);
		
		startBtn = new Button("Echo 서버 접속!!");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			shared = new Exam04_ShardObject();
			//버튼에서 action이 발생(클릭)했을 때 호출
			//서버프로그램을 시작
			//클라이언트의 접속을 기다려요 -> 접속이 되면 Thread를 하나 생성
			// -> Thread를 시작해서 클라이언트와 Thread가 통신하도록 만들어요
			//서버는 다시 다른 클라이언트의 접속을 기다려요
			Runnable runnable = ()->{
				try {
					server = new ServerSocket(6652);
					printMsg("Echo 서버 가동!!");
					while(true) {
						printMsg("클라이언트 접속 대기"); //blocking
						// 클라이언트가 들어오기 전까지 blocking
						Socket s = server.accept();
						printMsg("클라이언트 접속 성공");
						//클라이언트가 접속했으니 Thread만들고 시작해야해요
						EchoRunnable r = new EchoRunnable(s, shared);
						shared.addClient(r);
						executorService.execute(r); //thread실행
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
			executorService.execute(runnable);
		});	
		
		stopBtn = new Button("Echo 서버 종료!!");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t->{
			//버튼에서 action이 발생(클릭)했을 때 호출
			//접속버튼
//			try {
//				String msg = "end";
//				out.println(msg);
//				out.flush();
//				//자원해제
//				br.close();
//				//isr.close();
//				socket.close();
//			} catch (Exception e) {
//				System.out.println(e);
//			}
		});	
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane에 버튼을 올려요~
		flowpane.getChildren().add(startBtn);
		flowpane.getChildren().add(stopBtn);
		root.setBottom(flowpane);
		
		//Scene객체가 필요해요
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("다중 클라이언트 Echo Server!");
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
