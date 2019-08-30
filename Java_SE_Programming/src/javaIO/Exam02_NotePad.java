package javaIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * JavaFX�� �̿��ؼ� GUI ���α׷��� ������� �ؿ�
 * ȭ�鿡 â ������ Application�̶�� class�� instance�� ���� 
 */

public class Exam02_NotePad extends Application{

	TextArea textarea;
	Button openBtn, saveBtn;
	File file;
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg + "\n");
		});
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);
		
		textarea = new TextArea();
		root.setCenter(textarea);
		
		openBtn = new Button("���� ����");
		openBtn.setPrefSize(150, 50);
		openBtn.setOnAction(t->{
			textarea.clear(); //textarea���� ������ �� ������
			FileChooser chooser = new FileChooser();
			chooser.setTitle("������ ������ �����ϼ���");
			// ���� chooser�κ��� ������ ���Ͽ� ���� reference�� ȹ��
			file = chooser.showOpenDialog(primaryStage);
			// file��ü�κ��� input Stream�� �����
			try {
				FileReader fr = new FileReader(file);//stream reader
				BufferedReader br = new BufferedReader(fr);
				String line = "";
				while((line = br.readLine()) != null) {
					printMsg(line);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 	
		});
		
		saveBtn = new Button("���� ����");
		saveBtn.setPrefSize(150, 50);
		saveBtn.setOnAction(t->{
			String content = textarea.getText();
			try {
				FileWriter fw = new FileWriter(file);
				
				fw.write(content);
				fw.close(); 	//�ݵ�� closeó���� ����� ��
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���� ����");
				alert.setHeaderText("File save!!");
				alert.setContentText("���Ͽ� ������ ����Ǿ����!");
				alert.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		FlowPane flowpane= new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.getChildren().add(openBtn);
		flowpane.getChildren().add(saveBtn);
		
		root.setBottom(flowpane);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}
}
