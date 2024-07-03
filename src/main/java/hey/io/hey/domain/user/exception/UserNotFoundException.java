package hey.io.hey.domain.user.exception;

import hey.io.hey.common.exception.BusinessException;
import hey.io.hey.common.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }


}
