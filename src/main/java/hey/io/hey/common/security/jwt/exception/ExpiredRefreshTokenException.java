package hey.io.hey.common.security.jwt.exception;

import hey.io.hey.common.exception.ErrorCode;

public class ExpiredRefreshTokenException extends TokenException{

    public ExpiredRefreshTokenException() {
        super(ErrorCode.EXPIRED_JWT_REFRESH_TOKEN);
    }

}
