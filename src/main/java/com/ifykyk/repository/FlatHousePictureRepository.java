package com.ifykyk.repository;

import com.ifykyk.entity.FlatHousePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatHousePictureRepository extends JpaRepository<FlatHousePicture, String> {

  void deleteByFlatHouseReviewId(String flatHouseReviewId);

}
