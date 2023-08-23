package f4.product.domain.product.persist.entity;

import f4.product.domain.product.constant.AuctionStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
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
