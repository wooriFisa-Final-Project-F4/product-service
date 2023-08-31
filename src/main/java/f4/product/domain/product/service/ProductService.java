package f4.product.domain.product.service;

import f4.product.domain.product.dto.request.ProductSaveRequestDto;
import f4.product.domain.product.dto.request.ProductUpdateRequestDto;
import f4.product.domain.product.dto.response.FeignProductDto;
import f4.product.domain.product.dto.response.ProductReadResponseDto;
import java.util.List;

public interface ProductService {

  void saveProduct(ProductSaveRequestDto product);

  List<ProductReadResponseDto> findAll();

  ProductReadResponseDto findById(Long productId);

  List<ProductReadResponseDto> findByName(String name);

  List<ProductReadResponseDto> findByArtist(String artist);

  List<ProductReadResponseDto> findByMediumAndKeyword(String theme, String keyword);

  void updateProduct(Long productId, ProductUpdateRequestDto updateDto);

  void deleteProduct(Long productId);

  List<FeignProductDto> getProductsToBeEnded();
  FeignProductDto auctionStatusUpdate(long productId);
}
