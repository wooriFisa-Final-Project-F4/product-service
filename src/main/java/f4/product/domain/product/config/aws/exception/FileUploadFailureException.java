package f4.product.domain.product.config.aws.exception;

import f4.product.global.constant.CustomErrorCode;
import f4.product.global.exception.CustomException;

public class FileUploadFailureException extends CustomException {

  public FileUploadFailureException(CustomErrorCode customErrorCode) {
    super(customErrorCode);
  }
}
