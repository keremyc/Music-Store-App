package music.data;

import music.business.User;

import java.sql.*;

public class UserDB {

    public static void insert(User user){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement stmt = null;

        String query = "INSERT INTO USER(FirstName, LastName, EmailAddress, CompanyName, Address1, Address2, " +
                "City, State, Zip, Country, CreditCardType, CreditCardNumber, CreditCardExpirationDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try{
            // inserting the user object to the table with a new id.
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getCompanyName());
            stmt.setString(5, user.getAddress1());
            stmt.setString(6, user.getAddress2());
            stmt.setString(7, user.getCity());
            stmt.setString(8, user.getState());
            stmt.setString(9, user.getZip());
            stmt.setString(10, user.getCountry());
            stmt.setString(11, user.getCreditCardType());
            stmt.setString(12, user.getCreditCardNumber());
            stmt.setString(13, user.getCreditCardExpirationDate());
            stmt.executeUpdate();

            // get the the id created.
            String identityQuery = "SELECT @@IDENTITY as Identity";
            Statement identityStmt = conn.createStatement();
            ResultSet rs = identityStmt.executeQuery(identityQuery);
            rs.next();
            long userID = rs.getLong("Identity");
            user.setId(userID);
            rs.close();
            identityStmt.close();
        }catch (SQLException exp){
            exp.printStackTrace();
        }finally {
            DBUtil.closeStatement(stmt);
            pool.freeConnection(conn);
        }
    }

    public static void update(User user){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement stmt = null;

        String query = "UPDATE USER SET " +
                "FirstName = ?, " +
                "LastName = ?, " +
                "CompanyName = ?, " +
                "Address1 = ?, " +
                "Address2 = ?, " +
                "City = ?, " +
                "State = ?, " +
                "Zip = ?, " +
                "Country = ?, " +
                "CreditCardType = ?, " +
                "CreditCardNumber = ?, " +
                "CreditCardExpirationDate = ? " +
                "WHERE EmailAddress = ?";

        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getCompanyName());
            stmt.setString(4, user.getAddress1());
            stmt.setString(5, user.getAddress2());
            stmt.setString(6, user.getCity());
            stmt.setString(7, user.getState());
            stmt.setString(8, user.getZip());
            stmt.setString(9, user.getCountry());
            stmt.setString(10, user.getCreditCardType());
            stmt.setString(11, user.getCreditCardNumber());
            stmt.setString(12, user.getCreditCardExpirationDate());
            stmt.setString(13, user.getEmail());

            stmt.executeUpdate();
        }catch (SQLException exp){
            exp.printStackTrace();
        }finally {
            DBUtil.closeStatement(stmt);
            pool.freeConnection(conn);
        }
    }

    public static User select(String email){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String query = "SELECT * FROM USER " +
                "WHERE EmailAddress = ?";

        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (rs.next()){
                User user = new User();
                user.setId(rs.getLong("UserID"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setEmail(rs.getString("EmailAddress"));
                user.setCompanyName(rs.getString("CompanyName"));
                user.setAddress1(rs.getString("Address1"));
                user.setAddress2(rs.getString("Address2"));
                user.setCity(rs.getString("City"));
                user.setState(rs.getString("State"));
                user.setZip(rs.getString("Zip"));
                user.setCountry(rs.getString("Country"));
                user.setCreditCardType(rs.getString("CreditCardType"));
                user.setCreditCardNumber(rs.getString("CreditCardNumber"));
                user.setCreditCardExpirationDate(rs.getString("CreditCardExpirationDate"));
                return user;
            } else {
                return null;
            }
        }catch (SQLException exp){
            exp.printStackTrace();
            return null;
        }finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            pool.freeConnection(conn);
        }
    }

    // checks if a user exists with given email.
    public static boolean emailExists(String email){
        User user = select(email);
        if (user == null){
            return false;
        } else {
            return true;
        }
    }

}
