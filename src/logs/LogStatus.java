package logs;

import java.sql.ResultSet;

import database.DBConnect;
import dialogs.Confirmation;
import dialogs.ErrorMessage;
import backup.ClearLog;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LogStatus extends Application{

	Stage window;
	Scene scene;
	MenuItem iexport, idelete, iclose;
	Menu filem;
	TableView<LogClass> table;
	TableColumn<LogClass, String> namecol, levelcol, indtimecol, outdtimecol;
	
	@SuppressWarnings("unchecked")
	public void setLogs()
	{
		window= new Stage();
		window.setTitle("Login Status");
		window.initModality(Modality.APPLICATION_MODAL);
		window.getIcons().add(new Image("/pic/slogo.png"));
		table= new TableView<>();
		
		namecol= new TableColumn<>("Username");
		namecol.setMinWidth(150);
		namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		levelcol= new TableColumn<>("User Level");
		levelcol.setMinWidth(100);
		levelcol.setCellValueFactory(new PropertyValueFactory<>("level"));
		
		indtimecol= new TableColumn<>("Date/ Time Signed in");
		indtimecol.setMinWidth(250);
		indtimecol.setCellValueFactory(new PropertyValueFactory<>("indatetime"));
		
		outdtimecol= new TableColumn<>("Date/ Time Signed out");
		outdtimecol.setMinWidth(250);
		outdtimecol.setCellValueFactory(new PropertyValueFactory<>("outdatetime"));
		
		table.getColumns().addAll(namecol, levelcol, indtimecol, outdtimecol);
		table.setItems(getLogs());
		
		filem= new Menu("Logs");
		iexport= new MenuItem("Export to Excel");
		idelete= new MenuItem("Delete Logs...");
		idelete.setOnAction( e -> {
			clearLogs();
		});
		iclose= new MenuItem("Close");
		iclose.setOnAction(e -> {
			window.close();
		});
		
		filem.getItems().addAll(idelete, new SeparatorMenuItem(), iclose);
		
		MenuBar bar= new MenuBar();
		bar.getMenus().add(filem);
		
		BorderPane layall = new BorderPane();
		layall.setTop(bar);
		layall.setCenter(table);
		
		scene= new Scene(layall);
		window.setScene(scene);
	}
	@Override
	public void start(Stage windowLog) throws Exception 
	{
		window=windowLog;
		setLogs();
		window.show();
		
	}
	public static void main(String [] args)
	{
		launch(args);
	}
	
	public ObservableList<LogClass> getLogs()
	{
		ObservableList<LogClass> log = FXCollections.observableArrayList();
		String sql="SELECT name, level, signedin, signedout FROM  logs ORDER BY No Desc";
		try {
	    	DBConnect.connect();
			ResultSet rec = DBConnect.stmt.executeQuery(sql);
			while((rec!=null) && (rec.next()))
			{ 
				String name = (rec.getString("name"));
				String level = (rec.getString("level"));
				String in = (rec.getString("signedin"));
				String out = (rec.getString("signedout"));
				
			log.add(new LogClass(name,level,in,out));
			}
			rec.close();
			} catch (Exception e) {
			ErrorMessage.display("Launching Error", e.getMessage()+ "Database Communications Link Failure");
			e.printStackTrace();
			}
		
		return log;
	}
	
	private void clearLogs()
	{
		boolean result = Confirmation.display("Delete Logs", " Are you sure you want Clear the log records? ");
		if(result)
		{	
			new ClearLog().deleteLog();
			table.setItems(getLogs());
		}
	}
}
