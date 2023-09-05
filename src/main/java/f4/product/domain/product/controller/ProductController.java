package f4.product.domain.product.controller;

import f4.product.domain.product.dto.request.ProductSaveRequestDto;
import f4.product.domain.product.dto.request.ProductUpdateRequestDto;
import f4.product.domain.product.dto.response.AuctionTimeStatusDto;
import f4.product.domain.product.dto.response.FeignProductDto;
import f4.product.domain.product.dto.response.ProductReadResponseDto;
import f4.product.domain.product.service.AuctionStatusService;
import f4.product.domain.product.service.FavoriteService;
import f4.product.domain.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/v1")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class ProductController {

  private final ProductService productService;
  private final AuctionStatusService auctionStatusService;

  @PostMapping("/save")
  public ResponseEntity<?> saveProduct(@ModelAttribute ProductSaveRequestDto requestDto) {
    productService.saveProduct(requestDto);
    return new ResponseEntity<>("상품 등록이 완료되었습니다.", HttpStatus.OK);
  }

  @GetMapping("/findAll")
  public ResponseEntity<List<ProductReadResponseDto>> findAll() {
    List<ProductReadResponseDto> products = productService.findAll();
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @GetMapping("/update/{productId}")
  public ResponseEntity<ProductReadResponseDto> findById(@PathVariable Long productId) {
    ProductReadResponseDto response = productService.findById(productId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/by-name")
  public ResponseEntity<List<ProductReadResponseDto>> findByName(@RequestParam String name) {
    List<ProductReadResponseDto> products = productService.findByName(name);
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @GetMapping("/by-artist")
  public ResponseEntity<List<ProductReadResponseDto>> findByArtist(@RequestParam String artist) {
    List<ProductReadResponseDto> products = productService.findByArtist(artist);
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @GetMapping("/search")
  public ResponseEntity<List<ProductReadResponseDto>> findByMediumAndKeyword(
      @RequestParam String category, @RequestParam String keyword) {
    List<ProductReadResponseDto> products =
        productService.findByMediumAndKeyword(category, keyword);
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @PutMapping("/{productId}")
  public ResponseEntity<String> updateProduct(
      @PathVariable Long productId,
      @ModelAttribute ProductUpdateRequestDto updateRequestDto) {
    productService.updateProduct(productId, updateRequestDto);
    return ResponseEntity.ok("상품이 수정되었습니다.");
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
    productService.deleteProduct(productId);
    return ResponseEntity.ok("상품이 삭제되었습니다.");
  }

  @PutMapping("/update-to-end")
  public ResponseEntity<List<FeignProductDto>> auctionStatusUpdateToEnd() {
    List<FeignProductDto> updatedProducts = auctionStatusService.auctionStatusUpdateToEnd();
    return new ResponseEntity<>(updatedProducts, HttpStatus.OK);
  }

  @PutMapping("/update-to-progress")
  public ResponseEntity<Void> auctionStatusUpdateToProgress() {
    auctionStatusService.updateAuctionStatusToProgress();
    return ResponseEntity.ok().build();
  }

  @GetMapping("/status/{id}")
  public AuctionTimeStatusDto getStatus(@PathVariable Long id) {
    return auctionStatusService.getStatus(id);
  }
}
