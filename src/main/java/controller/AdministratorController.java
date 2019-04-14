/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DAO;
import model.Product;

/**
 *
 * @author nvollhes
 */
@WebServlet(name = "AdministratorController", urlPatterns = {"/AdministratorController"})
public class AdministratorController extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        // Trouver l'évenemment qui appelle cette servlet
        String action = request.getParameter("action"); 
        HttpSession session = request.getSession();
        DAO dao = new DAO();
        
        ArrayList<String> prod = dao.tousLesProduits();
        request.setAttribute("Listeproduits", prod);
        
        // pour le CA par produit
        String date_debut_prod = request.getParameter("date_debut_prod");
        String date_fin_prod = request.getParameter("date_fin_prod");
        
        // CA par Client
        String date_debut_clt= request.getParameter("date_debut_clt");
        String date_fin_clt = request.getParameter("date_fin_clt");
        
        // CA par catégorie d'article
        String date_debut_ctg= request.getParameter("date_debut_ctg");
        String date_fin_ctg = request.getParameter("date_fin_ctg");
        
        //Ca par zone géographique
        String date_debut_geo= request.getParameter("date_debut_geo");
        String date_fin_geo = request.getParameter("date_fin_geo");
        
        //CA par ZIP 
        String date_debut_zip= request.getParameter("date_debut_zip");
        String date_fin_zip = request.getParameter("date_fin_zip");
        
        if (null != action){
            switch (action){
                case "Logout":
                    doLogout(request);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    break;
                case "caByProductCode":
                    session.setAttribute("productCodeCA", dao.CAparDateEtCategorieProduit(date_debut_ctg, date_fin_ctg));
                    session.setAttribute("dateProductCode", "valable du " + date_debut_ctg+ " au " + date_fin_ctg);
                    
                    request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                    break;
                case "caByProduct":
                    session.setAttribute("productCA", dao.chiffreAffaireByProduct(date_debut_prod, date_fin_prod));
                    session.setAttribute("dateProduct", "du " + date_debut_prod + " au " + date_fin_prod);

                    request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                    break;
                case "caByGeo":
                    session.setAttribute("geoCA", dao.chiffreAffaireParEtat(date_debut_geo, date_fin_geo));
                    session.setAttribute("dateGeo", "du " + date_debut_geo + " au " + date_fin_geo);

                    request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                    break;
                case "caByCli":
                    session.setAttribute("cliCA", dao.CAParDateEtClient(date_debut_clt, date_fin_clt));
                    session.setAttribute("dateCli", "valade du " + date_debut_clt + " au " + date_fin_clt);
                    request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                    break;
                case "caByZip":
                    session.setAttribute("zipCA", dao.chiffreAffaireByZip(date_debut_zip, date_fin_zip));
                    session.setAttribute("datezip", "valade du " + date_debut_zip + " au " + date_fin_zip);
                    request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                    break;
            }
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(AdministratorController.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ParseException ex) {
            Logger.getLogger(AdministratorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */


    
    private void doLogout(HttpServletRequest request) {
        // On termine la session 
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
    }
    
    private String findUserInSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session == null) ? null : (String) session.getAttribute("userName");
    }
    

}
