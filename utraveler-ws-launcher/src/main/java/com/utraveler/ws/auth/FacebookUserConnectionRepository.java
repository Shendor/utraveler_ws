/*
package com.utraveler.ws.auth;

import java.util.List;
import java.util.Set;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class FacebookUserConnectionRepository implements UsersConnectionRepository {

    private ConnectionRepository connectionRepository;


    public FacebookUserConnectionRepository(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }


    @Override
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        return Lists.newArrayList();
    }


    @Override
    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        return Sets.newHashSet();
    }


    @Override
    public ConnectionRepository createConnectionRepository(String userId) {
        return connectionRepository;
    }
}
*/
