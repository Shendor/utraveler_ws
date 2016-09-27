package com.utraveler.ws.service;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailServiceImpl implements MailService {


    @Override
    public void sendMail(final String email, String subject, String text) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.mail.yahoo.com");
        mailSender.setPort(587);
        mailSender.setUsername(SUPPORT_EMAIL);
        mailSender.setPassword("u170988Traveller");
        mailSender.setJavaMailProperties(props);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(SUPPORT_EMAIL);
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }


    public static void main(String[] args) {
        String text = "Hi,\n In order to change your password click on this link http://utraveler.net/reset_password?stamp=figfajd3293423in23urf2fwi32h&user=user1 \n Regards,\n uTraveler team";
        new MailServiceImpl().sendMail("shandor1988@yahoo.com", "uTraveler - Request reset password", text);
    }
}
