package com.example.demo.service.helper;

import com.example.demo.exception.LalinguaBussinessException;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CloudStorageHelper {

  public static String BUCKET_NAME = "lalingua_review_bucket";
  private final ImageConvertHelper imageConvertHelper;

  private Storage getStorage() {
    try (InputStream input = new ClassPathResource(
        "lalingua-review-72ca6f2d7097.json").getInputStream()) {
      return StorageOptions.newBuilder().setCredentials(ServiceAccountCredentials.fromStream(input))
          .build().getService();
    } catch (IOException e) {
      throw new LalinguaBussinessException("cloud storage not found.");
    }
  }

  public void uploadObject(String base64, String path) {
    Storage storage = this.getStorage();
    BlobId blobId = BlobId.of(BUCKET_NAME, path);
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
    Storage.BlobTargetOption precondition;
    if (storage.get(BUCKET_NAME, path) == null) {
      precondition = Storage.BlobTargetOption.doesNotExist();
    } else {
      precondition = Storage.BlobTargetOption.generationMatch(
          storage.get(BUCKET_NAME, path).getGeneration());
    }
    storage.create(blobInfo, imageConvertHelper.toJpegByte(base64), precondition);
  }


  public void deleteObject(String path) {
    Storage storage = this.getStorage();
    Blob blob = storage.get(BUCKET_NAME, path);
    BlobId idWithGeneration = blob.getBlobId();
    storage.delete(idWithGeneration);
  }

  public String downloadObjectIntoMemory(String objectName) {
    Storage storage = this.getStorage();
    return imageConvertHelper.toBase64(storage.readAllBytes(BUCKET_NAME, objectName));
  }
}

