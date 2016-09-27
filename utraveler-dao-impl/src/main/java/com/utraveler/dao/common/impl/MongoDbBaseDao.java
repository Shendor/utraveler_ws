package com.utraveler.dao.common.impl;

import java.util.Collection;

import org.springframework.data.mongodb.core.MongoOperations;

import com.utraveler.dao.HiLoKeyGenerator;
import com.utraveler.dao.common.Dao;
import com.utraveler.dao.entity.BaseEntity;

public abstract class MongoDbBaseDao<T extends BaseEntity> implements Dao<T> {

    protected MongoOperations mongoOperations;
    private HiLoKeyGenerator<Long> keyGenerator;


    public MongoDbBaseDao(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }


    public void setKeyGenerator(HiLoKeyGenerator<Long> keyGenerator) {
        this.keyGenerator = keyGenerator;
    }


    @Override
    public void delete(long entityId) {
        T foundEntity = getById(entityId);
        if (foundEntity != null) {
            mongoOperations.remove(foundEntity);
        }
    }


    @Override
    public void delete(T entity) {
        if (entity != null) {
            mongoOperations.remove(entity);
        }
    }


    @Override
    public void insert(T entity) {
        if (keyGenerator != null) {
            long key = keyGenerator.getNextKey(getTableName());
            entity.setId(key);
        }
        mongoOperations.insert(entity);
    }


    @Override
    public void insert(Collection<T> entities) {
        String tableName = getTableName();
        if (keyGenerator != null) {
            for (T entity : entities) {
                long key = keyGenerator.getNextKey(tableName);
                entity.setId(key);
            }
        }
        mongoOperations.insert(entities, tableName);
    }


    @Override
    public void update(T entity) {
        if (entity.getId() > 0) {
            mongoOperations.save(entity);
        }
    }


    protected abstract String getTableName();
}
