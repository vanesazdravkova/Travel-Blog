package bg.softuni.travelProject.service.impl;

import bg.softuni.travelProject.model.entity.SecureTokenEntity;
import bg.softuni.travelProject.model.entity.UserEntity;
import bg.softuni.travelProject.repository.SecureTokenRepository;
import bg.softuni.travelProject.service.SecureTokenService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SecureTokenServiceImpl implements SecureTokenService {

    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);

    private final SecureTokenRepository secureTokenRepository;

    public SecureTokenServiceImpl(SecureTokenRepository secureTokenRepository) {
        this.secureTokenRepository = secureTokenRepository;
    }

    @Override
    public SecureTokenEntity createSecureToken(UserEntity user) {
        String tokenValue = new String(Base64.encodeBase64URLSafeString(DEFAULT_TOKEN_GENERATOR.generateKey()));
        SecureTokenEntity secureTokenEntity = new SecureTokenEntity();
        secureTokenEntity.setToken(tokenValue);
        secureTokenEntity.setExpireAt(LocalDateTime.now().plusMinutes(30));
        secureTokenEntity.setUser(user);
        return this.secureTokenRepository.save(secureTokenEntity);
    }

    @Override
    public void cleanUpSecureTokens(){
        List<SecureTokenEntity> tokensToDelete = secureTokenRepository.findAllByExpireAtBefore(LocalDateTime.now());
        secureTokenRepository.deleteAll(tokensToDelete);
    }
}
