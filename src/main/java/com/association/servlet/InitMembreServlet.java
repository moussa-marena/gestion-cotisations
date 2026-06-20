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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/init-membres")
public class InitMembreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            MembreDAO dao = new MembreDAO();

            // ===== Membre normal =====
            Membre moussa = dao.findByEmail("moussa.diallo@test.com");
            if (moussa == null) {
                moussa = new Membre();
                moussa.setNom("Diallo");
                moussa.setPrenom("Moussa");
                moussa.setEmail("moussa.diallo@test.com");
                moussa.setDateAdhesion(new Date());
                moussa.setStatut(StatutMembre.ACTIF);
                moussa.setRole(Role.MEMBRE);
                moussa.setMotDePasse(PasswordUtil.hash("test123"));
                dao.save(moussa);
                out.println("<p style='color:green'>✅ Membre Moussa créé !</p>");
            } else {
                moussa.setMotDePasse(PasswordUtil.hash("test123"));
                moussa.setRole(Role.MEMBRE);
                moussa.setStatut(StatutMembre.ACTIF);
                dao.update(moussa);
                out.println("<p style='color:green'>✅ Membre Moussa mis à jour !</p>");
            }

            out.println("<hr>");
            out.println("<h3 style='color:green'>✅ Prêt !</h3>");
            out.println("<table border='1' cellpadding='8' style='border-collapse:collapse'>");
            out.println("<tr style='background:#eee'>");
            out.println("<th>Email</th><th>Mot de passe</th><th>Rôle</th>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>admin@association.com</td>");
            out.println("<td>admin123</td>");
            out.println("<td style='color:red'>ADMIN</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>moussa.diallo@test.com</td>");
            out.println("<td>test123</td>");
            out.println("<td style='color:blue'>MEMBRE</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("<br><a href='login'>→ Aller au login</a>");

        } catch (Exception e) {
            out.println("<p style='color:red'>❌ Erreur : " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }
    }
}