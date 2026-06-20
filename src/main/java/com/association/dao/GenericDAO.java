package com.association.dao;

import java.util.List;

public interface GenericDAO<T, ID> {

    // Créer
    void save(T entity);

    // Lire
    T findById(ID id);
    List<T> findAll();

    // Modifier
    T update(T entity);

    // Supprimer
    void delete(T entity);
    void deleteById(ID id);

    // Compter
    long count();
}