package f4.product.domain.product.service.impl;

import f4.product.domain.product.constant.AuctionStatus;
import f4.product.domain.product.dto.request.ProductSaveRequestDto;
import f4.product.domain.product.dto.response.ProductReadResponseDto;
import f4.product.domain.product.persist.entity.Product;
import f4.product.domain.product.persist.entity.ProductImage;
import f4.product.domain.product.persist.repository.ProductImageRepository;
import f4.product.domain.product.persist.repository.ProductRepository;
import f4.product.domain.product.service.ProductService;
import f4.product.global.constant.CustomErrorCode;
import f4.product.global.exception.CustomException;
import f4.product.global.service.S3Service;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductImageRepository productImageRepository;
  private final S3Service s3Service;
  private final ModelMapper modelMapper;

  @Override
  @Transactional
  public void saveProduct(ProductSaveRequestDto requestDto) {
    String identifier = requestDto.getArtist() + requestDto.getName();

    productRepository.findByIdentifier(identifier)
        .ifPresent(
            data -> {
              throw new CustomException(CustomErrorCode.ALREADY_REGISTER_PRODUCT);
            });

    /* ProductSaveRequestDto를 Product객체로 변환함*/
    Product product = productBuild(requestDto, identifier);
    /* 데이터베이스에 저장*/
    Product save = productRepository.save(product);

    for (MultipartFile image : requestDto.getImages()) {
      String url = s3Service.uploadFile(image);
      productImageRepository.save(productImageBuild(save, url));
    }
  }
  //모든 작품 조회
  // Product 엔티티를 ProductReadResponseDto로 변환하여 리스트로 반환
  @Override
  public List<ProductReadResponseDto> readAllProducts() {
    List<Product> products = productRepository.findAll();
    return products.stream()
        .map(this::convertToResponseDto)
        .collect(Collectors.toList());
  }

  private ProductReadResponseDto convertToResponseDto(Product product) {
    ProductReadResponseDto responseDto = modelMapper.map(product, ProductReadResponseDto.class);

    // ProductImage 엔티티 정보를 ProductReadResponseDto에 추가
    List<String> imageUrls = product.getImages().stream()
        .map(ProductImage::getImageUrl)
        .collect(Collectors.toList());
    responseDto.setImageUrl(imageUrls);
    responseDto.setArtist(product.getArtist());
    responseDto.setCountry(product.getCountry());
    responseDto.setDescription(product.getDescription());
    responseDto.setCompletionDate(product.getCompletionDate());
    responseDto.setSize(product.getSize());
    responseDto.setTheme(product.getTheme());
    responseDto.setStyle(product.getStyle());
    responseDto.setTechnique(product.getTechnique());
    responseDto.setAuctionPrice(product.getAuctionPrice());
    responseDto.setAuctionStatus(product.getAuctionStatus());
    responseDto.setAuctionStartTime(product.getAuctionStartTime());
    responseDto.setAuctionEndTime(product.getAuctionEndTime());

    return responseDto;

  }

  // ProductImage 엔티티를 생성
  private static ProductImage productImageBuild(Product save, String url) {
    return ProductImage.builder()
        .product(save)
        .imageUrl(url)
        .build();
  }

  /* ProductSaveRequestDto를 Product객체로 변환함*/
  private static Product productBuild(ProductSaveRequestDto requestDto, String identifier) {
    // 고유식별자 생성
    return Product.builder()
        .name(requestDto.getName())
        .identifier(identifier)
        .artist(requestDto.getArtist())
        .country(requestDto.getCountry())
        .description(requestDto.getDescription())
        .completionDate(requestDto.getCompletionDate())
        .size(requestDto.getSize())
        .theme(requestDto.getTheme())
        .style(requestDto.getStyle())
        .technique(requestDto.getTechnique())
        .auctionPrice(requestDto.getAuctionPrice())
        .auctionStatus(AuctionStatus.of(requestDto.getAuctionStatus()))
        .auctionStartTime(requestDto.getAuctionStartTime())
        .auctionEndTime(requestDto.getAuctionEndTime())
        .build();
  }
}
