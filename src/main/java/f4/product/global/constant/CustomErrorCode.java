package f4.product.global.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {

    // Bad Request 400
    UNVALID_DATA_REQUEST("/user/v1/signup", 400, "이미 회원 가입한 계정이 존재합니다.");

    // Unathorized 401

    // Forbidden 402

    // Not Found 404


    private final String path;
    private final int code;
    private final String message;
}