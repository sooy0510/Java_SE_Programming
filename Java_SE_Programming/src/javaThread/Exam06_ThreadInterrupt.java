package javaThread;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam06_ThreadInterrupt extends Application{
	
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
		
		startBtn = new Button("Thread 시작!!");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			//버튼에서 action이 발생(클릭)했을 때 호출
			counterThread = new Thread(()->{
				try {
					for(int i=0; i<10; i++) {
						Thread.sleep(1000);
						printMsg(i+"-"+Thread.currentThread().getName());
					}
				} catch (Exception e) {
					//만약 interrupt()가 걸려있는 상태에서block상태로 진입하면
					//Exception을 내면서 catch문으로 이동
					
					printMsg("Thread가 종료되었습니다");
				}
			});
			counterThread.start();
			
		});
		
		stopBtn = new Button("Thread 중지!!");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t->{
			//버튼에서 action이 발생(클릭)했을 때 호출
			counterThread.interrupt(); //method가 실행된다고 바로 thread가 종료되는것은 아니다
										//interrupt() method가 호출된 Thread는 sleep()과 같이
										//block상태에 들어가야지 interrupt를 시킬 수 있음
			
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
		primaryStage.setTitle("Thread 예제입니다!");
		primaryStage.show();
		
	}
	
	
	public static void main(String[] args) {
		// main method를 호출하느 Thread는 main이란 이름의 thread
		System.out.println(Thread.currentThread().getName());
		launch(); //launch()호출되면 start호출됨ted method stub

	}

}
