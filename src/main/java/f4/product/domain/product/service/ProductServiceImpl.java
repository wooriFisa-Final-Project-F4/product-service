package f4.product.domain.product.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import f4.product.domain.auction.persist.entity.Auction;
import f4.product.domain.auction.persist.repository.AuctionRepository;
import f4.product.domain.product.dto.request.ProductRegisterRequestDto;
import f4.product.domain.product.persist.entity.Product;
import f4.product.domain.product.persist.repository.ProductRepository;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final AuctionRepository auctionRepository;
  private final ModelMapper modelMapper;
  private final AmazonS3 amazonS3;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  @Override
  @Transactional
  public void saveProduct(ProductRegisterRequestDto requestDto) throws IOException {
    String imageUrl = s3Upload(requestDto.getImages());
    Product product = modelMapper.map(requestDto, Product.class);
    product.setImages(imageUrl);
    Product savedProduct = productRepository.save(product);
    Auction auction = modelMapper.map(requestDto, Auction.class);
    auction.setProduct(savedProduct);
    Auction savedAuction = auctionRepository.save(auction);
  }

  private String s3Upload(List<MultipartFile> images) throws IOException {
    StringBuilder result = new StringBuilder();
    for (MultipartFile image : images) {
      String imageUrl = uploadImageToS3(image);
      result.append(imageUrl).append(" ");
    }
    return result.toString().trim(); // 공백 제거
  }

//    private String uploadImageToS3(MultipartFile image) throws IOException {
//    byte[] decodedImage = Base64.getDecoder().decode(image);
//    InputStream inputStream = new ByteArrayInputStream(decodedImage);
//    ObjectMetadata metadata = new ObjectMetadata();
//    metadata.setContentLength(decodedImage.length);
//    String fileName = UUID.randomUUID().toString() + ".png"; // assuming the image is a PNG
//    amazonS3.putObject(
//        new PutObjectRequest(bucket, fileName, inputStream, metadata)
//    );
//    return "https://" + bucket + ".s3.amazonaws.com/" + fileName;
//  }
  private String uploadImageToS3(MultipartFile image) {
    String fileName = "product-image-" + UUID.randomUUID() + "." + image.getOriginalFilename();
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(image.getContentType());
    metadata.setContentLength(image.getSize());

    try {
      amazonS3.putObject(
          new PutObjectRequest("f4-product-service-bucket", fileName, image.getInputStream(), metadata));
    } catch (IOException e) {
      // Handle exception
      e.printStackTrace();
      // You might want to throw a custom exception here or handle the error appropriately
      return "";
    }
    return "https://" + bucket + ".s3.amazonaws.com/" + fileName;
  }
}