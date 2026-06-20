package com.association.servlet.admin;

import com.association.model.Cotisation;
import com.association.model.Membre;
import com.association.service.CotisationService;
import com.association.service.MembreService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/cotisations")
public class CotisationServlet extends HttpServlet {

    private CotisationService cotisationService;
    private MembreService     membreService;

    @Override
    public void init() {
        cotisationService = new CotisationService();
        membreService     = new MembreService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "liste";

        // Mois et année sélectionnés (par défaut : mois courant)
        int mois  = cotisationService.getMoisCourant();
        int annee = cotisationService.getAnneeCourante();

        try {
            if (request.getParameter("mois") != null) {
                mois  = Integer.parseInt(request.getParameter("mois"));
            }
            if (request.getParameter("annee") != null) {
                annee = Integer.parseInt(request.getParameter("annee"));
            }
        } catch (NumberFormatException ignored) {}

        switch (action) {

            case "ajouter":
                // Charger la liste des membres actifs pour le select
                request.setAttribute("membres",
                    membreService.findAll());
                request.setAttribute("moisCourant",
                    cotisationService.getMoisCourant());
                request.setAttribute("anneeCourante",
                    cotisationService.getAnneeCourante());
                request.getRequestDispatcher(
                    "/WEB-INF/views/admin/cotisations/ajouter.jsp")
                    .forward(request, response);
                break;

            case "retard":
                List<Membre> enRetard =
                    cotisationService.getMembresEnRetard(mois, annee);
                List<Membre> aJour =
                    cotisationService.getMembresAJour(mois, annee);
                request.setAttribute("membresEnRetard", enRetard);
                request.setAttribute("membresAJour",    aJour);
                request.setAttribute("mois",  mois);
                request.setAttribute("annee", annee);
                request.setAttribute("nomMois",
                    CotisationService.getNomMois(mois));
                request.getRequestDispatcher(
                    "/WEB-INF/views/admin/cotisations/retard.jsp")
                    .forward(request, response);
                break;

            case "supprimer":
                try {
                    Long id = Long.parseLong(request.getParameter("id"));
                    cotisationService.supprimer(id);
                    response.sendRedirect(request.getContextPath()
                        + "/admin/cotisations?succes=Cotisation+supprimée");
                } catch (Exception e) {
                    response.sendRedirect(request.getContextPath()
                        + "/admin/cotisations?erreur=" + e.getMessage());
                }
                break;

            default: // liste
                List<Cotisation> cotisations =
                    cotisationService.findByMoisAnnee(mois, annee);
                request.setAttribute("cotisations",   cotisations);
                request.setAttribute("mois",          mois);
                request.setAttribute("annee",         annee);
                request.setAttribute("nomMois",
                    CotisationService.getNomMois(mois));
                request.setAttribute("totalEncaisse",
                    cotisationService.getTotalEncaisse(mois, annee));
                request.setAttribute("nbEnRetard",
                    cotisationService.getMembresEnRetard(mois, annee).size());
                request.getRequestDispatcher(
                    "/WEB-INF/views/admin/cotisations/liste.jsp")
                    .forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("ajouter".equals(action)) {

            String membreIdStr  = request.getParameter("membreId");
            String moisStr      = request.getParameter("mois");
            String anneeStr     = request.getParameter("annee");
            String modePaiement = request.getParameter("modePaiement");
            String reference    = request.getParameter("reference");

            try {
                Long    membreId = Long.parseLong(membreIdStr);
                Integer mois     = Integer.parseInt(moisStr);
                Integer annee    = Integer.parseInt(anneeStr);

                cotisationService.enregistrer(
                    membreId, mois, annee, modePaiement, reference);

                response.sendRedirect(request.getContextPath()
                    + "/admin/cotisations?succes=Cotisation+enregistrée+avec+succès");

            } catch (Exception e) {
                request.setAttribute("erreur",   e.getMessage());
                request.setAttribute("membres",  membreService.findAll());
                request.setAttribute("moisCourant",
                    cotisationService.getMoisCourant());
                request.setAttribute("anneeCourante",
                    cotisationService.getAnneeCourante());
                request.getRequestDispatcher(
                    "/WEB-INF/views/admin/cotisations/ajouter.jsp")
                    .forward(request, response);
            }
        }
    }
}