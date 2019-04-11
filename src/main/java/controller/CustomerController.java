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
        //Ouvrir une session appelle cette servlet
        HttpSession newSession = request.getSession();
        DAO dao = new DAO();
        String evenement = request.getParameter("evenement");

        //Ajouter commandes
        String quantite = request.getParameter("quantite");
        ArrayList<String> list = (ArrayList<String>) dao.tousLesProduits();
        request.setAttribute("listeProduit", list);

        //Edition commande
        String purchaseToEdit = request.getParameter("purchaseToEdit");

        // Suppression Commandes
        String purchaseToDelete = request.getParameter("purchaseToDelete");
        String password = (String) newSession.getAttribute("userPassword");

        //Information sur le client connecté
        Double solde = dao.montantDisponible(Integer.parseInt(password));
        newSession.setAttribute("solde", solde);

        try {
            Customer c = new Customer();
            c.setPassword(password);
            newSession.setAttribute("codes", voirCodesClient(request));
            switch (evenement) {
                
                case "ADD_ORDER":
                    dao.ajouterCommande(Integer.parseInt(password), Integer.parseInt(quantite), dao.numProduit(request.getParameter("produit")));
                    newSession.setAttribute("commandes", dao.commandesClient(c));
                    solde = dao.montantDisponible(Integer.parseInt(password));
                    newSession.setAttribute("solde", solde);
                    request.setAttribute("message", "Vous avez commandez " + quantite + " " + request.getParameter("produit") + ".");
                    request.getRequestDispatcher("WEB-INF/customer.jsp").forward(request, response);
                    break;
                    
                    
                case "EDIT_ORDER":
                    try {
                        String quantityToEdit = request.getParameter("quantityToEdit");
                        dao.editerCommande(Integer.parseInt(purchaseToEdit), Integer.parseInt(quantityToEdit), Integer.parseInt(password));
                        if (dao.editerCommande(Integer.parseInt(purchaseToEdit), Integer.parseInt(quantityToEdit), Integer.parseInt(password))) {
                            request.setAttribute("message", "Commande " + purchaseToEdit + " a été modifié");
                        } else {
                            request.setAttribute("message", "Pas assez d'argent" + purchaseToEdit);
                        }
                        newSession.setAttribute("commandes", dao.commandesClient(c));
                        solde = dao.montantDisponible(Integer.parseInt(password));
                        newSession.setAttribute("solde", solde);
                        request.getRequestDispatcher("WEB-INF/customer.jsp").forward(request, response);

                    } catch (SQLIntegrityConstraintViolationException e) {
                        request.setAttribute("message", "Modification impossible " + purchaseToEdit + ", cette commande est utilisée.");
                    }
                    break;
                    
                    
                case "DELETE_ORDER":
                    try {
                        dao.supprimerCommande(Integer.parseInt(purchaseToDelete));
                        newSession.setAttribute("commandes", dao.commandesClient(c));
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
                        newSession.setAttribute("solde", solde);
                        request.setAttribute("message", "Virement de : " + montantVerser + "€ débiter sur votre compte client.");
                        request.getRequestDispatcher("WEB-INF/customer.jsp").forward(request, response);
                    } catch (SQLIntegrityConstraintViolationException e) {

                    }
                    break;
                    
                case "SHOW_PRODUIT":
                    List<String> listeProduit = dao.tousLesProduits();
                    newSession.setAttribute("listeProduit", listeProduit);
                    request.getRequestDispatcher("WEB-INF/produits.jsp").forward(request, response);
                    break;

                case "SHOW_CLIENT":
                    request.getRequestDispatcher("WEB-INF/customer.jsp").forward(request, response);
                    break;

            }
        } catch (Exception ex) {
            Logger.getLogger("customerController").log(Level.SEVERE, "Action en erreur", ex);
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

    private ArrayList<DiscountCode> voirCodesClient(HttpServletRequest request) throws SQLException {
        ArrayList<DiscountCode> listCustomerCode = new ArrayList();
        DAO dao = new DAO();
        HttpSession newSession = request.getSession();
        String password = (String) newSession.getAttribute("userPassword");
        Customer c = new Customer();
        c.setPassword(password);
        listCustomerCode = (ArrayList<DiscountCode>) dao.codesClients(c);
        return listCustomerCode;
    }

}
