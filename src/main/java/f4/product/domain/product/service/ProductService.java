package f4.product.domain.product.service;

import f4.product.domain.product.dto.request.ProductRegisterRequestDto;
import f4.product.domain.product.persist.entity.Product;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

  void saveProduct(ProductRegisterRequestDto product) throws IOException;

}
