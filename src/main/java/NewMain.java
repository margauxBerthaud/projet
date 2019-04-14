
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.DAO;
import model.DiscountCode;
import model.Product;
import model.PurchaseOrder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adrien
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        
        DAO dao = new DAO();
        

        List<Product> listp = dao.listProduct();
        
        for (Product product : listp) {
            System.out.println("prod "+product.getDESCRIPTION());
        }

        System.out.println("NewMain.main "+dao.ajouterCommande(1, 40, 980001));
        
        
        dao.supprimerCommande(8539);
    }
    
}
