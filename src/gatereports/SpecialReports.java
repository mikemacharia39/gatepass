package gatereports;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import charts.StudentChart;
import charts.StudentPie;
import charts.WholeBar;
import charts.WholePie;
import dialogs.ErrorMessage;

public class SpecialReports extends Application{

	Stage window;
	Scene scene;
	Label labelbar, labelpie, labelvpie, labelvbar, labelstdpie, labelstdbar, labelspie, labelsbar, labellecbar, labellecpie;
	Image imb= new Image(SpecialReports.class.getResourceAsStream("/pic2/barpie.png"));
	ImageView ivb= new ImageView(imb);
	Text text1, text2;
	@Override
	public void start(Stage s) throws Exception {
		
		window=s;
		window= new Stage(StageStyle.UTILITY);
		window.setResizable(false);
		window.setTitle("Chart Reports");
		window.getIcons().add(new Image("/pic/slogo.png"));
		window.initModality(Modality.APPLICATION_MODAL);
		Image img1= new Image(SpecialReports.class.getResourceAsStream("/pic/Bar-chart.png"));
		ImageView ivimg1= new ImageView(img1);
		labelbar= new Label("",ivimg1);
		labelbar.setMinWidth(130);
		labelbar.setMinHeight(120);
		labelbar.setAlignment(Pos.CENTER);
		labelbar.setOnMouseClicked(e -> {
			try{
			new StudentChart().start(new Stage());
		
			} catch (Exception e1) {
				ErrorMessage.display("Launch Error", "Some error occured when launching application");
				e1.printStackTrace();
			}
		});
		
		text2= new Text("Click to View Graphs");
		text2.setFont(Font.font("Andalus", 19));
		
		Image img3= new Image(SpecialReports.class.getResourceAsStream("/pic/pie_chart.png"));
		ImageView ivimg3= new ImageView(img3);
		ivimg3.setFitWidth(90);
		ivimg3.setFitHeight(90);
		labelpie= new Label("", ivimg3);
		labelpie.setMinWidth(130);
		labelpie.setMinHeight(120);
		labelpie.setAlignment(Pos.CENTER);
		labelpie.setOnMouseClicked(e -> {
			try {
				new StudentPie().start(new Stage());
			} catch (Exception e1) {
				ErrorMessage.display("Launch Error", "Some error occured when launching application");
				e1.printStackTrace();
			}
		});
		
		//student
		Image imgst1= new Image(SpecialReports.class.getResourceAsStream("/pic/Bar-chart.png"));
		ImageView ivimgst1= new ImageView(imgst1);
		labelstdbar= new Label("",ivimgst1);
		labelstdbar.setMinWidth(130);
		labelstdbar.setMinHeight(120);
		labelstdbar.setAlignment(Pos.CENTER);
		labelstdbar.setOnMouseClicked(e -> {
			try{
				String lp= "Student";
				new WholeBar(lp).start(new Stage());
		
			} catch (Exception e1) {
				ErrorMessage.display("Launch Error", "Some error occured when launching application");
				e1.printStackTrace();
			}
		});
		
		Image imgsp3= new Image(SpecialReports.class.getResourceAsStream("/pic/pie_chart.png"));
		ImageView ivimgsp3= new ImageView(imgsp3);
		ivimg3.setFitWidth(90);
		ivimg3.setFitHeight(90);
		labelstdpie= new Label("", ivimgsp3);
		labelstdpie.setMinWidth(130);
		labelstdpie.setMinHeight(120);
		labelstdpie.setAlignment(Pos.CENTER);
		labelstdpie.setOnMouseClicked(e -> {
			try {
				String sp= "Student";
				new WholePie(sp).start(new Stage());
			} catch (Exception e1) {
				ErrorMessage.display("Launch Error", "Some error occured when launching application");
				e1.printStackTrace();
			}
		});
		//end part1
		
		//lecturer
				Image imglec1= new Image(SpecialReports.class.getResourceAsStream("/pic/Bar-chart.png"));
				ImageView ivimglec1= new ImageView(imglec1);
				labellecbar= new Label("",ivimglec1);
				labellecbar.setMinWidth(130);
				labellecbar.setMinHeight(120);
				labellecbar.setAlignment(Pos.CENTER);
				labellecbar.setOnMouseClicked(e -> {
					try {
						String sp= "Lecturer";
						new WholeBar(sp).start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error", "Some error occured when launching application");
						e1.printStackTrace();
					}
				});
				
				Image imglecp3= new Image(SpecialReports.class.getResourceAsStream("/pic/pie_chart.png"));
				ImageView ivimglecp3= new ImageView(imglecp3);
				ivimg3.setFitWidth(90);
				ivimg3.setFitHeight(90);
				labellecpie= new Label("", ivimglecp3);
				labellecpie.setMinWidth(130);
				labellecpie.setMinHeight(120);
				labellecpie.setAlignment(Pos.CENTER);
				labellecpie.setOnMouseClicked(e -> {
					try {
						String sp= "Lecturer";
						new WholePie(sp).start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error", "Some error occured when launching application");
						e1.printStackTrace();
					}
				});
				//end part2
				
				//student
				Image imgs1= new Image(SpecialReports.class.getResourceAsStream("/pic/Bar-chart.png"));
				ImageView ivimgs1= new ImageView(imgs1);
				labelsbar= new Label("",ivimgs1);
				labelsbar.setMinWidth(130);
				labelsbar.setMinHeight(120);
				labelsbar.setAlignment(Pos.CENTER);
				labelsbar.setOnMouseClicked(e -> {
					try {
						String sp= "Staff";
						new WholeBar(sp).start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error", "Some error occured when launching application");
						e1.printStackTrace();
					}
				});
				
				Image imgsps3= new Image(SpecialReports.class.getResourceAsStream("/pic/pie_chart.png"));
				ImageView ivimgsps3= new ImageView(imgsps3);
				ivimg3.setFitWidth(90);
				ivimg3.setFitHeight(90);
				labelspie= new Label("", ivimgsps3);
				labelspie.setMinWidth(130);
				labelspie.setMinHeight(120);
				labelspie.setAlignment(Pos.CENTER);
				labelspie.setOnMouseClicked(e -> {
					try {
						String sp= "Staff";
						new WholePie(sp).start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error", "Some error occured when launching application");
						e1.printStackTrace();
					}
				});
				//end part3
				
				//student
				Image imgv1= new Image(SpecialReports.class.getResourceAsStream("/pic/Bar-chart.png"));
				ImageView ivimgv1= new ImageView(imgv1);
				labelvbar= new Label("",ivimgv1);
				labelvbar.setMinWidth(130);
				labelvbar.setMinHeight(120);
				labelvbar.setAlignment(Pos.CENTER);
				labelvbar.setOnMouseClicked(e -> {
					try {
						String sp= "Visitor";
						new WholeBar(sp).start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error", "Some error occured when launching application");
						e1.printStackTrace();
					}
				});
				
				Image imgvp3= new Image(SpecialReports.class.getResourceAsStream("/pic/pie_chart.png"));
				ImageView ivimgvp3= new ImageView(imgvp3);
				ivimg3.setFitWidth(90);
				ivimg3.setFitHeight(90);
				labelvpie= new Label("", ivimgvp3);
				labelvpie.setMinWidth(130);
				labelvpie.setMinHeight(120);
				labelvpie.setAlignment(Pos.CENTER);
				labelvpie.setOnMouseClicked(e -> {
					try {
						String sp= "Visitor";
						new WholePie(sp).start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error", "Some error occured when launching application");
						e1.printStackTrace();
					}
				});
				//end part4
		text1=  new Text("Graph Representations");
		text1.setId("myText");
		VBox layst1= new VBox(15);
		layst1.getChildren().addAll(labelstdbar,labelstdpie);
		layst1.setAlignment(Pos.CENTER);
		
		VBox lays1= new VBox(15);
		lays1.getChildren().addAll(labelsbar,labelspie);
		lays1.setAlignment(Pos.CENTER);
		
		VBox layl1= new VBox(15);
		layl1.getChildren().addAll(labellecbar,labellecpie);
		layl1.setAlignment(Pos.CENTER);
		
		VBox layv1= new VBox(15);
		layv1.getChildren().addAll(labelvbar,labelvpie);
		layv1.setAlignment(Pos.CENTER);
				
		TitledPane t1 = new TitledPane("Student Graphs", layst1);
		TitledPane t2 = new TitledPane("Lecturer Graphs", layl1);
		TitledPane t3 = new TitledPane("Staff Graphs", lays1);		
		TitledPane t4 = new TitledPane("Visitors Graphs", layv1);		
		Accordion accordion = new Accordion();
        accordion.getPanes().add(t1);

        accordion.getPanes().add(t2);

        accordion.getPanes().add(t3);
        
        accordion.getPanes().add(t4);
        
        VBox layaccs= new VBox();
        layaccs.getChildren().addAll(accordion);
        layaccs.setPadding(new Insets(50,0,0,0));	
        
		VBox lay1= new VBox(15);
		lay1.getChildren().addAll(text2, labelbar, labelpie);
		lay1.setAlignment(Pos.TOP_CENTER);
		
		VBox lay2= new VBox();
		lay2.getChildren().addAll(lay1);
		
		VBox laybac= new VBox(10);
		laybac.getChildren().addAll(text1, ivb);
		laybac.setAlignment(Pos.CENTER_RIGHT);
		
		HBox lay1acc= new HBox();
		lay1acc.getChildren().addAll(lay2, layaccs);
		
		HBox lay2acc= new HBox(105);
		lay2acc.getChildren().addAll(lay1acc, laybac);
		lay2acc.setPadding(new Insets(10,10,10,8));
		
		scene= new Scene(lay2acc, 1050,500);
		window.setScene(scene);
		scene.getStylesheets().add(SpecialReports.class.getResource("special.css").toExternalForm());
		window.setX(290);
		window.setY(30);
		window.show();
	}
	public static void main(String [] args){launch(args);}
}
