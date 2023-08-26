package f4.product.domain.product.persist.repository;

import f4.product.domain.product.persist.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findByIdentifier(String identifier);

  Optional<Product> findByName(String name);
}
