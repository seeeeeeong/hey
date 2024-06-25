package hey.io.hey.common.security.jwt.exception;

import hey.io.hey.common.exception.ErrorCode;

public class InvalidTokenException extends TokenException{

    public InvalidTokenException(Throwable e) {
        super(ErrorCode.INVALID_TOKEN, e);
    }

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }

}
