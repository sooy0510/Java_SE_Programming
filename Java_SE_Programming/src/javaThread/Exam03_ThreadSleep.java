package javaThread;

import java.util.function.Consumer;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam03_ThreadSleep extends Application{
	TextArea textarea;
	Button btn;
	
	private void printMsg(String msg) {
		Platform.runLater(()->{//인자로 thread가 들어가기때문에 가능
			textarea.appendText(msg + "\n");
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
		
		btn = new Button("버튼 클릭!!");
		btn.setPrefSize(250, 50);
		btn.setOnAction(t->{
			//버튼에서 action이 발생(클릭)했을 때 호출
			//1부터 5까지 5번 반복 => for문 이용, stream이용시
			IntStream intStream = IntStream.rangeClosed(1, 5);
			intStream.forEach(value->{	//Consumer 사용
				//1부터 5까지 5번 반복하면서 Thread를 생성
				Thread thread = new Thread(()->{//runnable은 함수적 인터페이스로 
												//run이라는 추상메소드를 람다식으로 표현할 수 있음
					for(int i=0; i<5; i++) {
						try {
							Thread.sleep(3000);
							printMsg(i+" : "
									+Thread.currentThread().getName());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
													
				thread.setName("ThreadNumber-"+value);
				thread.start(); //runnable상태로 들어감
			});
		});
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane에 버튼을 올려요~
		flowpane.getChildren().add(btn);
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
