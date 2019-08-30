package javaThread;

/*
 * 1���� 100���� ������ ���� ���Ϸ��� �ؿ�
 * 1~10���� 1���� Thread�� ���� ����ؼ� ����� return
 * 11~20���� 1���� Thread�� ���� ����ؼ� ����� return
 * ..
 * 91~100���� 1���� Thread�� ���� ����ؼ� ����� return
 * ==> Thread Pool�� �̿��ؾ� �ϰ� Callable�� �̿��ؼ� return���� �޾ƾ���
 * ==> 10���� Thread�κ��� ���� �����͸� �޾Ƶ��̴� Thread�� �ϳ� �������ؿ�
 * 	   : blocking �ɸ����� �ֱ� ������..? 
 * 	   : � thread�� ���� ������ �𸣱⶧���� ���� ���ϴ� thread�� ���� �������� 
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
	//initBtn : Thread Pool�� �����ϴ� ��ư
	//startBtn : Thread Pool�� �̿��ؼ� Thread�� �����Ű�� ��ư
	//stopBtn : Thread Pool�� �����ϴ� ��ư
	ExecutorService executorService;
	//executorService : Thread Pool
	ExecutorCompletionService<Integer> executorCompletionService; //generic�� returnŸ��
	private int total = 0;
	
	private void printMsg(String msg) {
		//textarea�� ���ڿ� ����ϴ� method
		Platform.runLater(()->{
			textarea.appendText(msg+"\n");
		});
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//ȭ���� ���� thread�� JavaFX Application Thread
		//�� thread�� ��� ������ start��� method�� ȣ��
		System.out.println(Thread.currentThread().getName());
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
		
		initBtn = new Button("Thread Pool ����");
		initBtn.setPrefSize(250, 50);
		initBtn.setOnAction(t->{
			executorService = Executors.newCachedThreadPool();
			//Ȯ��� ����� �ִ� thread pool�� �����
			// ���� threadpool�� ������ �� �� ������ ���� thread pool�� �����
			executorCompletionService = 
					new ExecutorCompletionService<Integer>(executorService); 
		});
		
		
		stopBtn = new Button("Thread Pool ����");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t->{
			executorService.shutdown();
		});
		
		
		// callable�� �����ų ���� �ٸ�
		startBtn = new Button("Thread ����!!");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			//��ư���� action�� �߻�(Ŭ��)���� �� ȣ��
			for(int i=1; i<101; i=i+10) {
				final int k = i;
				// callable�� return�� ����
				//return���� �ִ� thread�� �����ϴ� ���
				Callable<Integer> callable = new Callable<Integer>() {
					@Override
					public Integer call() throws Exception {
						IntStream intStream = 
								IntStream.rangeClosed(k, k+9);
						int sum = intStream.sum();
						return sum;
					}
				};

				//Future<Integer> future =  //thread�� ������ ������� ����, Future ��ü��
				executorCompletionService.submit(callable); //future��ü�� ���� ������ block����
				//���� thread�� �����ؼ� take()�� ���������
			}
			// total�� ���� �־��ٰ��̱� ������ return����
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					for(int i=0; i<10; i++) {
						try {
							Future<Integer> future = 
									executorCompletionService.take(); //������� ��ٸ��� method
							total += future.get();
						} catch (InterruptedException | ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
					printMsg("���� ������� : "+ total);
				}
			};
			executorService.execute(runnable);
		});
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane�� ��ư�� �÷���~
		flowpane.getChildren().add(initBtn);
		flowpane.getChildren().add(startBtn);
		flowpane.getChildren().add(stopBtn);
		root.setBottom(flowpane);
		
		//Scene��ü�� �ʿ��ؿ�
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread �����Դϴ�!");
		primaryStage.show();
		
	}
	
	
	public static void main(String[] args) {
		// main method�� ȣ���ϴ� Thread�� main�̶� �̸��� thread
		System.out.println(Thread.currentThread().getName());
		launch(); //launch()ȣ��Ǹ� startȣ���ted method stub

	}
}
