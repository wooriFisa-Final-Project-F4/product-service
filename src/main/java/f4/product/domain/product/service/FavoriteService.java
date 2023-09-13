package f4.product.domain.product.service;

import f4.product.domain.product.dto.response.ProductReadResponseDto;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface FavoriteService {

  ResponseEntity<?> saveFavorite(Long userId, Long productId);

  List<ProductReadResponseDto> readFavoriteProducts(Long userId);

  void deleteFavoriteFavoriteWithCheck(Long userId, Long productId);
}
