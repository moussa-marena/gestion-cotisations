package com.association.util;

import com.association.dao.MembreDAO;
import com.association.model.Membre;
import com.association.model.Role;
import com.association.model.StatutMembre;

import java.util.Date;

public class InitDataUtil {

    // Crée le compte admin par défaut si aucun admin n'existe
    public static void creerAdminSiNecessaire() {
        MembreDAO dao = new MembreDAO();

        if (dao.findByEmail("admin@association.com") == null) {
            Membre admin = new Membre();
            admin.setNom("Admin");
            admin.setPrenom("Super");
            admin.setEmail("admin@association.com");
            admin.setMotDePasse(PasswordUtil.hash("admin123"));
            admin.setDateAdhesion(new Date());
            admin.setStatut(StatutMembre.ACTIF);
            admin.setRole(Role.ADMIN);

            dao.save(admin);
            System.out.println("=== Compte admin créé : admin@association.com / admin123 ===");
        }
    }
}