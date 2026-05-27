package com.association.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Démarre la connexion JPA au lancement de l'application
        JPAUtil.getEntityManager();
        System.out.println("=== Application démarrée - JPA initialisé ===");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Ferme proprement la connexion JPA à l'arrêt
        JPAUtil.close();
        System.out.println("=== Application arrêtée - JPA fermé ===");
    }
}