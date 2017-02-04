package backup;

import java.sql.SQLException;

import database.DBConnect;
import dialogs.ErrorMessage;
import dialogs.SuccessMessage;


public class ClearLog{

		public void deleteLog(){
		DBConnect.connect();
		String sql= "TRUNCATE logs";
		
		try {
			DBConnect.stmt.executeUpdate(sql);
			SuccessMessage.display("Success", "Logs have been successfully cleared");
		}
		
		catch (SQLException e) {
			DBConnect.closeConnection();
			ErrorMessage.display("Error", e.getMessage()+" \nerror during purge");
			e.printStackTrace();
		}
	}
}
