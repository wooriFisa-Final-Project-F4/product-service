package f4.product.global.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {

  // Bad Request 400
  UPLOAD_FAIL_RESPONSE(400, "s3 업로드 중 에러가 발생했습니다"),
  EMPTY_UPLOAD_FILE(400, "등록할 s3 이미지 파일이 비어있습니다."),
  INVALID_AUCTION_STATUS(400, "잘못된 옥션 상태입니다."),
  ALREADY_REGISTER_PRODUCT(400, "이미 등록된 상품입니다."),
  NOT_FOUND_PRODUCT(400, "존재하지 않는 상품입니다."),
  NOT_FOUND_ARTIST(400, "존재하지 않는 아티스트입니다.");

  // Unathorized 401

  // Forbidden 402

  private final int code;
  private final String message;
}
