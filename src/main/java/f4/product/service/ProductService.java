package f4.product.service;

import f4.product.dto.request.ProductSaveRequestDto;

public interface ProductService {
  void saveProduct(ProductSaveRequestDto product);
}
