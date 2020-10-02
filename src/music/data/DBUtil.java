package music.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    public static void closeStatement(Statement stmt){
        try {
            if (stmt != null){
                stmt.close();
            }
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet result){
        try {
            if (result != null){
                result.close();
            }
        }catch (SQLException exp){
            exp.printStackTrace();
        }
    }
}
