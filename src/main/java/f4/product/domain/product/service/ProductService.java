package f4.product.domain.product.service;

import f4.product.domain.product.dto.request.ProductSaveRequestDto;

public interface ProductService {

  void saveProduct(ProductSaveRequestDto product);
}
