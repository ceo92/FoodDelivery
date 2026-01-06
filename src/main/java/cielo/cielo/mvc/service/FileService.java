package cielo.cielo.mvc.service;

import cielo.cielo.mvc.dto.UploadFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileService {

  /**
   * API 목록
   * 1. 파일 전체경로 조회
   * 2. 원본파일명 => UUID
   * 3. UploadFile 객체로 파싱
   */

  /**
  * 스토리지 저장: UUID.jpg
  * DB 저장: UUID.jpg 원본파일명.jpg
   */

  @Value("${file.dir}")
  private String fileDir;

  public UploadFile storeFileToStorageAndGetUploadFile(MultipartFile multipartFile)
      throws IOException {
    String originalFilename = multipartFile.getOriginalFilename();
    String extension = getExtension(originalFilename);
    String storeFilename = getStoreFilename(extension);
    multipartFile.transferTo(new File(fullPath(storeFilename))); // 스토리지에 저장
    return new UploadFile(originalFilename, storeFilename); // UploadFile 반환
  }

  private String getStoreFilename(String extension) {
    return getUUID() + extension;
  }

  private String getUUID(){
    return UUID.randomUUID().toString();
  }
  private String getExtension(String originalFileName) {
    int delimiter = originalFileName.lastIndexOf(".");
    String extension = originalFileName.substring(delimiter, originalFileName.length());
    return extension;
  }

  public String fullPath(String originalFileName){
    return fileDir + originalFileName;
  }
}
