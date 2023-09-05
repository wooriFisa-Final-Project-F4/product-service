package f4.product.domain.product.controller;

import f4.product.domain.product.dto.response.ProductReadResponseDto;
import f4.product.domain.product.service.FavoriteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/favorite/v1")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class FavoriteController {

  private final FavoriteService favoriteService;

  @PostMapping
  public ResponseEntity<?> saveFavorite(
      @RequestHeader("userId") Long userId, //String 으로 전환???
      @RequestParam("productId") Long productId) {
    favoriteService.saveFavorite(userId, productId);
    return ResponseEntity.ok("관심상품이 등록되었습니다.");
  }

  @GetMapping
  public ResponseEntity<List<ProductReadResponseDto>> readFavoriteProducts(
      @RequestHeader("userId") Long userId) {
    List<ProductReadResponseDto> favoriteProducts = favoriteService.readFavoriteProducts(userId);
    return new ResponseEntity<>(favoriteProducts, HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteFavorite(
      @RequestHeader("userId") Long userId,
      @RequestParam Long productId) {
    favoriteService.deleteFavorite(userId, productId);
    return ResponseEntity.ok("관심상품이 삭제되었습니다.");
  }

}
