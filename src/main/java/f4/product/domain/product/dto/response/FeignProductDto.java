package f4.product.domain.product.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeignProductDto implements Serializable {
  private long id;
  private String name;
  private String image;
  private String artist;
  private String auctionPrice;
  private String auctionStatus;
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime auctionEndTime;
  private long bidUserId;
}
