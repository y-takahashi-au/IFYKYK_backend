package com.ifykyk.repository;

import com.ifykyk.entity.FlatHouse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatHouseRepository extends JpaRepository<FlatHouse, String> {

  @Query(value = "SELECT "
      + "house.id as id "
      + ", house.address as address "
      + ", AVG(review.star) as star "
      + ", MIN(picture.picture_path) as picture_path "
      + "FROM "
      + "flat_house AS house "
      + "INNER JOIN flat_house_review AS review "
      + "ON house.id = review.flat_house_id "
      + "AND review.status = 'APPROVED' "
      + "LEFT OUTER JOIN flat_house_review_picture AS picture "
      + "ON review.id = picture.flat_house_review_id "
      + "WHERE "
      + "review.star IS NOT NULL "
      + "GROUP BY "
      + "review.flat_house_id; "
      , nativeQuery = true)
  List<Overview> findAllOverviewsByPlace(String place);

  @Query(value = "SELECT "
      + "house.id "
      + ", house.address as address "
      + ", AVG(review.star) as star "
      + ", MIN(picture.picture_path) as picture_path "
      + "FROM "
      + "flat_house AS house "
      + "INNER JOIN flat_house_review AS review "
      + "ON house.id = review.flat_house_id "
      + "AND review.status = 'APPROVED' "
      + "LEFT OUTER JOIN flat_house_review_picture AS picture "
      + "ON review.id = picture.flat_house_review_id "
      + "WHERE "
      + "house.place LIKE '%test%' "
      + "AND review.star IS NOT NULL "
      + "GROUP BY "
      + "review.flat_house_id; "
      , nativeQuery = true)
  List<Overview> findAllOverviews();

  static interface Overview {

    String getId();

    String getAddress();

    Float getStar();

    String getPicturePath();
  }
}
