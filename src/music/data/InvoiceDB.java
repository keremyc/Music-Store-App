package music.data;

import music.business.Invoice;
import music.business.LineItem;
import music.business.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDB {

    public static List<Invoice> selectUnprocessedInvoices(){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        String query = "SELECT * " +
                "FROM INVOICE INNER JOIN USER ON USER.UserID = INVOICE.UserID " +
                "WHERE IsProcessed = 'n' " +
                "ORDER BY InvoiceDate";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            List<Invoice> listOfInvoices = new ArrayList<>();
            while (rs.next()){
                // The user will be retrieved from the db after creating UserDB.
                String userEmail = rs.getString("emailAddress");
                User user = UserDB.select(userEmail);

                long invoiceID = rs.getLong("invoiceID");
                List<LineItem> lineItems = LineItemDB.selectLineItems(invoiceID);

                //creating the invoice object
                Invoice invoice = new Invoice();
                invoice.setUser(user);
                invoice.setLineItems(lineItems);
                invoice.setInvoiceDate(rs.getDate("invoiceDate"));
                invoice.setInvoiceNumber(invoiceID);
                // No need to set the isProcessed field
                listOfInvoices.add(invoice);
            }
            return listOfInvoices;
        }catch (SQLException exp){
            exp.printStackTrace();
            return null;
        }finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            pool.freeConnection(conn);
        }
    }

    // this method updated the isProcessed attribute from 'n' to 'y'.
    public static void update(Invoice invoice){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement stmt = null;

        String query = "UPDATE Invoice " +
                "SET IsProcessed = 'y' " +
                "WHERE InvoiceID = ?";

        try {
            stmt = conn.prepareStatement(query);
            stmt.setLong(1, invoice.getInvoiceNumber());
            stmt.executeUpdate();
        }catch (SQLException exp){
            exp.printStackTrace();
        }finally {
            DBUtil.closeStatement(stmt);
            pool.freeConnection(conn);
        }
    }

    public static void insert(Invoice invoice){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement stmt = null;

        String query = "INSERT INTO INVOICE(UserID, InvoiceDate, TotalAmount, IsProcessed) " +
                "VALUES (?, NOW(), ?, 'n')";

        try {

            stmt = conn.prepareStatement(query);
            stmt.setLong(1, invoice.getUser().getId());
            stmt.setDouble(2, invoice.getInvoiceTotal());

            // First we insert the invoice object to the db. This saves the object with automatically created id.
            stmt.executeUpdate();

            //Get the invoiceID from the last INSERT statement
            String identityQuery = "SELECT @@IDENTITY as Identity"; // Get the id just created.
            Statement identityStmt = conn.createStatement();
            ResultSet result = identityStmt.executeQuery(identityQuery);
            result.next();
            long invoiceID = result.getLong("Identity");
            result.close();
            identityStmt.close();

            //Write line items of invoice to the LINE_ITEM table using the retrieved id.
            List<LineItem> lineItems = invoice.getLineItems();
            for (LineItem li: lineItems){
                LineItemDB.insert(invoiceID, li);
            }
        }catch (SQLException exp){
            exp.printStackTrace();
        }finally {
            DBUtil.closeStatement(stmt);
            pool.freeConnection(conn);
        }
    }

}
