/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author usuario
 */
@WebServlet(name = "EjBServer", urlPatterns = {"/EjBServer"})
public class EjBServer extends HttpServlet {

    @EJB
    private EjBSessionBeanLocal ejBSessionBean;

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EjBServer</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EjBServer at " + request.getContextPath() + "</h1>");
            out.println("<a href='PublishMessage'> Publicar noticia</a>"); // Enlace a al servlet PublishMessage
            out.println("<br><br>"); // 2 saltos de línea
            LinkedList<String> lista = ejBSessionBean.getList(); // Obtenemos el histórico de noticias
            if (lista != null) { // Si no esta vacía, 
                for (int i = 0; i < lista.size(); i++) { // iteramos sobre la lista,
                    out.println("" + lista.get(i)); // Imprimimos la noticia
                    out.println("<br>"); // salto de línea
                }
            }
            out.println("<br>"); // salto de línea
            out.println("" + ejBSessionBean.getNumber()); // Imprimimos el número total de noticias
            out.println("</body>");
            out.println("</html>");
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
        processRequest(request, response);
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
        processRequest(request, response);
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

}
