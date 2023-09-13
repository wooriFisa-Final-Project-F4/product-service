package f4.product.global.exception;

import f4.product.global.constant.CustomErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

  private final CustomErrorCode customErrorCode;

  public CustomException(CustomErrorCode customErrorCode) {
    super(customErrorCode.getMessage());
    this.customErrorCode = customErrorCode;
  }

  public CustomException(CustomErrorCode customErrorCode, Throwable cause) {
    super(customErrorCode.getMessage(), cause);
    this.customErrorCode = customErrorCode;
  }
}
