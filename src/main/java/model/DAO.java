/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author mberth03
 */
public class DAO {
    private final DataSource myDataSource;
    
    /**
	 * Construit le DAO avec sa source de données
	 * @param dataSource la source de données à utiliser
	 */
    public DAO(DataSource dataSource){
        this.myDataSource=dataSource;
    }
    public List<PurchaseOrder> commandesClient (Customer c) throws SQLException{
        List<PurchaseOrder> resultat=new LinkedList<>();
        String sql="SELECT ORDER_NUM, CUSTOMER_ID, PRODUCT_ID, QUANTITY, SHIPPING_COST, SALES_DATE, SHIPPING_DATE, PRODUCT.DESCRIPTION FROM CUSTOMER INNER JOIN PURCHASE_ORDER ON CUSTOMER.ID=PURCHASE_ORDER.CUSTOMER_ID WHERE CUSTOMER_ID=?";
        try (Connection connection=myDataSource.getConnection();
             PreparedStatement stmt=connection.prepareStatement(sql)) {
                ResultSet rs=stmt.executeQuery();
                while(rs.next()){
                    int numComm=rs.getInt("ORDER_NUM");
                    int numClient=rs.getInt("CUSTOMER_ID");
                    int numProduit=rs.getInt("PRODUCT_ID");
                    
                }
        }
    }
}
