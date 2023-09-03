package f4.product.domain.product.persist.repository;

import f4.product.domain.product.persist.entity.Favorite;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

  List<Favorite> findByUserId(Long userId);

  void deleteByUserIdAndProductId(Long userId, Long productId);
}
