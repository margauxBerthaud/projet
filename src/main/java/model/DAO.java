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
import java.util.Date;
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
       private int valeur_discount_code(int id_p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        /**
	 * Fonction permet de calculer le prix d'une commande
     * @param id_p
     * @param quantite
     * @param id_c
	 */
    public float prixCommande (int id_p, int quantite, int id_c ) throws SQLException{
        float resultat=0;
        String sql ="SELECT PRODUCT.PURCHASE_COST FROM PRODUCT WHERE PRODUCT_ID=?";
        try (Connection connection =myDataSource.getConnection();
             PreparedStatement stmt =connection.prepareStatement(sql)){
                ResultSet rs=stmt.executeQuery();
                stmt.setInt(1, id_p);
                while (rs.next()){
                    resultat=(rs.getFloat("PURCHASE_COST")*quantite)*(100-valeur_discount_code(id_p)/100);
                    
                }
        }
        return resultat;
    }
        /**
	 * La fonction commandesClient permet de ressortir la liste des commandes en passant le client en paramètre
     * @param c
     * @throws java.sql.SQLException
	 */
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
                    int quantite=rs.getInt("QUANTITY");
                    float remise=prixCommande(numClient,quantite,numProduit);
                    Date date = rs.getDate("SHIPPING_DATE");
                    String description=rs.getString("DESCRIPTION");
                    PurchaseOrder commande=new PurchaseOrder(numComm,numClient,quantite);
                    commande.setSHIPPING_DATE(date);
                    commande.setSHIPPING_COST(remise);
                    resultat.add(commande);
                    
                }
        }
        return null;
    }

 
}
