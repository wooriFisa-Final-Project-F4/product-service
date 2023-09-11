package f4.product.domain.product.controller;

import f4.product.domain.product.dto.response.ProductReadResponseDto;
import f4.product.domain.product.service.FavoriteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/favorite/v1")
@RequiredArgsConstructor
public class FavoriteController {

  private final FavoriteService favoriteService;

  @PostMapping
  public ResponseEntity<?> saveFavorite(
      @RequestHeader("userId") Long userId,
      @RequestParam("productId") Long productId) {
    log.info("관심상품 등록 요청 받음. 사용자 ID: {}, 상품 ID: {}", userId, productId);
    return favoriteService.saveFavorite(userId, productId);
  }

  @GetMapping
  public ResponseEntity<List<ProductReadResponseDto>> readFavoriteProducts(
      @RequestHeader("userId") Long userId) {
    log.info("관심상품 조회 요청 받음. 사용자 ID: {}", userId);
    List<ProductReadResponseDto> favoriteProducts = favoriteService.readFavoriteProducts(userId);
    log.info("관심상품 조회 완료. 조회된 상품 수: {}", favoriteProducts.size());
    return new ResponseEntity<>(favoriteProducts, HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteFavorite(
      @RequestHeader("userId") Long userId,
      @RequestParam Long productId) {
    log.info("관심상품 삭제 요청 받음. 사용자 ID: {}, 상품 ID: {}", userId, productId);
    favoriteService.deleteFavoriteFavoriteWithCheck(userId, productId);
    log.info("관심상품 삭제 완료. 사용자 ID: {}, 상품 ID: {}", userId, productId);
    return ResponseEntity.ok("관심상품이 삭제되었습니다.");
  }

}
