package com.association.service;

import com.association.dao.MembreDAO;
import com.association.model.Membre;
import com.association.model.Role;
import com.association.model.StatutMembre;
import com.association.util.PasswordUtil;

import java.util.Date;
import java.util.List;

public class MembreService {

    private final MembreDAO dao = new MembreDAO();

    // ===== CRÉER =====
    public void creer(String nom, String prenom, String email,
                      String telephone, String adresse,
                      String motDePasse, String role) throws Exception {

        if (dao.emailExiste(email.trim().toLowerCase())) {
            throw new Exception("Cet email est déjà utilisé.");
        }

        Membre m = new Membre();
        m.setNom(nom.trim());
        m.setPrenom(prenom.trim());
        m.setEmail(email.trim().toLowerCase());
        m.setTelephone(telephone);
        m.setAdresse(adresse);
        m.setMotDePasse(PasswordUtil.hash(motDePasse));
        m.setDateAdhesion(new Date());
        m.setStatut(StatutMembre.ACTIF);
        m.setRole(Role.valueOf(role));

        dao.save(m);
    }

    // ===== MODIFIER =====
    public void modifier(Long id, String nom, String prenom,
                         String email, String telephone,
                         String adresse, String role) throws Exception {

        Membre m = dao.findById(id);
        if (m == null) throw new Exception("Membre introuvable.");

        // Vérifier email uniquement si changé
        if (!m.getEmail().equals(email.trim().toLowerCase())) {
            if (dao.emailExiste(email.trim().toLowerCase())) {
                throw new Exception("Cet email est déjà utilisé.");
            }
        }

        m.setNom(nom.trim());
        m.setPrenom(prenom.trim());
        m.setEmail(email.trim().toLowerCase());
        m.setTelephone(telephone);
        m.setAdresse(adresse);
        m.setRole(Role.valueOf(role));

        dao.update(m);
    }

    // ===== CHANGER MOT DE PASSE =====
    public void changerMotDePasse(Long id, String nouveauMdp) throws Exception {
        Membre m = dao.findById(id);
        if (m == null) throw new Exception("Membre introuvable.");
        m.setMotDePasse(PasswordUtil.hash(nouveauMdp));
        dao.update(m);
    }

    // ===== TOGGLE ACTIF/INACTIF =====
    public void toggleStatut(Long id) throws Exception {
        Membre m = dao.findById(id);
        if (m == null) throw new Exception("Membre introuvable.");
        if (StatutMembre.ACTIF.equals(m.getStatut())) {
            m.setStatut(StatutMembre.INACTIF);
        } else {
            m.setStatut(StatutMembre.ACTIF);
        }
        dao.update(m);
    }

    // ===== SUPPRIMER =====
    public void supprimer(Long id) throws Exception {
        Membre m = dao.findById(id);
        if (m == null) throw new Exception("Membre introuvable.");
        dao.delete(m);
    }

    // ===== LIRE =====
    public Membre findById(Long id) { return dao.findById(id); }
    public List<Membre> findAll()   { return dao.findAll(); }
    public List<Membre> search(String keyword) { return dao.search(keyword); }
    public long count()             { return dao.count(); }
    public long countActifs()       { return dao.countActifs(); }
}