package github.tanishqtrivedi27.authService.services;

import github.tanishqtrivedi27.authService.entities.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsService extends UserInfo, UserDetails {
    private String username;
    private String password;
    Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

}
