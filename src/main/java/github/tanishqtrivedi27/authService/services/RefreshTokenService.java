package github.tanishqtrivedi27.authService.services;

import github.tanishqtrivedi27.authService.entities.RefreshToken;
import github.tanishqtrivedi27.authService.entities.UserInfo;
import github.tanishqtrivedi27.authService.repositories.RefreshTokenRepository;
import github.tanishqtrivedi27.authService.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshToken generateRefreshToken(String username) {
        UserInfo userInfoExtracted = userRepository.findByUsername(username);

        refreshTokenRepository.deleteByUserInfo(userInfoExtracted);
        refreshTokenRepository.flush();

        RefreshToken refreshToken = RefreshToken.builder().userInfo(userInfoExtracted).token(UUID.randomUUID().toString()).expiresAt(Instant.now().plusSeconds(30 * 24 * 60 * 60)).build();
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken() + " expired");
        }

        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}
