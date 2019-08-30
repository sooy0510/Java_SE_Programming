package javaThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/*
 * new �Ҷ����� thread��������� overhead�� ũ��
 * ����(����)ó���� �������� ���������� Thread������ �����ϰ� �ǰ� �̿� ���� Thread������
 * Scheduling�� ���� Overehead�� ���� => ���������� �ֿ����
 * Thread pool�� thread�� �̸� �������� ������ ���� ��� 
 * Java7�̻���� ��Ű���� ����
 */
public class Exam08_ThreadPoolBasic extends Application{
	TextArea textarea;
	Button initBtn, startBtn, stopBtn;
	//initBtn : Thread Pool�� �����ϴ� ��ư
	//startBtn : Thread Pool�� �̿��ؼ� Thread�� �����Ű�� ��ư
	//stopBtn : Thread Pool�� �����ϴ� ��ư
	ExecutorService executorService;
	
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
			int threadNum = 
					((ThreadPoolExecutor)executorService).getPoolSize();
			printMsg("���� Pool���� Thread ���� : "+threadNum);
			// ó���� ��������� Thread Pool �ȿ��� thread�� ����
			// ���� �ʿ��ϸ� ���������� Thread����
			// ����� Thread�� ���� ������ ����
			//60�� ���� Thread�� ������ ������ �ڵ������� thread ����
			
			///executorService = Executors.newFixedThreadPool(5); 
			// ó���� ��������� Thread Pool �ȿ��� thread�� ����
			// ���� �ʿ��ϸ� ���������� Thread����
			// ���ڷ� ���� int����ŭ�� Thread�� ���������
			// Thread�� ������ �ʴ��� ������� Thread�� ��� ����.
		});
		
		
		stopBtn = new Button("Thread Pool ����");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t->{
			executorService.shutdown();
		});
		
		
		startBtn = new Button("Thread ����!!");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			//��ư���� action�� �߻�(Ŭ��)���� �� ȣ��
			for(int i=0; i<10; i++) {
				final int k = i;
				Runnable runnable = () ->{
					//���ٳ����� �������� ����
					Thread.currentThread().setName("MYThread-"+ k);
					String msg = Thread.currentThread().getName()+
							"Pool�� ���� : "+((ThreadPoolExecutor)executorService).getPoolSize();
					printMsg(msg);
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
				executorService.execute(runnable); //���ڷ� runnable��ü �־��� , ���������� thread����� ����
													//executorService�� �� ������ ����
			}
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
