package f4.product.global.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import f4.product.global.constant.CustomErrorCode;
import f4.product.global.exception.CustomException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {

  private final AmazonS3Client s3Client;

  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  /* MultipartFile 타입의 파일을 받아 S3에 업로드 하고 해당 파일의 엔드포인트를 반환함*/
  public String uploadFile(MultipartFile file) {
    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
    try {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(file.getSize());
      s3Client.putObject(
          new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
      return s3Client.getUrl(bucketName, fileName).toString();
    } catch (AmazonServiceException e) {
      throw new CustomException(CustomErrorCode.UPLOAD_FAIL_RESPONSE, e);
    } catch (IOException e) {
      throw new CustomException(CustomErrorCode.EMPTY_UPLOAD_FILE, e);
    }
  }
}
