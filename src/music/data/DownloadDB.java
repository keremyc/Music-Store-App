package music.data;

import music.business.Download;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DownloadDB {

    // return the number of rows affected or 0 if any errors occurs.
    public static long insert(Download download){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement stmt = null;

        String query = "INSERT INTO DOWNLOAD(UserID, DownloadDate, ProductCode) " +
                "VALUES (?, NOW(), ?)";

        try {
            stmt = conn.prepareStatement(query);
            stmt.setLong(1, download.getUser().getId());
            stmt.setString(2, download.getProductCode());
            return stmt.executeUpdate();
        }catch (SQLException exp){
            exp.printStackTrace();
            return 0;
        }finally {
            DBUtil.closeStatement(stmt);
            pool.freeConnection(conn);
        }
    }
}
