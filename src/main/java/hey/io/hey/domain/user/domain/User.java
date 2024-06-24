package hey.io.hey.domain.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "\"user\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Column(nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String password;

    @Builder(access = AccessLevel.PRIVATE)
    private User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static User create(String email, String password) {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }
}
