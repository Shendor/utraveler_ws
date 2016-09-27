package com.utraveler.dao.common;

public interface Dao<T> extends InsertDao<T>, UpdateDao<T>, DeleteDao<T> {

    T getById(long id);
}
