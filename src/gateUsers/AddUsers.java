package gateUsers;

import database.DBConnect;
import dialogs.ErrorMessage;
import dialogs.SuccessMessage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddUsers extends Application {

    Stage window;
    Scene scene1;
    Label lname, lpass, lboxes;
    PasswordField password;
    TextField txtname;
    ComboBox<String> cuser;
    Button btnadd, btnclose;
    CheckBox box1, box2, box3, box4, box5, box6, box7, box8, box9;

    public static void main(String[] args) {
        launch(args);
    }

    public void setNewUser() {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        //window.getIcons().add(new Image("/pic/logo.png"));
        window.setResizable(false);
        window.setTitle("New User Details");

        lname = new Label("Username");
        lname.setStyle("-fx-font-weight:Bold");
        txtname = new TextField();
        txtname.setPromptText("Username");

        lpass = new Label("Password");
        lpass.setStyle("-fx-font-weight:Bold");
        password = new PasswordField();
        password.setPromptText("password");

        cuser = new ComboBox<String>();
        cuser.getItems().addAll("Super User", "Gate Admin", "Office Admin", "User");
        cuser.setPromptText("Select User Level");
        cuser.setEditable(true);
        lboxes = new Label("Permissions Allowed For the Selected User Level");
        lboxes.setStyle("-fx-font-weight:Bold; -fx-font-size:11px");
        box1 = new CheckBox("Creating Users");
        box2 = new CheckBox("Enrolling New Users");
        box3 = new CheckBox("Purging Databases");
        box4 = new CheckBox("Backup Databases");
        box5 = new CheckBox("Restoring Databases");
        box6 = new CheckBox("Taking Attendance");
        box7 = new CheckBox("Generating Reports");
        box8 = new CheckBox("Editing Users");
        box9 = new CheckBox("Editing Student Records");
        cuser.setOnAction(e -> {
            if (cuser.getValue().equals("Super User")) {
                box1.setSelected(true);
                box2.setSelected(true);
                box3.setSelected(true);
                box4.setSelected(true);
                box5.setSelected(true);
                box6.setSelected(true);
                box7.setSelected(true);
                box8.setSelected(true);
                box9.setSelected(true);
            } else if (cuser.getValue().equals("User")) {
                box1.setSelected(false);
                box2.setSelected(true);
                box3.setSelected(false);
                box4.setSelected(true);
                box5.setSelected(false);
                box6.setSelected(false);
                box7.setSelected(true);
                box8.setSelected(false);
                box9.setSelected(false);
            } else if (cuser.getValue().equals("Gate Admin")) {
                box1.setSelected(false);
                box2.setSelected(true);
                box3.setSelected(true);
                box4.setSelected(false);
                box5.setSelected(true);
                box6.setSelected(false);
                box7.setSelected(true);
                box8.setSelected(true);
                box9.setSelected(true);
            } else if (cuser.getValue().equals("Office Admin")) {
                box1.setSelected(false);
                box2.setSelected(true);
                box3.setSelected(true);
                box4.setSelected(false);
                box5.setSelected(true);
                box6.setSelected(false);
                box7.setSelected(true);
                box8.setSelected(true);
                box9.setSelected(true);
            }
        });

        btnadd = new Button("Add");
        btnadd.setOnAction(e -> {
            addToDB();
        });
        btnclose = new Button("Close");
        btnclose.setOnAction(e -> {

            window.close();
        });

        HBox layout1 = new HBox(15);
        layout1.getChildren().addAll(lname, txtname, lpass, password);
        layout1.setPadding(new Insets(10, 10, 10, 10));

        HBox layoutbtn = new HBox(10);
        layoutbtn.getChildren().addAll(btnadd, btnclose);
        layoutbtn.setAlignment(Pos.CENTER_RIGHT);
        layoutbtn.setPadding(new Insets(10, 0, 10, 0));

        HBox cf = new HBox(10);
        cf.getChildren().addAll(cuser);
        cf.setAlignment(Pos.CENTER);
        cf.setPadding(new Insets(10, 0, 10, 0));

        VBox laycf1 = new VBox();
        laycf1.getChildren().addAll(layout1, cf);
        laycf1.setBorder(new Border(new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        VBox layout2 = new VBox(10);
        layout2.getChildren().addAll(laycf1);
        layout2.setPadding(new Insets(10, 0, 10, 0));

        VBox layout3 = new VBox(10);
        layout3.getChildren().addAll(lboxes, box1, box2, box3, box4, box5, box6, box7, box8, box9);
        layout3.setBorder(new Border(new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        layout3.setPadding(new Insets(10, 10, 10, 10));

        BorderPane flayout = new BorderPane();
        flayout.setTop(layout2);
        flayout.setPadding(new Insets(10, 10, 10, 10));
        flayout.setCenter(layout3);
        flayout.setBottom(layoutbtn);
        flayout.setStyle("-fx-background-color: linear-gradient(#E4E9A9, #9CD777);");
        scene1 = new Scene(flayout, 500, 500);
        scene1.getStylesheets().add(AddUsers.class.getResource("users.css").toExternalForm());
        window.setScene(scene1);
        System.out.println("Application Started");
    }

    @Override
    public void start(Stage newpage) throws Exception {
        window = newpage;
        setNewUser();
        window.show();
    }

    public void addToDB() {
        String sql = "INSERT INTO login VALUES('" + txtname.getText() + "', '" + password.getText() + "', '" + cuser.getValue() + "')";
        try {
            if (txtname.getText().equals("")) {
                ErrorMessage.display("Is Empty", "Please enter the Username");
            } else if (password.getText().equals("")) {
                ErrorMessage.display("Is Empty", "Please enter the password");
            } else if (cuser.getSelectionModel().getSelectedItem().isEmpty()) {
                ErrorMessage.display("Is Empty", "Please select or Input level");
            } else if (txtname.getText().equals("") || password.getText().equals("")) {
                ErrorMessage.display("Empty", "Please fill all the field to proceed");
            } else {

                DBConnect.connect();
                DBConnect.stmt.execute(sql);

                UserCreation prod = new UserCreation();
                prod.setName(txtname.getText());
                prod.setLevel(cuser.getValue());
                Users.table.getItems().add(prod);

                SuccessMessage.display("Success", txtname.getText() + " added successfully");

                txtname.clear();
                password.clear();
                DBConnect.closeConnection();
            }
        } catch (SQLException se) {
            //JOptionPane.showMessageDialog(NewUser.this, se.getMessage(), "DataBase exception",
            //    JOptionPane.WARNING_MESSAGE);
            ErrorMessage.display("Database exception", se.getMessage() + " Error occured in the process, try again");
        } catch (NullPointerException e1) {
            ErrorMessage.display("Incomplete Selection", "Please, Select User Level please to proceed");
        } catch (Exception e) {
            ErrorMessage.display("Error", e.getMessage() + " error has occured, please try again");
            e.printStackTrace();

        }
    }
}

