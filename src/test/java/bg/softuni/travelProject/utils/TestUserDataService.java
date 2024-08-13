package bg.softuni.travelProject.utils;

import bg.softuni.travelProject.model.user.MyUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestUserDataService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!username.equals("admin")) {
            return new MyUserDetails(1L,
                    "user",
                    "User Userov",
                    "54321",
                    List.of(new SimpleGrantedAuthority("ROLE_USER")),
                    true);
        }

        return new MyUserDetails(1L,
                "admin",
                "Admin Adminov",
                "54321",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")),
                true);
    }
}
