package hey.io.hey.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "hey API 문서",
        description = "hey API 문서입니다.",
        version = "v1")
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi oauthOpenApi() {
        String[] paths = {"/oauth2/**"};

        return GroupedOpenApi
                .builder()
                .group("OAuth API")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi userOpenApi() {
        String[] paths = {"/users/**"};

        return GroupedOpenApi
                .builder()
                .group("유저 API")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi performanceOpenApi() {
        String[] paths = {"/performances/**"};

        return GroupedOpenApi
                .builder()
                .group("공연 API")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi artistOpenApi() {
        String[] paths = {"/artists/**"};

        return GroupedOpenApi
                .builder()
                .group("아티스트 API")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi fcmOpenApi() {
        String[] paths = {"/message/**"};

        return GroupedOpenApi
                .builder()
                .group("FCM API")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi batchOpenApi() {
        String[] paths = {"/batch/**"};

        return GroupedOpenApi
                .builder()
                .group("BATCH API")
                .pathsToMatch(paths)
                .build();
    }
}
