package f4.product.domain.product.dto.request;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequestDto implements Serializable {
  private String name;
  private List<MultipartFile> newImages;
  private boolean deleteExistingImages;
  private String artist;
  private String country;
  private String description;
  private String completionDate;
  private String size;
  private String medium;
  private String theme;
  private String style;
  private String technique;
  private String auctionPrice;
  private String auctionStatus;
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime auctionStartTime;
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime auctionEndTime;
}