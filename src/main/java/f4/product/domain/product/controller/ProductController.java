package f4.product.domain.product.controller;

import f4.product.domain.product.dto.request.ProductSaveRequestDto;
import f4.product.domain.product.dto.response.ProductReadResponseDto;
import f4.product.domain.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  @GetMapping
  public ResponseEntity<List<ProductReadResponseDto>> readAllProduct(){
    List<ProductReadResponseDto> products = productService.readAllProducts();
    return new ResponseEntity<>(products, HttpStatus.OK);
  }
  @GetMapping("/{productId}")
  public ResponseEntity<ProductReadResponseDto> getProductById(@PathVariable Long productId) {
    ProductReadResponseDto productDto = productService.readProductById(productId);
    return ResponseEntity.ok(productDto);
  }
  @GetMapping("/by-name")
  public ResponseEntity<List<ProductReadResponseDto>> getProductsByName(@RequestParam String name) {
    List<ProductReadResponseDto> products = productService.readProductsByName(name);
    return ResponseEntity.ok(products);
  }
  @GetMapping("/by-artist")
  public ResponseEntity<List<ProductReadResponseDto>> getProductsByArtist(@RequestParam String artist) {
    List<ProductReadResponseDto> products = productService.readProductsByArtist(artist);
    return ResponseEntity.ok(products);
  }
}
