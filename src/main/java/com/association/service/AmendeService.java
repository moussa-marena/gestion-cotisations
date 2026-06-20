package com.association.service;

import com.association.dao.AmendeDAO;
import com.association.dao.MembreDAO;
import com.association.model.Amende;
import com.association.model.Membre;
import com.association.model.StatutAmende;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AmendeService {

    private final AmendeDAO   amendeDAO   = new AmendeDAO();
    private final MembreDAO   membreDAO   = new MembreDAO();
    private final CotisationService cotisationService = new CotisationService();

    // Montant fixe de l'amende
    public static final double MONTANT_AMENDE = 1000.0; // en FCFA

    // ===== GÉNÉRER LES AMENDES POUR UN MOIS/ANNÉE =====
    public List<String> genererAmendes(Integer mois, Integer annee)
            throws Exception {

        List<Membre> enRetard =
            cotisationService.getMembresEnRetard(mois, annee);

        List<String> resultats = new ArrayList<>();

        for (Membre membre : enRetard) {

            // Ne pas générer deux fois pour le même mois
            if (amendeDAO.amendeExistePourPeriode(
                    membre.getId(), mois, annee)) {
                resultats.add("⚠️ " + membre.getNomComplet()
                    + " — amende déjà existante.");
                continue;
            }

            Amende amende = new Amende();
            amende.setMembre(membre);
            amende.setMontant(MONTANT_AMENDE);
            amende.setMotif("Retard de cotisation — "
                + CotisationService.getNomMois(mois) + " " + annee);
            amende.setDateGeneration(new Date());
            amende.setStatut(StatutAmende.EN_ATTENTE);
            amende.setMoisConcerne(mois);
            amende.setAnneeConcernee(annee);

            amendeDAO.save(amende);
            resultats.add("✅ Amende générée pour "
                + membre.getNomComplet());
        }

        if (enRetard.isEmpty()) {
            resultats.add("✅ Aucun membre en retard pour cette période !");
        }

        return resultats;
    }

    // ===== PAYER UNE AMENDE =====
    public void payerAmende(Long amendeId) throws Exception {
        Amende amende = amendeDAO.findById(amendeId);
        if (amende == null) throw new Exception("Amende introuvable.");
        if (StatutAmende.PAYEE.equals(amende.getStatut())) {
            throw new Exception("Cette amende est déjà payée.");
        }
        amende.setStatut(StatutAmende.PAYEE);
        amende.setDatePaiement(new Date());
        amendeDAO.update(amende);
    }

    // ===== ANNULER UNE AMENDE =====
    public void annulerAmende(Long amendeId) throws Exception {
        Amende amende = amendeDAO.findById(amendeId);
        if (amende == null) throw new Exception("Amende introuvable.");
        amende.setStatut(StatutAmende.ANNULEE);
        amendeDAO.update(amende);
    }

    // ===== SUPPRIMER UNE AMENDE =====
    public void supprimer(Long amendeId) throws Exception {
        Amende amende = amendeDAO.findById(amendeId);
        if (amende == null) throw new Exception("Amende introuvable.");
        amendeDAO.delete(amende);
    }

    // ===== LIRE =====
    public List<Amende> findAll()           { return amendeDAO.findAll(); }
    public List<Amende> findAllEnAttente()  { return amendeDAO.findAllEnAttente(); }
    public Amende       findById(Long id)   { return amendeDAO.findById(id); }

    public List<Amende> findByMembre(Long membreId) {
        return amendeDAO.findByMembre(membreId);
    }

    public List<Amende> findEnAttenteByMembre(Long membreId) {
        return amendeDAO.findEnAttenteByMembre(membreId);
    }

    public Double totalEnAttente() {
        return amendeDAO.totalAmendesEnAttente();
    }

    public long countEnAttente() {
        return amendeDAO.findAllEnAttente().size();
    }
}