package f4.product.domain.product.service.feign;

import f4.product.domain.product.service.feign.dto.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceAPI {
//
//  @GetMapping("/product/user/{userId}")
//  ProductResponseDto getUserById(@RequestHeader("userId") Long userId);
  @GetMapping("/product/{userId}")
  public ProductResponseDto existsByUserId(@PathVariable("userId") Long userId);

}
