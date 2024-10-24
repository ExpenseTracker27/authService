package github.tanishqtrivedi27.authService.services;

import github.tanishqtrivedi27.authService.entities.UserInfo;
import github.tanishqtrivedi27.authService.eventProducer.UserInfoProducer;
import github.tanishqtrivedi27.authService.models.UserInfoDTO;
import github.tanishqtrivedi27.authService.repositories.UserRepository;
import github.tanishqtrivedi27.authService.utils.ValidationUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;


@Component
@Data
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoProducer userInfoProducer;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserInfoProducer userInfoProducer) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userInfoProducer = userInfoProducer;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new CustomUserDetails(user);
    }

    public UserInfo checkIfUserExists(UserInfoDTO userInfoDTO) {
        return userRepository.findByUsername(userInfoDTO.getUsername());
    }

    public String getUserIdByUsername(String username) {
        return userRepository.findByUsername(username).getUserId();
    }

    public Boolean signupUser(UserInfoDTO userInfoDTO) {
        if (!ValidationUtil.validateEmail(userInfoDTO.getEmail()) || !ValidationUtil.validatePassword(userInfoDTO.getPassword())) {
            return false;
        }

        userInfoDTO.setPassword(passwordEncoder.encode(userInfoDTO.getPassword()));
        if (Objects.nonNull(checkIfUserExists(userInfoDTO))) {
            return false;
        }

        String userId = UUID.randomUUID().toString();
        userInfoDTO.setUserId(userId);
        userRepository.save(new UserInfo(userId, userInfoDTO.getUsername(), userInfoDTO.getPassword(), new HashSet<>()));
        userInfoProducer.sendEventToKafka(userInfoDTO);

        return true;
    }
}