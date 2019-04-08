/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
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
        //Ouvrir un session appelle cette servlet
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

        try {
            Customer c = new Customer();
            c.setPassword(password);
            newSession.setAttribute("codes", voirCodesClient(request));
            switch (evenement) {
                case "Ajout_Commande":
                    dao.ajouterCommande(Integer.parseInt(password), Integer.parseInt(quantite), dao.numProduit(request.getParameter("produit")));
                    newSession.setAttribute("commandes", dao.commandesClient(c));
                    request.setAttribute("message", "Vous avez commandez " + quantite + " " + request.getParameter("produit") + ".");
                    request.getRequestDispatcher("WEB-INF/customer.jsp").forward(request, response);
                    break;
                case "Edit_Commande":
                    try {
                        String quantityToEdit = request.getParameter("quantityToEdit");
                        dao.editerCommande(Integer.parseInt(purchaseToEdit), Integer.parseInt(quantityToEdit), Integer.parseInt(password));
                        request.setAttribute("message", "Commande " + purchaseToEdit + " modifiée");
                        newSession.setAttribute("commandes", dao.commandesClient(c));
                        request.getRequestDispatcher("WEB-INF/customer.jsp").forward(request, response);
                    } catch (SQLIntegrityConstraintViolationException e) {
                        request.setAttribute("message", "Impossible de modifier " + purchaseToEdit + ", cette commande.");
                    }
                    break;
                case "Supprimer_Commande":
                    try {
                        dao.supprimerCommande(Integer.parseInt(purchaseToDelete));
                        newSession.setAttribute("commandes", dao.commandesClient(c));
                        request.setAttribute("message", "Commande " + purchaseToDelete + " Supprimée");
                        request.getRequestDispatcher("WEB-INF/customer.jsp").forward(request, response);
                    } catch (SQLIntegrityConstraintViolationException e) {
                        request.setAttribute("message2", "Impossible de supprimer " + purchaseToDelete + ", cette commande.");
                    }
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

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
