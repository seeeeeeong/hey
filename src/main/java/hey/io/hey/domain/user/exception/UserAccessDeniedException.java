package hey.io.hey.domain.user.exception;

import hey.io.hey.common.exception.BusinessException;
import hey.io.hey.common.exception.ErrorCode;

public class UserAccessDeniedException extends BusinessException {

    public UserAccessDeniedException() {
        super(ErrorCode.USER_ACCESS_DENIED);
    }


}
