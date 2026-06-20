package com.association.servlet.membre;

import com.association.model.Membre;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/dashboard/membre")
public class DashboardMembreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        Membre membre = (Membre) request.getSession()
                                        .getAttribute("membreConnecte");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css'>");
        out.println("</head><body class='p-4'>");

        // Message si accès refusé
        if ("refuse".equals(request.getParameter("acces"))) {
            out.println("<div class='alert alert-danger'>");
            out.println("⛔ Accès refusé. Vous n'avez pas les droits admin.");
            out.println("</div>");
        }

        out.println("<div class='alert alert-info'>");
        out.println("<h4>✅ Connexion réussie !</h4>");
        out.println("<p>Bienvenue <strong>" +
                    membre.getNomComplet() + "</strong></p>");
        out.println("<p>Rôle : <span class='badge bg-primary'>" +
                    membre.getRole() + "</span></p>");
        out.println("</div>");
        out.println("<p>Le dashboard membre complet sera construit en Phase 4.</p>");
        out.println("<a href='" + request.getContextPath() +
                    "/logout' class='btn btn-outline-danger'>Se déconnecter</a>");
        out.println("</body></html>");
    }
}