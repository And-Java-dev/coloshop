package com.example.coloshop.service;


import org.springframework.scheduling.annotation.Async;


public interface EmailService {

    @Async
    void sendSimpleMessage(String to, String subject, String text);

    @Async
    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment);
}
