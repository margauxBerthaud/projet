/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Customer;
import model.DAO;
import model.DiscountCode;
import model.Product;

/**
 *
 * @author nvollhes
 */
@WebServlet(name = "CustomerController", urlPatterns = {"/CustomerController"})
public class CustomerController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        //Ouvrir une session appelle cette servlet
        HttpSession session = request.getSession();
        DAO dao = new DAO();
        //int qte = Integer.parseInt(request.getParameter("qte"));
        String action = request.getParameter("action");
        action = (action == null)? "" : action;
        //Ajouter commandes
        
        ArrayList<String> list = (ArrayList<String>) dao.tousLesProduits();
        request.setAttribute("listeProduits", list);
        

        //Edition commande
        String purchaseToEdit = request.getParameter("purchaseToEdit");

        // Suppression Commandes
        String purchaseToDelete = request.getParameter("purchaseToDelete");
        String password = (String) session.getAttribute("userPassword");

        //Information sur le client connecté
        Double solde = dao.montantDisponible(Integer.parseInt(password));
        session.setAttribute("solde", solde);
        System.out.println("action  "+action);
        request.setAttribute("codes", voirCodesClient(request));
        try {
            Customer c = new Customer();
            c.setPassword(password);
            session.setAttribute("codes", voirCodesClient(request));
            switch (action) {
                
                case "ADD_COMMANDE":
                    String quantite = request.getParameter("quantite");
                    System.out.println("Test id: "+Integer.parseInt(password)+"  qte "+quantite+"    prod "+dao.numProduit(request.getParameter("produit")));
                    dao.ajouterCommande(Integer.parseInt(password), Integer.parseInt(quantite), dao.numProduit(request.getParameter("produit")));
                    session.setAttribute("commande", dao.commandesClient(c));

                    System.out.println("Test action: "+action+"  qte "+quantite);
                    solde = dao.montantDisponible(Integer.parseInt(password));
                    session.setAttribute("solde", solde);
                    request.setAttribute("message", "Vous avez commandez " + quantite + " " + request.getParameter("produit") + ".");
                    request.getRequestDispatcher("WEB-INF/customer.jsp").forward(request, response);
                    break;
                    
                    
                case "EDIT_COMMANDE":
                    try {
                        String quantityToEdit = request.getParameter("quantityToEdit");
                        dao.editerCommande(Integer.parseInt(purchaseToEdit), Integer.parseInt(quantityToEdit), Integer.parseInt(password));
                        if (dao.editerCommande(Integer.parseInt(purchaseToEdit), Integer.parseInt(quantityToEdit), Integer.parseInt(password))) {
                            request.setAttribute("message", "Commande " + purchaseToEdit + " a été modifié");
                        } else {
                            request.setAttribute("message", "Pas assez d'argent" + purchaseToEdit);
                        }
                        session.setAttribute("commande", dao.commandesClient(c));
                        solde = dao.montantDisponible(Integer.parseInt(password));
                        session.setAttribute("solde", solde);
                        request.getRequestDispatcher("WEB-INF/customer.jsp").forward(request, response);

                    } catch (SQLIntegrityConstraintViolationException e) {
                        request.setAttribute("message", "Modification impossible " + purchaseToEdit + ", cette commande est utilisée.");
                    }
                    break;
                    
                    
                case "DELETE_COMMANDE":
                    try {
                        System.out.println("hooooooooooo "+purchaseToDelete);
                        dao.supprimerCommande(Integer.parseInt(purchaseToDelete));
                        session.setAttribute("commande", dao.commandesClient(c));
                        request.setAttribute("message", "Commande " + purchaseToDelete + " Supprimée");
                        request.getRequestDispatcher("WEB-INF/customer.jsp").forward(request, response);
                        
                    } catch (SQLIntegrityConstraintViolationException e) {
                        request.setAttribute("message2", "Impossible de supprimer " + purchaseToDelete + ", cette commande.");
                    }
                    break;
                    
                case "DO_VIREMENT":
                    try {
                        int montantVerser = Integer.parseInt(request.getParameter("montant"));
                        dao.virement(Integer.parseInt(password), montantVerser);
                        solde = dao.montantDisponible(Integer.parseInt(password));
                        session.setAttribute("solde", solde);
                        request.setAttribute("message", "Virement de : " + montantVerser + "$ débiter sur votre compte client.");
                        request.getRequestDispatcher("WEB-INF/customer.jsp").forward(request, response);
                    } catch (SQLIntegrityConstraintViolationException e) {

                    }
                    break;
                    
                case "SHOW_PRODUIT":
                    System.out.println("action  "+action);
                    ArrayList<Product> listeProduit = dao.listProduct();
                    session.setAttribute("listeProduit", listeProduit);
                    request.getRequestDispatcher("WEB-INF/produits.jsp").forward(request, response);
                    break;

                case "SHOW_CLIENT":
                    request.getRequestDispatcher("WEB-INF/customer.jsp").forward(request, response);
                    break;

            }
        } catch (Exception ex) {
            Logger.getLogger("CustomerController").log(Level.SEVERE, "Action en erreur", ex);
            request.setAttribute("message", ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    	private String trouverUserDansSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session == null) ? null : (String) session.getAttribute("userName");
	}
        
        private void supprimerCode(String code) throws SQLException {
		DAO dao = new DAO();
                dao.deleteDiscountCode(code);
		
	}

    private List<DiscountCode> voirCodesClient(HttpServletRequest request) throws SQLException {
        List<DiscountCode> listCustomerCode = new LinkedList();
        DAO dao = new DAO();
        HttpSession newSession = request.getSession();
        String password = (String) newSession.getAttribute("userPassword");
        Customer c = new Customer();
        c.setPassword(password);
        listCustomerCode = (List<DiscountCode>) dao.codesClients(c);
        return listCustomerCode;
    }

}
