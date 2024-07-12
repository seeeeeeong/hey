package hey.io.hey.domain.user.domain;

import hey.io.hey.common.entity.BaseEntityWithUpdate;
import hey.io.hey.domain.follow.domain.PerformanceFollow;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "\"user\"")
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntityWithUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialCode socialCode;

    private LocalDateTime deletedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private User(String email, SocialCode socialCode) {
        this.email = email;
        this.socialCode = socialCode;
    }

    public static User create(String email, SocialCode socialCode) {
        return User.builder()
                .email(email)
                .socialCode(socialCode)
                .build();
    }

    public void deleteUser() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isAdmin() {
        return this.userRole == UserRole.ADMIN;
    }

}
