package github.tanishqtrivedi27.authService.repositories;

import github.tanishqtrivedi27.authService.entities.RefreshToken;
import github.tanishqtrivedi27.authService.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUserInfo(UserInfo userInfo);
}
