package bg.softuni.travelProject.service;

import bg.softuni.travelProject.model.dto.UserRegisterDto;
import bg.softuni.travelProject.model.entity.UserEntity;

import java.util.Locale;

public interface UserService {

    void register(UserRegisterDto userRegisterDto, Locale preferedLocale);

    void sendVerificationMail(UserEntity newUser, Locale preferedLocale);

    void verifyAccount(String token);

    boolean notVerifiedProfile(String username);

    UserEntity findByUsername(String username);
}
