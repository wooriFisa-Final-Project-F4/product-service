package f4.product.domain.product.dto.request;

import f4.product.domain.product.constant.AuctionStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaveRequestDto {

  private String name;
  private List<MultipartFile> images;
  private String artist;
  private String country;
  private String description;
  private String completionDate;
  private String size;
  private String theme;
  private String style;
  private String technique;
  private String mediums;
  private String auctionPrice;
  private AuctionStatus auctionStatus;
  private LocalDateTime auctionStartTime;
  private LocalDateTime auctionEndTime;
  private String bidUserId;
}
