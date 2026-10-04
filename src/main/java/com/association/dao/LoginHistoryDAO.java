package com.association.dao;

import com.association.model.LoginHistory;
import com.association.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class LoginHistoryDAO {

    // Enregistrer une entrée
    public void save(LoginHistory history) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(history);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Erreur LoginHistory : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Tous les historiques (avec membre chargé si existe)
    public List<LoginHistory> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT l FROM LoginHistory l " +
                "LEFT JOIN FETCH l.membre " +
                "ORDER BY l.dateConnexion DESC",
                LoginHistory.class)
                .getResultList();
        } finally {
            em.close();
        }
    }

    // Historiques d'un membre
    public List<LoginHistory> findByMembre(Long membreId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT l FROM LoginHistory l " +
                "LEFT JOIN FETCH l.membre " +
                "WHERE l.membre.id = :membreId " +
                "ORDER BY l.dateConnexion DESC",
                LoginHistory.class)
                .setParameter("membreId", membreId)
                .getResultList();
        } finally {
            em.close();
        }
    }

    // Nombre total de connexions réussies
    public long countSucces() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT COUNT(l) FROM LoginHistory l " +
                "WHERE l.statut = 'SUCCES'", Long.class)
                .getSingleResult();
        } finally {
            em.close();
        }
    }

    // Nombre total d'échecs
    public long countEchecs() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT COUNT(l) FROM LoginHistory l " +
                "WHERE l.statut = 'ECHEC'", Long.class)
                .getSingleResult();
        } finally {
            em.close();
        }
    }

    // 10 dernières connexions
    public List<LoginHistory> findDernieres(int limit) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT l FROM LoginHistory l " +
                "LEFT JOIN FETCH l.membre " +
                "ORDER BY l.dateConnexion DESC",
                LoginHistory.class)
                .setMaxResults(limit)
                .getResultList();
        } finally {
            em.close();
        }
    }
}