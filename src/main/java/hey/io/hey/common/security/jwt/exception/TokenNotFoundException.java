package hey.io.hey.common.security.jwt.exception;

import hey.io.hey.common.exception.ErrorCode;

public class TokenNotFoundException extends TokenException{

    public TokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TokenNotFoundException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

}
