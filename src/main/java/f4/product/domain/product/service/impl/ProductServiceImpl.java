package f4.product.domain.product.service.impl;

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
    Product product = modelMapper.map(requestDto, Product.class);
    product.setIdentifier(identifier);
    return product;
  }

  private String generateIdentifier(ProductSaveRequestDto requestDto) {
    return requestDto.getArtist() + requestDto.getName();
  }

  private ProductReadResponseDto convertProductToDto(Product product) {
    return modelMapper.map(product, ProductReadResponseDto.class);
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
}
