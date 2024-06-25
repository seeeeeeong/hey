package hey.io.hey.common.utils;

import hey.io.hey.common.exception.ErrorCode;
import hey.io.hey.common.security.jwt.JwtProperties;
import hey.io.hey.common.security.jwt.JwtType;
import hey.io.hey.common.security.jwt.exception.InvalidTokenException;
import hey.io.hey.common.security.jwt.exception.TokenNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeaderUtils {

    public static String getJwtToken(HttpServletRequest request, JwtType jwtType) {
        String authorization = request.getHeader(JwtProperties.JWT_TOKEN_HEADER);

        if (Objects.isNull(authorization)) {
            if (jwtType == JwtType.ACCESS) {
                throw new TokenNotFoundException(ErrorCode.JWT_TOKEN_NOT_FOUND);
            } else if (jwtType == JwtType.REFRESH) {
                throw new TokenNotFoundException(ErrorCode.JWT_REFRESH_TOKEN_NOT_FOUND);
            } else if (jwtType == JwtType.BOTH) {
                throw new TokenNotFoundException(ErrorCode.JWT_TOKEN_NOT_FOUND);
            }
        }

        String[] tokens = StringUtils.delimitedListToStringArray(authorization, " ");

        if (tokens.length != 2 || !"Bearer".equals(tokens[0])) {
            throw new InvalidTokenException();
        }

        return tokens[1];

    }
}
