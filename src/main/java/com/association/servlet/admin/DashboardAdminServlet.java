package com.association.servlet.admin;

import com.association.model.Membre;
import com.association.service.AmendeService;
import com.association.service.CotisationService;
import com.association.service.MembreService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dashboard/admin")
public class DashboardAdminServlet extends HttpServlet {

    private MembreService     membreService;
    private CotisationService cotisationService;
    private AmendeService     amendeService;

    @Override
    public void init() {
        membreService     = new MembreService();
        cotisationService = new CotisationService();
        amendeService     = new AmendeService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        Membre membre = (Membre) request.getSession()
                                        .getAttribute("membreConnecte");

        // ===== STATISTIQUES =====
        int mois  = cotisationService.getMoisCourant();
        int annee = cotisationService.getAnneeCourante();

        // Membres
        long totalMembres  = membreService.count();
        long membresActifs = membreService.countActifs();

        // Cotisations du mois
        double totalEncaisse = cotisationService.getTotalEncaisse(mois, annee);
        int    nbAJour       = cotisationService.getMembresAJour(mois, annee).size();
        int    nbEnRetard    = cotisationService.getMembresEnRetard(mois, annee).size();

        // Amendes
        double totalAmendes  = amendeService.totalEnAttente();
        long   nbAmendes     = amendeService.countEnAttente();

        // Passer les données à la JSP
        request.setAttribute("membre",         membre);
        request.setAttribute("totalMembres",   totalMembres);
        request.setAttribute("membresActifs",  membresActifs);
        request.setAttribute("totalEncaisse",  totalEncaisse);
        request.setAttribute("nbAJour",        nbAJour);
        request.setAttribute("nbEnRetard",     nbEnRetard);
        request.setAttribute("totalAmendes",   totalAmendes);
        request.setAttribute("nbAmendes",      nbAmendes);
        request.setAttribute("nomMois",
            CotisationService.getNomMois(mois));
        request.setAttribute("annee",          annee);

        request.getRequestDispatcher(
            "/WEB-INF/views/admin/dashboard.jsp")
            .forward(request, response);
    }
}