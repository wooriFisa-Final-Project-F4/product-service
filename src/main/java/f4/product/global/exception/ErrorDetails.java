package f4.product.global.exception;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorDetails {

  private LocalDateTime time;
  private int code;
  private String message;
}
