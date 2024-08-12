package bg.softuni.travelProject.service.impl;

import bg.softuni.travelProject.model.dto.UserEditDto;
import bg.softuni.travelProject.model.dto.UserRegisterDto;
import bg.softuni.travelProject.model.email.AccountVerificationEmailContext;
import bg.softuni.travelProject.model.entity.SecureTokenEntity;
import bg.softuni.travelProject.model.entity.TripEntity;
import bg.softuni.travelProject.model.entity.UserEntity;
import bg.softuni.travelProject.model.enums.RoleNameEnum;
import bg.softuni.travelProject.model.view.UserView;
import bg.softuni.travelProject.repository.RoleRepository;
import bg.softuni.travelProject.repository.SecureTokenRepository;
import bg.softuni.travelProject.repository.TripRepository;
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

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
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
    private final TripRepository tripRepository;
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
            TripRepository tripRepository,
            SecureTokenRepository secureTokenRepository,
            SecureTokenService secureTokenService
    ) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
        this.tripRepository = tripRepository;
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
    public UserView findById(Long id) {
        return this.userRepository.findById(id)
                .map(userEntity -> modelMapper.map(userEntity, UserView.class))
                .orElseThrow(() -> new ObjectNotFoundException("User with id " + id + " not found!"));
    }

    @Override
    public void updateUserProfile(UserEditDto userEditDto) {
        UserEntity user = this.userRepository.findById(userEditDto.getId())
                .orElseThrow(() -> new ObjectNotFoundException("User with id " + userEditDto.getId() + " was not found!"));

        user.setFirstName(userEditDto.getFirstName())
                .setLastName(userEditDto.getLastName())
                .setUsername(userEditDto.getUsername())
                .setEmail(userEditDto.getEmail());

        this.userRepository.save(user);
    }

    @Override
    public UserEditDto getUserEditDetails(Long id) {
        return this.userRepository.findById(id)
                .map(userEntity -> modelMapper.map(userEntity, UserEditDto.class))
                .orElseThrow(() -> new ObjectNotFoundException("User with ID " + id + " not found!"));
    }

    @Override
    public boolean usernameExists(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public boolean emailExists(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public boolean addOrRemoveTripFromFavorites(String username, Long id) {
        UserEntity user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User with username " + username + " was not found!"));

        TripEntity trip = tripRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Trip with id " + id + " not found!"));

        if (!user.getFavorites().contains(trip)) {
            user.getFavorites().add(trip);
        } else {
            user.getFavorites().remove(trip);
        }
        userRepository.save(user);
        return user.getFavorites().contains(trip);
    }

    @Override
    public List<String> getAdminsEmails() {
        return this.userRepository.findByRole(RoleNameEnum.ADMIN).stream().map(UserEntity::getEmail).collect(Collectors.toList());
    }

    @Override
    public long getCountRegisteredUsers() {
        return this.userRepository.count();
    }

    @Override
    public UserEntity findByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("User with email " + email + "not found!"));
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

    @Override
    public void cleanUpNotVerifiedUsers(){
        List<UserEntity> usersToDelete = userRepository
                .findAllByAccountVerifiedEqualsAndCreatedOnBefore(false,
                        Timestamp.valueOf(LocalDateTime.now().minusMinutes(25)));

        userRepository.deleteAll(usersToDelete);
    }
}