package javaThread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam09_ThreadCallable extends Application{
	TextArea textarea;
	Button initBtn, startBtn, stopBtn;
	//initBtn : Thread Pool을 생성하는 버튼
	//startBtn : Thread Pool을 이용해서 Thread를 실행시키는 버튼
	//stopBtn : Thread Pool을 종료하는 버튼
	ExecutorService executorService;
	//executorService : Thread Pool
	
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
			int threadNum = 
					((ThreadPoolExecutor)executorService).getPoolSize();
			printMsg("현재 Pool안의 Thread 개수 : "+threadNum);
			// 처음에 만들어지는 Thread Pool 안에는 thread가 없어
			// 만약 필요하면 내부적으로 Thread생성
			// 만드는 Thread의 수는 제한이 없음
			//60초 동아 Thread가 사용되지 않으면 자동적으로 thread 삭제
			
			///executorService = Executors.newFixedThreadPool(5); 
			// 처음에 만들어지는 Thread Pool 안에는 thread가 없어
			// 만약 필요하면 내부적으로 Thread생성
			// 인자로 들어온 int수만큼의 Thread만 만들수잇음
			// Thread가 사용되지 않더라도 만들어진 Thread는 계속 유지.
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
			for(int i=0; i<10; i++) {
				final int k = i;
				// callable은 return이 있음
				//return값이 있는 thread를 실행하는 방법
				Callable<String> callable = new Callable<String>() {
					@Override
					public String call() throws Exception {
						Thread.currentThread().setName("MYThread-"+ k);
						String msg = Thread.currentThread().getName()+
								"Pool의 개수 : "+((ThreadPoolExecutor)executorService).getPoolSize();
						System.out.println(msg);
						//printMsg(msg);
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return Thread.currentThread().getName() + "종료";
					}
				};

				Future<String> future =  //thread를 실행한 결과값을 받음, Future 객체로
						executorService.submit(callable);
				// future :pending 객체/
				// 실제로 객체가 들어가있진 않고 담을수 있는 형태만 마련한 상태
				// future 객체의  특정 method를 호출할때 호출된다
				try {
					String result = future.get();
					// get() method가 blocking method...
					// 나머지가ㅣ 실행이 안되서 순차처리가 됨
					System.out.println(result);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
