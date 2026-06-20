package com.association.servlet;

import com.association.dao.MembreDAO;
import com.association.model.Membre;
import com.association.model.Role;
import com.association.model.StatutMembre;
import com.association.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private MembreDAO membreDAO;

    @Override
    public void init() {
        membreDAO = new MembreDAO();
    }

    // GET → afficher la page de login
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Si déjà connecté, rediriger directement
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("membreConnecte") != null) {
            redirectSelon(session, response);
            return;
        }

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    // POST → traiter le formulaire
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email     = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        // ===== Validation basique =====
        if (email == null || email.trim().isEmpty() ||
            motDePasse == null || motDePasse.trim().isEmpty()) {
            request.setAttribute("erreur", "Email et mot de passe obligatoires.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // ===== Recherche du membre =====
        Membre membre = membreDAO.findByEmail(email.trim().toLowerCase());

        // ===== Vérifications =====
        if (membre == null) {
            request.setAttribute("erreur", "Email ou mot de passe incorrect.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        if (!PasswordUtil.verifier(motDePasse, membre.getMotDePasse())) {
            request.setAttribute("erreur", "Email ou mot de passe incorrect.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        if (!StatutMembre.ACTIF.equals(membre.getStatut())) {
            request.setAttribute("erreur",
                "Votre compte est inactif. Contactez l'administrateur.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // ===== Création de la session =====
        // Invalider l'ancienne session si elle existe
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) oldSession.invalidate();

        // Créer une nouvelle session
        HttpSession session = request.getSession(true);
        session.setAttribute("membreConnecte", membre);
        session.setAttribute("membreId",       membre.getId());
        session.setAttribute("membreNom",      membre.getNomComplet());
        session.setAttribute("membreRole",     membre.getRole().name());
        session.setMaxInactiveInterval(30 * 60); // 30 minutes

        // ===== Redirection selon le rôle =====
        redirectSelon(session, response);
    }

    // Redirige selon le rôle stocké en session
    private void redirectSelon(HttpSession session,
                                HttpServletResponse response)
            throws IOException {
        String role = (String) session.getAttribute("membreRole");
        if (Role.ADMIN.name().equals(role)) {
            response.sendRedirect(
                response.encodeRedirectURL("dashboard/admin"));
        } else {
            response.sendRedirect(
                response.encodeRedirectURL("dashboard/membre"));
        }
    }
}