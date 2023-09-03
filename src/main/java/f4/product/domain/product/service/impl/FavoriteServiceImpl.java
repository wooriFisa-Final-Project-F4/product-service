package f4.product.domain.product.service.impl;

import f4.product.domain.product.dto.request.FeignUserDto;
import f4.product.domain.product.dto.response.ProductReadResponseDto;
import f4.product.domain.product.persist.entity.Favorite;
import f4.product.domain.product.persist.entity.Product;
import f4.product.domain.product.persist.repository.FavoriteRepository;
import f4.product.domain.product.service.FavoriteService;
import f4.product.domain.product.service.ProductService;
import f4.product.domain.product.service.UserServiceAPI;
import f4.product.global.constant.CustomErrorCode;
import f4.product.global.exception.CustomException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

  private final UserServiceAPI userServiceAPI;
  private final ProductService productService;
  private final ProductServiceImpl productServiceImpl;
  private final FavoriteRepository favoriteRepository;

  @Override
  public void saveFavorite(Long userId, Long productId) {
    // 사용자 정보 가져오기 -> User Service에서 getUserById(userId)구현 필요
    FeignUserDto userDto = userServiceAPI.getUserById(userId);

    if (userDto == null) {
      throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
    }
    // 상품 정보 가져오기
    Product product = productService.findProductById(productId);

    // Favorite 엔티티 생성 및 저장
    Favorite favorite = Favorite.builder()
        .userId(userDto.getUserId())
        .product(product)
        .build();
    favoriteRepository.save(favorite);

  }

  @Override
  public List<ProductReadResponseDto> readFavoriteProducts(Long userId) {
    // 사용자 정보 가져오기 -> User Service에서 getUserById(userId) 구현 필요
    FeignUserDto userDto = userServiceAPI.getUserById(userId);

    if (userDto == null) {
      throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
    }

    // 사용자의 관심 상품 목록 조회
    List<Favorite> favorites = favoriteRepository.findByUserId(userDto.getUserId());

    // 관심 상품 목록을 ProductReadResponseDto로 변환
    List<ProductReadResponseDto> favoriteProducts = new ArrayList<>();
    for (Favorite favorite : favorites) {
      Product product = favorite.getProduct();

      ProductReadResponseDto productDto = productServiceImpl.convertProductToDto(product);

      favoriteProducts.add(productDto);
    }

    return favoriteProducts;
  }


}
