package backup;

import database.DBConnect;
import dialogs.ErrorMessage;
import dialogs.SuccessMessage;

import java.sql.SQLException;

public class PurgeData {
    public void deleteLog() {
        DBConnect.connect();
        String sql = "TRUNCATE  students";
        String sql2 = "TRUNCATE attendance";
        try {
            DBConnect.stmt.executeUpdate(sql);
            DBConnect.stmt.executeUpdate(sql2);
            SuccessMessage.display("Purge Successful", "Class and attendance records data have been successfully purged");
        } catch (SQLException e) {
            DBConnect.closeConnection();
            ErrorMessage.display("Database Error", e.getMessage());
            e.printStackTrace();
        }
    }
}
