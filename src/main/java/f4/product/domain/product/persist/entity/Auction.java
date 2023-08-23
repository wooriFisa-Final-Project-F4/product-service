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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Auction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @OneToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @Column(name = "bid_price")
  private String bidPrice;

  @Column(name = "auction_status")
  @Enumerated(EnumType.STRING)
  private AuctionStatus status;

  @Column(name = "auction_start_time")
  private LocalDateTime auctionStartTime;

  @Column(name = "auction_end_time")
  private LocalDateTime auctionEndTime;

  @Column(name = "bid_user_id")
  private String bidUserId;

}
