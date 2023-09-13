package f4.product.domain.product.service;

import f4.product.domain.product.dto.response.AuctionTimeStatusDto;
import f4.product.domain.product.dto.response.FeignProductDto;
import java.util.List;

public interface AuctionStatusService {

  AuctionTimeStatusDto getStatus(Long Id);

  List<FeignProductDto> auctionStatusUpdateToEnd();

  void updateAuctionStatusToProgress();
}
