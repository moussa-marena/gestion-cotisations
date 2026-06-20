package com.association.dao;

import com.association.model.Amende;
import com.association.model.StatutAmende;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AmendeDAO extends AbstractDAO<Amende, Long> {

    // Toutes les amendes d'un membre - avec membre chargé
    public List<Amende> findByMembre(Long membreId) {
        EntityManager em = getEM();
        try {
            TypedQuery<Amende> query = em.createQuery(
                "SELECT a FROM Amende a " +
                "JOIN FETCH a.membre m " +
                "WHERE a.membre.id = :membreId " +
                "ORDER BY a.dateGeneration DESC", Amende.class);
            query.setParameter("membreId", membreId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Amendes en attente d'un membre - avec membre chargé
    public List<Amende> findEnAttenteByMembre(Long membreId) {
        EntityManager em = getEM();
        try {
            TypedQuery<Amende> query = em.createQuery(
                "SELECT a FROM Amende a " +
                "JOIN FETCH a.membre m " +
                "WHERE a.membre.id = :membreId " +
                "AND a.statut = :statut " +
                "ORDER BY a.dateGeneration DESC", Amende.class);
            query.setParameter("membreId", membreId);
            query.setParameter("statut", StatutAmende.EN_ATTENTE);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Toutes les amendes en attente - avec membre chargé
    public List<Amende> findAllEnAttente() {
        EntityManager em = getEM();
        try {
            TypedQuery<Amende> query = em.createQuery(
                "SELECT a FROM Amende a " +
                "JOIN FETCH a.membre m " +
                "WHERE a.statut = :statut " +
                "ORDER BY a.dateGeneration DESC", Amende.class);
            query.setParameter("statut", StatutAmende.EN_ATTENTE);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Toutes les amendes - avec membre chargé
    public List<Amende> findAll() {
        EntityManager em = getEM();
        try {
            TypedQuery<Amende> query = em.createQuery(
                "SELECT a FROM Amende a " +
                "JOIN FETCH a.membre m " +
                "ORDER BY a.dateGeneration DESC", Amende.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Vérifier si une amende existe déjà pour un mois
    public boolean amendeExistePourPeriode(Long membreId,
                                           Integer mois, Integer annee) {
        EntityManager em = getEM();
        try {
            Long count = em.createQuery(
                "SELECT COUNT(a) FROM Amende a " +
                "WHERE a.membre.id = :membreId " +
                "AND a.moisConcerne = :mois " +
                "AND a.anneeConcernee = :annee", Long.class)
                .setParameter("membreId", membreId)
                .setParameter("mois", mois)
                .setParameter("annee", annee)
                .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    // Total des amendes en attente
    public Double totalAmendesEnAttente() {
        EntityManager em = getEM();
        try {
            Double total = em.createQuery(
                "SELECT SUM(a.montant) FROM Amende a " +
                "WHERE a.statut = :statut", Double.class)
                .setParameter("statut", StatutAmende.EN_ATTENTE)
                .getSingleResult();
            return total != null ? total : 0.0;
        } finally {
            em.close();
        }
    }
}