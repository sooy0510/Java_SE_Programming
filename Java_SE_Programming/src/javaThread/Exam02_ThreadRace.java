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
	private ProgressBar progressBar = new ProgressBar(0.0); //막대형태
	private ProgressIndicator progressIndicator =	//원형태
			new ProgressIndicator(0.0);
	
	public UserPanel() {
		
	}

	public UserPanel(String name) {
		// method앞에 this생략
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
		//Thread가 동작해서 progressBar를 제어하면 됨
		Random random = new Random();
		double k = 0; 
		while(progressBar.getProgress() < 1.0) {
			try {
				Thread.sleep(1000); //1초 동안 현재 Thread를 sleep
				k += (random.nextDouble() * 0.1);
				final double tt = k;
				//k값이 지속적으로 증가
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
			Arrays.asList("이수연","최주현","공선아","소현우");
	
	//progressBar를 제어할 Thread가 FlowPane 1개당 1개씩 존재해야 해요
	private List<ProgressRunnable> uRunnable = 
			new ArrayList<ProgressRunnable>();
	
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
		
		//center부분을 차지할 TilePane을 생성해야 한다
		TilePane center = new TilePane();//격자 형태로 만들어짐
		center.setPrefColumns(1); //1열만 존재하는 TilePane
		center.setPrefRows(4);	//4행만 존재하는 TilePane
		
		//메시지가 출력될 TextArea 생성 및 크기 결정
		textarea = new TextArea();
		textarea.setPrefSize(600, 100);
		Rank rank = new Rank(textarea);
		//이제 만들어진 TilePane에 3개의 FlowPane과 TextArea를 부착
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
		
		
		btn = new Button("버튼 클릭!!");
		btn.setPrefSize(250, 50);
		btn.setOnAction(t->{
			//버튼에서 action이 발생(클릭)했을 때 호출
			//uRunnable(ArrayList)를 돌면서 Thread를 생성하고
			//start()호출
			for(ProgressRunnable runnable : uRunnable) {
				new Thread(runnable).start();
			}
			
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
