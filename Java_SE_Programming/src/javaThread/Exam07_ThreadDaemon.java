package javaThread;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam07_ThreadDaemon extends Application{

	TextArea textarea;
	Button startBtn, stopBtn;
	Thread counterThread;
	
	private void printMsg(String msg) {
		//textarea에 문자열 출력하는 method
		Platform.runLater(()->{
			textarea.appendText(msg+"\n");
		});
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//화면을 띄우는 thread는 JavaFX Application Thread
		//이 thread가 어느 시점에 start라는 method를 호출
		System.out.println(Thread.currentThread().getName());
		//JavaFX는 내부적으로 화면을 제어하는 Thread를 생성해서 사용해요
		//primaryStage는 실제로 화면에 띄우는 창을 지칭하는 reference
		//화면구성해서 window 띄우는 코드
		//화면기본 layout을 설정 => 화면을 동서남북중앙(5개의 영역)으로 분리
		//BorderPane은 layout 매니저
		BorderPane root = new BorderPane();
		//BorderPane 의 크기를 결정 => 화면에 띄우는window의 크기 설정
		root.setPrefSize(700, 500);
		
		//Component생성해서 
		textarea = new TextArea();
		root.setCenter(textarea);
		
		startBtn = new Button("Thread 클릭!!");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			//버튼에서 action이 발생(클릭)했을 때 호출
			//Thread생성(for loop를 1초 sleep하면서 10번 반복)
			//이 Thread는 dead상태로 가기위해서는 10초가 걸림// 중갅에 x눌러서 화면꺼도 프로그램은 살아있음
			Thread thread = new Thread(()->{
				try {
					for(int i=0; i<10; i++) {
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			});
			thread.setDaemon(true); //해당 Thread를 daemon thread로 설정
			//자식 thread가 된다고 생각하면 됨
			//부모 thread가 중지되면 자동적으로 자식 thread도 중지
			//x눌러서 화면끄면 부모 thread가 죽기 때문에 자동으로 자식 thread죽어서 프로그램이 종료
			//Thread의 상태를 지정해주는 것이기 때문에 start() method가 호출되기 전에 지정되어얗ㄴ다
			thread.start();
		});
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane에 버튼을 올려요~
		flowpane.getChildren().add(startBtn);
		root.setBottom(flowpane);
		
		//Scene객체가 필요해요
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread 예제입니다!");
		primaryStage.show();
		
	}
	
	
	public static void main(String[] args) {
		// main method를 호출하느 Thread는 main이란 이름의 thread
		System.out.println(Thread.currentThread().getName());
		launch(); //launch()호출되면 start호출됨ted method stub

	}

}
