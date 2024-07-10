package hey.io.hey.common.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "잘못된 입력값입니다."),
    METHOD_NOT_ALLOWED(405, "C002", "허용하지 않는 HTTP 메서드입니다."),
    ENTITY_NOT_FOUND(400, "C003", "엔티티를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "C004", "서버 오류"),
    INVALID_TYPE_VALUE(400, "C005", "잘못된 타입의 값입니다."),
    HANDLE_ACCESS_DENIED(403, "C006", "접근이 거부됐습니다."),
    ENCRYPTION_ERROR(500, "C007", "암호화에 실패했습니다."),
    DECRYPTION_ERROR(500, "C008", "복호화에 실패했습니다."),
    PARSING_ERROR(500, "C009", "파싱에 실패했습니다."),

    // Security
    AUTHORITY_NOT_FOUND(404, "S001", "유저 권한이 없습니다."),
    INVALID_TOKEN(400, "S002", "유효하지 않은 토큰입니다."),
    JWT_ACCESS_TOKEN_NOT_FOUND(404, "S003", "jwt access token이 없습니다."),
    JWT_REFRESH_TOKEN_NOT_FOUND(404, "S004", "jwt refresh token이 없습니다."),
    EXPIRED_JWT_ACCESS_TOKEN(400, "S005", "jwt access token이 만료되었습니다."),
    EXPIRED_JWT_REFRESH_TOKEN(400, "S006", "jwt refresh token이 만료되었습니다."),
    AUTH_CODE_NOT_FOUND(404, "S007", "authorization header가 비었습니다."),
    JWT_TOKEN_NOT_FOUND(404, "S008", "jwt token이 없습니다."),

    // User
    USER_NOT_FOUND(404, "U001", "회원을 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(409, "U002", "이미 존재하는 유저입니다."),
    DUPLICATE_USER(400, "U003", "유저가 중복되었습니다."),
    USER_ACCESS_DENIED(403, "U005", "접근할 수 있는 권한이 없습니다."),

    // OAuth
    OAUTH_NOT_FOUND(404, "O001", "유저의 refresh token을 찾을 수 없습니다."),
    OAUTH_SERVER_FAILED(500, "O002", "OAuth 서버와의 통신 중 에러가 발생하였습니다."),
    GENERATE_KEY_FAILED(500, "O003", "키 생성에 실패하였습니다."),

    // Performance
    PERFORMANCE_NOT_FOUND(404, "P001", "공연을 찾을 수 없습니다."),
    ALREADY_FOLLOWED_PERFORMANCE(404, "P002", "이미 팔로우한 공연입니다."),

    // Artist
    ARTIST_NOT_FOUND(404, "AR001", "아티스트를 찾을 수 없습니다."),
    ALREADY_FOLLOWED_ARTIST(404, "AR002", "이미 팔로우한 아티스트입니다."),


    // Place
    PLACE_NOT_FOUND(404, "PL001", "장소를 찾을 수 없습니다."),

    // FOLLOW
    FOLLOW_NOT_FOUND(404, "F001", "팔로잉을 찾을 수 없습니다."),

    // FCM
    FAILED_SEND_TOPIC(500, "FC001", "TOPIC 전송에 실패했습니다."),
    FCM_TOKEN_NOT_FOUND(404, "FC002", "FCM token이 없습니다."),
    FAILED_SUBSCRIBE_TOPIC(500, "FC003", "TOPIC 구독에 실패했습니다."),
    UNFAILED_SUBSCRIBE_TOPIC(500, "FC003", "TOPIC 구독취소에 실패했습니다."),

    // Apple
    APPLE_EMAIL_NOT_FOUND(400, "A001", "애플 소셜서버로부터 이메일을 받지 못했습니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
