package f4.product.domain.product.service;

import f4.product.domain.product.dto.request.ProductSaveRequestDto;
import f4.product.domain.product.dto.request.ProductUpdateRequestDto;
import f4.product.domain.product.dto.response.ProductReadResponseDto;
import f4.product.domain.product.persist.entity.Product;
import java.util.List;

public interface ProductService {

  void saveProduct(ProductSaveRequestDto product);

  List<ProductReadResponseDto> findAll();

  ProductReadResponseDto findById(Long productId);

  List<ProductReadResponseDto> findByName(String name);

  List<ProductReadResponseDto> findByArtist(String artist);

  List<ProductReadResponseDto> findByMediumAndKeyword(String theme, String keyword);
  List<ProductReadResponseDto> findByMedium(String medium);

  void updateProduct(Long productId, ProductUpdateRequestDto updateDto);

  void deleteProduct(Long productId);

  Product findProductById(Long productId);

}
