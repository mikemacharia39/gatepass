package editEntiry;

import database.DBConnect;
import dialogs.ErrorMessage;
import dialogs.SuccessMessage;
import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import reports.LecturersReports;

import javax.imageio.ImageIO;
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

import static jssc.SerialPort.MASK_RXCHAR;

public class EditLec extends Application {

    public static TextField txtid;
    //java.sql.Connection conn = null;
    public static Statement stmt;
    Stage window;
    Scene scene;
    Label lnametop, lpic;
    Image img = new Image(EditLec.class.getResourceAsStream("/pic/addp.png"));
    ImageView iv = new ImageView(img);
    Button btnadd, btnclose, btnenrolfinger, btnphoto;
    TextField txtfname, txtlname, txtadm, txtphone;
    TextField txtaddress;
    TextField txttown;
    TextField txtenrolfinger;
    Label lfname, llname, ladm, lphone, lid, laddress, ltown, ltitle, lphoto,
            lpic2, labelfinger, ldepart;
    Text personal, address, occupation, photo,
            space1, space2, space3, space4, space5, space6, space7, space8, space9, spaceadm,
            textfinger;
    ComboBox<String> cmbTitle, cmbdepart;
    FileChooser fileChooser = new FileChooser();
    File file = new File("C:/Users/MIKE/Desktop/Bestest/Hospital/anon.png");
    Image imagepic = new Image(EditLec.class.getResourceAsStream("/pic/anon.png"));
    ImageView iv2 = new ImageView(imagepic);
    Image imgfinger = new Image(EditLec.class.getResourceAsStream("/pic/searchfg.png"));
    ImageView ivfinger = new ImageView(imgfinger);
    //whats new
    SerialPort arduinoPort = null;
    java.sql.PreparedStatement pstmt;
    FileInputStream fis;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage ns) throws Exception {

        window = ns;
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        //window.setAlwaysOnTop(true);
        window.getIcons().add(new Image("/pic/slogo.png"));
        lpic = new Label();
        lpic.setGraphic(iv);

        lnametop = new Label();
        lnametop.setText("-Your Name Here-");
        lnametop.setId("uppername");

        btnadd = new Button("Update Record");
        btnadd.setOnAction(e -> {
            updateData();
        });
        btnclose = new Button("Close");
        btnphoto = new Button("Remove Photo");

        address = new Text("Address");
        photo = new Text("Photo");
        occupation = new Text("Occupational Details");
        personal = new Text("Personal Details");
        space1 = new Text(" ");
        space9 = new Text(" ");
        spaceadm = new Text(" ");
        textfinger = new Text("Enrol Fingerprint");

        ltitle = new Label("Prefix");
        lfname = new Label("*First Name");
        llname = new Label("*Last Name");
        ladm = new Label("*Staff Id");
        lphone = new Label("*Phone Number");
        lid = new Label("*National Id");
        laddress = new Label("Address");
        ltown = new Label("Town/City");
        ldepart = new Label("Department");
        txtfname = new TextField();
        txtlname = new TextField();
        txtadm = new TextField();
        txtphone = new TextField();
        txtid = new TextField();
        txtaddress = new TextField();
        txttown = new TextField();

        labelfinger = new Label();
        labelfinger.setGraphic(ivfinger);
        btnenrolfinger = new Button("Capture Prints");
        txtenrolfinger = new TextField();
        txtenrolfinger.setMinWidth(270);
        txtenrolfinger.setEditable(false);
        txtenrolfinger.setStyle("-fx-background-color:#9CD777;");
        txtenrolfinger.setPromptText("-capture information-");

        cmbTitle = new ComboBox<String>();
        cmbTitle.getItems().addAll("Mr", "Mrs", "Miss", "Sir", "Dr", "Prof", "Hon");
        cmbTitle.setValue("Mr");

        cmbdepart = new ComboBox<String>();
        cmbdepart.getItems().addAll("Computer Science", "Acturial Science", "I.T.", "Food Science", "Engineering", "Agriculture");
        cmbdepart.setValue("I.T.");
        cmbdepart.setEditable(true);

        GridPane laypers = new GridPane();
        laypers.setVgap(7);
        laypers.setHgap(5);
        laypers.setPadding(new Insets(1, 15, 0, 10));
        GridPane.setConstraints(personal, 0, 0);
        GridPane.setConstraints(space1, 0, 1);
        GridPane.setConstraints(ltitle, 0, 2);
        GridPane.setConstraints(cmbTitle, 1, 2);
        GridPane.setConstraints(lfname, 0, 3);
        GridPane.setConstraints(txtfname, 1, 3);
        GridPane.setConstraints(llname, 0, 4);
        GridPane.setConstraints(txtlname, 1, 4);
        GridPane.setConstraints(lphone, 0, 5);
        GridPane.setConstraints(txtphone, 1, 5);
        GridPane.setConstraints(space1, 0, 6);
        GridPane.setConstraints(occupation, 0, 7);
        GridPane.setConstraints(space1, 0, 8);
        GridPane.setConstraints(ldepart, 0, 9);
        GridPane.setConstraints(cmbdepart, 1, 9);
        GridPane.setConstraints(address, 0, 10);
        GridPane.setConstraints(space1, 0, 11);
        GridPane.setConstraints(laddress, 0, 12);
        GridPane.setConstraints(txtaddress, 1, 12);
        GridPane.setConstraints(ltown, 0, 13);
        GridPane.setConstraints(txttown, 1, 13);
        laypers.getChildren().addAll(personal, ltitle, cmbTitle, lfname, txtfname, llname, txtlname, occupation, lphone, ldepart, cmbdepart,
                txtphone, address, laddress, txtaddress, ltown, txttown);

        GridPane laypers2 = new GridPane();
        laypers2.setVgap(10);
        laypers2.setHgap(5);
        laypers2.setPadding(new Insets(1, 15, 0, 10));
        GridPane.setConstraints(spaceadm, 0, 1);
        GridPane.setConstraints(ladm, 0, 2);
        GridPane.setConstraints(txtadm, 1, 2);
        GridPane.setConstraints(lid, 0, 3);
        GridPane.setConstraints(txtid, 1, 3);
        laypers2.getChildren().addAll(spaceadm, ladm, txtadm, lid, txtid);

        iv2.setEffect(new DropShadow(1, Color.BLACK));
        iv2.setFitHeight(90);
        iv2.setFitWidth(100);
        lpic2 = new Label();
        lpic2.setGraphic(iv2);
        lpic2.setAlignment(Pos.CENTER);
        lpic2.setStyle("-fx-background-radius:8; -fx-background-color: linear-gradient(#63A842, #6AB335);");
        lpic2.setCursor(Cursor.HAND);
        lpic2.setMinWidth(110);
        lpic2.setMinHeight(100);

        txtadm.setEditable(false);
        lpic2.setDisable(true);
        txtid.setEditable(false);

        Tooltip tp = new Tooltip("Click to Browse and Add Photo...");
        Tooltip.install(lpic2, tp);

        HBox layaddpic = new HBox();
        layaddpic.getChildren().add(lpic2);

        HBox laypic = new HBox(10);
        laypic.getChildren().addAll(lpic, lnametop);
        laypic.setAlignment(Pos.CENTER_LEFT);

        HBox laysave = new HBox();
        laysave.getChildren().addAll(btnadd);
        laysave.setAlignment(Pos.CENTER_LEFT);

        HBox layclose = new HBox();
        layclose.getChildren().addAll(btnclose);
        layclose.setAlignment(Pos.CENTER_RIGHT);

        HBox laysc = new HBox(500);
        laysc.getChildren().addAll(laysave, layclose);
        laysc.setPadding(new Insets(5, 0, 10, 10));

        VBox laytop = new VBox(10);
        laytop.getChildren().addAll(laypic, laysc);

        HBox laybtnpic = new HBox();
        laybtnpic.getChildren().add(btnphoto);
        laybtnpic.setPadding(new Insets(10, 0, 0, 0));
        laybtnpic.setAlignment(Pos.CENTER);

        VBox laypicture = new VBox(1);
        laypicture.getChildren().addAll(photo, space9, layaddpic, laybtnpic);

        HBox laypicdob = new HBox(1);
        laypicdob.getChildren().addAll(laypicture, laypers2);

        //my fingers are here
        HBox tfinger = new HBox();
        tfinger.getChildren().add(textfinger);
        tfinger.setPadding(new Insets(0, 0, 10, 0));

        VBox layfinger = new VBox(10);
        layfinger.getChildren().addAll(txtenrolfinger, btnenrolfinger);
        layfinger.setAlignment(Pos.CENTER_RIGHT);

        HBox layfdet = new HBox(15);
        layfdet.getChildren().addAll(labelfinger, layfinger);

        VBox laydet2 = new VBox(10);
        laydet2.getChildren().addAll(tfinger, layfdet);
        //they end here

        VBox picfinger = new VBox(13);
        picfinger.getChildren().addAll(laypicdob, laydet2);

        HBox layside1_2 = new HBox(10);
        layside1_2.getChildren().addAll(laypers, picfinger);

        BorderPane layall = new BorderPane();
        layall.setPadding(new Insets(10, 10, 0, 10));
        layall.setTop(laytop);
        layall.setCenter(layside1_2);
        layall.setStyle("-fx-background-color: linear-gradient(#E4E9A9, #9CD777);");

        scene = new Scene(layall, 700, 500);
        scene.getStylesheets().add(EditLec.class.getResource("newPeople.css").toExternalForm());
        window.setScene(scene);
        window.setResizable(false);
        window.setTitle("Edit Lecturer Lecturer");
        loadData();
        window.show();
        initialize("COM25");
        System.out.println("Application Started");
        //Actions are here
        lnametop.textProperty().bind(new StringBinding() {
            {
                bind(txtfname.textProperty());
            }

            @Override
            protected String computeValue() {

                String showmename = txtfname.getText().toUpperCase();
                return showmename;
            }

        });
        //for exiting the system
        btnclose.setOnAction(e -> {
            window.close();
        });
        lpic2.setOnMouseClicked(e -> {
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
                    // file=fileChooser.get
                    BufferedImage bufferedImage = ImageIO.read(file);
                    imagepic = SwingFXUtils.toFXImage(bufferedImage, null);
                    iv2.setImage(imagepic);
                    iv2.setFitHeight(90);
                    iv2.setFitWidth(100);
                    lpic2.setGraphic(iv2);
                } catch (IOException ex) {
                    Logger.getLogger(EditLec.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnphoto.setOnAction(e -> {

        });
        btnenrolfinger.setOnAction(e -> {
            try {
                arduinoPort.writeByte((byte) -1);
            } catch (Exception e1) {
                ErrorMessage.display("Device Error", "Problem during capture, device is unavailable");
                e1.printStackTrace();
            }
        });

    }

    public void loadData() {
        ResultSet rs;
        String search = LecturersReports.lstudname.getText();
        String query = "SELECT prefix, first_name, last_name, s_id, n_id, department, phone, address, town, thumb_id, lecturer, picture"
                + " FROM lecturers where n_id='" + search + "'";

        DBConnect.connect();
        try { //connect

            Statement stmt = DBConnect.conn.createStatement();
            rs = stmt.executeQuery(query);

            //    rs = DBManager.stmt.executeQuery(query);
            if (rs.next())//record exists
            {
                byte[] b = null;
                try {
                    cmbTitle.setValue(rs.getString(1));
                    txtadm.setText(rs.getString(4));
                    txtfname.setText(rs.getString(2));
                    txtlname.setText(rs.getString(3));
                    txtid.setText(rs.getString(5));
                    txtaddress.setText(rs.getString(8));
                    txtphone.setText(rs.getString(7));
                    cmbdepart.setValue(rs.getString(6));
                    txttown.setText(rs.getString(9));
                    txtenrolfinger.setText(rs.getString(10));
                    b = rs.getBytes(12);

                    ByteArrayInputStream bis = new ByteArrayInputStream(b);
                    BufferedImage read = ImageIO.read(bis);
                    imagepic = SwingFXUtils.toFXImage(read, null);
                    iv2.setImage(imagepic);
                    iv2.setFitHeight(90);
                    iv2.setFitWidth(100);
                    lpic2.setGraphic(iv2);

                } catch (SQLException e) {
                    ErrorMessage.display("SQL Error", e.getMessage() + " \nError found");
                    e.printStackTrace();

                } catch (NullPointerException e) {
                    //ErrorMessage.display("Null Error ","Some Details not Found");
                    e.printStackTrace();

                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorMessage.display("Error", e.getMessage() + " error found");
                }
            } else {
                ErrorMessage.display("Error", "Record does not exist");
            }
            DBConnect.closeConnection();
        } catch (Exception e) {
            ErrorMessage.display("Database Exception", e.getMessage());
        }
    }

    public void updateData() {

        if (txtid.getText().equals("") || txtid.getText().equals("") ||
                txtfname.getText().equals("") || txtlname.getText().equals("") || txtphone.getText().equals("")) {
            ErrorMessage.display("Blank Spaces", "Enter the available field provided");
        } else {

            String fileUpdate = "UPDATE lecturers SET n_id='" + txtid.getText()
                    + "', prefix ='" + cmbTitle.getValue()
                    + "', first_name ='" + txtfname.getText()
                    + "', last_name ='" + txtlname.getText()
                    + "', town ='" + txttown.getText()
                    + "', phone ='" + txtphone.getText()
                    + "', address ='" + txtaddress.getText()
                    + "', thumb_id ='" + txtenrolfinger.getText()
                    + "', department ='" + cmbdepart.getValue() + "'"
                    + "where n_id='" + txtid.getText() + "'";
            //connect to database
            DBConnect.connect();
            try {
                DBConnect.stmt.execute(fileUpdate);
                SuccessMessage.display("Update", "Record Updated Successfully");
                LecturersReports.tableView.setItems(new LecturersReports().getAtt());
                clear();
                DBConnect.closeConnection();
            } catch (SQLException x) {
                ErrorMessage.display("UpdateError", x.getMessage() + " \nerror occured");
            } catch (Exception ex) {
                ErrorMessage.display("Error", ex.getMessage() + " \nerror occured");
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

                if (serialPortEvent.isRXCHAR() && serialPortEvent.getEventValue() > 0) {
                    try {

                        //BufferedReader reader = new BufferedReader(new InputStreamReader(serialPort.readString()));

                        //istream = new SerialInputStream(serialPort);
                        //istream.setTimeout(10000);
                        //BufferedReader reader = new BufferedReader(new InputStreamReader(istream));

                        //serialPortEvent.getEventValue()
                        String sb = serialPort.readString(4);

                        //sb= sb.replace("@", "").replace("#", "");
                        System.out.println(sb);
                        if (sb.contains("NM")) {

                            txtenrolfinger.setText("No Match Found");
                        } else if (sb.contains("WF")) {
                            txtenrolfinger.setText("Waiting to enroll finger");
                        } else if (sb.contains("NW")) {
                            txtenrolfinger.setText("Expected New ID #");
                        } else if (sb.contains("UE")) {
                            txtenrolfinger.setText("Connection or Reading Print Error");
                        } else if (sb.contains("RF")) {
                            txtenrolfinger.setText("Remove Finger");
                        } else if (sb.contains("PM")) {
                            txtenrolfinger.setText("Prints Matched");
                        } else if (sb.contains("PF")) {
                            txtenrolfinger.setText("Place the same finger again");
                        } else if (sb.contains("IT")) {
                            txtenrolfinger.setText("Image Taken");
                        } else if (sb.contains("IE")) {
                            txtenrolfinger.setText("Imaging Error");
                        } else if (sb.contains("IS")) {
                            txtenrolfinger.setText("Finger ID id: ");
                        } else {
                            txtenrolfinger.setText(sb);
                            //sb=sb.replaceAll("\\r|\\n", "");
                            //sb=sb.substring(sb.indexOf("@")+1, sb.indexOf("#"));
                        }
                        //cleanIt();

                    } catch (SerialPortException exception) {
                        System.err.println(exception);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });

            arduinoPort = serialPort;
            success = true;
        } catch (SerialPortException ex) {
            Logger.getLogger(EditLec.class.getName())
                    .log(Level.SEVERE, null, ex);
            System.out.println("SerialPortException: " + ex);
            ErrorMessage.display("Device Status", "No Device on Connected!!!");
        }
        return success;

    }

    public void clear() {
        txtfname.setText("");
        txtlname.setText("");
        txtadm.setText("");
        //lphoto.setGraphic(null);
        txtaddress.setText("");
        txttown.setText("");
        txtid.setText("");
        txtphone.setText("");
        txtenrolfinger.clear();
    }
}
