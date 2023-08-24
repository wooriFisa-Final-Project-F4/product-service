package f4.product.domain.product.controller;

import f4.product.domain.product.dto.request.ProductRegisterRequestDto;
import f4.product.domain.product.persist.repository.ProductRepository;
import f4.product.domain.product.service.ProductService;
import java.io.IOException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ModelAttribute;


import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/product/v1")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:63342/")
public class ProductController {

  private final ProductService productService;
  private final ProductRepository productRepository;
  private Object MediaType;

  @PostMapping("/save")
  public ResponseEntity<?> saveProduct( @ModelAttribute ProductRegisterRequestDto requestDto)
      throws IOException {
    System.out.println("********1*******");
  System.out.println(requestDto.getImages().get(0));
//    System.out.println(images.get(0));
    System.out.println("********2*******");
    productService.saveProduct(requestDto);
    return new ResponseEntity<>("상품 등록이 완료되었습니다.", HttpStatus.OK);
  }
}

