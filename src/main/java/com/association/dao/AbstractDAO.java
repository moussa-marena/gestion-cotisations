package com.association.dao;

import com.association.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractDAO<T, ID> implements GenericDAO<T, ID> {

    // Récupère automatiquement le type de l'entité (ex: Membre.class)
    private final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.entityClass = (Class<T>) ((ParameterizedType)
                getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    // ===================== MÉTHODE UTILITAIRE =====================

    protected EntityManager getEM() {
        return JPAUtil.getEntityManager();
    }

    // ===================== CRUD DE BASE =====================

    @Override
    public void save(T entity) {
        EntityManager em = getEM();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Erreur lors de la sauvegarde : " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public T findById(ID id) {
        EntityManager em = getEM();
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> findAll() {
        EntityManager em = getEM();
        try {
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            TypedQuery<T> query = em.createQuery(jpql, entityClass);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public T update(T entity) {
        EntityManager em = getEM();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T updated = em.merge(entity);
            tx.commit();
            return updated;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Erreur lors de la mise à jour : " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(T entity) {
        EntityManager em = getEM();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // merge nécessaire si l'entité vient d'un autre EntityManager
            T managed = em.merge(entity);
            em.remove(managed);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Erreur lors de la suppression : " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(ID id) {
        T entity = findById(id);
        if (entity != null) {
            delete(entity);
        }
    }

    @Override
    public long count() {
        EntityManager em = getEM();
        try {
            String jpql = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e";
            return em.createQuery(jpql, Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
}