package gateUsers;
import java.sql.ResultSet;

import main.MainWindow;
import database.DBConnect;
import dialogs.Confirmation;
import dialogs.ErrorMessage;
import dialogs.SuccessMessage;
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Users extends Application{
	
	public static TableView<UserCreation> table;
	static TextField txtname;
	static TextField txtlevel;
	Button badd, bdel, bedit;
	TableColumn<UserCreation, String> namecol, levelcol;
	ObservableList<UserCreation> data;
	Stage primaryStage;
	
	public static void main(String [] args)
	{
		launch(args);
	}

	@SuppressWarnings("unchecked")
	public void showUsers()
	{
		primaryStage = new Stage();
		primaryStage.setTitle("e-Tracker Users");
		primaryStage.setResizable(false);
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.getIcons().add(new Image("/pic/slogo.png"));
		//name
		namecol = new TableColumn<>("Username");
		namecol.setMinWidth(250);
		namecol.setCellValueFactory(new PropertyValueFactory<>("name"));//has to be the same as in the UserCreation class
				
		//level
		levelcol = new TableColumn<>("User Level");
		levelcol.setMinWidth(250);
		levelcol.setCellValueFactory(new PropertyValueFactory<>("level"));
		
		table= new TableView<>();
		table.setItems(getProduct());
		table.getColumns().addAll(namecol, levelcol);
		table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
		
		txtname= new TextField();
		txtname.setEditable(false);
		txtname.setPromptText("Username");
		
		txtlevel= new TextField();
		txtlevel.setEditable(false);
		txtlevel.setPromptText("User Level e.g. Admin");
		
		badd= new Button("Add...");
		badd.setOnAction(e ->{
			addClicked();
		});
		bdel= new Button("Delete");
		bdel.setOnAction(e ->{
			deleteClicked();
		});
		
		bedit= new Button("Edit...");
		bedit.setOnAction( e ->{
			setPressbEdit();
		});
	
		HBox layout2= new HBox();
		layout2.setSpacing(10); //similar to that 10
		//layout2.setPadding(new Insets(20,20,20,20));
		layout2.getChildren().addAll(txtname, txtlevel, bedit, badd, bdel);
		layout2.setAlignment(Pos.CENTER_RIGHT);
		
		VBox layout = new VBox(12);
		layout.getChildren().addAll(table, layout2);
		layout.setPadding(new Insets(20,20,20,20));
		layout.setStyle("-fx-background-color: linear-gradient(#E4E9A9, #9CD777);");
		Scene scene= new Scene(layout);
		scene.getStylesheets().add(Users.class.getResource("users.css").toExternalForm());
		primaryStage.setScene(scene);
		
		if(MainWindow.ladmins.getText().equals("User") || MainWindow.ladmins.getText().equals("Office Admin") || 
		   MainWindow.ladmins.getText().equals("Gate Admin"))
		{
			badd.setDisable(true);
			bdel.setDisable(true);
			bedit.setDisable(true);
		}
	}
	
	@Override
	public void start(Stage primary) throws Exception {
		primaryStage= primary;
		showUsers();
		primaryStage.show();
	}
	// to get  all UserCreation
	public ObservableList<UserCreation> getProduct()
	{
		ObservableList<UserCreation> product = FXCollections.observableArrayList();
		String sql="SELECT username, level FROM  login";
		try {
	    	DBConnect.connect();
			ResultSet rec = DBConnect.stmt.executeQuery(sql);
			while((rec!=null) && (rec.next()))
			{ 
				String names= (rec.getString("username"));
				String levels= (rec.getString("level"));
				
			product.add(new UserCreation(names, levels));
			}
			rec.close();
			} catch (Exception e) {
			ErrorMessage.display("Launching Error", "Database Communications Link Failure");
			e.printStackTrace();
			}
		
		return product;
	}
	
	public void addClicked()
	{
		try {
			new AddUsers().start(new Stage());
		} catch (Exception e) {
			ErrorMessage.display("Launching Error",e.getMessage()+" \nError during launching application");
			e.printStackTrace();
		};
	}
	public void deleteClicked()
	{
		if(txtname.getText().equals("") || txtlevel.getText().equals(""))
		{
			// Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
		}
		else
		{
		boolean result = Confirmation.display("Delete Record", " Are you sure you want to delete this \n user? ");
			if(result)
			{
				ObservableList<UserCreation> selectedProd, allProd;
				allProd= table.getItems();
				selectedProd = table.getSelectionModel().getSelectedItems();
				//selectedProd.forEach(allProd:: remove);
		
				//the delete query is now complete  ------------------>
				String query = "DELETE FROM login where username='" + txtname.getText() + "'";
				//connect to database
				DBConnect.connect();
				try {
                DBConnect.stmt.execute(query);
                selectedProd.forEach(allProd:: remove);
                SuccessMessage.display("Success", "User has been deleted successfully");
                DBConnect.closeConnection();
				} catch (Exception ea) {
                ErrorMessage.display("Launching Error", ea.getMessage()+" Error has occurred. Consult administrator");
				} 
				}
			}
	}	
	public Users(){    
	}
	private void showPersonDetails(UserCreation person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            txtname.setText(person.getName());
            txtlevel.setText(person.getLevel());
        } else {
            // Person is null, remove all the text.
            txtname.setText("");
            txtlevel.setText("");
        }
    }
	
	public void setPressbEdit(){
		if(txtname.getText().equals("") || txtlevel.getText().equals(""))
		{		
			// Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
		}
		else
		{
			EditingClass ec= new EditingClass();
			ec.setToEdit();
		}
	}
}
