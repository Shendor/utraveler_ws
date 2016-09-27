package com.utraveler.dao.common;

import java.util.Collection;

public interface InsertDao<T> {

    void insert(T entity);


    void insert(Collection<T> entities);
}
