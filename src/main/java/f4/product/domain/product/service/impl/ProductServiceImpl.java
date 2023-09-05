package f4.product.domain.product.service.impl;

import f4.product.domain.product.constant.AuctionStatus;
import f4.product.domain.product.dto.request.ProductSaveRequestDto;
import f4.product.domain.product.dto.request.ProductUpdateRequestDto;
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
import java.util.Optional;
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

    validateUniqueProductIdentifier(requestDto);

    Product product = convertDtoToProduct(requestDto);

    Product savedProduct = productRepository.save(product);

    saveProductImages(savedProduct, requestDto.getImages());
  }

  @Override
  public List<ProductReadResponseDto> findAll() {
    return productRepository.findAll().stream()
        .map(this::convertProductToDto)
        .collect(Collectors.toList());
  }

  @Override
  public ProductReadResponseDto findById(Long productId) {
    Product product = findProductOrThrow(productId);
    return convertProductToDto(product);
  }

  @Override
  public List<ProductReadResponseDto> findByName(String name) {
    return findProductsOrThrow(productRepository.findByName(name));
  }

  @Override
  public List<ProductReadResponseDto> findByArtist(String artist) {
    return findProductsOrThrow(productRepository.findByArtist(artist));
  }

  @Override
  public List<ProductReadResponseDto> findByMediumAndKeyword(String medium, String keyword) {
    return findProductsOrThrow(productRepository.findByMediumAndKeyword(medium, keyword));
  }

  private void validateUniqueProductIdentifier(ProductSaveRequestDto requestDto) {
    String identifier = generateIdentifier(requestDto);
    if (productRepository.findByIdentifier(identifier).isPresent()) {
      throw new CustomException(CustomErrorCode.ALREADY_REGISTER_PRODUCT);
    }
  }

  private Product convertDtoToProduct(ProductSaveRequestDto requestDto) {
    String identifier = generateIdentifier(requestDto);
    return Product.builder()
        .name(requestDto.getName())
        .identifier(identifier)
        .artist(requestDto.getArtist())
        .country(requestDto.getCountry())
        .description(requestDto.getDescription())
        .completionDate(requestDto.getCompletionDate())
        .size(requestDto.getSize())
        .medium(requestDto.getMedium())
        .theme(requestDto.getTheme())
        .style(requestDto.getStyle())
        .technique(requestDto.getTechnique())
        .auctionPrice(requestDto.getAuctionPrice())
        .auctionStatus(AuctionStatus.of(requestDto.getAuctionStatus()))
        .auctionStartTime(requestDto.getAuctionStartTime())
        .auctionEndTime(requestDto.getAuctionEndTime())
        .build();
  }

  private String generateIdentifier(ProductSaveRequestDto requestDto) {
    return requestDto.getArtist() + requestDto.getName();
  }

  public ProductReadResponseDto convertProductToDto(Product product) {
    ProductReadResponseDto dto = modelMapper.map(product, ProductReadResponseDto.class);

    // 이미지 URL들을 추출하여 List에 추가
    List<String> images =
        product
            .getImages()
            .stream()
            .map(ProductImage::getImageUrl)
            .collect(Collectors.toList());

    dto.setImages(images);
    return dto;
  }

  private List<ProductReadResponseDto> findProductsOrThrow(
      Optional<List<Product>> optionalProducts) {
    return optionalProducts
        .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_PRODUCT))
        .stream()
        .map(this::convertProductToDto)
        .collect(Collectors.toList());
  }

  private Product findProductOrThrow(Long id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_PRODUCT));
  }

  // 각 이미지를 S3에 저장하고, 그 URL을 상품 이미지 테이블에 저장
  private void saveProductImages(Product product, List<MultipartFile> images) {
    images.forEach(
        image -> {
          String url = s3Service.uploadFile(image);
          ProductImage productImage = createProductImage(product, url);
          productImageRepository.save(productImage);
        });
  }


  // 상품 이미지 Entity 생성
  private ProductImage createProductImage(Product product, String url) {
    return ProductImage.builder().product(product).imageUrl(url).build();
  }

  @Override
  @Transactional
  public void updateProduct(Long productId, ProductUpdateRequestDto updateDto) {
    Product product = findProductOrThrow(productId);

    // 이미지 삭제 여부에 따른 처리
    if (updateDto.isDeleteExistingImages()) {
      // 기존 이미지를 S3에서 삭제하고 상품 이미지 테이블에서 삭제
      deleteProductImages(product.getImages());
      productImageRepository.deleteAll(product.getImages());
      // 이미지와 관련된 정보를 초기화
      product.getImages().clear();
    }

    // 나머지 필드 업데이트 처리
    updateProductFields(product, updateDto);

    // 새 이미지 업로드 및 상품 이미지 테이블에 저장
    saveProductImages(product, updateDto.getNewImages());

    // 상품 정보 업데이트
    productRepository.save(product);
  }

  // 새로운 이미지 업로드 및 저장
  private void saveNewImages(Product product, List<MultipartFile> newImages) {
    newImages.forEach(
        image -> {
          String url = s3Service.uploadFile(image);
          ProductImage productImage = createProductImage(product, url);
          productImageRepository.save(productImage);
        });
  }

  // 기존 이미지 삭제
  private void deleteExistingImages(List<ProductImage> images) {
    for (ProductImage image : images) {
      String imageUrl = image.getImageUrl();
      s3Service.deleteFile(imageUrl);
    }
  }

  // 기존 상품 정보 수정
  private void updateProductFields(Product product, ProductUpdateRequestDto updateDto) {
    // 나머지 필드 업데이트 처리
    product.setName(updateDto.getName());
    product.setArtist(updateDto.getArtist());
    product.setCountry(updateDto.getCountry());
    product.setDescription(updateDto.getDescription());
    product.setCompletionDate(updateDto.getCompletionDate());
    product.setSize(updateDto.getSize());
    product.setMedium(updateDto.getMedium());
    product.setTheme(updateDto.getTheme());
    product.setStyle(updateDto.getStyle());
    product.setTechnique(updateDto.getTechnique());
    product.setAuctionPrice(updateDto.getAuctionPrice());
    product.setAuctionStatus(AuctionStatus.of(updateDto.getAuctionStatus()));
    product.setAuctionStartTime(updateDto.getAuctionStartTime());
    product.setAuctionEndTime(updateDto.getAuctionEndTime());

    //새 이미지를 업로드 할 때만 이미지 관련 필드를 처리함
  }

  @Override
  @Transactional
  public void deleteProduct(Long productId) {
    Product product = findProductOrThrow(productId);

    // S3에서 이미지 삭제
    deleteProductImages(product.getImages());

    // 상품 이미지 데이터베이스에서 삭제
    productImageRepository.deleteAll(product.getImages());
    // 이미지와 관련된 정보를 초기화
    product.getImages().clear();

    // 상품 정보 데이터베이스에서 삭제
    productRepository.delete(product);
  }

  // 이미지를 S3에서 삭제
  private void deleteProductImages(List<ProductImage> images) {
    for (ProductImage image : images) {
      String imageUrl = image.getImageUrl();
      s3Service.deleteFile(imageUrl);
    }
  }

  public Product findProductById(Long productId) {
    return productRepository.findById(productId)
        .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_PRODUCT));
  }

  public String getProductImageUrl(Product product) {
    if (!product.getImages().isEmpty()) {
      return product.getImages().get(0).getImageUrl();
    }
    return null;
  }
}
