package com.utraveler.dao;

import java.util.Collection;

public interface DeleteEventDao {

    boolean deleteFromEvent(long eventId);

    boolean deleteFromEvent(Collection<Long> entitiesId, long eventId);
}
