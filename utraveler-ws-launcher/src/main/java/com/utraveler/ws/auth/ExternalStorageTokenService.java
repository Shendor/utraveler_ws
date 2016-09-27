package com.utraveler.ws.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;

public class ExternalStorageTokenService implements TokenStore {

    private static final int DEFAULT_FLUSH_INTERVAL = 1000;

    private final ConcurrentHashMap<String, OAuth2AccessToken> accessTokenStore = new ConcurrentHashMap<String, OAuth2AccessToken>();

    private final ConcurrentHashMap<String, OAuth2AccessToken> authenticationToAccessTokenStore = new ConcurrentHashMap<String, OAuth2AccessToken>();

    private final ConcurrentHashMap<String, Collection<OAuth2AccessToken>> userNameToAccessTokenStore = new ConcurrentHashMap<String, Collection<OAuth2AccessToken>>();

    private final ConcurrentHashMap<String, Collection<OAuth2AccessToken>> clientIdToAccessTokenStore = new ConcurrentHashMap<String, Collection<OAuth2AccessToken>>();

    private final ConcurrentHashMap<String, OAuth2RefreshToken> refreshTokenStore = new ConcurrentHashMap<String, OAuth2RefreshToken>();

    private final ConcurrentHashMap<String, String> accessTokenToRefreshTokenStore = new ConcurrentHashMap<String, String>();

    private final ConcurrentHashMap<String, OAuth2Authentication> authenticationStore = new ConcurrentHashMap<String, OAuth2Authentication>();

    private final ConcurrentHashMap<String, OAuth2Authentication> refreshTokenAuthenticationStore = new ConcurrentHashMap<String, OAuth2Authentication>();

    private final ConcurrentHashMap<String, String> refreshTokenToAcessTokenStore = new ConcurrentHashMap<String, String>();

    private final DelayQueue<TokenExpiry> expiryQueue = new DelayQueue<TokenExpiry>();

    private final ConcurrentHashMap<String, TokenExpiry> expiryMap = new ConcurrentHashMap<String, TokenExpiry>();

    private int flushInterval = DEFAULT_FLUSH_INTERVAL;

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    private AtomicInteger flushCounter = new AtomicInteger(0);


    /**
     * The number of tokens to store before flushing expired tokens. Defaults to 1000.
     *
     * @param flushInterval the interval to set
     */
    public void setFlushInterval(int flushInterval) {
        this.flushInterval = flushInterval;
    }


    /**
     * The interval (count of token inserts) between flushing expired tokens.
     *
     * @return the flushInterval the flush interval
     */
    public int getFlushInterval() {
        return flushInterval;
    }


    /**
     * Convenience method for super admin users to remove all tokens (useful for testing, not really in production)
     */
    public void clear() {
        accessTokenStore.clear();
        authenticationToAccessTokenStore.clear();
        userNameToAccessTokenStore.clear();
        clientIdToAccessTokenStore.clear();
        refreshTokenStore.clear();
        accessTokenToRefreshTokenStore.clear();
        authenticationStore.clear();
        refreshTokenAuthenticationStore.clear();
        refreshTokenToAcessTokenStore.clear();
        expiryQueue.clear();
    }


    public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
        this.authenticationKeyGenerator = authenticationKeyGenerator;
    }


    public int getAccessTokenCount() {
        Assert.state(accessTokenStore.isEmpty() || accessTokenStore.size() >= accessTokenToRefreshTokenStore.size(),
                     "Too many refresh tokens");
        Assert.state(accessTokenStore.size() == authenticationToAccessTokenStore.size(),
                     "Inconsistent token store state");
        Assert.state(accessTokenStore.size() <= authenticationStore.size(), "Inconsistent authentication store state");
        return accessTokenStore.size();
    }


    public int getRefreshTokenCount() {
        Assert.state(refreshTokenStore.size() == refreshTokenToAcessTokenStore.size(),
                     "Inconsistent refresh token store state");
        return accessTokenStore.size();
    }


    public int getExpiryTokenCount() {
        return expiryQueue.size();
    }


    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken accessToken = authenticationToAccessTokenStore.get(authenticationKeyGenerator
                                                                                     .extractKey(authentication));
        if (accessToken != null && !authentication.equals(readAuthentication(accessToken.getValue()))) {
            // Keep the stores consistent (maybe the same user is represented by this authentication but the details
            // have changed)
            storeAccessToken(accessToken, authentication);
        }

        return accessToken;
    }


    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }


    @Override
    public OAuth2Authentication readAuthentication(String token) {
        return this.authenticationStore.get(token);
    }


    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthenticationForRefreshToken(token.getValue());
    }


    public OAuth2Authentication readAuthenticationForRefreshToken(String token) {
        return this.refreshTokenAuthenticationStore.get(token);
    }


    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        if (this.flushCounter.incrementAndGet() >= this.flushInterval) {
            flush();
            this.flushCounter.set(0);
        }
        this.accessTokenStore.put(token.getValue(), token);
        this.authenticationStore.put(token.getValue(), authentication);
        this.authenticationToAccessTokenStore.put(authenticationKeyGenerator.extractKey(authentication), token);
        if (!authentication.isClientOnly()) {
            addToCollection(this.userNameToAccessTokenStore, authentication.getName(), token);
        }
        addToCollection(this.clientIdToAccessTokenStore, authentication.getAuthorizationRequest().getClientId(), token);
        if (token.getExpiration() != null) {
            TokenExpiry expiry = new TokenExpiry(token.getValue(), token.getExpiration());
            // Remove existing expiry for this token if present
            expiryQueue.remove(expiryMap.put(token.getValue(), expiry));
            this.expiryQueue.put(expiry);
        }
        if (token.getRefreshToken() != null && token.getRefreshToken().getValue() != null) {
            this.refreshTokenToAcessTokenStore.put(token.getRefreshToken().getValue(), token.getValue());
            this.accessTokenToRefreshTokenStore.put(token.getValue(), token.getRefreshToken().getValue());
        }
    }


    private void addToCollection(ConcurrentHashMap<String, Collection<OAuth2AccessToken>> store, String key,
                                 OAuth2AccessToken token) {
        if (!store.containsKey(key)) {
            synchronized (store) {
                if (!store.containsKey(key)) {
                    store.put(key, new HashSet<OAuth2AccessToken>());
                }
            }
        }
        store.get(key).add(token);
    }


    @Override
    public void removeAccessToken(OAuth2AccessToken accessToken) {
        removeAccessToken(accessToken.getValue());
    }


    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        return this.accessTokenStore.get(tokenValue);
    }


    public void removeAccessToken(String tokenValue) {
        OAuth2AccessToken removed = this.accessTokenStore.remove(tokenValue);
        String refresh = this.accessTokenToRefreshTokenStore.remove(tokenValue);
        if (refresh != null) {
            // Don't remove the refresh token itself - it's up to the caller to do that
            this.refreshTokenToAcessTokenStore.remove(tokenValue);
        }
        OAuth2Authentication authentication = this.authenticationStore.remove(tokenValue);
        if (authentication != null) {
            this.authenticationToAccessTokenStore.remove(authenticationKeyGenerator.extractKey(authentication));
            Collection<OAuth2AccessToken> tokens;
            tokens = this.userNameToAccessTokenStore.get(authentication.getName());
            if (tokens != null) {
                tokens.remove(removed);
            }
            String clientId = authentication.getAuthorizationRequest().getClientId();
            tokens = this.clientIdToAccessTokenStore.get(clientId);
            if (tokens != null) {
                tokens.remove(removed);
            }
            this.authenticationToAccessTokenStore.remove(authenticationKeyGenerator.extractKey(authentication));
        }
    }


    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        this.refreshTokenStore.put(refreshToken.getValue(), refreshToken);
        this.refreshTokenAuthenticationStore.put(refreshToken.getValue(), authentication);
    }


    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        return this.refreshTokenStore.get(tokenValue);
    }


    @Override
    public void removeRefreshToken(OAuth2RefreshToken refreshToken) {
        removeRefreshToken(refreshToken.getValue());
    }


    public void removeRefreshToken(String tokenValue) {
        this.refreshTokenStore.remove(tokenValue);
        this.refreshTokenAuthenticationStore.remove(tokenValue);
        this.refreshTokenToAcessTokenStore.remove(tokenValue);
    }


    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }


    private void removeAccessTokenUsingRefreshToken(String refreshToken) {
        String accessToken = this.refreshTokenToAcessTokenStore.remove(refreshToken);
        if (accessToken != null) {
            removeAccessToken(accessToken);
        }
    }


    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        Collection<OAuth2AccessToken> result = clientIdToAccessTokenStore.get(clientId);
        return result != null ? Collections.<OAuth2AccessToken>unmodifiableCollection(result) : Collections
                .<OAuth2AccessToken>emptySet();
    }


    @Override
    public Collection<OAuth2AccessToken> findTokensByUserName(String userName) {
        Collection<OAuth2AccessToken> result = userNameToAccessTokenStore.get(userName);
        return result != null ? Collections.<OAuth2AccessToken>unmodifiableCollection(result) : Collections
                .<OAuth2AccessToken>emptySet();
    }


    private void flush() {
        TokenExpiry expiry = expiryQueue.poll();
        while (expiry != null) {
            removeAccessToken(expiry.getValue());
            expiry = expiryQueue.poll();
        }
    }


    private static class TokenExpiry implements Delayed {

        private final long expiry;

        private final String value;


        public TokenExpiry(String value, Date date) {
            this.value = value;
            this.expiry = date.getTime();
        }


        public int compareTo(Delayed other) {
            if (this == other) {
                return 0;
            }
            long diff = getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS);
            return (diff == 0 ? 0 : ((diff < 0) ? -1 : 1));
        }


        public long getDelay(TimeUnit unit) {
            return expiry - System.currentTimeMillis();
        }


        public String getValue() {
            return value;
        }

    }
}