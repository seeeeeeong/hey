package hey.io.hey.common.security.jwt.exception;

import hey.io.hey.common.exception.ErrorCode;

public class ExpiredAccessTokenException extends TokenException{
    public ExpiredAccessTokenException() {
        super(ErrorCode.EXPIRED_JWT_ACCESS_TOKEN);
    }
}
