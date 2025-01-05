package com.example.demo.repository;

import com.example.demo.entity.FlatHousePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatHousePictureRepository extends JpaRepository<FlatHousePicture, String> {

  void deleteByFlatHouseReviewId(String flatHouseReviewId);

}
