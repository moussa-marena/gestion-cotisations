package com.association.servlet.admin;

import com.association.model.Amende;
import com.association.service.AmendeService;
import com.association.service.CotisationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/amendes")
public class AmendeServlet extends HttpServlet {

    private AmendeService amendeService;

    @Override
    public void init() {
        amendeService = new AmendeService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "liste";

        switch (action) {

            case "generer":
                // Afficher le formulaire de génération
                request.setAttribute("moisCourant",
                    new CotisationService().getMoisCourant());
                request.setAttribute("anneeCourante",
                    new CotisationService().getAnneeCourante());
                request.getRequestDispatcher(
                    "/WEB-INF/views/admin/amendes/generer.jsp")
                    .forward(request, response);
                break;

            case "payer":
                try {
                    Long id = Long.parseLong(request.getParameter("id"));
                    amendeService.payerAmende(id);
                    response.sendRedirect(request.getContextPath()
                        + "/admin/amendes?succes=Amende+payée+avec+succès");
                } catch (Exception e) {
                    response.sendRedirect(request.getContextPath()
                        + "/admin/amendes?erreur=" + e.getMessage());
                }
                break;

            case "annuler":
                try {
                    Long id = Long.parseLong(request.getParameter("id"));
                    amendeService.annulerAmende(id);
                    response.sendRedirect(request.getContextPath()
                        + "/admin/amendes?succes=Amende+annulée");
                } catch (Exception e) {
                    response.sendRedirect(request.getContextPath()
                        + "/admin/amendes?erreur=" + e.getMessage());
                }
                break;

            case "supprimer":
                try {
                    Long id = Long.parseLong(request.getParameter("id"));
                    amendeService.supprimer(id);
                    response.sendRedirect(request.getContextPath()
                        + "/admin/amendes?succes=Amende+supprimée");
                } catch (Exception e) {
                    response.sendRedirect(request.getContextPath()
                        + "/admin/amendes?erreur=" + e.getMessage());
                }
                break;

            default: // liste
                List<Amende> amendes = amendeService.findAll();
                request.setAttribute("amendes",        amendes);
                request.setAttribute("totalEnAttente",
                    amendeService.totalEnAttente());
                request.setAttribute("nbEnAttente",
                    amendeService.countEnAttente());
                request.getRequestDispatcher(
                    "/WEB-INF/views/admin/amendes/liste.jsp")
                    .forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("generer".equals(action)) {
            try {
                Integer mois  = Integer.parseInt(
                    request.getParameter("mois"));
                Integer annee = Integer.parseInt(
                    request.getParameter("annee"));

                List<String> resultats =
                    amendeService.genererAmendes(mois, annee);

                request.setAttribute("resultats", resultats);
                request.setAttribute("moisCourant",
                    new CotisationService().getMoisCourant());
                request.setAttribute("anneeCourante",
                    new CotisationService().getAnneeCourante());
                request.getRequestDispatcher(
                    "/WEB-INF/views/admin/amendes/generer.jsp")
                    .forward(request, response);

            } catch (Exception e) {
                response.sendRedirect(request.getContextPath()
                    + "/admin/amendes?erreur=" + e.getMessage());
            }
        }
    }
}