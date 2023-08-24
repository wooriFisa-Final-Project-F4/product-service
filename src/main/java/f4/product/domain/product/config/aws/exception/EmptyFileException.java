package f4.product.domain.product.config.aws.exception;

import f4.product.global.constant.CustomErrorCode;
import f4.product.global.exception.CustomException;

public class EmptyFileException extends CustomException {

  public EmptyFileException(CustomErrorCode customErrorCode) {
    super(customErrorCode);
  }
}
