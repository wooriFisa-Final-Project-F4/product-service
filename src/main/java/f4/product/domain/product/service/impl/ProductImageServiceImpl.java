package f4.product.domain.product.service.impl;


import f4.product.domain.product.persist.entity.Product;
import f4.product.domain.product.persist.entity.ProductImage;
import f4.product.domain.product.persist.repository.ProductImageRepository;
import f4.product.domain.product.service.ProductImageService;
import f4.product.global.service.S3Service;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

  private final S3Service s3Service;
  private final ProductImageRepository productImageRepository;

  @Transactional
  public void saveProductImages(Product product, List<MultipartFile> images) {
    images.forEach(
        image -> {
          String url = s3Service.uploadFile(image);
          ProductImage productImage = createProductImage(product, url);
          productImageRepository.save(productImage);
        });
  }

  @Transactional
  public void deleteProductImages(List<ProductImage> images) {
    for (ProductImage image : images) {
      String imageUrl = image.getImageUrl();
      s3Service.deleteFile(imageUrl);
    }
  }

  private ProductImage createProductImage(Product product, String url) {
    return ProductImage.builder().product(product).imageUrl(url).build();
  }
}