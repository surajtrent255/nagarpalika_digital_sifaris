package com.ishanitech.ipalika.service;

public interface MailService {

    void sendEmail(String message, String email);
    void sendAccountVerifyEmail(String msg, String to, String url);

}
