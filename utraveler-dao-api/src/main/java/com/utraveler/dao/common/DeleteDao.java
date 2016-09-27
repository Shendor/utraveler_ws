package com.utraveler.dao.common;

public interface DeleteDao<T> {

    void delete(T entity);


    void delete(long id);

}
