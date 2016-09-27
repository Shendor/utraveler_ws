package com.utraveler.mapper.impl;

import com.utraveler.dao.entity.RoutePushpinEntity;
import com.utraveler.dao.entity.impl.RoutePushpinEntityImpl;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.Color;
import com.utraveler.model.GeoCoordinate;
import com.utraveler.model.RoutePushpin;

public class RoutePushpinMapper implements Mapper<RoutePushpin, RoutePushpinEntity> {

    @Override
    public RoutePushpinEntity mapModel(RoutePushpin model) {
        RoutePushpinEntityImpl routePushpinEntity = null;
        if (model.getCoordinate() != null) {
            routePushpinEntity = new RoutePushpinEntityImpl();
            routePushpinEntity.setId(model.getId());
            routePushpinEntity.setName(model.getName());
            routePushpinEntity.setDescription(model.getDescription());
            routePushpinEntity.setLatitude(model.getCoordinate().getLatitude());
            routePushpinEntity.setLongitude(model.getCoordinate().getLongitude());
            routePushpinEntity.setThumbnail(model.getThumbnail());
            if (model.getColor() != null) {
                routePushpinEntity.setColor(new byte[]{model.getColor().getR(), model.getColor().getG(), model.getColor().getB()});
            }
        }

        return routePushpinEntity;
    }


    @Override
    public RoutePushpin mapEntity(RoutePushpinEntity entity) {
        RoutePushpin routePushpin = null;
        if (entity.getLatitude() != 0 && entity.getLongitude() != 0) {
            routePushpin = new RoutePushpin();
            routePushpin.setId(entity.getId());
            routePushpin.setName(entity.getName());
            routePushpin.setDescription(entity.getDescription());
            routePushpin.setCoordinate(new GeoCoordinate(entity.getLatitude(), entity.getLongitude()));
            routePushpin.setThumbnail(entity.getThumbnail());
            if (entity.getColor() != null && entity.getColor().length == 3) {
                routePushpin.setColor(new Color(entity.getColor()[0], entity.getColor()[1], entity.getColor()[2]));
            }
        }
        return routePushpin;
    }
}
