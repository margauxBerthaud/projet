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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author mberth03
 */
public class DAO {

    private final DataSource myDataSource;

    /**
     * Construit le DAO avec sa source de données
     *
     * @param dataSource la source de données à utiliser
     */
    public DAO() {
        this.myDataSource = DataSourceFactory.getDataSource();
    }

    /**
     * Fonction permet de calculer le prix d'une commande
     *
     * @param id_p
     * @param quantite
     * @param id_c
     * @return
     * @throws java.sql.SQLException
     */
    public double prixCommande(int quantite, int id_p, int id_c) throws SQLException {
        double resultat = 0;
        String sql = "SELECT PRODUCT.PURCHASE_COST FROM PRODUCT WHERE PRODUCT_ID=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            stmt.setInt(1, id_p);
            
            while (rs.next()) {
                resultat = (rs.getDouble("PURCHASE_COST") * quantite / 100);

            }
        }
        return resultat;
    }

    /**
     * La fonction commandesClient permet de ressortir la liste des commandes en
     * passant le client en paramètre
     *
     * @param c
     * @throws java.sql.SQLException
     */
    public List<PurchaseOrder> commandesClient(Customer c) throws SQLException {
        List<PurchaseOrder> resultat = new LinkedList<>();
        int id = Integer.parseInt(c.getPassword());
        String sql = "SELECT PURCHASE_ORDER.ORDER_NUM,PURCHASE_ORDER.CUSTOMER_ID,PURCHASE_ORDER.PRODUCT_ID,PURCHASE_ORDER.QUANTITY,\n" +
"PURCHASE_ORDER.SHIPPING_COST,PURCHASE_ORDER.SALES_DATE,PURCHASE_ORDER.SHIPPING_DATE,PURCHASE_ORDER.FREIGHT_COMPANY,\n" +
"((PURCHASE_ORDER.QUANTITY * PRODUCT.PURCHASE_COST ) - ((PURCHASE_ORDER.QUANTITY * PRODUCT.PURCHASE_COST )*RATE)/100)\n" +
"as \"COST\"\n" +
"FROM PURCHASE_ORDER \n" +
"INNER JOIN PRODUCT USING(PRODUCT_ID)\n" +
"INNER JOIN PRODUCT_CODE ON (PRODUCT_CODE = PROD_CODE)\n" +
"INNER JOIN DISCOUNT_CODE USING (DISCOUNT_CODE)\n" +
"WHERE CUSTOMER_ID = ?";
        
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
              
                int numComm = rs.getInt("ORDER_NUM");
                int numClient = rs.getInt("CUSTOMER_ID");
                int numProduit = rs.getInt("PRODUCT_ID");
                int quantite = rs.getInt("QUANTITY");
                float cost = rs.getFloat("COST");
               
                Date date = rs.getDate("SHIPPING_DATE");
               
                PurchaseOrder commande = new PurchaseOrder(numComm, numClient, quantite);
               
                commande.setSHIPPING_DATE(date);
                commande.setSHIPPING_COST(cost);
                resultat.add(commande);

            }
        }
        return resultat;
    }

    /**
     * Fonction permettant de ressortir tous les produits disponibles sous forme
     * de liste
     *
     */
    public ArrayList<String> tousLesProduits() throws SQLException {
        ArrayList<String> resultat = new ArrayList<>();
        String sql = "SELECT DESCRIPTION FROM PRODUCT WHERE QUANTITY_ON_HAND>0";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String description = rs.getString("DESCRIPTION");
                resultat.add(description);
            }
        }
        return resultat;
    }

    /**
     * Fonction permettant d'afficher l'argent disponible selon le client
     *
     */
    public double montantDisponible(int c) throws SQLException {
        double resultat = 0;
        String sql = "SELECT CREDIT_LIMIT FROM CUSTOMER WHERE CUSTOMER_ID=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, c);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultat = rs.getInt("CREDIT_LIMIT");
            }
        }
        return resultat;
    }

    /**
     * Fonction permettant d'afficher infos disponibles selon le client
     *
     */
    public String infosDisponibles() throws SQLException {
        String resultat = "";
        String sql = "SELECT EMAIL, CUSTOMER_ID FROM CUSTOMER";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String email = rs.getString("EMAIL");
                int id = rs.getInt("CUSTOMER_ID");
                String newLine = System.getProperty("line.separator");
                resultat = " " + email + "id :  " + id;
            }
        }
        return resultat;
    }

    /**
     * Fonction permettant d'afficher infos disponibles pour une catégorie de
     * produit
     *
     */
    public String getDescription(int product_id) throws SQLException {
        String resultat = "";
        String sql = "SELECT DESCRIPTION FROM PRODUCT_CODE WHERE PRODUCT_ID=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, product_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultat = rs.getString("DESCRIPTION");
            }

        }
        return resultat;
    }

    /**
     * Fonction permettant de récupérer le prix en fonction de l'id
     *
     * @param product_id
     * @return
     * @throws java.sql.SQLException
     */
    public double recupererPrix(int product_id) throws SQLException {
        double resultat = 0;
        String sql = "SELECT PURCHASE_COST FROM PRODUCT WHERE PRODUCT_ID=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, product_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultat = rs.getDouble("PURCHASE_COST");
            }
        }
        return resultat;
    }

    /**
     * Fonction permettant d'afficher le chiffre d'affaire par catégorie
     * d'article et période
     *
     * @param dateD
     * @param dateF
     * @return
     * @throws java.sql.SQLException
     */
    public Map<String, Double> CAparDateEtCategorieProduit(String dateD, String dateF) throws SQLException {
        Map<String, Double> resultat = new HashMap();
        String sql = "SELECT PURCHASE_ORDER.PRODUCT_ID, QUANTITY, PRODUCT_CODE.DESCRIPTION FROM PURCHASE_ORDER"
                + " INNER JOIN PRODUCT"
                + " USING (PRODUCT_ID)"
                + " INNER JOIN PRODUCT_CODE"
                + " ON PRODUCT.PRODUCT_CODE = PRODUCT_CODE.PROD_CODE"
                + " WHERE SHIPPING_DATE BETWEEN ? AND ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date debut = null;
            Date fin = null;
            try {
                debut = sdf.parse(dateD);
            } catch (ParseException e1) {
                // TODO Auto-generated catch block

            }
            try {
                fin = sdf.parse(dateF);
            } catch (ParseException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
            java.sql.Date data1 = new java.sql.Date(debut.getTime());
            java.sql.Date data2 = new java.sql.Date(fin.getTime());
            stmt.setDate(1, data1);
            stmt.setDate(2, data2);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String state = rs.getString("DESCRIPTION");
                double prix = rs.getDouble("QUANTITY") * recupererPrix(rs.getInt("PRODUCT_ID"));
                if (resultat.containsKey(state)) {
                    resultat.put(state, resultat.get(state) + prix);
                    System.out.println("nouveau ca  " + state + " est de " + resultat.get(state));
                } else {
                    resultat.put(state, prix);
                    System.out.println("ca = " + state + " est de " + prix);
                }

            }

        }
        return resultat;
    }

    /**
     * Fonction permettant de savoir si le client a un compte
     *
     * @return
     * @throws java.sql.SQLException
     */
    public Customer connexionClient(String EMAIL) throws SQLException {
        Customer c = new Customer();
        String sql = "SELECT * FROM CUSTOMER WHERE EMAIL=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, EMAIL);
            ResultSet rs = stmt.executeQuery();
            System.out.println(rs);
            if (!rs.next()) {
                System.out.println("no data");
                c.setEMAIL("nodata");
                c.setPassword("nodata");
                c.setNAME("nodata");

                return c;
            } else {
                do {
                    System.out.println(String.valueOf(rs.getInt("CUSTOMER_ID")));
                    c.setEMAIL(EMAIL);
                    c.setPassword(String.valueOf(rs.getInt("CUSTOMER_ID")));
                    c.setNAME(rs.getString("NAME"));
                    c.setCITY(rs.getString("CITY"));
                    c.setADDRESSLINE1(rs.getString("ADDRESSLINE1"));
                    c.setPHONE(rs.getString("PHONE"));
                    c.setCREDIT_LIMIT(rs.getInt("CREDIT_LIMIT"));
                } while (rs.next());

            }

        }
        return c;
    }

    public String nameCustomer(int customer_id) throws SQLException {
        String resultat = "";
        String sql = "SELECT NAME FROM CUSTOMER WHERE CUSTOMER_ID = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customer_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultat = rs.getString("NAME");

            }
        }

        return resultat;
    }

    /**
     * Fonction permettant de connaître le chiffre d'affaire en fonction du
     * client
     *
     * @param dateD
     * @param dateF
     * @return
     * @throws java.sql.SQLException
     */
    public Map<String, Double> CAParDateEtClient(String dateD, String dateF) throws SQLException {
        Map<String, Double> resultat = new HashMap<>();
        String sql = "SELECT CUSTOMER_ID, PRODUCT_ID, QUANTITY FROM PURCHASE_ORDER"
                + " WHERE SHIPPING_DATE BETWEEN ? AND ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date debut = null;
            Date fin = null;
            try {
                debut = sdf.parse(dateD);
            } catch (ParseException e1) {
                // TODO Auto-generated catch block

            }
            try {
                fin = sdf.parse(dateF);
            } catch (ParseException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
            java.sql.Date data1 = new java.sql.Date(debut.getTime());
            java.sql.Date data2 = new java.sql.Date(fin.getTime());
            stmt.setDate(1, data1);
            stmt.setDate(2, data2);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String client = nameCustomer(rs.getInt("CUSTOMER_ID"));
                double prix = rs.getDouble("QUANTITY") * recupererPrix(rs.getInt("PRODUCT_ID"));
                if (resultat.containsKey(client)) {
                    resultat.put(client, prix + resultat.get(client));
                    System.out.println("nouveau chiffre d'affaire  " + client + " est de " + resultat.get(client));
                } else {
                    resultat.put(client, prix);
                    System.out.println("chiffre d'affaire = " + client + " est de " + prix);
                }

            }

        }
        return resultat;
    }


    // Retourne l'id du produit
    public int numProduit(String prod) throws SQLException {
        int result = 0;

        String sql = "SELECT * FROM PRODUCT WHERE DESCRIPTION LIKE ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, prod);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                result = rs.getInt("PRODUCT_ID");
            }
        }

        return result;
    }

    public boolean verifierSolde(int customer_id, int product_id, int quantity) throws SQLException {
        boolean resultat = false;
        double solde = this.montantDisponible(customer_id);
        if (solde >= prixCommande(quantity, product_id, customer_id)) {
            resultat = true;
            System.out.println("Voila le prix " + prixCommande(quantity, product_id, customer_id));
        }
        return resultat;
    }

    public int miseAJourSolde(int id, double prix) throws SQLException {
        int resultat = 0;
        String sql = "UPDATE CUSTOMER SET CREDIT_LIMIT=? WHERE CUSTOMER_ID=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(2, id);
            stmt.setInt(1, (int) (this.montantDisponible(id) - prix));
            resultat = stmt.executeUpdate();
        }
        return resultat;
    }

    public int numeroCommande() throws SQLException {
        List<Integer> resultat = new ArrayList<>();

        String sql = "SELECT ORDER_NUM FROM PURCHASE_ORDER";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ORDER_NUM");
                resultat.add(id);
            }
        }
        int numAlea = (int) (Math.random() * 20000);
        while (resultat.contains(numAlea)) {
            numAlea = (int) (Math.random() * 20000);
        }
        return numAlea;
    }

    public int ajouterCommande(int customer_id, int quantity, int product_id) throws SQLException {
        int resultat = 0;
        String sql = "INSERT INTO PURCHASE_ORDER (ORDER_NUM, CUSTOMER_ID, PRODUCT_ID, QUANTITY,SHIPPING_DATE) VALUES (?,?,?,?,?)";
    
       
            try (Connection connection = myDataSource.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)) {
                
                stmt.setInt(1, numeroCommande());
                stmt.setInt(2, customer_id);
                stmt.setInt(3, product_id);
                stmt.setInt(4, quantity);
                stmt.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
                
                System.out.println("Test num"+numeroCommande()+"  idUser "+customer_id+" prod "+product_id+" qte "+quantity+"  date"+java.sql.Date.valueOf(java.time.LocalDate.now()) );
                resultat = stmt.executeUpdate();
            }
            
     
        return resultat;

    }
    
    
    public int ancienneQuantite(int order_num) throws SQLException {
        int resultat = 0;
        String sql = "SELECT QUANTITY FROM PURCHASE_ORDER WHERE ORDER_NUM = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order_num);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultat = rs.getInt("QUANTITY");
            }
        }
        return resultat;
    }


    public int clientParNumCommande(int order_num) throws SQLException {
        int resultat = 0;

        String sql = "SELECT CUSTOMER_ID FROM PURCHASE_ORDER WHERE ORDER_NUM = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order_num);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultat = rs.getInt("CUSTOMER_ID");
            }
        }
        return resultat;
    }

    public int produitParNumCommande(int order_num) throws SQLException {
        int resultat = 0;
        String sql = "SELECT PRODUCT_ID FROM PURCHASE_ORDER WHERE ORDER_NUM=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order_num);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultat = rs.getInt("PRODUCT_ID");
            }
        }
        return resultat;
    }

    public boolean editerCommande(int order_num, int quantity, int customer_id) throws SQLException {
        boolean resultat = false;
        int ancienneQuantite = this.ancienneQuantite(order_num);
        if (ancienneQuantite >= ancienneQuantite(order_num)) {
            
            String sql = "UPDATE PURCHASE_ORDER SET QUANTITY=? WHERE ORDER_NUM=?";
            try (Connection connection = myDataSource.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, quantity);
                stmt.setInt(2, order_num);
                int result = stmt.executeUpdate();
                resultat = true;

            }

        } else {
            int difference = quantity - ancienneQuantite;
            if (this.verifierSolde(customer_id, this.produitParNumCommande(order_num), difference)) {
                this.miseAJourSolde(customer_id, prixCommande(difference, produitParNumCommande(order_num), customer_id));
                String sql = "UPDATE PURCHASE_ORDER SET QUANTITY=? WHERE ORDER_NUM=?";
                try (Connection connection = myDataSource.getConnection();
                        PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, quantity);
                    stmt.setInt(2, order_num);
                    int result = stmt.executeUpdate();
                    resultat = true;
                }
            }

        }
        return resultat;
    }

    public int quantiteProduit(int order_num) throws SQLException {
        int resultat = 0;
        String sql = "SELECT QUANTITY FROM PURCHASE_ORDER WHERE ORDER_NUM=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order_num);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultat = rs.getInt("QUANTITY");

            }

        }
        return resultat;
    }

    public int supprimerCommande(int order_num) throws SQLException {
        int resultat = 0;
        
        String sql = "DELETE FROM PURCHASE_ORDER WHERE ORDER_NUM=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order_num);
            resultat = stmt.executeUpdate();
        }
        return resultat;
    }

    /**
     *
     * @param deb date de début d'analyse
     * @param fin date de fin d'analyse
     * @return le chiffre d'affaires représenté par état
     * @throws SQLException
     */
    public Map<String, Double> chiffreAffaireParEtat(String deb, String fin) throws SQLException {
        Map<String, Double> ret = new HashMap<>();
        String sql = "SELECT PRODUCT_ID, CUSTOMER_ID, QUANTITY, STATE FROM PURCHASE_ORDER"
                + " INNER JOIN CUSTOMER"
                + " USING (CUSTOMER_ID)"
                + " WHERE SHIPPING_DATE BETWEEN ? AND ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed1 = null;
            Date parsed2 = null;
            try {
                parsed1 = sdf.parse(deb);
            } catch (ParseException e1) {
                // TODO Auto-generated catch block

            }
            try {
                parsed2 = sdf.parse(fin);
            } catch (ParseException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
            java.sql.Date data1 = new java.sql.Date(parsed1.getTime());
            java.sql.Date data2 = new java.sql.Date(parsed2.getTime());

            stmt.setDate(1, data1);
            stmt.setDate(2, data2);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String state = rs.getString("STATE");
                double price = rs.getDouble("QUANTITY") * recupererPrix(rs.getInt("PRODUCT_ID"));
                if (ret.containsKey(state)) {
                    ret.put(state, ret.get(state) + price);
                    System.out.println("nouveau ca  " + state + " est de " + ret.get(state));
                } else {
                    ret.put(state, price);
                    System.out.println("ca = " + state + " est de " + price);
                }

            }
        }

        return ret;
    }

    public ArrayList<Product> listProduct() throws SQLException {

        ArrayList<Product> result = new ArrayList<>();

        String sql = "SELECT * FROM PRODUCT WHERE QUANTITY_ON_HAND > 0";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("PRODUCT_ID");
                String des = rs.getString("DESCRIPTION");
                double price = rs.getDouble("PURCHASE_COST");
                Product c = new Product(id);
                c.setDESCRIPTION(des);
                c.setPURCHASE_COST(price);
                result.add(c);

            }
        }
        return result;
    }

}
