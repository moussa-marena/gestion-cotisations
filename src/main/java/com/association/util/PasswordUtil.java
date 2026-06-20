package com.association.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hacher un mot de passe
    public static String hash(String motDePasse) {
        return BCrypt.hashpw(motDePasse, BCrypt.gensalt(12));
    }

    // Vérifier un mot de passe contre son hash
    public static boolean verifier(String motDePasse, String hash) {
        try {
            return BCrypt.checkpw(motDePasse, hash);
        } catch (Exception e) {
            return false;
        }
    }
}