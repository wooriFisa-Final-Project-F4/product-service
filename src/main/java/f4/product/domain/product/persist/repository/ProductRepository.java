package f4.product.domain.product.persist.repository;

import f4.product.domain.product.dto.response.ProductReadResponseDto;
import f4.product.domain.product.persist.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface ProductRepository extends JpaRepository<Product, JpaSpecificationExecutor<Product>> {

  Optional<Product> findByIdentifier(String identifier);
  Optional<Product> findByName(String name);
  Optional<Product> findByArtist(String artist);


  List<Product> findAll(Specification<Product> spec, Sort sort);

  Optional<Product> findById(Long productId);
}
