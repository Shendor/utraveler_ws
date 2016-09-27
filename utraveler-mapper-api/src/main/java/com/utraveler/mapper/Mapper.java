package com.utraveler.mapper;

import com.utraveler.dao.entity.BaseEntity;

public interface Mapper<M, E extends BaseEntity> {

    E mapModel(M model);


    M mapEntity(E entity);
}
