package music.data;

import music.business.LineItem;
import music.business.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineItemDB {

    public static List<LineItem> selectLineItems(long invoiceID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String query = "SELECT * FROM LINE_ITEM WHERE InvoiceID = ?";

        try {
            stmt = conn.prepareStatement(query);
            stmt.setLong(1, invoiceID);
            rs = stmt.executeQuery();
            List<LineItem> listOfItems = new ArrayList<>();
            while (rs.next()){
                LineItem li = new LineItem();
                Long productId = rs.getLong("productId");
                Product product = ProductDB.select(productId);
                li.setProduct(product);
                li.setQuantity(rs.getInt("quantity"));
                // doesn't need to set the id of lineItem.
                listOfItems.add(li);
            }
            return listOfItems;
        }catch (SQLException exp){
            exp.printStackTrace();
            return null;
        }finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            pool.freeConnection(conn);
        }
    }

    // return the number of rows affected. 0 if an error occurs.
    public static long insert(long invoiceID, LineItem lineItem){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement stmt = null;

        String query = "INSERT INTO LINE_ITEM(InvoiceID, ProductID, Quantity)" +
                " VALUES (?, ?, ?)";

        try{
            stmt = conn.prepareStatement(query);
            stmt.setLong(1, invoiceID);
            stmt.setLong(2, lineItem.getProduct().getId());
            stmt.setInt(3, lineItem.getQuantity());
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
