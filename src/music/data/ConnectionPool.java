package music.data;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static ConnectionPool instance = null;
    private DataSource source = null;

    private ConnectionPool(){
        try {
            InitialContext context = new InitialContext();
            source = (DataSource)context.lookup("java:/comp/env/jdbc/musicDB");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public synchronized static ConnectionPool getInstance(){
        if (instance == null){
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return source.getConnection();
        }catch (SQLException exp){
            exp.printStackTrace();
            return null;
        }
    }

    public void freeConnection(Connection con){
        try{
            con.close();
        }catch (SQLException exp){
            exp.printStackTrace();
        }
    }
}
