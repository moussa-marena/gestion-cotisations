package com.association.servlet.membre;

import com.association.model.Amende;
import com.association.model.Cotisation;
import com.association.model.Membre;
import com.association.service.AmendeService;
import com.association.service.CotisationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard/membre")
public class DashboardMembreServlet extends HttpServlet {

    private CotisationService cotisationService;
    private AmendeService     amendeService;

    @Override
    public void init() {
        cotisationService = new CotisationService();
        amendeService     = new AmendeService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        Membre membre = (Membre) request.getSession()
                                        .getAttribute("membreConnecte");

        // Message accès refusé
        if ("refuse".equals(request.getParameter("acces"))) {
            request.setAttribute("acceRefuse", true);
        }

        // Statistiques du membre
        int mois  = cotisationService.getMoisCourant();
        int annee = cotisationService.getAnneeCourante();

        // Historique des cotisations
        List<Cotisation> cotisations =
            cotisationService.getHistoriqueByMembre(membre.getId());

        // Amendes en attente
        List<Amende> amendesEnAttente =
            amendeService.findEnAttenteByMembre(membre.getId());

        // Toutes les amendes
        List<Amende> toutesAmendes =
            amendeService.findByMembre(membre.getId());

        // A-t-il payé ce mois ?
        boolean aPayeCeMois = cotisationService
            .getMembresAJour(mois, annee)
            .stream()
            .anyMatch(m -> m.getId().equals(membre.getId()));

        // Total amendes dues
        double totalAmendesDues = amendesEnAttente.stream()
            .mapToDouble(Amende::getMontant)
            .sum();

        request.setAttribute("membre",           membre);
        request.setAttribute("cotisations",      cotisations);
        request.setAttribute("amendesEnAttente", amendesEnAttente);
        request.setAttribute("toutesAmendes",    toutesAmendes);
        request.setAttribute("aPayeCeMois",      aPayeCeMois);
        request.setAttribute("totalAmendesDues", totalAmendesDues);
        request.setAttribute("nomMois",
            CotisationService.getNomMois(mois));
        request.setAttribute("annee",            annee);
        request.setAttribute("nbCotisations",    cotisations.size());
        request.setAttribute("nbAmendes",        amendesEnAttente.size());

        request.getRequestDispatcher(
            "/WEB-INF/views/membre/dashboard.jsp")
            .forward(request, response);
    }
}