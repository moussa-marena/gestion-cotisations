package com.association.dao;

import com.association.model.Membre;
import com.association.model.StatutMembre;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class MembreDAO extends AbstractDAO<Membre, Long> {

    // Trouver un membre par email (pour le login)
    public Membre findByEmail(String email) {
        EntityManager em = getEM();
        try {
            TypedQuery<Membre> query = em.createQuery(
                "SELECT m FROM Membre m WHERE m.email = :email", Membre.class);
            query.setParameter("email", email);
            List<Membre> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    // Trouver tous les membres actifs
    public List<Membre> findAllActifs() {
        EntityManager em = getEM();
        try {
            TypedQuery<Membre> query = em.createQuery(
                "SELECT m FROM Membre m WHERE m.statut = :statut " +
                "ORDER BY m.nom ASC", Membre.class);
            query.setParameter("statut", StatutMembre.ACTIF);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Rechercher des membres par nom ou prénom
    public List<Membre> search(String keyword) {
        EntityManager em = getEM();
        try {
            TypedQuery<Membre> query = em.createQuery(
                "SELECT m FROM Membre m WHERE " +
                "LOWER(m.nom) LIKE :keyword OR " +
                "LOWER(m.prenom) LIKE :keyword OR " +
                "LOWER(m.email) LIKE :keyword " +
                "ORDER BY m.nom ASC", Membre.class);
            query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Vérifier si un email existe déjà
    public boolean emailExiste(String email) {
        EntityManager em = getEM();
        try {
            Long count = em.createQuery(
                "SELECT COUNT(m) FROM Membre m WHERE m.email = :email",
                Long.class)
                .setParameter("email", email)
                .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    // Compter les membres actifs
    public long countActifs() {
        EntityManager em = getEM();
        try {
            return em.createQuery(
                "SELECT COUNT(m) FROM Membre m WHERE m.statut = :statut",
                Long.class)
                .setParameter("statut", StatutMembre.ACTIF)
                .getSingleResult();
        } finally {
            em.close();
        }
    }
}