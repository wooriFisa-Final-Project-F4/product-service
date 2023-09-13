package f4.product.domain.product.controller;

import f4.product.domain.product.constant.Role;
import f4.product.domain.product.dto.request.ProductSaveRequestDto;
import f4.product.domain.product.dto.request.ProductUpdateRequestDto;
import f4.product.domain.product.dto.response.AuctionTimeStatusDto;
import f4.product.domain.product.dto.response.FeignProductDto;
import f4.product.domain.product.dto.response.ProductReadResponseDto;
import f4.product.domain.product.service.AuctionStatusService;
import f4.product.domain.product.service.ProductService;
import f4.product.global.constant.CustomErrorCode;
import f4.product.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;
  private final AuctionStatusService auctionStatusService;

  @PostMapping("/save")
  public ResponseEntity<?> saveProduct(
      @ModelAttribute ProductSaveRequestDto requestDto,
      @RequestHeader("role") String role
  ) {
    Role userRole = Role.of(role);

    if (userRole == Role.ADMIN) {
      log.info("상품 등록 요청 받음. 상품명: {}", requestDto.getName());
      productService.saveProduct(requestDto);
      log.info("상품 등록 완료. 상품명: {}", requestDto.getName());
      return new ResponseEntity<>("상품 등록이 완료되었습니다.", HttpStatus.OK);
    } else {
      throw new CustomException(CustomErrorCode.NOT_EXIST_ROLE);
    }
  }

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

  @PutMapping("/{productId}")
  public ResponseEntity<String> updateProduct(
      @PathVariable Long productId,
      @ModelAttribute ProductUpdateRequestDto updateRequestDto,
      @RequestHeader("role") String role
  ) {
    Role userRole = Role.of(role);

    if (userRole == Role.ADMIN) {
      log.info("상품 수정 요청 받음. 상품 ID: {}", productId);
      productService.updateProduct(productId, updateRequestDto);
      log.info("상품이 수정 완료. 상품 ID: {}", productId);
      return ResponseEntity.ok("상품이 수정되었습니다.");
    } else {
      throw new CustomException(CustomErrorCode.NOT_EXIST_ROLE);
    }
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<String> deleteProduct(
      @PathVariable Long productId
//      @RequestHeader("role") String role
  ) {
//    Role userRole = Role.of(role);

//    if (userRole == Role.ADMIN) {
      log.info("상품 삭제 요청 받음. 상품 ID: {}", productId);
      productService.deleteProduct(productId);
      log.info("상품 삭제 완료. 상품 ID: {}", productId);
      return ResponseEntity.ok("상품이 삭제되었습니다.");
//    } else {
//      throw new CustomException(CustomErrorCode.NOT_EXIST_ROLE);
//    }
  }

  @PutMapping("/update-to-end")
  public List<FeignProductDto> auctionStatusUpdateToEnd() {
    log.info("경매 상태 업데이트 요청. (END로 변경)");
    List<FeignProductDto> updatedProducts = auctionStatusService.auctionStatusUpdateToEnd();
    log.info("경매 상태 업데이트 완료. (END로 변경), 업데이트된 상품 수: {}", updatedProducts.size());
    return updatedProducts;
  }

  @PutMapping("/update-to-progress")
  public void auctionStatusUpdateToProgress() {
    log.info("경매 상태 업데이트 요청. (PROGRESS로 변경)");
    auctionStatusService.updateAuctionStatusToProgress();
    log.info("경매 상태 업데이트 완료. (PROGRESS으로 변경)");
  }

  @GetMapping("/status/{id}")
  public AuctionTimeStatusDto getStatus(@PathVariable Long id) {
    log.info("경매 상태 조회 요청 받음. 상품 ID: {}", id);
    return auctionStatusService.getStatus(id);
  }
}
