package bg.softuni.travelProject.service;

import bg.softuni.travelProject.model.email.AbstractEmailContext;

public interface EmailService {

    void sendEmail(AbstractEmailContext context);
}
