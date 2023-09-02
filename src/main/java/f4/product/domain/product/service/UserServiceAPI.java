package f4.product.domain.product.service;

import f4.product.domain.product.dto.request.FeignUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceAPI {

  @GetMapping("/user/v1/{userId}")
  FeignUserDto getUserById(@RequestHeader("userId") Long userId);


}
