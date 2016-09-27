package com.utraveler.dao;

public interface HiLoKeyGenerator<T> {

    long getNextKey(String tableName);
}
