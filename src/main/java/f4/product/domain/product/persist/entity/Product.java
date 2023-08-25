package f4.product.domain.product.persist.entity;

import f4.product.domain.product.constant.AuctionStatus;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "images")
  private String images;

  @Column(name = "artist")
  private String artist;

  @Column(name = "country")
  private String country;

  @Column(name = "description")
  private String description;

  @Column(name = "completion_date")
  private String completionDate;

  @Column(name = "size")
  private String size;

  @Column(name = "theme")
  private String theme;

  @Column(name = "style")
  private String style;

  @Column(name = "technique")
  private String technique;

  @Column(name = "auction_price")
  private String auctionPrice;

  @Enumerated(EnumType.STRING)
  @Column(name = "auction_status")
  private AuctionStatus auctionStatus;

  @Column(name = "auction_start_time")
  private LocalDateTime auctionStartTime;

  @Column(name = "auction_end_time")
  private LocalDateTime auctionEndTime;

  @Column(name = "bid_user_id")
  private String bidUserId;
}
