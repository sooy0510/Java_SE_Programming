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
		
		startBtn = new Button("Thread Ŭ��!!");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			//��ư���� action�� �߻�(Ŭ��)���� �� ȣ��
			//Thread����(for loop�� 1�� sleep�ϸ鼭 10�� �ݺ�)
			//�� Thread�� dead���·� �������ؼ��� 10�ʰ� �ɸ�// �߁C�� x������ ȭ�鲨�� ���α׷��� �������
			Thread thread = new Thread(()->{
				try {
					for(int i=0; i<10; i++) {
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			});
			thread.setDaemon(true); //�ش� Thread�� daemon thread�� ����
			//�ڽ� thread�� �ȴٰ� �����ϸ� ��
			//�θ� thread�� �����Ǹ� �ڵ������� �ڽ� thread�� ����
			//x������ ȭ����� �θ� thread�� �ױ� ������ �ڵ����� �ڽ� thread�׾ ���α׷��� ����
			//Thread�� ���¸� �������ִ� ���̱� ������ start() method�� ȣ��Ǳ� ���� �����Ǿ�餤��
			thread.start();
		});
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane�� ��ư�� �÷���~
		flowpane.getChildren().add(startBtn);
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
