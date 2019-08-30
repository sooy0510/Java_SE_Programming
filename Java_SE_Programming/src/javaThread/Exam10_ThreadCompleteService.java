package javaThread;

/*
 * 1부터 100까지 숫자의 합을 구하려고 해요
 * 1~10까지 1개의 Thread가 합을 계산해서 결과를 return
 * 11~20까지 1개의 Thread가 합을 계산해서 결과를 return
 * ..
 * 91~100까지 1개의 Thread가 합을 계산해서 결과를 return
 * ==> Thread Pool을 이용해야 하고 Callable을 이용해서 return값을 받아야함
 * ==> 10개의 Thread로부터 각각 데이터를 받아들이는 Thread를 하나 만들어야해요
 * 	   : blocking 걸릴수도 있기 때문에..? 
 * 	   : 어떤 thread가 먼저 끝날지 모르기때문에 합을 구하는 thread를 따로 만들어야함 
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam10_ThreadCompleteService extends Application{
	TextArea textarea;
	Button initBtn, startBtn, stopBtn;
	//initBtn : Thread Pool을 생성하는 버튼
	//startBtn : Thread Pool을 이용해서 Thread를 실행시키는 버튼
	//stopBtn : Thread Pool을 종료하는 버튼
	ExecutorService executorService;
	//executorService : Thread Pool
	ExecutorCompletionService<Integer> executorCompletionService; //generic은 return타입
	private int total = 0;
	
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
		
		initBtn = new Button("Thread Pool 생성");
		initBtn.setPrefSize(250, 50);
		initBtn.setOnAction(t->{
			executorService = Executors.newCachedThreadPool();
			//확장된 기능이 있는 thread pool을 만들기
			// 기존 threadpool을 가지고 좀 더 성능이 향상된 thread pool을 만들기
			executorCompletionService = 
					new ExecutorCompletionService<Integer>(executorService); 
		});
		
		
		stopBtn = new Button("Thread Pool 종료");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t->{
			executorService.shutdown();
		});
		
		
		// callable은 실행시킬 때만 다름
		startBtn = new Button("Thread 실행!!");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			//버튼에서 action이 발생(클릭)했을 때 호출
			for(int i=1; i<101; i=i+10) {
				final int k = i;
				// callable은 return이 있음
				//return값이 있는 thread를 실행하는 방법
				Callable<Integer> callable = new Callable<Integer>() {
					@Override
					public Integer call() throws Exception {
						IntStream intStream = 
								IntStream.rangeClosed(k, k+9);
						int sum = intStream.sum();
						return sum;
					}
				};

				//Future<Integer> future =  //thread를 실행한 결과값을 받음, Future 객체로
				executorCompletionService.submit(callable); //future객체로 지금 받으면 block당함
				//끝난 thread를 감지해서 take()를 실행시켜줌
			}
			// total에 값을 넣어줄것이기 때문에 return없음
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					for(int i=0; i<10; i++) {
						try {
							Future<Integer> future = 
									executorCompletionService.take(); //결과값을 기다리는 method
							total += future.get();
						} catch (InterruptedException | ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
					printMsg("최종 결과값은 : "+ total);
				}
			};
			executorService.execute(runnable);
		});
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane에 버튼을 올려요~
		flowpane.getChildren().add(initBtn);
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
