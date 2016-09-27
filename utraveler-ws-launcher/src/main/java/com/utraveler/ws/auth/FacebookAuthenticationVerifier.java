package com.utraveler.ws.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.core.Authentication;

import com.utraveler.ws.auth.model.UTravelerAuthentication;

public class FacebookAuthenticationVerifier implements SocialNetworkAuthenticationVerifier {

    @Override
    public UTravelerAuthentication verifyAndGetAuthentication(Authentication authentication) {
        String principal = authentication.getPrincipal().toString();
        Pattern pattern = Pattern.compile("facebookauth__\\S+__\\S+");
        Matcher matcher = pattern.matcher(principal);
        if (matcher.matches()) {
            String email = principal.substring(principal.indexOf("__") + 2, principal.lastIndexOf("__"));
            String accessToken = principal.substring(principal.lastIndexOf("__") + 2);
            return new UTravelerAuthentication(email, authentication.getCredentials());
           /* String response = getHTML("url");
            if (response != null && email.equals(response)) {
                return new UTravelerAuthentication(email, authentication.getCredentials());
            }*/
        }
        return null;
    }


    public String getHTML(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
