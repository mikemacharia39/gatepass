package office;

import static jssc.SerialPort.MASK_RXCHAR;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import officeReports.OfficePersonalReport;
import database.DBConnect;
import dialogs.ErrorMessage;
import dialogs.SuccessMessage;
import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx. scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;

public class EditOffice extends Application{

	Stage window;
	Scene scene;
	Label lnametop, lpic;
	Image img= new Image(EditOffice.class.getResourceAsStream("/pic/addp.png"));
	ImageView iv = new ImageView(img);
	Button btnadd, btnclose, btnenrolfinger, btnphoto;
	ComboBox<String> cbCourse, cbTitle;
	TextField txtfname, txtlname, txtphone;
	static TextField txtid;
	TextField txtenrolfinger;
	TextField txtsid;
	Label lfname, llname, lphone, lid, sid, ltitle, 
	lpic2, lphoto, labelfinger;
	Text personal, phone, photo, 
	space1, space9, spaceadm,
	textfinger;

	File file= new File("C:/Users/MIKE/Desktop/Bestest/Hospital/anon.png");
	Image imagepic= new Image(EditOffice.class.getResourceAsStream("/pic/anon.png"));
	ImageView iv2= new ImageView(imagepic);

	Image imgfinger= new Image(EditOffice.class.getResourceAsStream("/pic/searchfg.png"));
	ImageView ivfinger= new ImageView(imgfinger);

	SerialPort arduinoPort = null;
	java.sql.PreparedStatement pstmt;
	FileInputStream fis;
	FileChooser fileChooser= new FileChooser();
	@Override
	public void start(Stage ns) throws Exception {

		window= ns;
		window= new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		//window.setAlwaysOnTop(true);
		
		window.setOnCloseRequest(e -> {
			e.consume();
			disconnectArduino();
			window.close();
			});
		
		lpic = new Label();
		lpic.setGraphic(iv);

		cbTitle= new ComboBox<String>();
		cbTitle.getItems().addAll("Mr","Mrs","Miss","Sir","Dr","Prof","Hon");
		cbTitle.setValue("Mr");
		ltitle= new Label("Title");
		
		lnametop= new Label();
		lnametop.setText("-Your Name Here-");
		lnametop.setId("uppername");

		btnadd= new Button("Update Record");
		btnadd.setOnAction(e -> {
			updateData();
		});
		btnclose= new Button("Close");
		btnphoto= new Button("Remove Photo");
		btnphoto.setOnAction(e ->{

		});

		photo= new Text("Photo");
		phone = new Text("Phone Number");
		personal= new Text("Personal Details");
		space1= new Text(" ");
		space9= new Text(" ");
		spaceadm= new Text(" ");
		textfinger= new Text("Enrol Fingerprint");

		txtsid= new TextField();
		sid= new Label("*Staff Id");
		txtsid.setEditable(false);
		lfname = new Label("*First Name"); 
		llname=new Label("*Last Name"); 
		lphone= new Label("*Phone Number"); 
		lid= new Label("*National Id"); 
		txtfname= new TextField();
		txtlname= new TextField();
		txtphone= new TextField();
		txtid= new TextField();		
		txtid.setEditable(false);
		labelfinger= new Label();
		labelfinger.setGraphic(ivfinger);
		btnenrolfinger= new Button("Capture Prints");
		txtenrolfinger= new TextField();
		txtenrolfinger.setMinWidth(300);

		GridPane laypers= new GridPane();
		laypers.setVgap(10);
		laypers.setHgap(5);
		laypers.setPadding(new Insets(1,15,0,10));
		
		GridPane.setConstraints(personal, 0, 0);
		GridPane.setConstraints(space1, 0, 1);
		
		GridPane.setConstraints(ltitle, 0, 2);
		GridPane.setConstraints(cbTitle, 1, 2);
		GridPane.setConstraints(lfname, 0, 3);
		GridPane.setConstraints(txtfname, 1, 3);
		GridPane.setConstraints(llname, 0, 4);
		GridPane.setConstraints(txtlname, 1, 4);
		GridPane.setConstraints(space1, 0, 5);
		GridPane.setConstraints(phone, 0,6);
		GridPane.setConstraints(space1, 0, 7);
		GridPane.setConstraints(lphone, 0, 8);
		GridPane.setConstraints(txtphone, 1, 8);
		GridPane.setConstraints(space1, 0, 9);
		
		laypers.getChildren().addAll(personal, lfname, txtfname, llname, txtlname, phone, lphone, 
				txtphone, ltitle, cbTitle);

		
		
		GridPane laypers2= new GridPane();
		laypers2.setVgap(10);
		laypers2.setHgap(5);
		laypers2.setPadding(new Insets(1,15,0,10));
		GridPane.setConstraints(spaceadm, 0, 1);

		GridPane.setConstraints(lid, 0, 3);
		GridPane.setConstraints(txtid, 1, 3);
		GridPane.setConstraints(sid, 0, 4);
		GridPane.setConstraints(txtsid, 1, 4);
		laypers2.getChildren().addAll(spaceadm, lid, txtid, sid, txtsid);

		iv2.setEffect(new DropShadow(1, Color.BLACK));
		iv2.setFitHeight(90);
		iv2.setFitWidth(100);
		lpic2= new Label();
		lpic2.setGraphic(iv2);
		lpic2.setAlignment(Pos.CENTER);
		lpic2.setStyle("-fx-background-radius:9; -fx-background-color: linear-gradient(#63A842, #6AB335);");
		lpic2.setCursor(Cursor.HAND);
		lpic2.setMinWidth(110);
		lpic2.setMinHeight(100);

		//prefix	first_name	last_name	staff_id	n_id	phone	thumb_id	picture
		
		HBox layaddpic= new HBox();
		layaddpic.getChildren().add(lpic2);

		HBox laypic= new HBox(10);
		laypic.getChildren().addAll(lpic, lnametop);
		laypic.setAlignment(Pos.CENTER_LEFT);

		HBox laysave= new HBox();
		laysave.getChildren().addAll(btnadd);
		laysave.setAlignment(Pos.CENTER_LEFT);

		HBox layclose= new HBox();
		layclose.getChildren().addAll(btnclose);
		layclose.setAlignment(Pos.CENTER_RIGHT);

		HBox laysc= new HBox(500);
		laysc.getChildren().addAll(laysave, layclose);
		laysc.setPadding(new Insets(5, 0, 10, 10));

		VBox laytop= new VBox(10);
		laytop.getChildren().addAll(laypic, laysc);

		HBox laybtnpic= new HBox();
		laybtnpic.getChildren().add(btnphoto);
		laybtnpic.setPadding(new Insets(10,0,0,0));
		laybtnpic.setAlignment(Pos.CENTER);

		VBox laypicture= new VBox(1);
		laypicture.getChildren().addAll(photo,space9,layaddpic, laybtnpic);

		HBox laypicdob= new HBox(1);
		laypicdob.getChildren().addAll(laypicture, laypers2);

		//my fingers are here
		HBox tfinger= new HBox();
		tfinger.getChildren().add(textfinger);
		tfinger.setPadding(new Insets(0,0,10,0));

		VBox layfinger= new VBox(10);
		layfinger.getChildren().addAll(txtenrolfinger, btnenrolfinger);
		layfinger.setAlignment(Pos.CENTER_RIGHT);

		HBox layfdet= new HBox(15);
		layfdet.getChildren().addAll(labelfinger, layfinger);

		VBox laydet2= new VBox(10);
		laydet2.getChildren().addAll(tfinger, layfdet);
		//they end here

		VBox picfinger= new VBox(13);
		picfinger.getChildren().addAll(laypicdob, laydet2);

		HBox layside1_2= new HBox(10);
		layside1_2.getChildren().addAll(laypers, picfinger);

		BorderPane layall= new BorderPane();
		layall.setPadding(new Insets(10, 10, 0, 10));
		layall.setTop(laytop);
		layall.setCenter(layside1_2);
		layall.setStyle("-fx-background-color: linear-gradient(#E4E9A9, #9CD777);");
		scene= new Scene(layall, 700, 500);
		scene.getStylesheets().add(EditOffice.class.getResource("newPeople.css").toExternalForm());
		window.setScene(scene);
		window.setTitle("Add New Office Staff");
		window.setResizable(false);
		loadData();
		window.show();
		initialize("COM25");
		System.out.println("Application Started");

		//Actions are here
		lnametop.textProperty().bind(new StringBinding(){
			{
				bind(txtfname.textProperty());
			}
			@Override
			protected String computeValue() {

				String showmename= txtfname.getText();
				return showmename;
			}

		});
		//for exiting the system
		btnclose.setOnAction(e ->{
			disconnectArduino();
			window.close();
		});
		lpic2.setOnMouseClicked(e ->{
			file = fileChooser.showOpenDialog(window);
			fileChooser.setTitle("Select Photo");
			if (file != null) {
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("All Images", "*.*"),
						new FileChooser.ExtensionFilter("JPG", "*.jpg"),
						new FileChooser.ExtensionFilter("GIF", "*.gif"),
						new FileChooser.ExtensionFilter("BMP", "*.bmp"),
						new FileChooser.ExtensionFilter("PNG", "*.png")
						);
				try {
					BufferedImage bufferedImage = ImageIO.read(file);
					imagepic = SwingFXUtils.toFXImage(bufferedImage, null);
					iv2.setImage(imagepic);
					iv2.setFitHeight(90);
					iv2.setFitWidth(100);
					lpic2.setGraphic(iv2);
				} catch (IOException ex) {
					Logger.getLogger(EditOffice.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});

	}

	public void loadData(){
		ResultSet rs;
		String search= OfficeRecords.lstudname.getText();
		//prefix	first_name	last_name	staff_id	n_id	phone	thumb_id	picture
		String query="SELECT prefix, first_name, last_name, staff_id, n_id, phone, thumb_id, picture FROM office where n_id='"+search+"'";
		
		DBConnect.connect();
	      try { //connect

	          Statement stmt = (Statement) DBConnect.conn.createStatement();
	          rs = stmt.executeQuery(query);

	          //    rs = DBManager.stmt.executeQuery(query);
	          if (rs.next())//record exists
	          {
	        	  byte[] b= null;
	    	      try {
	    	    	  	cbTitle.setValue(rs.getString(1));
	    	    	   txtsid.setText(rs.getString(4));
	    	           txtfname.setText(rs.getString(2));
	    	           txtlname.setText(rs.getString(3));    
	    	           txtid.setText(rs.getString(5));
	    	           txtphone.setText(rs.getString(6));
	    	           txtenrolfinger.setText(rs.getString(7));
	    	           b=rs.getBytes(8);
	    	          ByteArrayInputStream bis = new ByteArrayInputStream(b);
	    	         BufferedImage read = ImageIO.read(bis);
	    	         imagepic = SwingFXUtils.toFXImage(read, null);
	    	         iv2.setImage(imagepic);
					 iv2.setFitHeight(90);
					 iv2.setFitWidth(100);
					 lpic2.setGraphic(iv2);
	    	        	
	    	      } catch (SQLException e) {
	    	    	  ErrorMessage.display("SQL Error", e.getMessage()+ " \nError found");
	    	          e.printStackTrace();
	    	          
	    	      }catch (NullPointerException e) {
	    	    	  //ErrorMessage.display("Null Error ","Some Details not Found");
	    	          e.printStackTrace();
	    	          
	    	      }  catch (Exception e) {
	    	          e.printStackTrace();
	    	          ErrorMessage.display("Error", e.getMessage()+ " error found");
	    	      }  
	        	  } else {
	              ErrorMessage.display("Error","Record does not exist");
	          }
	          DBConnect.closeConnection();
	      } catch (Exception e) {
	          ErrorMessage.display("Database Exception",e.getMessage()+"");
	      }
	}
	
public void updateData(){
		
		if(txtid.getText().equals("") || txtid.getText().equals("") ||
				txtfname.getText().equals("")||txtlname.getText().equals("")||txtphone.getText().equals(""))
				{	
					ErrorMessage.display("Blank Spaces", "Enter the available field provided");
				}
				else{
					String fileUpdate="UPDATE office SET n_id='" +txtid.getText()
							+ "', prefix ='" +cbTitle.getValue()
							+ "', first_name ='" +txtfname.getText()
		           			+ "', last_name ='" +txtlname.getText()
		           			+ "', phone ='" +txtphone.getText()
		           			+ "', thumb_id ='" +txtenrolfinger.getText() +"'"
		                    + "where n_id='" + txtid.getText() + "'";
		        //connect to database
		  DBConnect.connect();
		  try {
			  DBConnect.stmt.execute(fileUpdate);
		      SuccessMessage.display("Update", "Record Updated Successfully");
		      OfficeRecords.tableView.setItems(new OfficeRecords().getAtt());
		      clear();
		      DBConnect.closeConnection();
		  } catch (SQLException x) {
			     ErrorMessage.display("UpdateError", x.getMessage()+" \nerror occured");
			  }catch (Exception ex) {
		     ErrorMessage.display("Error", ex.getMessage()+" \nerror occured");
		  }
		
	}
	}
	
	private boolean initialize(String port) {

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
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | 
					SerialPort.FLOWCONTROL_RTSCTS_OUT);

			serialPort.addEventListener((SerialPortEvent serialPortEvent) -> {

				if(serialPortEvent.isRXCHAR() && serialPortEvent.getEventValue() > 0){
					try {

						//BufferedReader reader = new BufferedReader(new InputStreamReader(serialPort.readString()));

						//istream = new SerialInputStream(serialPort);
						//istream.setTimeout(10000);
						//BufferedReader reader = new BufferedReader(new InputStreamReader(istream));

						//serialPortEvent.getEventValue()
						String sb = serialPort.readString(4);

						//sb= sb.replace("@", "").replace("#", "");
						System.out.println(sb);
						if(sb.contains("NM"))
						{

							txtenrolfinger.setText("No Match Found");
						}
						else if(sb.contains("WF"))
						{
							txtenrolfinger.setText("Waiting to enroll finger");
						}
						else if(sb.contains("NW"))
						{
							txtenrolfinger.setText("Expected New ID #");
						}
						else if(sb.contains("UE"))
						{
							txtenrolfinger.setText("Connection or Reading Print Error");
						}
						else if(sb.contains("RF"))
						{
							txtenrolfinger.setText("Remove Finger");
						}
						else if(sb.contains("PM"))
						{
							txtenrolfinger.setText("Prints Matched");
						}
						else if(sb.contains("PF"))
						{
							txtenrolfinger.setText("Place the same finger again");
						}
						else if(sb.contains("IT"))
						{
							txtenrolfinger.setText("Image Taken");
						}
						else if(sb.contains("IE"))
						{
							txtenrolfinger.setText("Imaging Error");
						}
						else if(sb.contains("IS"))
						{
							txtenrolfinger.setText("Finger ID id: ");
						}

						else{
							txtenrolfinger.setText(sb);  
							
						}
						//cleanIt();

					}
					catch (SerialPortException exception)
					{
						System.err.println(exception.toString());
					}
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			arduinoPort = serialPort;
			success = true;
		} catch (SerialPortException ex) {
			Logger.getLogger(EditOffice.class.getName())
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

	public void clear()
	{
		txtfname.setText("");
		txtlname.setText(""); 
		//lphoto.setGraphic(null);
		txtsid.setText("");
		txtid.setText("");
		txtphone.setText(""); 
		txtenrolfinger.clear();
	}


	public static void main(String[] args)
	{
		launch(args);
	}
}
