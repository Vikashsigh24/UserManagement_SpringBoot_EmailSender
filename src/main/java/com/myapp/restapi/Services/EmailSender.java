package com.myapp.restapi.Services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

  private final JavaMailSender mailSender;

  public EmailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Async
  public void sendEmail(String to, String subject, String body) {
    Thread.currentThread().setName("Email-Sender-Thread");
    System.out.println("Sending email in Thread: " + Thread.currentThread().getName());

    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("To Do App <jntd8025@gmail.com>");
    message.setTo(to);
    message.setSubject(subject);
    message.setText(body);
    mailSender.send(message);

    System.out.println("Email sent in Thread: " + Thread.currentThread().getName());

  }

}
