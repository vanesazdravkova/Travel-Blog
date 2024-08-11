package bg.softuni.travelProject.service;

import java.util.Locale;

public interface ForgottenPasswordService {

    void sendResetPasswordEmail(String email, Locale locale);

    void updatePassword(String password, String token);

    boolean invalidToken(String token);
}
