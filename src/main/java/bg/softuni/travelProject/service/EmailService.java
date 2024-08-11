package bg.softuni.travelProject.service;

import bg.softuni.travelProject.model.email.AbstractEmailContext;

public interface EmailService {

    void sendEmail(AbstractEmailContext context);

    void sendSimpleMessage(String to, String subject, String text);
}
