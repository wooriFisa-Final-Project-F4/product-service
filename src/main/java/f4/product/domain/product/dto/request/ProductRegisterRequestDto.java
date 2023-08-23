package f4.product.domain.product.dto.request;

import f4.product.domain.product.constant.AuctionStatus;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductRegisterRequestDto {

  private String name;
  private MultipartFile images;
  private String artist;
  private MultipartFile description;
  private String completionDate;
  private String size;
  private String theme;
  private String style;
  private String technique;
  private String mediums;
  private String country;
  private String bidPrice;
  private AuctionStatus auctionStatus;
  private LocalDateTime auctionStartTime;
  private LocalDateTime auctionEndTime;
  private String bidUserId;
}
