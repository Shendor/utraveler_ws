/*
package com.utraveler.ws.auth;

import java.util.List;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.google.common.collect.Lists;

public class FacebookConnectionRepository implements ConnectionRepository {

    @Override
    public MultiValueMap<String, Connection<?>> findAllConnections() {
        return new LinkedMultiValueMap<>();
    }


    @Override
    public List<Connection<?>> findConnections(String providerId) {
        return Lists.newArrayList();
    }


    @Override
    public <A> List<Connection<A>> findConnections(Class<A> apiType) {
        return Lists.newArrayList();
    }


    @Override
    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUserIds) {
        return new LinkedMultiValueMap<>();
    }


    @Override
    public Connection<?> getConnection(ConnectionKey connectionKey) {
        return null;
    }


    @Override
    public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
        return null;
    }


    @Override
    public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
        return null;
    }


    @Override
    public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
        return null;
    }


    @Override
    public void addConnection(Connection<?> connection) {

    }


    @Override
    public void updateConnection(Connection<?> connection) {

    }


    @Override
    public void removeConnections(String providerId) {

    }


    @Override
    public void removeConnection(ConnectionKey connectionKey) {

    }
}
*/
