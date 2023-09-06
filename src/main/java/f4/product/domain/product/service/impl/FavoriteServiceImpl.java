package f4.product.domain.product.service.impl;

import f4.product.domain.product.dto.response.ProductReadResponseDto;
import f4.product.domain.product.persist.entity.Favorite;
import f4.product.domain.product.persist.entity.Product;
import f4.product.domain.product.persist.repository.FavoriteRepository;
import f4.product.domain.product.service.FavoriteService;
import f4.product.domain.product.service.ProductService;
import f4.product.domain.product.service.feign.UserServiceAPI;
import f4.product.domain.product.service.feign.dto.ProductResponseDto;
import f4.product.global.constant.CustomErrorCode;
import f4.product.global.exception.CustomException;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

  private final UserServiceAPI userServiceAPI;
  private final ProductService productService;
  private final ProductServiceImpl productServiceImpl;
  private final FavoriteRepository favoriteRepository;

  @Override
  @Transactional
  public ResponseEntity<?> saveFavorite(Long userId, Long productId) {
    ProductResponseDto feignResponse = userServiceAPI.existsByUserId(userId);

    if (!feignResponse.isExisted()) {
      throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
    }

    Product product = productService.findProductById(productId);

    if (isCheckedFavorite(userId, productId)) {
      deleteFavorite(userId, productId);
      log.info("관심상품 삭제 완료. 사용자 ID: {}, 상품 ID: {}", userId, productId);
      return ResponseEntity.ok("관심상품이 삭제되었습니다.");
    } else {
      addFavoriteIfNotFull(userId, product);
      log.info("관심상품 등록 완료. 사용자 ID: {}, 상품 ID: {}", userId, productId);
      return ResponseEntity.ok("관심상품이 등록되었습니다.");
    }
  }

  public boolean isCheckedFavorite(Long userId, Long productId) {
    return favoriteRepository.countByUserIdAndProductId(userId, productId) > 0;
  }

  //10개 미만인 경우 생성 메서드
  public void addFavoriteIfNotFull(Long userId, Product product) {
    if (favoriteRepository.countByUserId(userId) >= 10) {
      throw new CustomException(CustomErrorCode.TOO_MANY_FAVORITE_PRODUCTS);
    }
    saveFavoriteProduct(userId, product);
  }


  public void deleteFavorite(Long userId, Long productId) {
    favoriteRepository.deleteByUserIdAndProductId(userId, productId);
  }

  // Favorite 엔티티 생성 및 저장
  public void saveFavoriteProduct(Long userId, Product product) {
    Favorite favorite = Favorite.builder()
        .userId(userId)
        .product(product)
        .build();
    favoriteRepository.save(favorite);
  }

  @Override
  public List<ProductReadResponseDto> readFavoriteProducts(Long userId) {
    ProductResponseDto userDto = userServiceAPI.existsByUserId(userId);

    if (userDto.isExisted()) {
      throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
    }

    // 사용자의 관심 상품 목록 조회
    List<Favorite> favorites = favoriteRepository.findByUserId(userId);

    // 관심 상품 목록을 ProductReadResponseDto로 변환
    List<ProductReadResponseDto> favoriteProducts = new ArrayList<>();
    for (Favorite favorite : favorites) {
      Product product = favorite.getProduct();

      ProductReadResponseDto productDto = productServiceImpl.convertProductToDto(product);

      favoriteProducts.add(productDto);
    }
    return favoriteProducts;
  }

  @Override
  public void deleteFavoriteFavoriteWithCheck(Long userId, Long productId) {
    ProductResponseDto userDto = userServiceAPI.existsByUserId(userId);

    if (userDto.isExisted()) {
      throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
    }
    // 관심 상품 삭제
    deleteFavorite(userId, productId);
  }
}
