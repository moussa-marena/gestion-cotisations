package com.association.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "amendes")
public class Amende {

    // ===================== CLÉ PRIMAIRE =====================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // ===================== INFORMATIONS AMENDE =====================
    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "motif", nullable = false, length = 255)
    private String motif;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_generation", nullable = false)
    private Date dateGeneration;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_paiement")
    private Date datePaiement; // null si pas encore payée

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 20)
    private StatutAmende statut;

    @Column(name = "mois_concerne")
    private Integer moisConcerne; // Le mois de retard concerné

    @Column(name = "annee_concernee")
    private Integer anneeConcernee;

    @Column(name = "commentaire", length = 255)
    private String commentaire;

    // ===================== RELATION AVEC MEMBRE =====================
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membre_id", nullable = false)
    private Membre membre;

    // ===================== CONSTRUCTEURS =====================
    public Amende() {
        // Constructeur vide obligatoire pour JPA
    }

    public Amende(Double montant, String motif, Membre membre,
                  Integer moisConcerne, Integer anneeConcernee) {
        this.montant        = montant;
        this.motif          = motif;
        this.membre         = membre;
        this.moisConcerne   = moisConcerne;
        this.anneeConcernee = anneeConcernee;
        this.dateGeneration = new Date();
        this.statut         = StatutAmende.EN_ATTENTE;
    }

    // ===================== GETTERS & SETTERS =====================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public Date getDateGeneration() { return dateGeneration; }
    public void setDateGeneration(Date dateGeneration) { this.dateGeneration = dateGeneration; }

    public Date getDatePaiement() { return datePaiement; }
    public void setDatePaiement(Date datePaiement) { this.datePaiement = datePaiement; }

    public StatutAmende getStatut() { return statut; }
    public void setStatut(StatutAmende statut) { this.statut = statut; }

    public Integer getMoisConcerne() { return moisConcerne; }
    public void setMoisConcerne(Integer moisConcerne) { this.moisConcerne = moisConcerne; }

    public Integer getAnneeConcernee() { return anneeConcernee; }
    public void setAnneeConcernee(Integer anneeConcernee) { this.anneeConcernee = anneeConcernee; }

    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }

    public Membre getMembre() { return membre; }
    public void setMembre(Membre membre) { this.membre = membre; }

    // ===================== MÉTHODES UTILES =====================

    // Vérifie si l'amende est payée
    public boolean estPayee() {
        return StatutAmende.PAYEE.equals(this.statut);
    }

    // Vérifie si l'amende est en attente
    public boolean estEnAttente() {
        return StatutAmende.EN_ATTENTE.equals(this.statut);
    }

    @Override
    public String toString() {
        return "Amende{id=" + id +
               ", membre=" + (membre != null ? membre.getNomComplet() : "null") +
               ", montant=" + montant +
               ", motif='" + motif + "'" +
               ", statut=" + statut + "}";
    }
}