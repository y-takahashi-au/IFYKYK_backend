package com.example.demo.service;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestService {

  public void test() {
    String PROJECT_ID = "lalingua-review";
    String BUCKET_NAME = "lalingua_review_bucket";
    String OBJECT_NAME = "flat_house/IFYKYK.png";
    try (InputStream input = new ClassPathResource(
        "lalingua-review-72ca6f2d7097.json").getInputStream()) {
      Storage storage =
          StorageOptions.newBuilder()
              .setCredentials(
                  ServiceAccountCredentials.fromStream(
                      input))
              .build()
              .getService();
      Page<Bucket> buckets = storage.list();

      Page<Blob> blobs = storage.list(BUCKET_NAME);
      for (Blob blob : blobs.iterateAll()) {
        System.out.println(blob.getName());
      }
      Blob aa = storage.get(BUCKET_NAME, OBJECT_NAME);
      System.out.println(aa.getName());

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

