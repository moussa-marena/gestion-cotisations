package com.association.servlet.admin;

import com.association.model.Membre;
import com.association.service.MembreService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/membres")
public class MembreServlet extends HttpServlet {

    private MembreService service;

    @Override
    public void init() {
        service = new MembreService();
    }

    // GET → afficher la liste ou le formulaire
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "liste";

        switch (action) {

            case "ajouter":
                request.getRequestDispatcher(
                    "/WEB-INF/views/admin/membres/ajouter.jsp")
                    .forward(request, response);
                break;

            case "modifier":
                Long idModif = Long.parseLong(
                    request.getParameter("id"));
                Membre aModifier = service.findById(idModif);
                if (aModifier == null) {
                    response.sendRedirect(request.getContextPath()
                        + "/admin/membres?erreur=Membre+introuvable");
                    return;
                }
                request.setAttribute("membre", aModifier);
                request.getRequestDispatcher(
                    "/WEB-INF/views/admin/membres/modifier.jsp")
                    .forward(request, response);
                break;

            case "toggle":
                try {
                    Long idToggle = Long.parseLong(
                        request.getParameter("id"));
                    service.toggleStatut(idToggle);
                    response.sendRedirect(request.getContextPath()
                        + "/admin/membres?succes=Statut+mis+à+jour");
                } catch (Exception e) {
                    response.sendRedirect(request.getContextPath()
                        + "/admin/membres?erreur=" + e.getMessage());
                }
                break;

            case "supprimer":
                try {
                    Long idSuppr = Long.parseLong(
                        request.getParameter("id"));
                    service.supprimer(idSuppr);
                    response.sendRedirect(request.getContextPath()
                        + "/admin/membres?succes=Membre+supprimé");
                } catch (Exception e) {
                    response.sendRedirect(request.getContextPath()
                        + "/admin/membres?erreur=" + e.getMessage());
                }
                break;

            default: // liste
                String keyword = request.getParameter("search");
                List<Membre> membres;
                if (keyword != null && !keyword.trim().isEmpty()) {
                    membres = service.search(keyword.trim());
                    request.setAttribute("keyword", keyword);
                } else {
                    membres = service.findAll();
                }
                request.setAttribute("membres", membres);
                request.setAttribute("total",   service.count());
                request.setAttribute("actifs",  service.countActifs());
                request.getRequestDispatcher(
                    "/WEB-INF/views/admin/membres/liste.jsp")
                    .forward(request, response);
                break;
        }
    }

    // POST → traiter les formulaires
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("ajouter".equals(action)) {

            String nom       = request.getParameter("nom");
            String prenom    = request.getParameter("prenom");
            String email     = request.getParameter("email");
            String telephone = request.getParameter("telephone");
            String adresse   = request.getParameter("adresse");
            String mdp       = request.getParameter("motDePasse");
            String role      = request.getParameter("role");

            try {
                service.creer(nom, prenom, email,
                              telephone, adresse, mdp, role);
                response.sendRedirect(request.getContextPath()
                    + "/admin/membres?succes=Membre+ajouté+avec+succès");
            } catch (Exception e) {
                request.setAttribute("erreur", e.getMessage());
                request.setAttribute("nom",       nom);
                request.setAttribute("prenom",    prenom);
                request.setAttribute("email",     email);
                request.setAttribute("telephone", telephone);
                request.setAttribute("adresse",   adresse);
                request.getRequestDispatcher(
                    "/WEB-INF/views/admin/membres/ajouter.jsp")
                    .forward(request, response);
            }

        } else if ("modifier".equals(action)) {

            Long   id        = Long.parseLong(request.getParameter("id"));
            String nom       = request.getParameter("nom");
            String prenom    = request.getParameter("prenom");
            String email     = request.getParameter("email");
            String telephone = request.getParameter("telephone");
            String adresse   = request.getParameter("adresse");
            String role      = request.getParameter("role");

            try {
                service.modifier(id, nom, prenom, email,
                                 telephone, adresse, role);
                response.sendRedirect(request.getContextPath()
                    + "/admin/membres?succes=Membre+modifié+avec+succès");
            } catch (Exception e) {
                Membre m = service.findById(id);
                request.setAttribute("membre", m);
                request.setAttribute("erreur", e.getMessage());
                request.getRequestDispatcher(
                    "/WEB-INF/views/admin/membres/modifier.jsp")
                    .forward(request, response);
            }
        }
    }
}