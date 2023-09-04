package f4.product.domain.product.persist.repository;

import f4.product.domain.product.persist.entity.Product;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface ProductRepository
    extends JpaRepository<Product, JpaSpecificationExecutor<Product>> {

  Optional<Product> findByIdentifier(String identifier);

  Optional<List<Product>> findByName(String name);

  Optional<List<Product>> findByArtist(String artist);

  Optional<Product> findById(Long productId);

  @Query(
      "SELECT p FROM Product p WHERE p.theme = :medium AND (LOWER(p.style) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.technique) LIKE LOWER(CONCAT('%', :keyword, '%')))")
  Optional<List<Product>> findByMediumAndKeyword(String medium, String keyword);

  @Query("SELECT p FROM Product p WHERE p.auctionStatus = 'progress' AND p.auctionEndTime < ?1")
  List<Product> findCompletedAuctionsInProgress(LocalDateTime now);
  List<Product> findByAuctionStartTimeBefore(LocalDateTime now);
  List<Product> findByOrderByAuctionPriceDesc();
}
