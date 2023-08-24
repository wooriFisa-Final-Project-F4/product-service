package f4.product.domain.product.dto.request;

import f4.product.domain.product.constant.AuctionStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString

@NoArgsConstructor
@AllArgsConstructor
public class ProductRegisterRequestDto {

  private String name;
//  private List<String> images;
  private List<MultipartFile> images;
  private String artist;

//  private List<MultipartFile> description;
//  private String completionDate;
//  private String size;
//  private String theme;
//  private String style;
//  private String technique;
//  private String mediums;
//  private String country;
//  private String bidPrice;
//  private AuctionStatus auctionStatus;
//  private LocalDateTime auctionStartTime;
//  private LocalDateTime auctionEndTime;
//  private String bidUserId;
}
