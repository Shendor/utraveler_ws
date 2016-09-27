package com.utraveler.dao.mysql;

import java.util.Collection;
import java.util.List;

import com.utraveler.dao.TripPlanDao;
import com.utraveler.dao.entity.TripPlanEntity;
import com.utraveler.dao.mysql.common.BaseDao;
import com.utraveler.dao.mysql.entity.TripPlanEntityImpl;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("tripPlanDao")
public class TripPlanDaoImpl extends BaseDao<TripPlanEntity> implements TripPlanDao {

    @Override
    public TripPlanEntity findTripPlanOfEvent(long eventId) {
        Query query = getSession().getNamedQuery("TripPlan.findTripPlanOfEvent");
        query.setParameter("eventId", eventId);

        List result = query.list();
        if (!result.isEmpty()) {
            return (TripPlanEntity)result.get(0);
        } else {
            return null;
        }
    }


    @Override
    public TripPlanEntity getById(long id) {
        return (TripPlanEntity)getSession().get(TripPlanEntityImpl.class, id);
    }


    @Override
    public boolean deleteFromEvent(long eventId) {
        Query query = getSession().getNamedQuery("TripPlan.deleteFromEvent");
        query.setLong("eventId", eventId);
        return query.executeUpdate() > 0;
    }


    @Override
    public boolean deleteFromEvent(Collection<Long> entitiesId, long eventId) {
        return false;
    }
}
