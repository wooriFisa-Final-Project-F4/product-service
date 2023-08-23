package f4.product.domain.product.service;

import f4.product.domain.product.dto.request.ProductRegisterRequestDto;

public interface ProductService {

  void saveProduct(ProductRegisterRequestDto product);

}
