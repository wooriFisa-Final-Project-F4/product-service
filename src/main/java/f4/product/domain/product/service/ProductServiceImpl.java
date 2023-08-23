package f4.product.domain.product.service;

import f4.product.domain.product.dto.request.ProductRegisterRequestDto;
import f4.product.domain.product.persist.entity.Auction;
import f4.product.domain.product.persist.entity.Product;
import f4.product.domain.product.persist.repository.AuctionRepository;
import f4.product.domain.product.persist.repository.ProductRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
  private final ProductRepository productRepository;
  private final AuctionRepository auctionRepository;
  private final ModelMapper modelMapper;

  @Override
  @Transactional
  public void register(ProductRegisterRequestDto requestDto){
    Product product = modelMapper.map(requestDto, Product.class);
    System.out.println(product);
    Product savedProduct = productRepository.save(product);

    Auction auction = modelMapper.map(requestDto, Auction.class);
    auction.setProduct(product);
    System.out.println(auction);
    Auction savedAuction = auctionRepository.save(auction);
  }
}
