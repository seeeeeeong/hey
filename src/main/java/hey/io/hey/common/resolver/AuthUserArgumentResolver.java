package hey.io.hey.common.resolver;

import hey.io.hey.common.security.jwt.JwtProperties;
import hey.io.hey.common.security.jwt.JwtTokenInfo;
import hey.io.hey.domain.user.domain.UserRole;
import io.jsonwebtoken.Claims;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(JwtTokenInfo.class) &&
                parameter.hasParameterAnnotation(AuthUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Claims claims = (Claims) authentication.getPrincipal();
        Long userId = Long.parseLong((String) claims.get(JwtProperties.USER_ID));
        UserRole role = UserRole.valueOf((String) claims.get(JwtProperties.ROLE));

        return JwtTokenInfo.builder()
                .userId(userId)
                .userRole(role)
                .build();
    }
}
