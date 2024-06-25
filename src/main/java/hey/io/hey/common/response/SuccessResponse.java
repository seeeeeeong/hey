package hey.io.hey.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuccessResponse<T> {

    private final boolean status = true;

    private T data;

    public static <T> SuccessResponse<T> of(T data) {
        SuccessResponse<T> SuccessResponse = new SuccessResponse<>();

        SuccessResponse.data = data;

        return SuccessResponse;
    }

    public ResponseEntity<SuccessResponse<T>> asHttp(HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(this);
    }

}