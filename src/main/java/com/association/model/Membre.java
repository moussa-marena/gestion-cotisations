package com.association.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "membres")
public class Membre {

    // ===================== CLÉ PRIMAIRE =====================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // ===================== INFORMATIONS PERSONNELLES =====================
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "telephone", length = 20)
    private String telephone;

    @Column(name = "adresse", length = 255)
    private String adresse;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_naissance")
    private Date dateNaissance;

    // ===================== INFORMATIONS D'ADHÉSION =====================
    @Temporal(TemporalType.DATE)
    @Column(name = "date_adhesion", nullable = false)
    private Date dateAdhesion;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 20)
    private StatutMembre statut;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role;

    // ===================== SÉCURITÉ =====================
    @Column(name = "mot_de_passe", nullable = false, length = 255)
    private String motDePasse;

    // ===================== RELATIONS =====================
    @OneToMany(mappedBy = "membre", cascade = CascadeType.ALL,
               fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Cotisation> cotisations = new ArrayList<>();

    @OneToMany(mappedBy = "membre", cascade = CascadeType.ALL,
               fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Amende> amendes = new ArrayList<>();

    // ===================== CONSTRUCTEURS =====================
    public Membre() {
        // Constructeur vide obligatoire pour JPA
    }

    public Membre(String nom, String prenom, String email,
                  String motDePasse, Date dateAdhesion) {
        this.nom         = nom;
        this.prenom      = prenom;
        this.email       = email;
        this.motDePasse  = motDePasse;
        this.dateAdhesion = dateAdhesion;
        this.statut      = StatutMembre.ACTIF;
        this.role        = Role.MEMBRE;
    }

    // ===================== GETTERS & SETTERS =====================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public Date getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(Date dateNaissance) { this.dateNaissance = dateNaissance; }

    public Date getDateAdhesion() { return dateAdhesion; }
    public void setDateAdhesion(Date dateAdhesion) { this.dateAdhesion = dateAdhesion; }

    public StatutMembre getStatut() { return statut; }
    public void setStatut(StatutMembre statut) { this.statut = statut; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public List<Cotisation> getCotisations() { return cotisations; }
    public void setCotisations(List<Cotisation> cotisations) { this.cotisations = cotisations; }

    public List<Amende> getAmendes() { return amendes; }
    public void setAmendes(List<Amende> amendes) { this.amendes = amendes; }

    // ===================== MÉTHODES UTILES =====================

    // Retourne le nom complet
    public String getNomComplet() {
        return prenom + " " + nom;
    }

    // Vérifie si le membre est actif
    public boolean isActif() {
        return StatutMembre.ACTIF.equals(this.statut);
    }

    // Vérifie si c'est un admin
    public boolean isAdmin() {
        return Role.ADMIN.equals(this.role);
    }

    @Override
    public String toString() {
        return "Membre{id=" + id +
               ", nom='" + nom + "'" +
               ", prenom='" + prenom + "'" +
               ", email='" + email + "'" +
               ", statut=" + statut +
               ", role=" + role + "}";
    }
}