package f4.product.domain.product.persist.entity;

import f4.product.domain.product.constant.SellStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    private String name;

    private byte[] images;

    @Column(name = "artist")
    private String artist;

    @Column(name = "description")
    private byte[] description;

    @Column(name = "completion_date")
    private String completionDate;

    @Column(name = "sell_status")
    @Enumerated(EnumType.STRING)
    private SellStatus status;

    @Column(name = "size")
    private String size;

    @Column(name = "theme")
    private String theme;

    @Column(name = "style")
    private String style;

    @Column(name = "technique")
    private String technique;

    @Column(name = "mediums")
    private String mediums;

    @Column(name = "country")
    private String country;

    @OneToOne(mappedBy = "product")
    private Auction auction;
}
