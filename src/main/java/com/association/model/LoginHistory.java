package com.association.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "login_history")
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "adresse_ip", length = 50)
    private String adresseIp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_connexion", nullable = false)
    private Date dateConnexion;

    @Column(name = "statut", nullable = false, length = 20)
    private String statut; // SUCCES ou ECHEC

    @Column(name = "message", length = 255)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membre_id")
    private Membre membre; // null si échec

    // ===== CONSTRUCTEURS =====
    public LoginHistory() {}

    public LoginHistory(String email, String adresseIp,
                        String statut, String message,
                        Membre membre) {
        this.email         = email;
        this.adresseIp     = adresseIp;
        this.statut        = statut;
        this.message       = message;
        this.membre        = membre;
        this.dateConnexion = new Date();
    }

    // ===== GETTERS & SETTERS =====
    public Long    getId()            { return id; }
    public void    setId(Long id)     { this.id = id; }

    public String  getEmail()         { return email; }
    public void    setEmail(String e) { this.email = e; }

    public String  getAdresseIp()     { return adresseIp; }
    public void    setAdresseIp(String ip) { this.adresseIp = ip; }

    public Date    getDateConnexion()  { return dateConnexion; }
    public void    setDateConnexion(Date d) { this.dateConnexion = d; }

    public String  getStatut()        { return statut; }
    public void    setStatut(String s) { this.statut = s; }

    public String  getMessage()       { return message; }
    public void    setMessage(String m) { this.message = m; }

    public Membre  getMembre()        { return membre; }
    public void    setMembre(Membre m) { this.membre = m; }

    public boolean estSucces() {
        return "SUCCES".equals(this.statut);
    }
}