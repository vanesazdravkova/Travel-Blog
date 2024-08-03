package bg.softuni.travelProject.service;

import bg.softuni.travelProject.model.entity.SecureTokenEntity;
import bg.softuni.travelProject.model.entity.UserEntity;

public interface SecureTokenService {

    SecureTokenEntity createSecureToken(UserEntity user);
}
