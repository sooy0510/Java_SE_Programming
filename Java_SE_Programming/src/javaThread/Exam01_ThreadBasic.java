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
 * Java Application�� main thread�� main() method�� ȣ���ؼ� ���� 
 * 
 * ���α׷��� main method()�� ����� �� ����Ǵ°� �ƴ϶� ���α׷�������
 * �Ļ��� ��� Thread�� ����� �� �����
 * 
 * 1. Thread�� ����
 * 		=> Thread class�� ��ӹ޾Ƽ� class�� �����ϰ� ��ü �����ؼ� ���
 * 		=> Runnable interface�� ������ class�� �����ϰ� ��ü�� �����ؼ� 
 * 			Thread�������� ���ڷ� �־ Thread ����
 * 		=> ���� ���Ǵ� Thread�� �̸��� ���!!
 * 2. ���� Thread�� ����(new) -> start() (thread�� �����Ű�°� �ƴ϶� runnable���·� ��ȯ)
 * 		-> JVM�ȿ� �ִ� Thread schedule�� ���� �ϳ��� Thread�� ���õǼ�
 * 			thread�� running���·� ��ȯ 
 * 		-> ��������� �Ǹ� Thread scheduler�� ���ؼ� runnable���·� ��ȯ(���� ��ȯ�Ǵ����� ��)
 * 		-> runnable���¿� �ִ� thread�� �ϳ��� �����ؼ� running ���·� ��ȯ
 */

public class Exam01_ThreadBasic extends Application{

	
	TextArea textarea;
	Button btn;
	
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
			//Textarea�� ���ڸ� �־��!
			new Thread(()->{ // ���ٽ��� runnable interface�� ������ �մ� run�̶�� ���� method�� ��Ī
				System.out.println(Thread.currentThread().getName());
				//ȭ������� JavaFX Application Thread�� ���
				//textaread�� ����ϱ� ���ؼ� JavaFX Application Thread���� ��Ź��
				// =>���߿� ȭ�鿡 thread�� ���������
				Platform.runLater(()->{
					textarea.appendText("�Ҹ����� �ƿ켺!!\n");
				});
			}).start(); //�׷��� thread�� �����Ҷ� run()�� �ƴ� start()�� ����
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//				}
//			}).start();
			
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
