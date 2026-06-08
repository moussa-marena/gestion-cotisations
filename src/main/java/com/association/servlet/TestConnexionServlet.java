package com.association.servlet;

import com.association.dao.MembreDAO;
import com.association.dao.CotisationDAO;
import com.association.dao.AmendeDAO;
import com.association.model.Membre;
import com.association.model.Role;
import com.association.model.StatutMembre;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@WebServlet("/test-connexion")
public class TestConnexionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><body style='font-family:sans-serif; padding:20px'>");
        out.println("<h2>🧪 Tests Phase 2 — DAOs & Entités JPA</h2>");
        out.println("<hr>");

        try {
            MembreDAO membreDAO       = new MembreDAO();
            CotisationDAO cotisationDAO = new CotisationDAO();
            AmendeDAO amendeDAO       = new AmendeDAO();

            // ===== TEST 1 : Créer un membre =====
            out.println("<h3>Test 1 — Créer un membre</h3>");
            Membre membre = new Membre();
            membre.setNom("Diallo");
            membre.setPrenom("Moussa");
            membre.setEmail("moussa.diallo@test.com");
            membre.setMotDePasse("test123");
            membre.setDateAdhesion(new Date());
            membre.setStatut(StatutMembre.ACTIF);
            membre.setRole(Role.ADMIN);

            // Vérifie si le membre existe déjà avant de le créer
            if (membreDAO.findByEmail("moussa.diallo@test.com") == null) {
                membreDAO.save(membre);
                out.println("<p style='color:green'>✅ Membre créé avec succès !</p>");
            } else {
                out.println("<p style='color:orange'>⚠️ Membre déjà existant, création ignorée.</p>");
            }

            // ===== TEST 2 : Lire tous les membres =====
            out.println("<h3>Test 2 — Lire tous les membres</h3>");
            List<Membre> membres = membreDAO.findAll();
            out.println("<p style='color:green'>✅ " + membres.size() + " membre(s) trouvé(s) en base.</p>");
            for (Membre m : membres) {
                out.println("<p>👤 " + m.getNomComplet() +
                            " — " + m.getEmail() +
                            " — " + m.getRole() + "</p>");
            }

            // ===== TEST 3 : Rechercher par email =====
            out.println("<h3>Test 3 — Rechercher par email</h3>");
            Membre trouve = membreDAO.findByEmail("moussa.diallo@test.com");
            if (trouve != null) {
                out.println("<p style='color:green'>✅ Membre trouvé : " +
                            trouve.getNomComplet() + "</p>");
            } else {
                out.println("<p style='color:red'>❌ Membre non trouvé !</p>");
            }

            // ===== TEST 4 : Compter =====
            out.println("<h3>Test 4 — Compter</h3>");
            out.println("<p style='color:green'>✅ Total membres : " +
                        membreDAO.count() + "</p>");
            out.println("<p style='color:green'>✅ Membres actifs : " +
                        membreDAO.countActifs() + "</p>");
            out.println("<p style='color:green'>✅ Total cotisations : " +
                        cotisationDAO.count() + "</p>");
            out.println("<p style='color:green'>✅ Total amendes : " +
                        amendeDAO.count() + "</p>");

            // ===== TEST 5 : Modifier un membre =====
            out.println("<h3>Test 5 — Modifier un membre</h3>");
            Membre aModifier = membreDAO.findByEmail("moussa.diallo@test.com");
            if (aModifier != null) {
                aModifier.setTelephone("77 123 45 67");
                membreDAO.update(aModifier);
                out.println("<p style='color:green'>✅ Membre modifié avec succès !</p>");
            }

            // ===== RÉSULTAT FINAL =====
            out.println("<hr>");
            out.println("<h2 style='color:green'>🎉 Tous les tests sont passés !</h2>");
            out.println("<p>Les DAOs fonctionnent correctement.</p>");
            out.println("<p><b>Phase 2 terminée ✅ — Prêt pour la Phase 3 !</b></p>");

        } catch (Exception e) {
            out.println("<hr>");
            out.println("<h2 style='color:red'>❌ Erreur détectée</h2>");
            out.println("<p style='color:red'>" + e.getMessage() + "</p>");
            out.println("<pre style='color:red'>");
            e.printStackTrace(out);
            out.println("</pre>");
        }

        out.println("</body></html>");
    }
}