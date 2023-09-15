package f4.product.domain.product.controller;

import f4.product.domain.product.dto.response.ProductReadResponseDto;
import f4.product.domain.product.service.AuctionStatusService;
import f4.product.domain.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/select")
@RequiredArgsConstructor
public class ProductSelectController {

  private final ProductService productService;
  private final AuctionStatusService auctionStatusService;

  @GetMapping("/findAll")
  public ResponseEntity<List<ProductReadResponseDto>> findAll() {
    log.info("모든 상품 조회 요청 받음.");
    List<ProductReadResponseDto> products = productService.findAll();
    log.info("모든 상품 조회 완료. 조회된 상품 수: {}", products.size());
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductReadResponseDto> findById(@PathVariable Long productId) {
    log.info("상품 id로 상품 조회 요청 받음. 상품 ID: {}", productId);
    ProductReadResponseDto response = productService.findById(productId);
    log.info("상품 id로 상품 조회 완료. 조회된 상품 ID: {}", response.getId());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/by-name")
  public ResponseEntity<List<ProductReadResponseDto>> findByName(@RequestParam String name) {
    log.info("상품명으로 상품 조회 요청 받음. 상품명: {}", name);
    List<ProductReadResponseDto> products = productService.findByName(name);
    log.info("상품명으로 상품 조회 완료. 상품명: {}, 조회된 상품 수: {}", name, products.size());
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @GetMapping("/by-artist")
  public ResponseEntity<List<ProductReadResponseDto>> findByArtist(@RequestParam String artist) {
    log.info("아티스트명으로 상품 조회 요청 받음. 아티스트명: {}", artist);
    List<ProductReadResponseDto> products = productService.findByArtist(artist);
    log.info("아티스트명으로 상품 조회 완료. 아티스트명: {}, 조회된 상품 수: {}", artist, products.size());
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @GetMapping("/search/category")
  public ResponseEntity<List<ProductReadResponseDto>> findByMediumAndKeyword(
      @RequestParam String category, @RequestParam String keyword) {
    log.info("카테고리와 키워드로 상품 검색 요청 받음. 카테고리: {}, 키워드: {}", category, keyword);
    List<ProductReadResponseDto> products =
        productService.findByMediumAndKeyword(category, keyword);
    log.info("카테고리와 키워드로 상품 검색 완료. 조회된 상품 수: {}", products.size());
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @GetMapping("/search")
  public ResponseEntity<List<ProductReadResponseDto>> findByMedium(
      @RequestParam String category) {
    log.info("카테고리로 상품 검색 요청 받음. 카테고리: {}", category);
    List<ProductReadResponseDto> products = productService.findByMedium(category);
    log.info("카테고리로 상품 검색 완료. 조회된 상품 수: {}, 카테고리: {}", products.size(), category);
    return new ResponseEntity<>(products, HttpStatus.OK);
  }


}
