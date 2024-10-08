package github.tanishqtrivedi27.authService.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDTO {
    private String username;
    private String password;
}
