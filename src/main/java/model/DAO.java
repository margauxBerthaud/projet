/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
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
        
    }
}
