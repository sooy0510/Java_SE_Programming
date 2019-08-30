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
		
		startBtn = new Button("Thread ����!!");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			//��ư���� action�� �߻�(Ŭ��)���� �� ȣ��
			counterThread = new Thread(()->{
				try {
					for(int i=0; i<10; i++) {
						Thread.sleep(1000);
						printMsg(i+"-"+Thread.currentThread().getName());
					}
				} catch (Exception e) {
					//���� interrupt()�� �ɷ��ִ� ���¿���block���·� �����ϸ�
					//Exception�� ���鼭 catch������ �̵�
					
					printMsg("Thread�� ����Ǿ����ϴ�");
				}
			});
			counterThread.start();
			
		});
		
		stopBtn = new Button("Thread ����!!");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t->{
			//��ư���� action�� �߻�(Ŭ��)���� �� ȣ��
			counterThread.interrupt(); //method�� ����ȴٰ� �ٷ� thread�� ����Ǵ°��� �ƴϴ�
										//interrupt() method�� ȣ��� Thread�� sleep()�� ����
										//block���¿� ������ interrupt�� ��ų �� ����
			
		});
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane�� ��ư�� �÷���~
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
