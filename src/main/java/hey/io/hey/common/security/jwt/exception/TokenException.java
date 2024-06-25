package hey.io.hey.common.security.jwt.exception;

import hey.io.hey.common.exception.BusinessException;
import hey.io.hey.common.exception.ErrorCode;

public class TokenException extends BusinessException {

    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TokenException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

}
