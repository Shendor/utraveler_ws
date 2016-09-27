package com.utraveler.ws.service;

public interface MailService {

    public static final String SUPPORT_EMAIL = "utraveler_helpdesk@yahoo.com";

    void sendMail(String email, String subject, String text);
}
