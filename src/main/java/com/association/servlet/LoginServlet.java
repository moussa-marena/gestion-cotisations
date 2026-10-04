package com.association.servlet;

import com.association.dao.LoginHistoryDAO;
import com.association.dao.MembreDAO;
import com.association.model.LoginHistory;
import com.association.model.Membre;
import com.association.model.Role;
import com.association.model.StatutMembre;
import com.association.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger log =
        LoggerFactory.getLogger(LoginServlet.class);

    private MembreDAO        membreDAO;
    private LoginHistoryDAO  historyDAO;

    @Override
    public void init() {
        membreDAO  = new MembreDAO();
        historyDAO = new LoginHistoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null &&
            session.getAttribute("membreConnecte") != null) {
            redirectSelon(session, response);
            return;
        }
        request.getRequestDispatcher("/login.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email      = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");
        String ip         = getClientIp(request);

        // ===== Validation basique =====
        if (email == null || email.trim().isEmpty() ||
            motDePasse == null || motDePasse.trim().isEmpty()) {

            log.warn("Tentative de connexion avec champs vides — IP: {}", ip);
            request.setAttribute("erreur",
                "Email et mot de passe obligatoires.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/login.jsp")
                   .forward(request, response);
            return;
        }

        String emailNorm = email.trim().toLowerCase();

        // ===== Recherche du membre =====
        Membre membre = membreDAO.findByEmail(emailNorm);

        if (membre == null) {
            log.warn("Connexion échouée — email inconnu: {} — IP: {}",
                emailNorm, ip);
            historyDAO.save(new LoginHistory(
                emailNorm, ip, "ECHEC",
                "Email inconnu", null));
            request.setAttribute("erreur",
                "Email ou mot de passe incorrect.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/login.jsp")
                   .forward(request, response);
            return;
        }

        if (!PasswordUtil.verifier(motDePasse, membre.getMotDePasse())) {
            log.warn("Connexion échouée — mauvais MDP: {} — IP: {}",
                emailNorm, ip);
            historyDAO.save(new LoginHistory(
                emailNorm, ip, "ECHEC",
                "Mot de passe incorrect", membre));
            request.setAttribute("erreur",
                "Email ou mot de passe incorrect.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/login.jsp")
                   .forward(request, response);
            return;
        }

        if (!StatutMembre.ACTIF.equals(membre.getStatut())) {
            log.warn("Connexion refusée — compte inactif: {} — IP: {}",
                emailNorm, ip);
            historyDAO.save(new LoginHistory(
                emailNorm, ip, "ECHEC",
                "Compte inactif", membre));
            request.setAttribute("erreur",
                "Votre compte est inactif. " +
                "Contactez l'administrateur.");
            request.getRequestDispatcher("/login.jsp")
                   .forward(request, response);
            return;
        }

        // ===== Connexion réussie =====
        log.info("Connexion réussie — {} ({}) — IP: {}",
            membre.getNomComplet(), membre.getRole(), ip);

        historyDAO.save(new LoginHistory(
            emailNorm, ip, "SUCCES",
            "Connexion réussie", membre));

        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) oldSession.invalidate();

        HttpSession session = request.getSession(true);
        session.setAttribute("membreConnecte", membre);
        session.setAttribute("membreId",       membre.getId());
        session.setAttribute("membreNom",      membre.getNomComplet());
        session.setAttribute("membreRole",     membre.getRole().name());
        session.setMaxInactiveInterval(30 * 60);

        redirectSelon(session, response);
    }

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

    // Récupère la vraie IP (derrière un proxy)
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}