package hey.io.hey.domain.oauth.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
@PropertySource("classpath:application-oauth.yml")
public class KakaoProperties {

    @Value("${oauth2.kakao.client-id}")
    private String clientId;
    @Value("${oauth2.kakao.client-secret}")
    private String clientSecret;
    @Value("${oauth2.kakao.token-uri}")
    private String tokenUrl;
    @Value("${oauth2.kakao.user-info-url}")
    private String userInfoUrl;
    @Value("${oauth2.kakao.redirect-uri}")
    private String redirectUri;

}
