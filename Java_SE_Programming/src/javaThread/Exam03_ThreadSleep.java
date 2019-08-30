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
		Platform.runLater(()->{//���ڷ� thread�� ���⶧���� ����
			textarea.appendText(msg + "\n");
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
		
		btn = new Button("��ư Ŭ��!!");
		btn.setPrefSize(250, 50);
		btn.setOnAction(t->{
			//��ư���� action�� �߻�(Ŭ��)���� �� ȣ��
			//1���� 5���� 5�� �ݺ� => for�� �̿�, stream�̿��
			IntStream intStream = IntStream.rangeClosed(1, 5);
			intStream.forEach(value->{	//Consumer ���
				//1���� 5���� 5�� �ݺ��ϸ鼭 Thread�� ����
				Thread thread = new Thread(()->{//runnable�� �Լ��� �������̽��� 
												//run�̶�� �߻�޼ҵ带 ���ٽ����� ǥ���� �� ����
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
				thread.start(); //runnable���·� ��
			});
		});
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane�� ��ư�� �÷���~
		flowpane.getChildren().add(btn);
		root.setBottom(flowpane);
		
		//Scene��ü�� �ʿ��ؿ�
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread �����Դϴ�!");
		primaryStage.show();
		
	}
	
	//Application�� �߻� Ŭ����
	public static void main(String[] args) {
		
		// ���� main method�� ȣ���� Thread�� �̸��� ���!
		// main method�� ȣ���ϴ� Thread�� main�̶� �̸��� thread
		System.out.println(Thread.currentThread().getName());
		launch(); //launch()ȣ��Ǹ� startȣ���
	}

}
