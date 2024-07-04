package hey.io.hey.domain.oauth.exception;


import hey.io.hey.common.exception.BusinessException;
import hey.io.hey.common.exception.ErrorCode;

public class OAuthNotFoundException extends BusinessException {

    public OAuthNotFoundException() {
        super(ErrorCode.OAUTH_NOT_FOUND);
    }

}
