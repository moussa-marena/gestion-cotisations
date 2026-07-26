package com.association.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailUtil {

    // ===== CONFIGURATION SMTP =====
    private static final String SMTP_HOST        = "smtp.gmail.com";
    private static final int    SMTP_PORT        = 587;
    private static final String EMAIL_EXPEDITEUR = "marenamoussa444@gmail.com";
    private static final String EMAIL_PASSWORD   = "nveu wtag qznf hzbl";
    private static final String NOM_EXPEDITEUR   = "SunuAssos";

    // ===== MÉTHODE PRINCIPALE D'ENVOI =====
    private static void envoyerEmail(String destinataire,
                                      String sujet,
                                      String contenuHTML)
            throws MessagingException {

        // Configuration SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth",            "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host",            SMTP_HOST);
        props.put("mail.smtp.port",            SMTP_PORT);
        props.put("mail.smtp.ssl.trust",       SMTP_HOST);

        // Authentification
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    EMAIL_EXPEDITEUR, EMAIL_PASSWORD);
            }
        });

        // Construction du message
        Message message = new MimeMessage(session);

        // Correction : gestion UnsupportedEncodingException
        try {
            message.setFrom(new InternetAddress(
                EMAIL_EXPEDITEUR, NOM_EXPEDITEUR, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            message.setFrom(new InternetAddress(EMAIL_EXPEDITEUR));
        }

        message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(destinataire));
        message.setSubject(sujet);
        message.setContent(contenuHTML, "text/html; charset=UTF-8");

        // Envoi
        Transport.send(message);
    }

    // ===== TEMPLATE HTML DE BASE =====
    private static String template(String titre, String contenu) {
        return "<!DOCTYPE html>" +
            "<html><head><meta charset='UTF-8'></head>" +
            "<body style='font-family:Arial,sans-serif; " +
                         "background:#EDF2F4; padding:20px;'>" +
            "<div style='max-width:600px; margin:0 auto; " +
                        "background:white; border-radius:12px; " +
                        "overflow:hidden; " +
                        "box-shadow:0 2px 8px rgba(0,0,0,0.1);'>" +

            "<div style='background:#0D3B66; padding:24px; " +
                        "text-align:center;'>" +
            "<h1 style='color:white; margin:0; font-size:22px;'>" +
                "SunuAssos</h1>" +
            "<p style='color:rgba(255,255,255,0.8); margin:4px 0 0;'>" +
                "Notre association, notre force</p>" +
            "</div>" +

            "<div style='padding:30px;'>" +
            "<h2 style='color:#0D3B66; margin-top:0;'>" + titre + "</h2>" +
            contenu +
            "</div>" +

            "<div style='background:#EDF2F4; padding:16px; " +
                        "text-align:center; " +
                        "color:#8D99AE; font-size:12px;'>" +
            "SunuAssos — Soutenir, Partager, Avancer ensemble" +
            "</div>" +

            "</div></body></html>";
    }

    // ===== EMAILS SPÉCIFIQUES =====

    // 1. Confirmation de paiement de cotisation
    public static void envoyerConfirmationCotisation(
            String email, String nomMembre,
            String periode, double montant) {

        new Thread(() -> {
            try {
                String sujet = "✅ Cotisation confirmée — " + periode;
                String contenu =
                    "<p>Bonjour <strong>" + nomMembre + "</strong>,</p>" +
                    "<p>Votre cotisation pour la période " +
                    "<strong>" + periode + "</strong> " +
                    "a bien été enregistrée.</p>" +
                    "<div style='background:#EDF2F4; padding:16px; " +
                               "border-radius:8px; " +
                               "border-left:4px solid #1B8A5A; " +
                               "margin:20px 0;'>" +
                    "<p style='margin:0; color:#1B8A5A;'>" +
                    "<strong>Période :</strong> " + periode + "<br>" +
                    "<strong>Montant :</strong> " + montant + " FCFA<br>" +
                    "<strong>Statut :</strong> PAYÉE ✅" +
                    "</p></div>" +
                    "<p>Merci pour votre engagement !</p>";
                envoyerEmail(email, sujet, template(
                    "Confirmation de cotisation", contenu));
            } catch (Exception e) {
                System.err.println("Erreur email cotisation : "
                    + e.getMessage());
            }
        }).start();
    }

    // 2. Notification d'amende générée
    public static void envoyerNotificationAmende(
            String email, String nomMembre,
            String motif, double montant) {

        new Thread(() -> {
            try {
                String sujet = "⚠️ Amende générée — SunuAssos";
                String contenu =
                    "<p>Bonjour <strong>" + nomMembre + "</strong>,</p>" +
                    "<p>Une amende a été générée sur votre compte.</p>" +
                    "<div style='background:#FFF3E0; padding:16px; " +
                               "border-radius:8px; " +
                               "border-left:4px solid #F4A261; " +
                               "margin:20px 0;'>" +
                    "<p style='margin:0; color:#e08c42;'>" +
                    "<strong>Motif :</strong> " + motif + "<br>" +
                    "<strong>Montant :</strong> " + montant + " FCFA<br>" +
                    "<strong>Statut :</strong> EN ATTENTE ⚠️" +
                    "</p></div>" +
                    "<p>Veuillez régulariser votre situation " +
                    "auprès de l'administrateur.</p>";
                envoyerEmail(email, sujet, template(
                    "Notification d'amende", contenu));
            } catch (Exception e) {
                System.err.println("Erreur email amende : "
                    + e.getMessage());
            }
        }).start();
    }

    // 3. Rappel de cotisation en retard
    public static void envoyerRappelRetard(
            String email, String nomMembre, String periode) {

        new Thread(() -> {
            try {
                String sujet = "🔔 Rappel cotisation — " + periode;
                String contenu =
                    "<p>Bonjour <strong>" + nomMembre + "</strong>,</p>" +
                    "<p>Nous vous rappelons que votre cotisation " +
                    "pour la période <strong>" + periode + "</strong> " +
                    "n'a pas encore été enregistrée.</p>" +
                    "<div style='background:#fde8e8; padding:16px; " +
                               "border-radius:8px; " +
                               "border-left:4px solid #c0392b; " +
                               "margin:20px 0;'>" +
                    "<p style='margin:0; color:#c0392b;'>" +
                    "⚠️ Période concernée : <strong>" +
                    periode + "</strong>" +
                    "</p></div>" +
                    "<p>Merci de régulariser votre situation " +
                    "dès que possible.</p>";
                envoyerEmail(email, sujet, template(
                    "Rappel de cotisation", contenu));
            } catch (Exception e) {
                System.err.println("Erreur email rappel : "
                    + e.getMessage());
            }
        }).start();
    }

    // 4. Email de bienvenue (nouveau membre)
    public static void envoyerBienvenue(
            String email, String nomMembre) {

        new Thread(() -> {
            try {
                String sujet = "🎉 Bienvenue dans SunuAssos !";
                String contenu =
                    "<p>Bonjour <strong>" + nomMembre + "</strong>,</p>" +
                    "<p>Nous sommes ravis de vous accueillir dans " +
                    "notre association <strong>SunuAssos</strong> !</p>" +
                    "<div style='background:#EDF2F4; padding:16px; " +
                               "border-radius:8px; " +
                               "border-left:4px solid #0D3B66; " +
                               "margin:20px 0;'>" +
                    "<p style='margin:0; color:#0D3B66;'>" +
                    "🔵 Restez à jour de vos cotisations<br>" +
                    "🟢 Participez activement à la vie associative<br>" +
                    "🟠 Consultez votre espace personnel" +
                    "</p></div>" +
                    "<p><em>Soutenir — Partager — Avancer ensemble</em></p>";
                envoyerEmail(email, sujet, template(
                    "Bienvenue dans SunuAssos ! 🎉", contenu));
            } catch (Exception e) {
                System.err.println("Erreur email bienvenue : "
                    + e.getMessage());
            }
        }).start();
    }
}