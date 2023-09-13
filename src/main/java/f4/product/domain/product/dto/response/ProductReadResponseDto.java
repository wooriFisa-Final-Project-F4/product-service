package f4.product.domain.product.dto.response;

import f4.product.domain.product.constant.AuctionStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReadResponseDto implements Serializable {

  private Long id;
  private String name;
  private String artist;
  private String identifier;
  private String country;
  private String description;
  private String completionDate;
  private String size;
  private String medium;
  private String theme;
  private String style;
  private String technique;
  private String auctionPrice;
  private AuctionStatus auctionStatus;
  private LocalDateTime auctionStartTime;
  private LocalDateTime auctionEndTime;
  private String bidUserId;
  private List<String> images;
}
