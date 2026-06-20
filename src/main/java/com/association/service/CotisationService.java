package com.association.service;

import com.association.dao.CotisationDAO;
import com.association.dao.MembreDAO;
import com.association.model.Cotisation;
import com.association.model.Membre;
import com.association.model.ModePaiement;
import com.association.model.StatutCotisation;
import com.association.model.StatutMembre;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CotisationService {

    private final CotisationDAO cotisationDAO = new CotisationDAO();
    private final MembreDAO     membreDAO     = new MembreDAO();

    // Montant fixe de la cotisation mensuelle
    public static final double MONTANT_COTISATION = 5000.0; // en FCFA

    // ===== ENREGISTRER UNE COTISATION =====
    public void enregistrer(Long membreId, Integer mois,
                            Integer annee, String modePaiement,
                            String reference) throws Exception {

        Membre membre = membreDAO.findById(membreId);
        if (membre == null) throw new Exception("Membre introuvable.");

        if (!StatutMembre.ACTIF.equals(membre.getStatut())) {
            throw new Exception("Ce membre est inactif.");
        }

        if (cotisationDAO.aDejaPayePourPeriode(membreId, mois, annee)) {
            throw new Exception("Ce membre a déjà payé pour "
                + getNomMois(mois) + " " + annee + ".");
        }

        Cotisation c = new Cotisation();
        c.setMembre(membre);
        c.setMontant(MONTANT_COTISATION);
        c.setMois(mois);
        c.setAnnee(annee);
        c.setDatePaiement(new Date());
        c.setModePaiement(ModePaiement.valueOf(modePaiement));
        c.setStatut(StatutCotisation.PAYEE);
        c.setReference(reference);

        cotisationDAO.save(c);
    }

    // ===== MEMBRES EN RETARD POUR UN MOIS/ANNÉE =====
    public List<Membre> getMembresEnRetard(Integer mois, Integer annee) {
        List<Membre> tousLesMembres = membreDAO.findAllActifs();
        List<Long>   aJour = cotisationDAO
                             .findMembresAJourPourPeriode(mois, annee);

        List<Membre> enRetard = new ArrayList<>();
        for (Membre m : tousLesMembres) {
            if (!aJour.contains(m.getId())) {
                enRetard.add(m);
            }
        }
        return enRetard;
    }

    // ===== MEMBRES À JOUR POUR UN MOIS/ANNÉE =====
    public List<Membre> getMembresAJour(Integer mois, Integer annee) {
        List<Membre> tousLesMembres = membreDAO.findAllActifs();
        List<Long>   aJour = cotisationDAO
                             .findMembresAJourPourPeriode(mois, annee);

        List<Membre> aJourList = new ArrayList<>();
        for (Membre m : tousLesMembres) {
            if (aJour.contains(m.getId())) {
                aJourList.add(m);
            }
        }
        return aJourList;
    }

    // ===== HISTORIQUE D'UN MEMBRE =====
    public List<Cotisation> getHistoriqueByMembre(Long membreId) {
        return cotisationDAO.findByMembre(membreId);
    }

    // ===== COTISATIONS DU MOIS EN COURS =====
    public List<Cotisation> getCotisationsMoisCourant() {
        Calendar cal = Calendar.getInstance();
        return cotisationDAO.findByMoisAnnee(
            cal.get(Calendar.MONTH) + 1,
            cal.get(Calendar.YEAR));
    }

    // ===== TOTAL ENCAISSÉ CE MOIS =====
    public Double getTotalMoisCourant() {
        Calendar cal = Calendar.getInstance();
        return cotisationDAO.totalEncaisseParPeriode(
            cal.get(Calendar.MONTH) + 1,
            cal.get(Calendar.YEAR));
    }

    // ===== SUPPRIMER =====
    public void supprimer(Long id) throws Exception {
        Cotisation c = cotisationDAO.findById(id);
        if (c == null) throw new Exception("Cotisation introuvable.");
        cotisationDAO.delete(c);
    }

    // ===== UTILITAIRES =====
    public List<Cotisation> findByMoisAnnee(Integer mois, Integer annee) {
        return cotisationDAO.findByMoisAnnee(mois, annee);
    }

    public Double getTotalEncaisse(Integer mois, Integer annee) {
        return cotisationDAO.totalEncaisseParPeriode(mois, annee);
    }

    public static String getNomMois(Integer mois) {
        String[] noms = {"", "Janvier", "Février", "Mars", "Avril",
                         "Mai", "Juin", "Juillet", "Août",
                         "Septembre", "Octobre", "Novembre", "Décembre"};
        return (mois >= 1 && mois <= 12) ? noms[mois] : "Inconnu";
    }

    public int getMoisCourant() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public int getAnneeCourante() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
}