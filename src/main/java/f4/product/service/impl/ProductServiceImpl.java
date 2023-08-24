package f4.product.service.impl;

import f4.product.dto.request.ProductSaveRequestDto;
import f4.product.persist.entity.Product;
import f4.product.persist.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import f4.product.service.ProductService;
import f4.product.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final S3Service s3Service;

  @Override
  @Transactional
  public void saveProduct(ProductSaveRequestDto requestDto) {
    /* image url들의 List를 담기위한 ArrayList 생성*/
    List<String> imageUrls = new ArrayList<>();

    /* 반복문을 사용하여 S3Service의 uplodaFile로 객체를 하나씩 넘기고 url을 반환받음*/
    for(MultipartFile image : requestDto.getImages()){
      String url = s3Service.uploadFile(image);
      imageUrls.add(url);
    }
//    List<String> imageUrls = requestDto.getImages().stream().map(s3Service::uploadFile).collect(Collectors.toList());

    /* imageUrls를 join문을 사용하여 url사이에 공백을 넣어줌*/
    String joinedImageUrls = String.join(" ", imageUrls);

    /* ProductSaveRequestDto를 Product객체로 변환함*/
    Product product = productBuild(requestDto, joinedImageUrls);

    /* 데이터베이스에 저장*/
    productRepository.save(product);
  }

  /* ProductSaveRequestDto를 Product객체로 변환함*/
  private static Product productBuild(ProductSaveRequestDto requestDto, String joinedImageUrls) {
    return Product.builder()
        .name(requestDto.getName())
        .images(joinedImageUrls)
        .artist(requestDto.getArtist())
        .country(requestDto.getCountry())
        .description(requestDto.getDescription())
        .completionDate(requestDto.getCompletionDate())
        .size(requestDto.getSize())
        .theme(requestDto.getTheme())
        .style(requestDto.getStyle())
        .technique(requestDto.getTechnique())
        .auctionPrice(requestDto.getAuctionPrice())
        .auctionStatus(requestDto.getAuctionStatus())
        .auctionStartTime(requestDto.getAuctionStartTime())
        .auctionEndTime(requestDto.getAuctionEndTime())
        .bidUserId(requestDto.getBidUserId())
        .build();
  }
}
