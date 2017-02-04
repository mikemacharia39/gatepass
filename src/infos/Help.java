package infos;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Help extends Application{
	
	Stage window;
	Scene scene;
	private Rectangle2D boxBounds = new Rectangle2D(700,700,600,600);
	 private VBox bottomPane;
	 private VBox topPane, thirdpane, fourthpane;
	 private Rectangle clipRect;
	 private Timeline timelineUp;
	 private Timeline timelineDown, timelinethree, timelinefour;
	 Text welc = new Text("Welcome to the Help System");
	 Text ushead, using, dethead, details, sysHead, sysdet;
	 Label tpic;
	 Image img= new Image(Help.class.getResourceAsStream("/pic/help1.png"));
	 ImageView ivt= new ImageView(img);
	
	@Override
	public void start(Stage shelp) throws Exception {
		
		window= shelp;
		window= new Stage(StageStyle.UTILITY);
		window.initModality(Modality.APPLICATION_MODAL);
		window.getIcons().add(new Image("/pic/slogo.png"));
		HBox root = new HBox();
		  root.setAlignment(Pos.CENTER);
		  root.autosize();
		  scene = new Scene(root, 804,615);
		  window.setTitle("Help - GatePass System");
		  window.setScene(scene);
		  scene.getStylesheets().add(Help.class.getResource("info.css").toExternalForm());
		  window.setResizable(false);
		  window.setX(255);
		  window.setY(27);
		  window.show();
		     
		  configureBox(root);
		
	}
	
	 private void configureBox(HBox root) {
		  StackPane container = new StackPane();
		  //container.setPrefHeight(700);
		  container.setPrefSize(boxBounds.getWidth(), boxBounds.getHeight());
		  container.setStyle("-fx-border-width:1px;-fx-border-style:solid;-fx-border-color:#999999;");
		  
		  // First PANE 
		  ivt.setFitWidth(600);
		  ivt.setFitHeight(60);
		  tpic= new Label();
		  tpic.setGraphic(ivt);
		  
		  welc.setFont(Font.font("Andalus", 25));
		  ushead= new Text("Using Help System");
		  ushead.setFont(Font.font("Andalus", 19));
		  using= new Text("Browse topics in the Contents frame (Contents icon) on the left."
		  + " Click on a topic to have it \ndisplayed. Use the Back and Forward buttons to navigate within the history of viewed topics. ");
		  using.setFont(Font.font("Andalus", 14));
		  
		  dethead= new Text("Details");
		  dethead.setFont(Font.font("Andalus", 19));
		  details= new Text("The help contents are to help with using the system to in the right way without and \ndifficulty. Select the "
		  + "sidebar options to find what you need to know about how to use the system.");
		  details.setFont(Font.font("Andalus", 14));
		  
		  VBox vcont= new VBox(15);
		  vcont.getChildren().addAll(welc, ushead, using, dethead, details);
		  vcont.setPadding(new Insets(5,10,5,10));
		  
		  //Stop[] stops = new Stop[] { new Stop(0, Color.web("#F89C8C")), new Stop(1, Color.web("#BE250A"))};
		  //LinearGradient lg = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		  bottomPane = new VBox(10);
		  bottomPane.getChildren().addAll(tpic, vcont);
		  
		  
		  // 2nd PANE 
		  
		  Label labelsys= new Label();
		  Image isys= new Image(Help.class.getResourceAsStream("/pic/window.png"));
		  ImageView ivsys= new ImageView(isys);
		  labelsys.setGraphic(ivsys);
		  sysHead= new Text("The Overview");
		  sysHead.setFont(Font.font("Andalus", 25));
		  
		  sysdet= new Text("When the Main Page is launched, the first thing you see is a dialog that allows you to\n"
		  +"select change your password.");
		  sysdet.setFont(Font.font("Andalus", 15));
		  
		  topPane = new VBox(15);
		  topPane.getChildren().addAll(sysHead, sysdet, labelsys);
		  topPane.setPadding(new Insets(20,0,10,10));
		  topPane.setStyle("-fx-background-color: linear-gradient(#F5F5F5, #F5EEEE);");
		  
		  //3RD Pane
		  Label lpane= new Label();
		  Image pan= new Image(Help.class.getResourceAsStream("/pic/pane.png"));
		  ImageView ivp= new ImageView(pan);
		  lpane.setGraphic(ivp);
		  Text prog= new Text("The All Programs Window");
		  prog.setFont(Font.font("Andalus", 25));
		  
		  thirdpane= new VBox(15);
		  thirdpane.getChildren().addAll(prog, lpane);
		  topPane.setPadding(new Insets(20,0,10,10));
		  thirdpane.setStyle("-fx-background-color: linear-gradient(#F5F5F5, #F5EEEE);");
		  
		  Text treg= new Text("Registering New");
		  treg.setFont(Font.font("Andalus", 25));
		  Label lreg= new Label("");
		  Image ireg= new Image(Help.class.getResourceAsStream("/pic/treg1.png"));
		  ImageView ivreg= new ImageView(ireg);
		  lreg.setGraphic(ivreg);
		  
		  Text textreg= new Text("Select the category you want to register then click next");
		  textreg.setFont(Font.font("Andalus", 15));
		  Text textreg1= new Text("Making a selection of registering a lecturer.");
		  textreg1.setFont(Font.font("Andalus", 15));
		  
		  Label lreg2= new Label();
		  Image ireg2= new Image(Help.class.getResourceAsStream("/pic/treg2.png"));
		  ImageView ivreg2= new ImageView(ireg2);
		  lreg2.setGraphic(ivreg2);
		  
		  fourthpane= new VBox(15);
		  fourthpane.getChildren().addAll(treg, textreg, lreg, textreg1 ,lreg2);
		  fourthpane.setStyle("-fx-background-color: linear-gradient(#F5F5F5, #EEEEEE);");
		  fourthpane.setPadding(new Insets(20,0,10,10));

		  container.getChildren().addAll(bottomPane,topPane, thirdpane, fourthpane);
		  setAnimation();
		  ScrollPane sc= new ScrollPane(container);
		  root.getChildren().addAll(getActionPane(),sc);
		 }
	 
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	private void setAnimation(){
		  // Initially hiding the Top Pane
		  clipRect = new Rectangle();
		        clipRect.setWidth(boxBounds.getWidth());
		  clipRect.setHeight(0);
		  clipRect.translateXProperty().set(boxBounds.getWidth());
		  topPane.setClip(clipRect);
		  topPane.translateXProperty().set(-boxBounds.getWidth());
		  
		  thirdpane.translateXProperty().set(-boxBounds.getWidth());
		  
		  fourthpane.translateXProperty().set(-boxBounds.getWidth());
		  
		  // Animation for bouncing effect.
		  final Timeline timelineBounce = new Timeline();
		  timelineBounce.setCycleCount(2);
		  timelineBounce.setAutoReverse(true);
		  final KeyValue kv1 = new KeyValue(clipRect.heightProperty(), (boxBounds.getHeight()-15));
		  final KeyValue kv2 = new KeyValue(clipRect.translateYProperty(), 15);
		  final KeyValue kv3 = new KeyValue(topPane.translateYProperty(), -15);
		  final KeyFrame kf1 = new KeyFrame(Duration.millis(100), kv1, kv2, kv3);
		  timelineBounce.getKeyFrames().add(kf1);
		  
		  // Event handler to call bouncing effect after the scroll down is finished.
		  EventHandler onFinished = new EventHandler() {
					@Override
					public void handle(Event event) {
						timelineBounce.play();
					}
		        };
		        
		        timelineDown = new Timeline();
		        timelineUp = new Timeline();
		        timelinethree= new Timeline();
		        timelinefour= new Timeline();
		        // Animation for scroll down.
		  timelineDown.setCycleCount(1);
		  timelineDown.setAutoReverse(true);
		  final KeyValue kvDwn1 = new KeyValue(clipRect.heightProperty(), boxBounds.getWidth());
		  final KeyValue kvDwn2 = new KeyValue(clipRect.translateXProperty(), 0);
		  final KeyValue kvDwn3 = new KeyValue(topPane.translateXProperty(), 0);
		  final KeyValue kvDwn4 = new KeyValue(thirdpane.translateXProperty(), -boxBounds.getHeight());
		  final KeyValue kvDwn5 = new KeyValue(fourthpane.translateXProperty(), -boxBounds.getHeight());
		  final KeyFrame kfDwn = new KeyFrame(Duration.millis(1000), onFinished, kvDwn1, kvDwn2, kvDwn3, kvDwn4, kvDwn5);
		  timelineDown.getKeyFrames().add(kfDwn);
		  
		  // Animation for scroll up.
		  timelineUp.setCycleCount(1); 
		  timelineUp.setAutoReverse(true);
		  final KeyValue kvUp1 = new KeyValue(clipRect.heightProperty(), 0);
		  final KeyValue kvUp2 = new KeyValue(clipRect.translateXProperty(), boxBounds.getHeight());
		  final KeyValue kvUp3 = new KeyValue(topPane.translateXProperty(), -boxBounds.getHeight());
		  final KeyValue kvUp4 = new KeyValue(thirdpane.translateXProperty(), -boxBounds.getHeight());
		  final KeyValue kvUp5 = new KeyValue(fourthpane.translateXProperty(), -boxBounds.getHeight());
		  final KeyFrame kfUp = new KeyFrame(Duration.millis(1000), kvUp1, kvUp2, kvUp3, kvUp4, kvUp5);
		  timelineUp.getKeyFrames().add(kfUp);
		  
		  //Animation for the scrollside
		  timelinethree.setCycleCount(1);
		  timelinethree.setAutoReverse(true);
		  final KeyValue kvside1 = new KeyValue(clipRect.heightProperty(), boxBounds.getWidth());
		  final KeyValue kvside2 = new KeyValue(clipRect.translateXProperty(), 0);
		  final KeyValue kvside3 = new KeyValue(thirdpane.translateXProperty(), 0);
		  final KeyValue kvside4 = new KeyValue(fourthpane.translateXProperty(), -boxBounds.getHeight());
		  final KeyFrame kfside = new KeyFrame(Duration.millis(1000), kvside1, kvside2, kvside3, kvside4);
		  timelinethree.getKeyFrames().add(kfside);
		  
		  timelinefour.setCycleCount(1);
		  timelinefour.setAutoReverse(true);
		  final KeyValue kvfourth1 = new KeyValue(clipRect.heightProperty(), boxBounds.getWidth());
		  final KeyValue kvfourth2 = new KeyValue(clipRect.translateXProperty(), 0);
		  final KeyValue kvfourth3 = new KeyValue(fourthpane.translateXProperty(), 0);
		  final KeyFrame kffourth = new KeyFrame(Duration.millis(1000), kvfourth1, kvfourth2, kvfourth3);
		  timelinefour.getKeyFrames().add(kffourth);
		 }
		 
		 private VBox getActionPane(){
		  VBox hb = new VBox();
		  Text cont= new Text("Contents Frame");
		  cont.setFont(Font.font("Andalus", 19));
		  
		  VBox lcont= new VBox();
		  lcont.getChildren().add(cont);
		  lcont.setPadding(new Insets(20,0,20,0));
		  
		  hb.setAlignment(Pos.TOP_LEFT);
		  hb.setPadding(new Insets(0,10,10,10));
		  hb.setSpacing(10);
		  hb.setPrefHeight(40);
		  Button upBtn = new Button("The Main Page");
		  upBtn.setOnAction(e ->{
			timelineUp.play();
			
		  });
		  Button downBtn = new Button("Second Page");
		  downBtn.setOnAction(e ->{
			  timelineDown.play();
		  });
		  
		  Button thirdBtn = new Button("third Page");
		  thirdBtn.setOnAction(e ->{
			  timelinethree.play();
		  });
		  
		  Button fourthBtn = new Button("fourth Page");
		  fourthBtn.setOnAction(e ->{
			  timelinefour.play();
		  });
		  
		  upBtn.setMinWidth(160);
		  downBtn.setMinWidth(145);
		  thirdBtn.setMinWidth(130);
		  fourthBtn.setMinWidth(115);
		  
		  hb.getChildren().addAll(lcont,upBtn, downBtn, thirdBtn, fourthBtn);
		  hb.setMinWidth(200);
		  return hb;
		 }
	
	public static void main(String[] args) {
		  launch(args);
	}
}
