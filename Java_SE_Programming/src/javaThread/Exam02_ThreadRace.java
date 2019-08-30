package javaThread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

class UserPanel extends FlowPane{
	private TextField nameField = new TextField();
	private ProgressBar progressBar = new ProgressBar(0.0); //��������
	private ProgressIndicator progressIndicator =	//������
			new ProgressIndicator(0.0);
	
	public UserPanel() {
		
	}

	public UserPanel(String name) {
		// method�տ� this����
		setPrefSize(700, 50);
		nameField.setPrefSize(100, 50);
		progressBar.setPrefSize(500, 50);
		progressIndicator.setPrefSize(50, 50);
		nameField.setText(name);
		getChildren().add(nameField);
		getChildren().add(progressBar);
		getChildren().add(progressIndicator);
	}

	public TextField getNameField() {
		return nameField;
	}

	public void setNameField(TextField nameField) {
		this.nameField = nameField;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public ProgressIndicator getProgressIndicator() {
		return progressIndicator;
	}

	public void setProgressIndicator(ProgressIndicator progressIndicator) {
		this.progressIndicator = progressIndicator;
	}
	
}

class Rank{
	int rank = 0;
	TextArea textarea;
	public Rank(TextArea textarea) {
		super();
		this.textarea = textarea;
	}
	
	public void setRank(int a) {
		rank += a;
	}
	
	public void printRank() {
		textarea.appendText(Integer.toString(rank));
	}
	
}

class ProgressRunnable implements Runnable{
	private String name;
	private ProgressBar progressBar;
	private ProgressIndicator progressIndicator;
	private TextArea textarea;
	private Rank rank;
	public ProgressRunnable() {
		// TODO Auto-generated constructor stub
	}

	public ProgressRunnable(String name, ProgressBar progressBar, ProgressIndicator progressIndicator,
			TextArea textarea, Rank rank) {
		super();
		this.name = name;
		this.progressBar = progressBar;
		this.progressIndicator = progressIndicator;
		this.textarea = textarea;
		this.rank = rank;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ProgressBar getProgressBar() {
		return progressBar;
	}
	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}
	public ProgressIndicator getProgressIndicator() {
		return progressIndicator;
	}
	public void setProgressIndicator(ProgressIndicator progressIndicator) {
		this.progressIndicator = progressIndicator;
	}
	public TextArea getTextarea() {
		return textarea;
	}
	public void setTextarea(TextArea textarea) {
		this.textarea = textarea;
	}
	@Override
	public void run() {
		//Thread�� �����ؼ� progressBar�� �����ϸ� ��
		Random random = new Random();
		double k = 0; 
		while(progressBar.getProgress() < 1.0) {
			try {
				Thread.sleep(1000); //1�� ���� ���� Thread�� sleep
				k += (random.nextDouble() * 0.1);
				final double tt = k;
				//k���� ���������� ����
				Platform.runLater(()->{
					progressBar.setProgress(tt);
					progressIndicator.setProgress(tt);
				});
				if(k>1.0) {
					break;
				}
			}catch(Exception e) {
				System.out.println(e);
			}
		}
		textarea.appendText(this.name);
		rank.setRank(1);
		rank.printRank();
	}
}

public class Exam02_ThreadRace extends Application {
	
	private List<String> names = 
			Arrays.asList("�̼���","������","������","������");
	
	//progressBar�� ������ Thread�� FlowPane 1���� 1���� �����ؾ� �ؿ�
	private List<ProgressRunnable> uRunnable = 
			new ArrayList<ProgressRunnable>();
	
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
		
		//center�κ��� ������ TilePane�� �����ؾ� �Ѵ�
		TilePane center = new TilePane();//���� ���·� �������
		center.setPrefColumns(1); //1���� �����ϴ� TilePane
		center.setPrefRows(4);	//4�ุ �����ϴ� TilePane
		
		//�޽����� ��µ� TextArea ���� �� ũ�� ����
		textarea = new TextArea();
		textarea.setPrefSize(600, 100);
		Rank rank = new Rank(textarea);
		//���� ������� TilePane�� 3���� FlowPane�� TextArea�� ����
		for(String name : names) {
			UserPanel panel = new UserPanel(name);
			center.getChildren().add(panel);
			uRunnable.add(
					new ProgressRunnable(
					panel.getNameField().getText(),
					panel.getProgressBar(),
					panel.getProgressIndicator(),
					textarea,
					rank));
		}
		center.getChildren().add(textarea);
		root.setCenter(center);
		
		
		btn = new Button("��ư Ŭ��!!");
		btn.setPrefSize(250, 50);
		btn.setOnAction(t->{
			//��ư���� action�� �߻�(Ŭ��)���� �� ȣ��
			//uRunnable(ArrayList)�� ���鼭 Thread�� �����ϰ�
			//start()ȣ��
			for(ProgressRunnable runnable : uRunnable) {
				new Thread(runnable).start();
			}
			
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
