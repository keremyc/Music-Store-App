package music.data;

import music.business.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDB {

    // this method retrieves a product based on product code from database.
    public static Product select(String code){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;

        String query = "SELECT * FROM PRODUCT WHERE ProductCode = ?";

        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, code);
            result = stmt.executeQuery();
            if (result.next()){
                Product product = new Product();
                product.setId(result.getLong("productID"));
                product.setCode(result.getString("productCode"));
                product.setDescription(result.getString("ProductDescription"));
                product.setPrice(result.getDouble("productPrice"));
                return product;
            }else {
                return null;
            }
        }catch (SQLException exp){
            exp.printStackTrace();
            return null;
        }finally {
            DBUtil.closeResultSet(result);
            DBUtil.closeStatement(stmt);
            pool.freeConnection(conn);
        }
    }

    // this method retrieves a product based on product id from database.
    public static Product select(Long id){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;

        String query = "SELECT * FROM PRODUCT WHERE ProductID = ?";

        try {
            stmt = conn.prepareStatement(query);
            stmt.setLong(1, id);
            result = stmt.executeQuery();
            if (result.next()){
                Product product = new Product();
                product.setId(result.getLong("productID"));
                product.setCode(result.getString("productCode"));
                product.setDescription(result.getString("ProductDescription"));
                product.setPrice(result.getDouble("productPrice"));
                return product;
            }else {
                return null;
            }
        }catch (SQLException exp){
            exp.printStackTrace();
            return null;
        }finally {
            DBUtil.closeResultSet(result);
            DBUtil.closeStatement(stmt);
            pool.freeConnection(conn);
        }
    }

    public static List<Product> selectProducts(){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        Statement stmt = null;
        ResultSet result = null;

        String query = "SELECT * FROM PRODUCT";

        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
            List<Product> list = new ArrayList<>();
            while (result.next()){
                Product pro = new Product();
                pro.setId(result.getLong("productID"));
                pro.setCode(result.getString("productCode"));
                pro.setDescription(result.getString("productDescription"));
                pro.setPrice(result.getDouble("productPrice"));
                list.add(pro);
            }
            return list;
        }catch (SQLException exp){
            exp.printStackTrace();
            return null;
        }finally {
            DBUtil.closeResultSet(result);
            DBUtil.closeStatement(stmt);
            pool.freeConnection(conn);
        }
    }
    
}
