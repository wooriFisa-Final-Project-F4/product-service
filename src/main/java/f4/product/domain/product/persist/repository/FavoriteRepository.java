package f4.product.domain.product.persist.repository;

import f4.product.domain.product.persist.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

}
