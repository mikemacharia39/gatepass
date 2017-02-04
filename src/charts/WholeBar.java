package charts;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import database.DBConnect;
import dialogs.ErrorMessage;

public class WholeBar extends Application {

	Stage primaryStage;
	public static Label lperson = new Label();
	String lp;
	public WholeBar(String lp)
	{
		this.lp=lp;
	}
    public static void main(String[] args) {
        launch(args);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void start(Stage primaryStage1) {
    	primaryStage= primaryStage1;
    	primaryStage= new Stage(StageStyle.TRANSPARENT);
    	primaryStage.getIcons().add(new Image("/pic/slogo.png"));
    	primaryStage.initModality(Modality.APPLICATION_MODAL);
    	
    	lperson= new Label();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart barChart = new BarChart(xAxis, yAxis);
        xAxis.setLabel("Date");
        yAxis.setLabel("Count");
        barChart.setData(getChartData());
        barChart.setTitle("Bar Chart Representation of "+lp+" Gate Entries");
        primaryStage.setTitle("");
        
        Button btnClose= new Button("Close");
        btnClose.setOnAction(e -> {
        	primaryStage.close();
        });

        VBox root = new VBox();
        root.getChildren().addAll(btnClose, barChart);
        root.setPadding(new Insets(10,10,0,0));
        root.setAlignment(Pos.TOP_RIGHT);
        primaryStage.setScene(new Scene(root,700,410));
        primaryStage.setY(80);
        primaryStage.setX(620);
        primaryStage.show();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private ObservableList<XYChart.Series<String, Double>> getChartData() {
        double aValue = 0;
        lperson= new Label();
        ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();
        Series<String, Double> aSeries = new Series<String, Double>();
        aSeries.setName("dates");
        
        String qcount= "SELECT date, COUNT(date) FROM  gateentry where type='"+lp+"' GROUP BY date";
		DBConnect.connect();
		try 
		{
			ResultSet rec = DBConnect.stmt.executeQuery(qcount);
			while(rec.next())
			{
				String date = rec.getString("date");
				int count= rec.getInt("COUNT(date)");
				aSeries.getData().add(new XYChart.Data(date, count));
	            aValue = aValue + Math.random() - .5;
			}
			
		} catch (SQLException e) 
		{
			ErrorMessage.display("SQL Error", e.getMessage()+"\n error");
			e.printStackTrace();
		}
        
        answer.addAll(aSeries);
        return answer;
    }
}