package hey.io.hey.domain.user.domain;

import hey.io.hey.common.entity.BaseEntityWithUpdate;
import hey.io.hey.domain.follow.domain.Follow;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "\"user\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntityWithUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Column(nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Follow> followingList;

    @Column
    private String fcmToken;

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

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
