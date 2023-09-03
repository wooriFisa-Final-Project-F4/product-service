package f4.product.domain.product.service;

import f4.product.domain.product.dto.response.ProductReadResponseDto;
import java.util.List;

public interface FavoriteService {

  void saveFavorite(Long userId, Long productId);

  List<ProductReadResponseDto> readFavoriteProducts(Long userId);

}
