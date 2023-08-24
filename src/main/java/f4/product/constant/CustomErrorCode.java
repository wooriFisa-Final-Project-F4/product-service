package f4.product.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {

  // Bad Request 400
  UPLOAD_FAIL_RESPONSE("/product/v1", 400, "s3 업로드 중 에러가 발생했습니다"),
  EMPTY_UPLOAD_FILE("/product/v1", 400, "등록할 s3 이미지 파일이 비어있습니다.")
  // Unathorized 401

  // Forbidden 402

  // Not Found 404
  ;

  private final String path;
  private final int code;
  private final String message;
}