package com.association.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cotisations")
public class Cotisation {

    // ===================== CLÉ PRIMAIRE =====================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // ===================== INFORMATIONS COTISATION =====================
    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "mois", nullable = false)
    private Integer mois; // 1 = Janvier, 12 = Décembre

    @Column(name = "annee", nullable = false)
    private Integer annee;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_paiement")
    private Date datePaiement;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode_paiement", length = 20)
    private ModePaiement modePaiement;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 20)
    private StatutCotisation statut;

    @Column(name = "reference", length = 100)
    private String reference; // Numéro de reçu ou référence paiement

    @Column(name = "commentaire", length = 255)
    private String commentaire;

    // ===================== RELATION AVEC MEMBRE =====================
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membre_id", nullable = false)
    private Membre membre;

    // ===================== CONSTRUCTEURS =====================
    public Cotisation() {
        // Constructeur vide obligatoire pour JPA
    }

    public Cotisation(Double montant, Integer mois, Integer annee,
                      ModePaiement modePaiement, Membre membre) {
        this.montant      = montant;
        this.mois         = mois;
        this.annee        = annee;
        this.modePaiement = modePaiement;
        this.membre       = membre;
        this.statut       = StatutCotisation.EN_ATTENTE;
        this.datePaiement = new Date();
    }

    // ===================== GETTERS & SETTERS =====================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }

    public Integer getMois() { return mois; }
    public void setMois(Integer mois) { this.mois = mois; }

    public Integer getAnnee() { return annee; }
    public void setAnnee(Integer annee) { this.annee = annee; }

    public Date getDatePaiement() { return datePaiement; }
    public void setDatePaiement(Date datePaiement) { this.datePaiement = datePaiement; }

    public ModePaiement getModePaiement() { return modePaiement; }
    public void setModePaiement(ModePaiement modePaiement) { this.modePaiement = modePaiement; }

    public StatutCotisation getStatut() { return statut; }
    public void setStatut(StatutCotisation statut) { this.statut = statut; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }

    public Membre getMembre() { return membre; }
    public void setMembre(Membre membre) { this.membre = membre; }

    // ===================== MÉTHODES UTILES =====================

    // Retourne le nom du mois en français
    public String getMoisNom() {
        String[] mois = {"", "Janvier", "Février", "Mars", "Avril",
                         "Mai", "Juin", "Juillet", "Août",
                         "Septembre", "Octobre", "Novembre", "Décembre"};
        return (this.mois >= 1 && this.mois <= 12) ? mois[this.mois] : "Inconnu";
    }

    // Retourne la période ex: "Janvier 2025"
    public String getPeriode() {
        return getMoisNom() + " " + annee;
    }

    // Vérifie si la cotisation est payée
    public boolean estPayee() {
        return StatutCotisation.PAYEE.equals(this.statut);
    }

    @Override
    public String toString() {
        return "Cotisation{id=" + id +
               ", membre=" + (membre != null ? membre.getNomComplet() : "null") +
               ", periode=" + getPeriode() +
               ", montant=" + montant +
               ", statut=" + statut + "}";
    }
}