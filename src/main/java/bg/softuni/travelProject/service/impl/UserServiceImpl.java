package bg.softuni.travelProject.service.impl;

import bg.softuni.travelProject.model.dto.UserRegisterDto;
import bg.softuni.travelProject.model.email.AccountVerificationEmailContext;
import bg.softuni.travelProject.model.entity.SecureTokenEntity;
import bg.softuni.travelProject.model.entity.UserEntity;
import bg.softuni.travelProject.model.enums.RoleNameEnum;
import bg.softuni.travelProject.repository.RoleRepository;
import bg.softuni.travelProject.repository.SecureTokenRepository;
import bg.softuni.travelProject.repository.UserRepository;
import bg.softuni.travelProject.service.EmailService;
import bg.softuni.travelProject.service.SecureTokenService;
import bg.softuni.travelProject.service.UserService;
import bg.softuni.travelProject.web.exception.InvalidTokenException;
import bg.softuni.travelProject.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final SecureTokenRepository secureTokenRepository;
    private final SecureTokenService secureTokenService;

    @Value("${site.base.url}")
    private String baseURL;

    public UserServiceImpl(
            UserRepository userRepository,
            ModelMapper modelMapper,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository,
            EmailService emailService,
            SecureTokenRepository secureTokenRepository,
            SecureTokenService secureTokenService
    ) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
        this.secureTokenRepository = secureTokenRepository;
        this.secureTokenService = secureTokenService;
    }

    @Override
    public void register(UserRegisterDto userRegisterDto, Locale preferedLocale) {
        UserEntity newUser = modelMapper.map(userRegisterDto, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        newUser.setRoles(roleRepository.findAll()
                .stream()
                .filter(r -> r.getRole() == (RoleNameEnum.USER))
                .collect(Collectors.toList()));

        newUser.setAccountVerified(false);
        sendVerificationMail(this.userRepository.save(newUser), preferedLocale);
    }

    @Override
    public void sendVerificationMail(UserEntity newUser, Locale preferedLocale){
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        SecureTokenEntity token = this.secureTokenService.createSecureToken(newUser);

        emailContext.setToken(token.getToken());
        emailContext.setLocale(preferedLocale);
        emailContext.setBaseUrl(baseURL);
        emailContext.initContext(newUser);

        emailService.sendEmail(emailContext);
    }

    @Override
    public void verifyAccount(String token) {
        Optional<SecureTokenEntity> tokenOpt = secureTokenRepository.findByToken(token);

        if (tokenOpt.isEmpty() || tokenOpt.get().isExpired() || tokenOpt.get().getUser() == null) {
            throw new InvalidTokenException("Token is invalid.");
        }

        SecureTokenEntity secureToken = tokenOpt.get();
        UserEntity user = secureToken.getUser();

        user.setAccountVerified(true);
        userRepository.save(user);
        secureTokenRepository.delete(secureToken);
    }

    @Override
    public boolean notVerifiedProfile(String username) {
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        return user != null && !user.isAccountVerified();
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User with username " + username + "not found!"));
    }
}
