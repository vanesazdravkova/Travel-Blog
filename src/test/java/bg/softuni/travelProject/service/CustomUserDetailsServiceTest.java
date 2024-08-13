package bg.softuni.travelProject.service;

import bg.softuni.travelProject.model.entity.RoleEntity;
import bg.softuni.travelProject.model.entity.UserEntity;
import bg.softuni.travelProject.model.enums.RoleNameEnum;
import bg.softuni.travelProject.model.user.MyUserDetails;
import bg.softuni.travelProject.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    private UserEntity testUser;
    private RoleEntity adminRole, userRole;

    private CustomUserDetailsService serviceToTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void init() {
        serviceToTest = new CustomUserDetailsService(mockUserRepository);

        adminRole = new RoleEntity();
        adminRole.setRole(RoleNameEnum.ADMIN);

        userRole = new RoleEntity();
        userRole.setRole(RoleNameEnum.USER);

        testUser = new UserEntity();
        testUser.setUsername("vanesa");
        testUser.setFirstName("Vanesa");
        testUser.setLastName("Zdravkova");
        testUser.setEmail("vanesa.zdravkova@gmail.com");
        testUser.setPassword("54321");
        testUser.setRoles(List.of(adminRole, userRole));
    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("invalid_username")
        );
    }

    @Test
    void testUserFound() {
        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).
                thenReturn(Optional.of(testUser));

        MyUserDetails myUserDetails = (MyUserDetails) serviceToTest.loadUserByUsername(testUser.getUsername());

        Assertions.assertEquals(myUserDetails.getUsername(), testUser.getUsername());
        Assertions.assertEquals(myUserDetails.getFullName(), testUser.getFirstName() + " " + testUser.getLastName());
        Assertions.assertEquals(myUserDetails.getId(), testUser.getId());
        Assertions.assertEquals(myUserDetails.getPassword(), testUser.getPassword());
        Assertions.assertEquals(2, myUserDetails.getAuthorities().size());

        String expectedRoles = "ROLE_ADMIN, ROLE_USER";
        String actualRoles = myUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(
                Collectors.joining(", "));

        Assertions.assertEquals(expectedRoles, actualRoles);
    }
}