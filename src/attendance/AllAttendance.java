package attendance;

import database.DBConnect;
import dialogs.ErrorMessage;
import gatereports.PersonalReports;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import officeReports.OfficePersonalReport;
import org.controlsfx.control.TextFields;
import register.NewLecturer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static jssc.SerialPort.MASK_RXCHAR;

public class AllAttendance extends Application {

    //FileInputStream fis;
    //java.sql.Connection conn = null;
    public static Statement stmt;
    private static final AudioClip alert = new AudioClip(AllAttendance.class.getResource("/pic/beep-e.wav").toString());
    TextField txtsearch;
    TableView<AttClass> table;
    TableColumn<AttClass, String> namecol, admcol, typecol, timecol, datecol;
    ObservableList<AttClass> att = FXCollections.observableArrayList();
    Stage window;
    Scene scene;
    VBox laytable;
    VBox topPane, bottomPane;
    Label lfinger, lpic, lname, lnotexist;
    Text progress, tclock;
    TextField txtfinger;
    Button btnrefresh;
    ScrollPane sc = new ScrollPane();
    Pagination pagination;
    SerialPort arduinoPort = null;
    java.sql.PreparedStatement pstmt;
    private Rectangle clipRect;
    private Timeline timelineUp;
    private Timeline timelineDown;
    private final Rectangle2D boxBounds = new Rectangle2D(800, 800, 870, 700);

    public static void main(String[] args) {
        launch(args);
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 5;
    }

    @Override
    public void start(Stage s) throws Exception {

        window = s;
        window = new Stage(StageStyle.UTILITY);//StageStyle.UNDECORATED
        //window.setAlwaysOnTop(true);
        window.initModality(Modality.APPLICATION_MODAL);
        window.getIcons().add(new Image("/pic/slogo.png"));
        window.setOnCloseRequest(e -> {
            e.consume();
            disconnectArduino();
            window.close();
        });
        HBox root = new HBox();
        scene = new Scene(root, 1100, 600);
        scene.getStylesheets().add(AllAttendance.class.getResource("att.css").toExternalForm());
        window.setScene(scene);
        window.setTitle("Gate Entry Progress");
        window.setX(250);
        window.setY(27);
        window.setResizable(false);
        window.show();
        configureBox(root);

        initialize("COM25");
        System.out.println("Application Started");


    }

    public ObservableList<AttClass> getAtt() {

        ObservableList<AttClass> entry = FXCollections.observableArrayList();
        String sql = "SELECT first_name, school_id, type, time_in, date FROM  gateentry  ORDER BY ind DESC";
        try {
            DBConnect.connect();
            ResultSet rec = DBConnect.stmt.executeQuery(sql);
            while ((rec != null) && (rec.next())) {
                String name = (rec.getString("first_name"));
                String adm = (rec.getString("school_id"));
                String type = (rec.getString("type"));
                String time = (rec.getString("time_in"));
                String date = (rec.getString("date"));

                entry.add(new AttClass(name, adm, type, time, date));
            }
            rec.close();
        } catch (SQLException ea) {
            ErrorMessage.display("Launching Error", ea.getMessage() + "Database Communications Link Failure");
            ea.printStackTrace();
        } catch (Exception e) {
            ErrorMessage.display("Launching Error", e.getMessage());
            e.printStackTrace();
        }
        return entry;
    }

    @SuppressWarnings("unchecked")
    private void configureBox(HBox root) {
        StackPane container = new StackPane();
        //container.setPrefHeight(700);
        container.setPrefSize(boxBounds.getWidth(), boxBounds.getHeight());
        container.setStyle("-fx-border-width:1px;-fx-border-style:solid;-fx-border-color:#999999;");

        table = new TableView<AttClass>();
        Label lview = new Label();
        lview.setText("View Records");
        lview.setId("lview");
        bottomPane = new VBox();

        tclock = new Text();
        tclock.setId("lview");
        //tclock.setFont(Font.font("Calibri", 20));
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> tclock.setText(DateFormat.getDateTimeInstance().format(new Date()))));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        bottomPane.getChildren().addAll(tclock, lview);
        bottomPane.setAlignment(Pos.CENTER);

        //table pane
        namecol = new TableColumn<>("First Name");
        namecol.setMinWidth(170);
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));

        admcol = new TableColumn<>("Identication No.");
        admcol.setMinWidth(180);
        admcol.setCellValueFactory(new PropertyValueFactory<>("adm"));

        typecol = new TableColumn<>("Type");
        typecol.setMinWidth(130);
        typecol.setCellValueFactory(new PropertyValueFactory<>("type"));

        timecol = new TableColumn<>("Signin");
        timecol.setMinWidth(140);
        timecol.setCellValueFactory(new PropertyValueFactory<>("timein"));

        datecol = new TableColumn<>("Date");
        datecol.setMinWidth(180);
        datecol.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.getColumns().addAll(namecol, admcol, typecol, timecol, datecol);
        table.setItems(getAtt());
        att = getAtt();
        table.setItems(FXCollections.observableArrayList(att));
        table.setMinHeight(500);

        btnrefresh = new Button("Refresh");
        btnrefresh.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                table.setItems(getAtt());
            }
        });
        laytable = new VBox(10);
        laytable.getChildren().addAll(table, btnrefresh);
        laytable.setAlignment(Pos.TOP_LEFT);

        container.getChildren().addAll(bottomPane, laytable);
        setAnimation();
        sc.setContent(container);
        root.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672)");
        root.getChildren().addAll(getActionPane(), sc);

        //service.start();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setAnimation() {
        // Initially hiding the Top Pane
        clipRect = new Rectangle();
        clipRect.setWidth(boxBounds.getWidth());
        clipRect.setHeight(0);
        clipRect.translateYProperty().set(boxBounds.getWidth());
        laytable.setClip(clipRect);
        laytable.translateYProperty().set(-boxBounds.getWidth());

        // Animation for bouncing effect.
        final Timeline timelineBounce = new Timeline();
        timelineBounce.setCycleCount(2);
        timelineBounce.setAutoReverse(true);
        final KeyValue kv1 = new KeyValue(clipRect.heightProperty(), (boxBounds.getHeight() - 15));
        final KeyValue kv2 = new KeyValue(clipRect.translateYProperty(), 15);
        final KeyValue kv3 = new KeyValue(laytable.translateYProperty(), -15);
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

        // Animation for scroll down.
        timelineDown.setCycleCount(1);
        timelineDown.setAutoReverse(true);
        final KeyValue kvDwn1 = new KeyValue(clipRect.heightProperty(), boxBounds.getWidth());
        final KeyValue kvDwn2 = new KeyValue(clipRect.translateYProperty(), 0);
        final KeyValue kvDwn3 = new KeyValue(laytable.translateYProperty(), 0);
        final KeyFrame kfDwn = new KeyFrame(Duration.millis(1000), onFinished, kvDwn1, kvDwn2, kvDwn3);
        timelineDown.getKeyFrames().add(kfDwn);

        // Animation for scroll up.
        timelineUp.setCycleCount(1);
        timelineUp.setAutoReverse(true);
        final KeyValue kvUp1 = new KeyValue(clipRect.heightProperty(), 0);
        final KeyValue kvUp2 = new KeyValue(clipRect.translateYProperty(), boxBounds.getHeight());
        final KeyValue kvUp3 = new KeyValue(laytable.translateYProperty(), -boxBounds.getHeight());
        final KeyFrame kfUp = new KeyFrame(Duration.millis(1000), kvUp1, kvUp2, kvUp3);
        timelineUp.getKeyFrames().add(kfUp);

    }

    private VBox getActionPane() {
        progress = new Text("Gate\nEntry Progress");
        progress.setTextAlignment(TextAlignment.CENTER);
        progress.setFont(Font.font("Times New Roman", 35));
        lname = new Label("-Your Name here-");

        Image iconf = new Image(AllAttendance.class.getResourceAsStream("/pic/finger.png"));
        ImageView ivconf = new ImageView(iconf);
        lpic = new Label();
        lpic.setGraphic(ivconf);

        Image img1 = new Image(PersonalReports.class.getResourceAsStream("/pic/cross.png"));
        ImageView imagvw = new ImageView(img1);
        imagvw.setFitHeight(70);
        imagvw.setFitWidth(70);
        lnotexist = new Label("", imagvw);
        lnotexist.setText("\n\n\n\n\nN/A");
        lnotexist.setFont(Font.font("Cooper Black", 15));
        lnotexist.setVisible(false);

        txtfinger = new TextField();
        txtfinger.setEditable(false);
        txtfinger.setMaxWidth(160);
        txtfinger.setStyle("-fx-background-radius:10; -fx-background-color:#9CD777;");

        txtsearch = new TextField();
        initFilter();

        Button btnadd = new Button("save");
        btnadd.setOnAction(e -> {
            setAddAttendance();
        });

        Button btnView = new Button("View Records");
        Button btnCloseView = new Button("Hide Records");

        btnView.setOnAction(e -> {
            timelineDown.play();
        });
        btnCloseView.setOnAction(e -> {
            timelineUp.play();
        });

        VBox laywrong = new VBox();
        laywrong.getChildren().addAll(lnotexist);
        laywrong.setPadding(new Insets(0, 0, 0, 0));
        laywrong.setAlignment(Pos.CENTER);

        HBox laytest = new HBox(5);
        laytest.getChildren().addAll(txtfinger  /* btnadd*/);
        laytest.setAlignment(Pos.CENTER);
        //btnadd was beside txtfinger

        VBox laybtnsearch = new VBox();
        laybtnsearch.getChildren().addAll(txtsearch);
        laybtnsearch.setAlignment(Pos.CENTER);
        laybtnsearch.setPadding(new Insets(20, 0, 0, 0));

        HBox laybtn = new HBox(5);
        laybtn.getChildren().addAll(btnView, btnCloseView);
        laybtn.setAlignment(Pos.CENTER);
        VBox lay1 = new VBox(10);
        lay1.getChildren().addAll(progress, lpic, lname, laytest);
        lay1.setAlignment(Pos.CENTER);
        VBox layside = new VBox(25);
        layside.getChildren().addAll(lay1, laybtn, laybtnsearch, laywrong);
        layside.setAlignment(Pos.TOP_CENTER);
        layside.setMinWidth(230);
        layside.setPadding(new Insets(20, 0, 10, 0));
        return layside;
    }

    private void initFilter() {
        txtsearch = TextFields.createSearchField();
        txtsearch.setStyle("-fx-background-radius:10;");
        txtsearch.setPromptText("Search The Records");
        txtsearch.setMaxWidth(90);
        txtsearch.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
                if (txtsearch.textProperty().get().isEmpty()) {
                    table.setItems(att);
                    return;
                }
                ObservableList<AttClass> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<AttClass, ?>> cols = table.getColumns();
                for (int i = 0; i < att.size(); i++) {

                    for (int j = 0; j < cols.size(); j++) {
                        TableColumn<AttClass, ?> col = cols.get(j);
                        String cellValue = col.getCellData(att.get(i)).toString();
                        cellValue = cellValue.toLowerCase();
                        if (cellValue.contains(txtsearch.textProperty().get().toLowerCase())) {
                            tableItems.add(att.get(i));
                            break;
                        }
                    }

                }
                table.setItems(tableItems);
            }
        });
    }

    public void setAddAttendance() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String d = dateformat.format(calendar.getTime());

        SimpleDateFormat dateformat1 = new SimpleDateFormat("hh:mm aaa");
        Calendar calendar1 = Calendar.getInstance();
        String d1 = dateformat1.format(calendar1.getTime());
        int id = 0;
        String search_first =
                "SELECT * FROM lecturers WHERE thumb_id = '" + txtfinger.getText() + "'  UNION"

                        + " SELECT * FROM staff WHERE thumb_id = '" + txtfinger.getText() + "' UNION "

                        + "SELECT * FROM students WHERE thumb_id = '" + txtfinger.getText() + "' UNION"

                        + " SELECT * FROM visitors WHERE thumb_id = '" + txtfinger.getText() + "'";


        DBConnect.connect();
        try {
            ResultSet rs = DBConnect.stmt.executeQuery(search_first);

            if (rs.next()) {
                lname.setText(rs.getString(2));
                String query = "INSERT INTO gateentry(ind, first_name, school_id, type, time_in, date, thumb_id)" +
                        "VALUES"
                        + "('" + id + "', "
                        + "(SELECT first_name FROM students where thumb_id= " + "'" + txtfinger.getText() + "' UNION "
                        + "SELECT first_name FROM lecturers WHERE thumb_id = '" + txtfinger.getText() + "' UNION "
                        + "SELECT first_name FROM visitors WHERE thumb_id = '" + txtfinger.getText() + "' UNION "
                        + "SELECT first_name FROM staff WHERE thumb_id = '" + txtfinger.getText() + "' ),"

                        + "(SELECT s_id FROM students where thumb_id= " + "'" + txtfinger.getText() + "' UNION "
                        + "SELECT s_id FROM lecturers WHERE thumb_id = '" + txtfinger.getText() + "' UNION "
                        + "SELECT n_id FROM visitors WHERE thumb_id = '" + txtfinger.getText() + "' UNION "
                        + "SELECT s_id FROM staff WHERE thumb_id = '" + txtfinger.getText() + "' ),"

                        + "(SELECT student FROM students where thumb_id= " + "'" + txtfinger.getText() + "' UNION "
                        + "SELECT lecturer FROM lecturers WHERE thumb_id = '" + txtfinger.getText() + "' UNION "
                        + "SELECT visitor FROM visitors WHERE thumb_id = '" + txtfinger.getText() + "' UNION "
                        + "SELECT staff FROM staff WHERE thumb_id = '" + txtfinger.getText() + "' ),"

                        + "'" + d1 + "', '" + d + "','" + txtfinger.getText() + "')";

                lnotexist.setVisible(false);
                DBConnect.stmt.execute(query);
                table.setItems(getAtt());
                DBConnect.closeConnection();
            } else {
                //ErrorMessage.display("Error","Record does not exist");
                lnotexist.setVisible(true);
                lname.setText("-You are who?-");
                AllAttendance.alert.play();

            }

        } catch (SQLException se) {
            //ErrorMessage.display("DataBase exception", " \nYour are not registered in the system");
            se.printStackTrace();
        } catch (Exception e) {
            ErrorMessage.display("Connection Error", "Contact Administrator " + e.getMessage());
            e.printStackTrace();
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
                        String sb = serialPort.readString(4);

                        System.out.println(sb);
                        if (sb.contains("NM")) {

                            txtfinger.setText("No Match Found");
                            lnotexist.setVisible(true);
                            AllAttendance.alert.play();
                        } else if (sb.contains("WF")) {
                            txtfinger.setText("Waiting to enroll finger");
                        } else if (sb.contains("NW")) {
                            txtfinger.setText("Expected New ID #");
                        } else if (sb.contains("UE")) {
                            txtfinger.setText("Connection or Reading Print Error");
                            lnotexist.setVisible(true);
                            AllAttendance.alert.play();

                        } else if (sb.contains("RF")) {
                            txtfinger.setText("Remove Finger");
                        } else if (sb.contains("PM")) {
                            txtfinger.setText("Prints Matched");
                        } else if (sb.contains("PF")) {
                            txtfinger.setText("Place the same finger again");
                        } else if (sb.contains("IT")) {
                            txtfinger.setText("Image Taken");
                        } else if (sb.contains("IE")) {
                            txtfinger.setText("Imaging Error");
                            lnotexist.setVisible(true);
                            AllAttendance.alert.play();

                        } else if (sb.contains("IS")) {
                            txtfinger.setText("Finger ID id: ");
                        } else {
                            Platform.runLater(() -> {
                                txtfinger.setText(sb);
                                setAddAttendance();
                            });
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
            Logger.getLogger(NewLecturer.class.getName())
                    .log(Level.SEVERE, null, ex);
            System.out.println("SerialPortException: " + ex);
            ErrorMessage.display("Device Status", "No Device on Connected!!!");
        }
        return success;

    }

    public void disconnectArduino() {

        System.out.println("disconnectArduino()");
        if (arduinoPort != null) {
            try {
                arduinoPort.removeEventListener();

                if (arduinoPort.isOpened()) {
                    arduinoPort.closePort();
                }

            } catch (SerialPortException ex) {
                Logger.getLogger(OfficePersonalReport.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }
}


