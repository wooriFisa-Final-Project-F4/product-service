package f4.product.domain.product.controller;

import f4.product.domain.product.dto.request.ProductRegisterRequestDto;
import f4.product.domain.product.persist.repository.ProductRepository;
import f4.product.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/v1")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;
  private final ProductRepository productRepository;

  @PostMapping
  public String register(@RequestBody ProductRegisterRequestDto requestDto) {
    System.out.println("*****controller : " + requestDto);
    productService.register(requestDto);
    return "상품 등록이 완료되었습니다.";
  }
}
