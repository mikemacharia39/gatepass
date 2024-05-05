package changepass;


import database.DBConnect;
import dialogs.ErrorMessage;
import dialogs.SuccessMessage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MainWindow;

import java.sql.ResultSet;

public class ChangePassword extends Application {

    public static TextField txtusername;
    Label lname, lnew;
    PasswordField newpass;
    Button btnsave, btnclose;
    Stage changepass;
    Scene scene1;

    public static void main(String[] args) {
        launch(args);
    }

    public void showChangedPassword() {
        changepass = new Stage();
        changepass.setTitle("Change Password");
        changepass.initModality(Modality.APPLICATION_MODAL);
        changepass.getIcons().add(new Image("/pic/slogo.png"));
        lname = new Label("Username");
        txtusername = new TextField();
        txtusername.setText(MainWindow.lname.getText());
        txtusername.setEditable(false);
        txtusername.setMaxSize(250, 100);

        lnew = new Label("New Password");
        newpass = new PasswordField();
        newpass.setPromptText("new password");
        newpass.setMaxSize(250, 100);

        btnsave = new Button("Save");
        btnsave.setOnAction(e -> {
            setChangePass();
        });
        btnclose = new Button("Close");
        btnclose.setOnAction(e -> {
            changepass.close();
        });

        VBox lay1 = new VBox(23);
        lay1.getChildren().addAll(lname, lnew);
        VBox lay2 = new VBox(10);
        lay2.getChildren().addAll(txtusername, newpass);

        HBox laydet = new HBox(10);
        laydet.setAlignment(Pos.CENTER);
        laydet.setPadding(new Insets(10, 10, 10, 10));
        laydet.setBorder(new Border(new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        laydet.getChildren().addAll(lay1, lay2);

        HBox laybtn = new HBox(10);
        laybtn.setAlignment(Pos.CENTER_RIGHT);
        laybtn.getChildren().addAll(btnsave, btnclose);

        VBox layall = new VBox(10);
        layall.setPadding(new Insets(10, 10, 10, 10));
        layall.getChildren().addAll(laydet, laybtn);
        layall.setStyle("-fx-background-color: linear-gradient(#E4E9A9, #9CD777);");
        scene1 = new Scene(layall);
        scene1.getStylesheets().add(ChangePassword.class.getResource("users.css").toExternalForm());
        changepass.setScene(scene1);
        changepass.setResizable(false);
        changepass.show();
    }

    @Override
    public void start(Stage cp) throws Exception {

        showChangedPassword();
    }

    public void setChangePass() {
        if (txtusername.getText().equals("") || newpass.getText().equals("")) {
            ErrorMessage.display("Warning", "Please fill all the fields provided to proceed");
            ResultSet rs;

            String query = "SELECT username, password from login WHERE username='" + txtusername.getText() + "'";
            DBConnect.connect();
            try {
                rs = DBConnect.stmt.executeQuery(query);

                if (rs.next())//valid
                {
                    if (rs.getString("username") != (txtusername.getText())) {
                        ErrorMessage.display("Error", "Wrong password");
                    }
                } else {
                    ErrorMessage.display("Error", "Incorrect Password");
                }
            } catch (Exception ea) {
                ErrorMessage.display("Error", ea.getMessage());
                ea.printStackTrace();
            }
        } else {
            String fileUpdate = "UPDATE login SET password = '" + newpass.getText() + "' where username='" + txtusername.getText() + "'";
            //connect to database
            DBConnect.connect();
            try {
                DBConnect.stmt.execute(fileUpdate);
                SuccessMessage.display("Success", "Your password is updated");
                DBConnect.closeConnection();
                newpass.clear();
            } catch (Exception e) {
                ErrorMessage.display("Database Exception", e.getMessage());
            }
        }
    }
}
