package f4.product.global.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorDetails {
  private LocalDateTime time;
  private int code;
  private String message;
}
