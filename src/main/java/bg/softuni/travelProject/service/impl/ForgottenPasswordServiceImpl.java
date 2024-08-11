package bg.softuni.travelProject.service.impl;

import bg.softuni.travelProject.model.email.ForgotPasswordEmailContext;
import bg.softuni.travelProject.model.entity.SecureTokenEntity;
import bg.softuni.travelProject.model.entity.UserEntity;
import bg.softuni.travelProject.repository.SecureTokenRepository;
import bg.softuni.travelProject.repository.UserRepository;
import bg.softuni.travelProject.service.EmailService;
import bg.softuni.travelProject.service.ForgottenPasswordService;
import bg.softuni.travelProject.service.SecureTokenService;
import bg.softuni.travelProject.service.UserService;
import bg.softuni.travelProject.web.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class ForgottenPasswordServiceImpl implements ForgottenPasswordService {

    private final UserService userService;
    private final SecureTokenService secureTokenService;
    private final SecureTokenRepository secureTokenRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${site.base.url}")
    private String baseURL;

    public ForgottenPasswordServiceImpl(UserService userService,
                                        SecureTokenService secureTokenService,
                                        SecureTokenRepository secureTokenRepository,
                                        EmailService emailService,
                                        UserRepository userRepository,
                                        PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.secureTokenService = secureTokenService;
        this.secureTokenRepository = secureTokenRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void sendResetPasswordEmail(String email, Locale locale) {
        UserEntity user = this.userService.findByEmail(email);
        SecureTokenEntity secureToken = secureTokenService.createSecureToken(user);

        ForgotPasswordEmailContext emailContext = new ForgotPasswordEmailContext();
        emailContext.setLocale(locale);
        emailContext.setToken(secureToken.getToken());
        emailContext.setBaseUrl(baseURL);
        emailContext.initContext(user);
        emailService.sendEmail(emailContext);
    }

    @Override
    public void updatePassword(String password, String token) {

        if (invalidToken(token)){
            throw new InvalidTokenException("This token is invalid.");
        }

        SecureTokenEntity secureToken = secureTokenRepository.findByToken(token).get();
        UserEntity user = secureToken.getUser();

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        secureTokenRepository.delete(secureToken);
    }

    @Override
    public boolean invalidToken(String token){
        Optional<SecureTokenEntity> tokenOpt = secureTokenRepository.findByToken(token);
        return tokenOpt.isEmpty() || tokenOpt.get().isExpired() || tokenOpt.get().getUser() == null;
    }
}
