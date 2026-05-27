package com.association.servlet;

import com.association.util.JPAUtil;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/test-connexion")
public class TestConnexionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            EntityManager em = JPAUtil.getEntityManager();
            em.close();
            out.println("<h2 style='color:green'>✅ Connexion MySQL OK !</h2>");
            out.println("<p>JPA et Hibernate sont bien configurés.</p>");
        } catch (Exception e) {
            out.println("<h2 style='color:red'>❌ Erreur de connexion</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }
}