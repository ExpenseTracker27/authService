package github.tanishqtrivedi27.authService.controllers;

import github.tanishqtrivedi27.authService.entities.RefreshToken;
import github.tanishqtrivedi27.authService.models.UserInfoDTO;
import github.tanishqtrivedi27.authService.response.JWTResponseDTO;
import github.tanishqtrivedi27.authService.services.JWTService;
import github.tanishqtrivedi27.authService.services.RefreshTokenService;
import github.tanishqtrivedi27.authService.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final JWTService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(JWTService jwtService, UserDetailsServiceImpl userDetailsService, RefreshTokenService refreshTokenService) {
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("auth/v1/signup")
    public ResponseEntity<?> signup(@RequestBody UserInfoDTO userInfoDTO) {
        try {
            Boolean isSignedUp = userDetailsService.signupUser(userInfoDTO);
            if (Boolean.FALSE.equals(isSignedUp)) {
                return new ResponseEntity<>("User already exists or validation error", HttpStatus.BAD_REQUEST);
            }

            RefreshToken refreshToken = refreshTokenService.generateRefreshToken(userInfoDTO.getUsername());
            String jwtToken = jwtService.generateToken(userInfoDTO.getUsername());
            return new ResponseEntity<>(JWTResponseDTO.builder().accessToken(jwtToken).refreshToken(refreshToken.getToken()).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Exception in user service",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("auth/v1/ping")
    public ResponseEntity<?> ping() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = userDetailsService.getUserIdByUsername(authentication.getName());
            return ResponseEntity.ok(userId);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
    }
}
