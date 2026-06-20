package com.association.dao;

import com.association.model.Cotisation;
import com.association.model.StatutCotisation;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CotisationDAO extends AbstractDAO<Cotisation, Long> {

    // Toutes les cotisations d'un membre - avec membre chargé
    public List<Cotisation> findByMembre(Long membreId) {
        EntityManager em = getEM();
        try {
            TypedQuery<Cotisation> query = em.createQuery(
                "SELECT c FROM Cotisation c " +
                "JOIN FETCH c.membre m " +
                "WHERE c.membre.id = :membreId " +
                "ORDER BY c.annee DESC, c.mois DESC", Cotisation.class);
            query.setParameter("membreId", membreId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Cotisations d'un mois/année donné - avec membre chargé
    public List<Cotisation> findByMoisAnnee(Integer mois, Integer annee) {
        EntityManager em = getEM();
        try {
            TypedQuery<Cotisation> query = em.createQuery(
                "SELECT c FROM Cotisation c " +
                "JOIN FETCH c.membre m " +
                "WHERE c.mois = :mois " +
                "AND c.annee = :annee " +
                "ORDER BY m.nom ASC", Cotisation.class);
            query.setParameter("mois", mois);
            query.setParameter("annee", annee);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Vérifier si un membre a payé pour un mois/année
    public boolean aDejaPayePourPeriode(Long membreId, Integer mois, Integer annee) {
        EntityManager em = getEM();
        try {
            Long count = em.createQuery(
                "SELECT COUNT(c) FROM Cotisation c WHERE c.membre.id = :membreId " +
                "AND c.mois = :mois AND c.annee = :annee " +
                "AND c.statut = :statut", Long.class)
                .setParameter("membreId", membreId)
                .setParameter("mois", mois)
                .setParameter("annee", annee)
                .setParameter("statut", StatutCotisation.PAYEE)
                .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    // IDs des membres qui ont payé pour un mois/année
    public List<Long> findMembresAJourPourPeriode(Integer mois, Integer annee) {
        EntityManager em = getEM();
        try {
            return em.createQuery(
                "SELECT c.membre.id FROM Cotisation c WHERE c.mois = :mois " +
                "AND c.annee = :annee AND c.statut = :statut", Long.class)
                .setParameter("mois", mois)
                .setParameter("annee", annee)
                .setParameter("statut", StatutCotisation.PAYEE)
                .getResultList();
        } finally {
            em.close();
        }
    }

    // Total des cotisations encaissées pour un mois/année
    public Double totalEncaisseParPeriode(Integer mois, Integer annee) {
        EntityManager em = getEM();
        try {
            Double total = em.createQuery(
                "SELECT SUM(c.montant) FROM Cotisation c WHERE c.mois = :mois " +
                "AND c.annee = :annee AND c.statut = :statut", Double.class)
                .setParameter("mois", mois)
                .setParameter("annee", annee)
                .setParameter("statut", StatutCotisation.PAYEE)
                .getSingleResult();
            return total != null ? total : 0.0;
        } finally {
            em.close();
        }
    }
}