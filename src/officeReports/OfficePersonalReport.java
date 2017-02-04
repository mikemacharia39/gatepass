package officeReports;

import static jssc.SerialPort.MASK_RXCHAR;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.mysql.jdbc.Statement;

import database.DBConnect;
import dialogs.ErrorMessage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;

public class OfficePersonalReport extends Application{

	private final String pattern = "dd/MM/yyyy";
	Label lfname, ljobid, llname, laddress, lid, ldob, lphone, lpic, lphoto, ltitle, ltimes, lnotexist;
	TextField txtfname, txtlname;
	static TextField txtschoolid;
	TextField txtid;
	TextField txtphone;
	DatePicker txtdob;
	TextArea txtaddress;
	Button btnclose, btnsearch, btnclear;
	Stage addemp;
	Scene scene1;
	
	ImageView iv;
	Image imagepic, imgfing;
	
	//Connection Variables
		SerialPort arduinoPort = null;
		 ObservableList<String> portList;
		 public static BufferedReader input;
			public static OutputStream output;
			
			final static int NEW_LINE_ASCII = 10;
			public static final int TIME_OUT = 2000;
			
			 /** Default bits per second for COM port. */
		 
			public static final int DATA_RATE = 9600;
			    String logText = "";
			    String usern;
				static String lev;
				
			    public boolean initialize(String port) {
			    
			    	System.out.println("connectArduino");
			    	//SuccessMessage.display("Device Status", "Device Connected!!!");
			        
			        boolean success = false;
			        SerialPort serialPort = new SerialPort(port);
			        try {
			            serialPort.openPort();
			            serialPort.setParams(
			                    SerialPort.BAUDRATE_9600,
			                    SerialPort.DATABITS_8,
			                    SerialPort.STOPBITS_1,
			                    SerialPort.PARITY_NONE);
			            serialPort.setEventsMask(MASK_RXCHAR);
			            serialPort.addEventListener((SerialPortEvent serialPortEvent) -> {
			                if(serialPortEvent.isRXCHAR()){
			                    try {
			                    	
			                    	String sb;
			                    	sb = serialPort.readString(4);
									//sb= sb.replace("@", "").replace("#", "");
									System.out.println(sb);
									
									if(sb.contains("NM"))
									{
										txtschoolid.setText("No Match Found");
										lnotexist.setVisible(true);
										clear();
									}
									
									else if(sb.contains("UE"))
									{
										txtschoolid.setText("Connection or Reading Print Error");
									}
									
									else if(sb.contains("PM"))
									{
										txtschoolid.setText("Prints Matched");
									}
									
									else if(sb.contains("IE"))
									{
										txtschoolid.setText("Imaging Error");
									}
									
									
									else{
										Platform.runLater(() -> {
				                        	txtschoolid.setText(" "+sb);
				                           loadData();
				                        });
					        	 
									}
			                    	
			                    } catch (SerialPortException ex) {
			                        Logger.getLogger(OfficePersonalReport.class.getName())
			                                .log(Level.SEVERE, null, ex);
			                    }
			                    
			                }
			            });
			            
			            arduinoPort = serialPort;
			            success = true;
			        } catch (SerialPortException ex) {
			            Logger.getLogger(OfficePersonalReport.class.getName())
			                    .log(Level.SEVERE, null, ex);
			            System.out.println("SerialPortException: " + ex.toString());
			            ErrorMessage.display("Device Status", "No Device on Connected!!!");
			        }

			        return success;
			    	 }
			    
			    
			    public void disconnectArduino(){
			        
			        System.out.println("disconnectArduino()");
			        if(arduinoPort != null){
			            try {
			                arduinoPort.removeEventListener();
			                
			                if(arduinoPort.isOpened()){
			                    arduinoPort.closePort();
			                }
			                
			            } catch (SerialPortException ex) {
			                Logger.getLogger(OfficePersonalReport.class.getName())
			                        .log(Level.SEVERE, null, ex);
			            }
			        }
			    }

			    @Override
			    public void stop() throws Exception {
			        disconnectArduino();
			        super.stop();
			    }
	
	public void setEditingStudent()
	{
		addemp= new Stage();
		addemp.setOnCloseRequest(e -> {
			e.consume();
			disconnectArduino();
			addemp.close();
			});
		addemp.setTitle("Auto search Office Entry Records");
		addemp.setResizable(false);
		addemp.initModality(Modality.APPLICATION_MODAL);
		addemp.getIcons().add(new Image("/pic/slogo.png"));
		ltitle= new Label("Office Entry Records");
		ltitle.setStyle("-fx-font-weight:bold; -fx-font-size:13px; -fx-text-fill: blue;");
		ljobid= new Label("University Id");
		
		imgfing=new Image(OfficePersonalReport.class.getResourceAsStream("/pic/find.png"));
		ImageView ivfind= new ImageView(imgfing);
		
		
		
		btnsearch= new Button("",ivfind);
		btnsearch.setPrefHeight(10);
		btnsearch.setStyle("-fx-background-radius:10;");
		btnsearch.setOnAction(e -> {
			loadData();
		});
		
		txtschoolid= new TextField();
		txtschoolid.setMinHeight(30);
		txtschoolid.setStyle("-fx-background-radius:10;");
		txtschoolid.setPromptText("Search by Thumb or ids");
		lfname= new Label("First Name");
		txtfname= new TextField();
		llname= new Label("Last Name");
		txtlname= new TextField();
		lid= new Label("National Id");
		txtid= new TextField();
		ldob= new Label("DOB");
		txtdob= new DatePicker();
		
		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = 
                DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        txtdob.setConverter(converter);
        txtdob.setPromptText(pattern.toLowerCase());
		
		lphone= new Label("Phone Number");
		txtphone= new TextField();
		laddress= new Label("Entry Times");
		txtaddress= new TextArea();
		txtaddress.setPrefSize(240, 150);
		txtaddress.setEditable(false);
		
		ltimes= new Label("Count: ");
		Image img1= new Image(OfficePersonalReport.class.getResourceAsStream("/pic/cross.png"));
		ImageView imagvw= new ImageView(img1);
		lnotexist= new Label("",imagvw);
		lnotexist.setText("\n\n\n\n\n\nN/A");
		lnotexist.setFont(Font.font("Cooper Black", 15));
		
		
		iv= new ImageView();
		lpic= new Label("Picture");
		lphoto= new Label();
		
		btnclear= new Button("Clear");
		btnclear.setOnAction(e ->{
			txtschoolid.setText("");
			txtfname.setText("");
			txtlname.setText("");
			txtid.setText("");
			txtphone.setText("");
			txtaddress.setText("");
			lphoto.setText("");
			ltimes.setText("Count");
		});
		
		btnclose= new Button("Close");
		btnclose.setOnAction(e ->{
			disconnectArduino();
			addemp.close();
		});
		
		GridPane lay1= new GridPane();
		lay1.setVgap(10);
		lay1.setHgap(5);
		GridPane.setConstraints(ljobid, 0, 0);
		GridPane.setConstraints(txtschoolid, 1, 0);
		GridPane.setConstraints(btnsearch, 2, 0);
		GridPane.setConstraints(lfname, 0, 1);
		GridPane.setConstraints(txtfname, 1, 1);
		GridPane.setConstraints(llname, 0, 2);
		GridPane.setConstraints(txtlname, 1, 2);
		GridPane.setConstraints(lid, 0, 3);
		GridPane.setConstraints(txtid, 1, 3);
		GridPane.setConstraints(ldob, 0, 4);
		GridPane.setConstraints(txtdob, 1, 4);
		
		GridPane.setConstraints(lphone, 0, 5);
		GridPane.setConstraints(txtphone, 1, 5);
		
		lay1.getChildren().addAll(ljobid, txtschoolid, lfname, txtfname, llname, txtlname, lid, 
				txtid, ldob, txtdob ,lphone, txtphone, btnsearch);
		
		HBox layaddr= new HBox(20);
		layaddr.getChildren().addAll(laddress,txtaddress);
		layaddr.setPadding(new Insets(10,0,10,0));
		
		HBox laybtn= new HBox(10);
		laybtn.setAlignment(Pos.CENTER_RIGHT);
		laybtn.getChildren().addAll(btnclear, btnclose);
		
		VBox layleft= new VBox(10);
		layleft.getChildren().addAll(lay1, layaddr, ltimes);
		layleft.setPadding(new Insets(10,0,10,0));
		
		VBox lay6= new VBox(10);
		lay6.getChildren().add(lpic);
		VBox lay7= new VBox();
		lay7.getChildren().addAll(lphoto);
		lnotexist.setVisible(false);
		lay7.setBorder(new Border(new BorderStroke(Color.LIGHTGREY, null, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		lay7.setStyle("-fx-background-color:#dddddd; -fx-background-radius:1;");
		lay7.setPrefSize(200, 150);
		
		VBox layer= new VBox(1);
		layer.getChildren().addAll(lnotexist);
		layer.setAlignment(Pos.CENTER);
		layer.setPadding(new Insets(10,0,0,0));
		
		VBox lay8= new VBox(10);
		lay8.getChildren().addAll(lay6,lay7, layer);
		
		BorderPane layall= new BorderPane();
		layall.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672)");
		layall.setPadding(new Insets(10,20,10,20));
		layall.setTop(ltitle);
		layall.setLeft(layleft);
		layall.setRight(lay8);
		layall.setBottom(laybtn);
		scene1= new Scene(layall);
		addemp.setScene(scene1);
		scene1.getStylesheets().add(OfficePersonalReport.class.getResource("reports.css").toExternalForm());
	}
	@Override
	public void start(Stage ae) throws Exception {
		addemp=ae;
		setEditingStudent();
		addemp.show();
		initialize("COM25");
		System.out.println("Application Started");
	}
	
	public void loadData(){
		ResultSet rs;
		ltimes.setText("");
		String search= txtschoolid.getText();
	String query="SELECT * FROM office WHERE thumb_id = '"+txtschoolid.getText()+"' OR staff_id = '"+search+"' OR n_id ='"+search+"' " ;
		
		
		DBConnect.connect();
	      try { //connect

	          Statement stmt = (Statement) DBConnect.conn.createStatement();
	          rs = stmt.executeQuery(query);

	          //    rs = DBConnect.stmt.executeQuery(query);
	          if (rs.next())//record exists
	          {
	        	  byte[] b= null;
	    	      try {
	    	    	   txtschoolid.setText(rs.getString("staff_id"));
	    	           txtfname.setText(rs.getString("first_name"));
	    	           txtlname.setText(rs.getString("last_name"));    
	    	           txtid.setText(rs.getString("n_id"));
	    	           txtdob.setValue(LocalDate.now());
	    	           txtphone.setText(rs.getString("phone"));
	    	           txtaddress.setText("");
	    	           lnotexist.setVisible(false);
	    	           b=rs.getBytes("picture");
	    	           
	    	          ByteArrayInputStream bis = new ByteArrayInputStream(b);
	    	         BufferedImage read = ImageIO.read(bis);
	    	         imagepic = SwingFXUtils.toFXImage(read, null);
	    	         iv.setImage(imagepic);
					 iv.setFitHeight(150);
					 iv.setFitWidth(205);
					 lphoto.setGraphic(iv);
					 lphoto.setAlignment(Pos.CENTER);

	    	      } catch (SQLException e) {
	    	    	  //ErrorMessage.display("SQL Error", e.getMessage()+ " \nError found");
	    	          e.printStackTrace();
	    	          
	    	      }  catch (Exception e) {
	    	          e.printStackTrace();
	    	          //ErrorMessage.display("Error", e.getMessage()+ " error found");
	    	      } 
	    	      
	    	      try 
					{
	    	    	  lnotexist.setVisible(false);
			    	  String qcount= "SELECT staff_id, time_in ,date, COUNT(date) FROM  officeentry where staff_id='"+search+"' OR thumb_id='"+search+"' GROUP BY date";
			    	  DBConnect.connect();
			    	  ResultSet rec = DBConnect.stmt.executeQuery(qcount);
			    	  String a=(" Date\t\t"+"Time\t\t"+" Count\n").toUpperCase(); 
			    	  txtaddress.setText(txtaddress.getText()+""+a);
			      while(rec.next())
			      {
			    	  String times = rec.getString("time_in");
			    	  	String date = rec.getString("DATE");
	   					int count= rec.getInt("COUNT(DATE)");
	   					String all=date+"\t"+ times +"\t\t\t"+count+"\n";
	   					txtaddress.setText(txtaddress.getText()+""+all);
	    	    	  }
			      DBConnect.closeConnection();
					} catch (SQLException e) 
					{
						e.printStackTrace();
					}
					
					try 
					{
					  String qcount= "SELECT staff_id, date, COUNT(date) FROM  officeentry "
					  		+ "where staff_id='"+search+"' OR thumb_id='"+search  +"'";
			    	  DBConnect.connect();
			    	  ResultSet rec = DBConnect.stmt.executeQuery(qcount);
			    	  //clear();
			      while(rec.next())
			      {
	        		  lnotexist.setVisible(false);
			    	  int times= rec.getInt("COUNT(date)");
			    	  ltimes.setText("Access Count: "+ltimes.getText()+""+times+ " Times");
			    	  ltimes.setFont(Font.font("Calibri", 16));
			      }
			      DBConnect.closeConnection();
					} catch (SQLException e) 
					{
						ErrorMessage.display("SQL Exception",e.getMessage()+"");
						e.printStackTrace();
					}
	    	      
	        	  } else {
	              //ErrorMessage.display("Error","Record does not exist");
	        		  lnotexist.setVisible(true);
	        		  clear();
	          }
	          DBConnect.closeConnection();
	      } catch (Exception e) {
	    	  ErrorMessage.display("Database Exception",e.getMessage()+"");
	      }
	}
	public static void main(String[] args)
	{
		Locale.setDefault(Locale.US);
		launch(args);
	}
	public void clear()
	{
		txtfname.clear();
		txtlname.clear(); 
		txtschoolid.clear();
		//lphoto.setGraphic(null);
		txtaddress.clear();
		txtid.clear();
		txtphone.clear();
	}
}
