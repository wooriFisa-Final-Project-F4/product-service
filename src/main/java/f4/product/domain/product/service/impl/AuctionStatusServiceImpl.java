package f4.product.domain.product.service.impl;

import f4.product.domain.product.constant.AuctionStatus;
import f4.product.domain.product.dto.response.AuctionTimeStatusDto;
import f4.product.domain.product.dto.response.FeignProductDto;
import f4.product.domain.product.persist.entity.Product;
import f4.product.domain.product.persist.repository.ProductRepository;
import f4.product.domain.product.service.AuctionStatusService;
import f4.product.global.constant.CustomErrorCode;
import f4.product.global.exception.CustomException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionStatusServiceImpl implements AuctionStatusService {

  private final ProductRepository productRepository;
  private final ProductServiceImpl productServiceImpl;

  @Transactional
  @Override
  public List<FeignProductDto> auctionStatusUpdateToEnd() {
    LocalDateTime now = LocalDateTime.now();
    List<Product> products = productRepository.findCompletedAuctionsInProgress(now);
    List<FeignProductDto> feignProductDtos = new ArrayList<>();

    for (Product product : products) {
      product.setAuctionStatus(AuctionStatus.END);
      productRepository.save(product);
      feignProductDtos.add(convertProductToFeignProductDto(product));
    }

    return feignProductDtos;
  }

  @Transactional
  @Override
  public void updateAuctionStatusToProgress() {
    LocalDateTime now = LocalDateTime.now();
    List<Product> waitingProducts = productRepository.findStartedAuctionsWaiting(now);

    for (Product product : waitingProducts) {
      product.setAuctionStatus(AuctionStatus.PROGRESS);
      productRepository.save(product);
    }
  }

//  @Override
//  public List<FeignProductDto> getProductsSortedByAuctionPrice() {
//    List<Product> sortedProducts = productRepository.findByOrderByAuctionPriceDesc();
//    return sortedProducts.stream()
//        .map(this::convertProductToFeignProductDto)
//        .collect(Collectors.toList());
//  }

  @Override
  public AuctionTimeStatusDto getStatus(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_PRODUCT));

    AuctionTimeStatusDto statusDto = AuctionTimeStatusDto.builder()
        .auctionStatus(product.getAuctionStatus().toString())
        .auctionStartTime(product.getAuctionStartTime())
        .auctionEndTime(product.getAuctionEndTime())
        .build();

    return statusDto;
  }

  private FeignProductDto convertProductToFeignProductDto(Product product) {
    String imageUrl = productServiceImpl.getProductImageUrl(product);

    return FeignProductDto.builder()
        .id(product.getId())
        .name(product.getName())
        .image(imageUrl)
        .artist(product.getArtist())
        .auctionPrice(product.getAuctionPrice())
        .auctionStatus(String.valueOf(product.getAuctionStatus()))
        .auctionEndTime(product.getAuctionEndTime())
        .bidUserId(product.getBidUserId() != null ? product.getBidUserId() : 0)
        .build();
  }
}
