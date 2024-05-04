package com.zvaryyka.motelwebapplication.services;

import com.zvaryyka.motelwebapplication.models.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSenderService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmail(String toEmail, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nzvarykin@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        log.info("Email send to " + toEmail + " successfully");

    }

    public void sendEmailsAboutConsultation(String email) {
        sendEmail(email, "Гостиница Motel!",
                "Спасибо, скоро с вами свяжеться администрация отеля");
        sendEmail("nzvarykin@gmail.com", "Запрос на консультацию", email + " Запросил консультацию!");

    }

    public void sendEmailAboutCreatedNewOwner(Person person) {

        sendEmail("nzvarykin@gmail.com", "Создан аккаунт владельца!",
                "Был создан новый акканут владельца, данные: \nЛогин: " + "\n" + person.getLogin() + "\nПароль: "
                        + "\n" + person.getPassword());


    }
}
