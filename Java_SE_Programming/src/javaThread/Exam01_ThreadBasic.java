package javaThread;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/*
 * Java Application은 main thread가 main() method를 호출해서 실행 
 * 
 * 프로그램은 main method()가 종료될 때 종료되는게 아니라 프로그램내에서
 * 파생된 모든 Thread가 종료될 때 종료됨
 * 
 * 1. Thread의 생성
 * 		=> Thread class를 상속받아서 class를 정의하고 객체 생성해서 사용
 * 		=> Runnable interface를 구현한 class를 정의하고 객체를 생성해서 
 * 			Thread생성자의 인자로 넣어서 Thread 생성
 * 		=> 현재 사용되는 Thread의 이름을 출력!!
 * 2. 실제 Thread의 생성(new) -> start() (thread를 실행시키는게 아니라 runnable상태로 전환)
 * 		-> JVM안에 있는 Thread schedule에 의해 하나의 Thread가 선택되서
 * 			thread가 running상태로 전환 
 * 		-> 어느시점이 되면 Thread scheduler에 의해서 runnable상태로 전환(언제 전환되는지는 모름)
 * 		-> runnable상태에 있는 thread중 하나를 선택해서 running 상태로 전환
 */

public class Exam01_ThreadBasic extends Application{

	
	TextArea textarea;
	Button btn;
	
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
			//Textarea에 글자를 넣어요!
			new Thread(()->{ // 람다식은 runnable interface가 가지고 잇는 run이라는 실행 method를 지칭
				System.out.println(Thread.currentThread().getName());
				//화면제어는 JavaFX Application Thread가 담당
				//textaread에 출력하기 위해서 JavaFX Application Thread한테 부탁함
				// =>나중에 화면에 thread를 실행시켜줘
				Platform.runLater(()->{
					textarea.appendText("소리없는 아우성!!\n");
				});
			}).start(); //그래서 thread를 시작할때 run()이 아닌 start()로 시작
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//				}
//			}).start();
			
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
