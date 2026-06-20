package com.association.filter;

import com.association.model.Membre;
import com.association.model.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthFilter implements Filter {

    // URLs accessibles sans connexion
    private static final List<String> URL_PUBLIQUES = Arrays.asList(
        "/login",
        "/login.jsp",
        "/reset-password",
        "/test-connexion",
        "/init-membres"
    );

    // URLs accessibles uniquement par les admins
    private static final List<String> URL_ADMIN = Arrays.asList(
        "/dashboard/admin",
        "/admin/membres",
        "/admin/cotisations",
        "/admin/amendes"
    );

    // Ressources statiques (toujours accessibles)
    private static final List<String> EXTENSIONS_STATIQUES = Arrays.asList(
        ".css", ".js", ".png", ".jpg", ".jpeg",
        ".gif", ".ico", ".woff", ".woff2", ".ttf"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Rien à initialiser
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  request  = (HttpServletRequest)  servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();

        // Chemin relatif sans le context path
        // ex: /gestion-cotisations/dashboard/admin → /dashboard/admin
        String path = uri.substring(contextPath.length());

        // ===== 1. Laisser passer les ressources statiques =====
        if (estRessourceStatique(path)) {
            chain.doFilter(request, response);
            return;
        }

        // ===== 2. Laisser passer les URLs publiques =====
        if (estUrlPublique(path)) {
            chain.doFilter(request, response);
            return;
        }

        // ===== 3. Vérifier la session =====
        HttpSession session = request.getSession(false);
        Membre membre = (session != null)
                ? (Membre) session.getAttribute("membreConnecte")
                : null;

        // Non connecté → rediriger vers login
        if (membre == null) {
            response.sendRedirect(
                contextPath + "/login?expired=true");
            return;
        }

        // ===== 4. Vérifier le rôle pour les URLs admin =====
        if (estUrlAdmin(path) && !Role.ADMIN.equals(membre.getRole())) {
            // Membre normal qui essaie d'accéder à une page admin
            response.sendRedirect(
                contextPath + "/dashboard/membre?acces=refuse");
            return;
        }

        // ===== 5. Tout est OK, laisser passer =====
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Rien à libérer
    }

    // ===== MÉTHODES UTILITAIRES =====

    private boolean estRessourceStatique(String path) {
        for (String ext : EXTENSIONS_STATIQUES) {
            if (path.endsWith(ext)) return true;
        }
        return false;
    }

    private boolean estUrlPublique(String path) {
        for (String url : URL_PUBLIQUES) {
            if (path.equals(url) || path.startsWith(url + "/")) {
                return true;
            }
        }
        return false;
    }

    private boolean estUrlAdmin(String path) {
        for (String url : URL_ADMIN) {
            if (path.equals(url) || path.startsWith(url + "/")) {
                return true;
            }
        }
        return false;
    }
}