package f4.product.domain.product.service.feign;

import f4.product.domain.product.service.feign.dto.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceAPI {

  @GetMapping("/api/user/v1/product/{userId}")
  ProductResponseDto existsByUserId(@PathVariable("userId") Long userId);

}
