package f4.product.domain.product.controller;

import f4.product.domain.product.dto.request.ProductSaveRequestDto;
import f4.product.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/v1")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping("/save")
  public ResponseEntity<?> saveProduct(@ModelAttribute ProductSaveRequestDto requestDto) {
    productService.saveProduct(requestDto);
    return new ResponseEntity<>("상품 등록이 완료되었습니다.", HttpStatus.OK);
  }
}
