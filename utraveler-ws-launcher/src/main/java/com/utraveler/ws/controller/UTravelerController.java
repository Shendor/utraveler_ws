package com.utraveler.ws.controller;

import com.utraveler.auth.AuthenticationService;
import com.utraveler.ws.response.JsonResponse;
import com.utraveler.ws.service.MailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UTravelerController {

    private static final Logger LOGGER = Logger.getLogger(UTravelerController.class);

    private AuthenticationService authenticationService;
    private MailService mailService;


    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "home";
    }


    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "about";
    }


    @RequestMapping(value = "/privacy", method = RequestMethod.GET)
    public String privacy() {
        return "privacy";
    }


    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public String resetPassword() {
        return "reset-password";
    }


    @RequestMapping(value = "/reset", method = RequestMethod.POST, headers = "Content-Type=application/x-www-form-urlencoded")
    @ResponseBody
    public JsonResponse resetPassword(@RequestParam("email") final String email,
                                      @RequestParam("code") final String requestCode,
                                      @RequestParam("password") final String password) {
        boolean isSuccess = false;
        try {
            LOGGER.info("Initialize operation: Reset password");
            isSuccess = authenticationService.resetPassword(email, requestCode, password);
            LOGGER.info("Operation executed successfully: Reset password");
        } catch (Exception ex) {
            LOGGER.error("Operation executed with error: Reset password = " + ex.getMessage());
        }

        return new JsonResponse(isSuccess);
    }


    @RequestMapping(value = "/feedback", method = RequestMethod.POST, headers = "Content-Type=application/x-www-form-urlencoded")
    @ResponseBody
    public JsonResponse sendFeedback(@RequestParam("text") final String text) {
        boolean isSuccess = false;
        try {
            if (text.length() <= 4000) {
                mailService.sendMail(MailService.SUPPORT_EMAIL, "Feedback", text);
                isSuccess = true;
            }
        } catch (Exception ex) {
            LOGGER.error("Cannot send feedback: " + ex.getMessage());
        }

        return new JsonResponse(isSuccess);
    }
}
