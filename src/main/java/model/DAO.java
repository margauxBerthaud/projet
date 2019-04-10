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
        DataSource dataSource = null;
        this.myDataSource = dataSource;
    }

    public int faireVirement(int id, double montant) throws SQLException {
        int resultat = 0;
        String sql = "UPDATE CUSTOMER SET CREDIT_LIMIT=? WHERE CUSTOMER_ID=?";
        try (
                Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, (int) (this.montantDisponible(id) + montant));
            stmt.setInt(2, id);
            resultat = stmt.executeUpdate();
        }
        return resultat;

    }

    /**
     * Fonction permettant de récupérer la valeur du taux de remise en passant
     * en paramètre le client
     */
    private float valeur_discount_code(int customerID) throws SQLException {
        float discount = 0;
        String sql = "SELECT DISCOUNT_CODE.RATE FROM DISCOUNT_CODE INNER JOIN CUSTOMER ON DISCOUNT_CODE.DISCOUNT_CODE=CUSTOMER.DISCOUNT_CODE WHERE CUSTOMER_ID=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                float taux = rs.getByte("RATE");
                discount = taux;
            }
            return discount;
        }
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
    public float prixCommande(int id_p, int quantite, int id_c) throws SQLException {
        float resultat = 0;
        String sql = "SELECT PRODUCT.PURCHASE_COST FROM PRODUCT WHERE PRODUCT_ID=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            stmt.setInt(1, id_p);
            while (rs.next()) {
                resultat = (rs.getFloat("PURCHASE_COST") * quantite) * (100 - valeur_discount_code(id_p) / 100);

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
        String sql = "SELECT ORDER_NUM, CUSTOMER_ID, PRODUCT_ID, QUANTITY, SHIPPING_COST, SALES_DATE, SHIPPING_DATE, PRODUCT.DESCRIPTION FROM CUSTOMER INNER JOIN PURCHASE_ORDER ON CUSTOMER.ID=PURCHASE_ORDER.CUSTOMER_ID WHERE CUSTOMER_ID=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int numComm = rs.getInt("ORDER_NUM");
                int numClient = rs.getInt("CUSTOMER_ID");
                int numProduit = rs.getInt("PRODUCT_ID");
                int quantite = rs.getInt("QUANTITY");
                float remise = prixCommande(numClient, quantite, numProduit);
                Date date = rs.getDate("SHIPPING_DATE");
                String description = rs.getString("DESCRIPTION");
                PurchaseOrder commande = new PurchaseOrder(numComm, numClient, quantite);
                commande.setSHIPPING_DATE(date);
                commande.setSHIPPING_COST(remise);
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
    public List<String> tousLesProduits() throws SQLException {
        List<String> resultat = new LinkedList<>();
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
        int resultat = 0;
        String sql = "SELECT CREDIT_LIMIT FROM CUSTOMER WHERE CUSTOMER_ID=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
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
    public String infosDisponibles(Customer c) throws SQLException {
        String resultat = null;
        String sql = "SELECT NAME, ADRESSLINE1, ADRESSLINE2, CITY, STATE, PHONE, FAX, EMAIL FROM CUSTOMER WHERE CUSTOMER_ID=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nom = rs.getString("NAME");
                String adresse1 = rs.getString("ADRESSLINE1");
                String adresse2 = rs.getString("ADRESSLINE2");
                String ville = rs.getString("CITY");
                String etat = rs.getString("STATE");
                String tel = rs.getString("PHONE");
                String fax = rs.getString("FAX");
                String email = rs.getString("EMAIL");
                String newLine = System.getProperty("line.separator");
                resultat = " " + nom + newLine + adresse1 + " " + adresse2 + newLine + ville + newLine + etat + newLine + tel + newLine + fax + newLine + email;
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
        String resultat = null;
        String sql = "SELECT DESCRIPTION FROM PRODUCT_CODE WHERE PROD_CODE=?";
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
    public float recupererPrix(int product_id) throws SQLException {
        float resultat = 0;
        String sql = "SELECT PURCHASE_COST FROM PRODUCT WHERE PRODUCT_ID=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultat = rs.getFloat("PURCHASE_COST");
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
        String sql = "SELECT PROD_CODE, SUM(QUANTITY) AS SALES \n"
                + "FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PRODUCT.PRODUCT_ID=PURCHASE_ORDER.PRODUCT_ID \n"
                + "INNER JOIN PRODUCT_CODE ON PRODUCT.PRODUCT_CODE=PRODUCT_CODE.PROD_CODE  \n"
                + "WHERE SHIPPING_DATE>=? AND SHIPPING_DATE<=?  GROUP BY PROD_CODE";
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
                String produit = getDescription(rs.getInt("PROD_CODE"));
                double prix = rs.getDouble("SALES") * recupererPrix(rs.getInt("PRODUCT_ID"));
                resultat.put(produit, prix);

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
    public Customer connexionClient(String email) throws SQLException {
        Customer c = new Customer();
        String sql = "SELECT * FROM CUSTOMER WHERE EMAIL=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {

                c.setEMAIL("nodata");
                c.setPassword("nodata");
                c.setNAME("nodata");

                return c;
            } else {
                do {

                    c.setEMAIL(email);
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

    /**
     * Fonction permettant de connaître tous les codes que possèdent un client
     *
     * @return
     * @throws java.sql.SQLException
     */
    public List<DiscountCode> codesClients(Customer c) throws SQLException {
        List<DiscountCode> dc = new LinkedList<>();
        int id = Integer.parseInt(c.getPassword());
        String sql = "SELECT * FROM DISCOUNT_CODE INNER JOIN CUSTOMER USING DISCOUNT_CODE WHERE CUSTOMER_ID=? ";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String discount = rs.getString("DISCOUNT_CODE");
                float rate = rs.getFloat("RATE");
                DiscountCode d = new DiscountCode(discount, rate);
                dc.add(d);
            }
        }
        return dc;
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
        String sql = "SELECT CUSTOMER.NAME, SUM(QUANTITY) AS SALES \n"
                + "FROM CUSTOMER INNER JOIN PURCHASE_ORDER ON CUSTOMER.CUSTOMER_ID=PURCHASE_ORDER.CUSTOMER_ID\n"
                + "INNER JOIN PRODUCT ON PRODUCT.PRODUCT_ID=PURCHASE_ORDER.PRODUCT_ID\n"
                + "WHERE SHIPPING_DATE<=? AND SHIPPING_DATE>=?  GROUP BY CUSTOMER.\"NAME\";";
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
                String client = rs.getString("NAME");
                double prix = rs.getDouble("SALES") * recupererPrix(rs.getInt("PRODUCT_ID"));
                if (resultat.containsKey(client)) {
                    resultat.put(client, prix + resultat.get(client));
                } else {
                    resultat.put(client, prix);
                }

            }

        }
        return resultat;
    }

    /**
     * Fonction permettant de connaître le chiffre d'affaire en fonction de la
     * zone géographique du client
     *
     * @param dateD
     * @param dateF
     * @return
     * @throws java.sql.SQLException
     */
    public Map<String, Double> CAParDateEtZoneClient(String dateD, String dateF) throws SQLException {
        Map<String, Double> resultat = new HashMap<>();
        String sql = "SELECT CITY,COUNT(QUANTITY) AS SALES FROM CUSTOMER \n"
                + "                INNER JOIN PURCHASE_ORDER ON CUSTOMER.CUSTOMER_ID=PURCHASE_ORDER.CUSTOMER_ID\n"
                + "                INNER JOIN PRODUCT ON PRODUCT.PRODUCT_ID=PURCHASE_ORDER.PRODUCT_ID\n"
                + "                WHERE SALES_DATE<=? AND SALES_DATE>=?\n"
                + "                GROUP BY CITY";
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
                String ville = rs.getString("CITY");
                double prix = rs.getFloat("SALES") * recupererPrix(rs.getInt("PRODUCT_ID"));
                if (resultat.containsKey(ville)) {
                    resultat.put(ville, resultat.get(ville) + prix);
                } else {
                    resultat.put(ville, prix);
                }

            }
        }
        return resultat;
    }

    /**
     * Fonction permettant de connaître le chiffre d'affaire en fonction de la
     * zone géographique du producteur
     *
     * @param dateD
     * @param dateF
     * @return
     * @throws java.sql.SQLException
     */
    public Map<String, Double> CAParDateEtZoneProducteur(String dateD, String dateF) throws SQLException {
        Map<String, Double> resultat = new HashMap<>();
        String sql = "SELECT MANUFACTURER.CITY,COUNT(QUANTITY) AS SALES FROM CUSTOMER \n"
                + "                INNER JOIN PURCHASE_ORDER ON CUSTOMER.CUSTOMER_ID=PURCHASE_ORDER.CUSTOMER_ID\n"
                + "                INNER JOIN PRODUCT ON PRODUCT.PRODUCT_ID=PURCHASE_ORDER.PRODUCT_ID\n"
                + "                INNER JOIN  MANUFACTURER ON PRODUCT.MANUFACTURER_ID=MANUFACTURER.MANUFACTURER_ID\n"
                + "                WHERE SALES_DATE<=? AND SALES_DATE>=?\n"
                + "                GROUP BY CITY";
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
                String ville = rs.getString("MANUFACTURER.CITY");
                double prix = rs.getFloat("SALES") * recupererPrix(rs.getInt("PRODUCT_ID"));
                if (resultat.containsKey(ville)) {
                    resultat.put(ville, resultat.get(ville) + prix);
                } else {
                    resultat.put(ville, prix);
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
        if (solde >= prixCommande(product_id, quantity, customer_id)) {
            resultat = true;
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
        int numAlea = (int) (Math.random() * 10000);
        while (resultat.contains(numAlea)) {
            numAlea = (int) (Math.random() * 10000);
        }
        return numAlea;
    }

    public int ajouterCommande(int customer_id, int quantity, int product_id) throws SQLException {
        int resultat = 0;
        String sql = "INSERT INTO PURCHASE_ORDER (ORDER_NUM, CUSTOMER_ID, PRODUCT_ID, QUANTITY,SHIPPING_DATE) VALUES (?,?,?,?,?)";
        if (verifierSolde(customer_id, product_id, quantity) == true) {
            this.miseAJourSolde(customer_id, prixCommande(product_id, quantity, customer_id));
            try (Connection connection = myDataSource.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, numeroCommande());
                stmt.setInt(2, customer_id);
                stmt.setInt(3, product_id);
                stmt.setInt(4, quantity);

                stmt.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
                resultat = stmt.executeUpdate();
            }
        } else {
            throw new SQLException("Vous n'avez pas assez d'argent disponible");

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

    public int virement(int customer_id, double montant) throws SQLException {
        int resultat = 0;
        String sql = "UPDATE CUSTOMER SET CREDIT_LIMIT=? WHERE CUSTOMER_ID=?";
        try (
                Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, (int) (montantDisponible(customer_id) + montant));
            stmt.setInt(2, customer_id);
            resultat = stmt.executeUpdate();
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
        if (quantity >= ancienneQuantite(order_num)) {
            this.virement(this.clientParNumCommande(order_num), this.prixCommande(this.produitParNumCommande(order_num), ancienneQuantite - quantity, this.clientParNumCommande(order_num)));
            String sql = "UPDATE PURCHASE_ORDER SET QUANTITY=? WHERE ORDER_NUM=?";
            try (Connection connection = myDataSource.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, quantity);
                stmt.setInt(2, order_num);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    resultat = true;
                    int result = stmt.executeUpdate();
                }

            }

        } else {
            int difference = quantity - ancienneQuantite;
            if (this.verifierSolde(customer_id, this.produitParNumCommande(order_num), difference)) {
                this.miseAJourSolde(customer_id, prixCommande(produitParNumCommande(order_num), difference, customer_id));
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
        this.virement(this.clientParNumCommande(order_num), this.prixCommande(this.produitParNumCommande(order_num), this.quantiteProduit(order_num), this.clientParNumCommande(order_num)));
        String sql = "DELETE FROM PURCHASE_ORDER WHERE ORDER_NUM=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order_num);
            resultat = stmt.executeUpdate();
        }
        return resultat;
    }
}
