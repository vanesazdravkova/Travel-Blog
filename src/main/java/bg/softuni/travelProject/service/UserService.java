package bg.softuni.travelProject.service;

import bg.softuni.travelProject.model.dto.UserEditDto;
import bg.softuni.travelProject.model.dto.UserRegisterDto;
import bg.softuni.travelProject.model.entity.UserEntity;
import bg.softuni.travelProject.model.view.UserView;

import java.util.List;
import java.util.Locale;

public interface UserService {

    void register(UserRegisterDto userRegisterDto, Locale preferedLocale);

    void sendVerificationMail(UserEntity newUser, Locale preferedLocale);

    UserView findById(Long id);

    void updateUserProfile(UserEditDto userEditDto);

    UserEditDto getUserEditDetails(Long id);

    boolean usernameExists(String username);

    boolean emailExists(String email);

    boolean addOrRemoveTripFromFavorites(String username, Long id);

    List<String> getAdminsEmails();

    long getCountRegisteredUsers();

    UserEntity findByEmail(String email);

    void verifyAccount(String token);

    boolean notVerifiedProfile(String username);

    UserEntity findByUsername(String username);

    void cleanUpNotVerifiedUsers();
}
