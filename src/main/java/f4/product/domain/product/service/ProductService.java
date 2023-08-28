package f4.product.domain.product.service;

import f4.product.domain.product.dto.request.ProductSaveRequestDto;
import f4.product.domain.product.dto.response.ProductReadResponseDto;
import java.util.List;

public interface ProductService {

  void saveProduct(ProductSaveRequestDto product);

  List<ProductReadResponseDto> readAllProducts();
  ProductReadResponseDto readProductById(Long productId);

  List<ProductReadResponseDto> readProductsByName(String name);

  List<ProductReadResponseDto> readProductsByArtist(String artist);

  List<ProductReadResponseDto> searchProductsByCategory(String category, String keyword);
}
