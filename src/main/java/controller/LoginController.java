package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Customer;
import model.DAO;
import model.DiscountCode;
import model.PurchaseOrder;

public class LoginController extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException, SQLException {
		// Quelle action a appelé cette servlet ?
		String action = request.getParameter("action");
                response.setContentType("text/html;charset=UTF-8");
		if (null != action) {
			switch (action) {
				case "login":
                                     System.out.println("login");
					checkLogin(request);
                                       
					break;
				case "Logout":
					doLogout(request);
					break;
			}
		}

		// Est-ce que l'utilisateur est connecté ?
		// On cherche l'attribut userName dans la session
		String userName = findUserInSession(request);
		String jspView = null;
		if (null == userName) { // L'utilisateur n'est pas connecté
			// On choisit la page de login
			jspView = "login.jsp";

		}  else if (!"admin".equals(userName)){
                    jspView = "WEB-INF/customer.jsp";

                
                } 
                else if("admin".equals(userName)) { // L'utilisateur est connecté
			// On choisit la page d'affichage
			jspView = "WEB-INF/admin.jsp";
		}
		// On va vers la page choisie
		request.getRequestDispatcher(jspView).forward(request, response);

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


	private void checkLogin(HttpServletRequest request) throws SQLException {
                // Interaction avec la base de données
                DAO dao = new DAO();
                CustomerController cc = new CustomerController();
		// Les paramètres transmis dans la requête
		String loginParam = request.getParameter("loginParam");
		String passwordParam = request.getParameter("passwordParam");

                // Login de l'administrateur
		if ((loginParam.equals("admin@admin") && (passwordParam.equals("admin")))) {
			// On a trouvé la combinaison login / password
			// On stocke l'information dans la session
			HttpSession session = request.getSession(true); // démarre la session
			session.setAttribute("userName", "admin");
		} 
                else if(!"".equals(loginParam) && !"".equals(passwordParam)){
                   Customer c = dao.connexionClient(loginParam);
                  
                // On récupère les propriétés du client 
                   String login = c.getEMAIL();
                   String password = c.getPassword();
                   String userName = c.getNAME();
                   String phone = c.getPHONE();
                   String address = c.getADDRESSLINE1();
                   
                   // Si l'email et le mot de passe correspondent, le client peut se connecter
                    if ((login.equals(loginParam) && (password.equals(passwordParam)))) {
                    // On stocke l'information dans la session
			HttpSession session = request.getSession(true); // démarre la session
			session.setAttribute("userName", userName);
                        session.setAttribute("userEmail", login);
                        session.setAttribute("userPassword", password);
                        session.setAttribute("userAddress", address);
                        session.setAttribute("userPhone", phone);
                        session.setAttribute("commande", dao.commandesClient(c));  
                        ArrayList<String> des = dao.tousLesProduits();
                        request.setAttribute("listeProduits", des);
                        Double solde = dao.montantDisponible(Integer.parseInt(password));
                        session.setAttribute("solde", solde);
                    } 
                    else if (login.equals("nodata")){
                        request.setAttribute("errorMessage", "Login/Password incorrect");
                    }
                    else if ("".equals(loginParam) || "".equals(passwordParam)){ // On positionne un message d'erreur pour l'afficher dans la JSP
                            request.setAttribute("errorMessage", "Login/Password incorrect");
                    }
                    else{
                        request.setAttribute("errorMessage", "Login/Password incorrect");
                    }

	}
    }

	private void doLogout(HttpServletRequest request) {
		// On termine la session
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}

	private String findUserInSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session == null) ? null : (String) session.getAttribute("userName");
	}
        
        public List<PurchaseOrder> viewCommandes(HttpServletRequest request) throws SQLException {
            List<PurchaseOrder> result = new LinkedList<>();
            DAO dao= new DAO();
            HttpSession session = request.getSession();
            String password = ((String)session.getAttribute("userPassword"));
            Customer c = new Customer();
            c.setPassword(password);
            result = dao.commandesClient(c);                    
            return result;
         }
         
}
