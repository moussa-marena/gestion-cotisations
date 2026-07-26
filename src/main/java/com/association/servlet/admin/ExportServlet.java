package com.association.servlet.admin;

import com.association.model.Cotisation;
import com.association.model.Membre;
import com.association.service.CotisationService;
import com.association.service.MembreService;
import com.association.util.ExportExcelUtil;
import com.association.util.ExportPDFUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/export")
public class ExportServlet extends HttpServlet {

    private MembreService     membreService;
    private CotisationService cotisationService;

    @Override
    public void init() {
        membreService     = new MembreService();
        cotisationService = new CotisationService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String type   = request.getParameter("type");   // pdf ou excel
        String donnee = request.getParameter("donnee"); // membres ou cotisations

        int mois  = cotisationService.getMoisCourant();
        int annee = cotisationService.getAnneeCourante();

        try {
            if ("membres".equals(donnee)) {
                List<Membre> membres = membreService.findAll();
                if ("pdf".equals(type)) {
                    ExportPDFUtil.exportMembres(membres, response);
                } else {
                    ExportExcelUtil.exportMembres(membres, response);
                }

            } else if ("cotisations".equals(donnee)) {
                List<Cotisation> cotisations =
                    cotisationService.findByMoisAnnee(mois, annee);
                double total =
                    cotisationService.getTotalEncaisse(mois, annee);
                String nomMois =
                    CotisationService.getNomMois(mois);

                if ("pdf".equals(type)) {
                    ExportPDFUtil.exportCotisations(
                        cotisations, nomMois, annee, total, response);
                } else {
                    ExportExcelUtil.exportCotisations(
                        cotisations, nomMois, annee, total, response);
                }
            }

        } catch (Exception e) {
            response.sendRedirect(request.getContextPath()
                + "/dashboard/admin?erreur=Erreur+export+:+" + e.getMessage());
        }
    }
}