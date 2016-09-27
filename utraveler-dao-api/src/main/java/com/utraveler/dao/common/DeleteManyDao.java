package com.utraveler.dao.common;

import java.util.Collection;

public interface DeleteManyDao {

    void delete(Collection<Long> entitiesId);
}
