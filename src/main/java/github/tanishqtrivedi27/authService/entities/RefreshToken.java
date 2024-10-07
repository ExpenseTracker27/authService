package github.tanishqtrivedi27.authService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    private Instant expiresAt;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    private UserInfo userInfo;
}
