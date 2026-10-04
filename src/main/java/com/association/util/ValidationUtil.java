package com.association.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ValidationUtil {

    // Regex email
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Regex téléphone (formats sénégalais)
    private static final Pattern PHONE_PATTERN =
        Pattern.compile("^(\\+221|00221)?[0-9]{9}$");

    // ===== VALIDER UN MEMBRE =====
    public static Map<String, String> validerMembre(
            String nom, String prenom, String email,
            String telephone, String motDePasse, boolean estNouveau) {

        Map<String, String> erreurs = new HashMap<>();

        // Nom
        if (nom == null || nom.trim().isEmpty()) {
            erreurs.put("nom", "Le nom est obligatoire.");
        } else if (nom.trim().length() < 2) {
            erreurs.put("nom", "Le nom doit contenir au moins 2 caractères.");
        } else if (nom.trim().length() > 100) {
            erreurs.put("nom", "Le nom ne peut pas dépasser 100 caractères.");
        }

        // Prénom
        if (prenom == null || prenom.trim().isEmpty()) {
            erreurs.put("prenom", "Le prénom est obligatoire.");
        } else if (prenom.trim().length() < 2) {
            erreurs.put("prenom",
                "Le prénom doit contenir au moins 2 caractères.");
        } else if (prenom.trim().length() > 100) {
            erreurs.put("prenom",
                "Le prénom ne peut pas dépasser 100 caractères.");
        }

        // Email
        if (email == null || email.trim().isEmpty()) {
            erreurs.put("email", "L'email est obligatoire.");
        } else if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            erreurs.put("email", "Format d'email invalide.");
        }

        // Téléphone (optionnel mais validé si renseigné)
        if (telephone != null && !telephone.trim().isEmpty()) {
            String tel = telephone.trim().replaceAll("\\s", "");
            if (!PHONE_PATTERN.matcher(tel).matches()) {
                erreurs.put("telephone",
                    "Format de téléphone invalide (ex: 771234567).");
            }
        }

        // Mot de passe (uniquement pour la création)
        if (estNouveau) {
            if (motDePasse == null || motDePasse.isEmpty()) {
                erreurs.put("motDePasse", "Le mot de passe est obligatoire.");
            } else if (motDePasse.length() < 6) {
                erreurs.put("motDePasse",
                    "Le mot de passe doit contenir au moins 6 caractères.");
            } else if (motDePasse.length() > 50) {
                erreurs.put("motDePasse",
                    "Le mot de passe ne peut pas dépasser 50 caractères.");
            }
        }

        return erreurs;
    }

    // ===== VALIDER UNE COTISATION =====
    public static Map<String, String> validerCotisation(
            String membreIdStr, String moisStr,
            String anneeStr, String modePaiement) {

        Map<String, String> erreurs = new HashMap<>();

        // Membre
        if (membreIdStr == null || membreIdStr.trim().isEmpty()) {
            erreurs.put("membreId", "Veuillez sélectionner un membre.");
        }

        // Mois
        if (moisStr == null || moisStr.trim().isEmpty()) {
            erreurs.put("mois", "Le mois est obligatoire.");
        } else {
            try {
                int mois = Integer.parseInt(moisStr);
                if (mois < 1 || mois > 12) {
                    erreurs.put("mois", "Le mois doit être entre 1 et 12.");
                }
            } catch (NumberFormatException e) {
                erreurs.put("mois", "Mois invalide.");
            }
        }

        // Année
        if (anneeStr == null || anneeStr.trim().isEmpty()) {
            erreurs.put("annee", "L'année est obligatoire.");
        } else {
            try {
                int annee = Integer.parseInt(anneeStr);
                if (annee < 2020 || annee > 2030) {
                    erreurs.put("annee",
                        "L'année doit être entre 2020 et 2030.");
                }
            } catch (NumberFormatException e) {
                erreurs.put("annee", "Année invalide.");
            }
        }

        // Mode de paiement
        if (modePaiement == null || modePaiement.trim().isEmpty()) {
            erreurs.put("modePaiement",
                "Veuillez sélectionner un mode de paiement.");
        }

        return erreurs;
    }

    // ===== MÉTHODE UTILITAIRE =====
    public static boolean hasErreurs(Map<String, String> erreurs) {
        return erreurs != null && !erreurs.isEmpty();
    }
}