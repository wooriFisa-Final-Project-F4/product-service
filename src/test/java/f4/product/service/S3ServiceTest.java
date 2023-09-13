package f4.product.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import f4.product.global.service.S3Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class S3ServiceTest {

  @Mock
  private S3Service s3Service;

  @DisplayName("S3 업로드 테스트")
  @Test
  void uploadFile() {
    // given
    MultipartFile file = mock(MultipartFile.class);

    when(s3Service.uploadFile(file)).thenReturn("url1");

    // when
    when(s3Service.uploadFile(file)).thenReturn("url1");
    // then
    verify(s3Service, times(1)).uploadFile(file);
  }
}
