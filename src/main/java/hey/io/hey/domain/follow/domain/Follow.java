package hey.io.hey.domain.follow.domain;

import hey.io.hey.common.entity.BaseEntity;
import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @Builder
    private Follow(User user, Performance performance) {
        this.user = user;
        this.performance = performance;
    }

    public static Follow of(User user, Performance performance) {
        return Follow.builder()
                .user(user)
                .performance(performance)
                .build();
    }

}
