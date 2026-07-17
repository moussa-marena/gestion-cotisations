package com.association.servlet.membre;

import com.association.model.Membre;
import com.association.service.MembreService;
import com.association.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/membre/profil")
public class ProfilServlet extends HttpServlet {

    private MembreService membreService;

    @Override
    public void init() {
        membreService = new MembreService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        Membre membre = (Membre) request.getSession()
                                        .getAttribute("membreConnecte");
        request.setAttribute("membre", membre);
        request.getRequestDispatcher(
            "/WEB-INF/views/membre/profil.jsp")
            .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        Membre membre = (Membre) request.getSession()
                                        .getAttribute("membreConnecte");
        String action = request.getParameter("action");

        if ("modifierProfil".equals(action)) {
            try {
                String telephone = request.getParameter("telephone");
                String adresse   = request.getParameter("adresse");

                membreService.modifier(
                    membre.getId(),
                    membre.getNom(),
                    membre.getPrenom(),
                    membre.getEmail(),
                    telephone,
                    adresse,
                    membre.getRole().name()
                );

                // Recharger le membre en session
                Membre updated = membreService.findById(membre.getId());
                request.getSession().setAttribute("membreConnecte", updated);

                response.sendRedirect(request.getContextPath()
                    + "/membre/profil?succes=Profil+mis+à+jour");

            } catch (Exception e) {
                request.setAttribute("erreur", e.getMessage());
                request.setAttribute("membre", membre);
                request.getRequestDispatcher(
                    "/WEB-INF/views/membre/profil.jsp")
                    .forward(request, response);
            }

        } else if ("changerMdp".equals(action)) {
            String ancienMdp   = request.getParameter("ancienMdp");
            String nouveauMdp  = request.getParameter("nouveauMdp");
            String confirmMdp  = request.getParameter("confirmMdp");

            try {
                if (!PasswordUtil.verifier(ancienMdp, membre.getMotDePasse())) {
                    throw new Exception("Ancien mot de passe incorrect.");
                }
                if (!nouveauMdp.equals(confirmMdp)) {
                    throw new Exception("Les nouveaux mots de passe ne correspondent pas.");
                }
                if (nouveauMdp.length() < 6) {
                    throw new Exception("Le mot de passe doit contenir au moins 6 caractères.");
                }

                membreService.changerMotDePasse(membre.getId(), nouveauMdp);
                response.sendRedirect(request.getContextPath()
                    + "/membre/profil?succes=Mot+de+passe+changé");

            } catch (Exception e) {
                request.setAttribute("erreurMdp", e.getMessage());
                request.setAttribute("membre", membre);
                request.getRequestDispatcher(
                    "/WEB-INF/views/membre/profil.jsp")
                    .forward(request, response);
            }
        }
    }
}