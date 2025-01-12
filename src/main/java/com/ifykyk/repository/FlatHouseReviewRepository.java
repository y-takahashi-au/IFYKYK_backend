package com.ifykyk.repository;

import com.ifykyk.entity.FlatHouseReview;
import com.ifykyk.enums.AppliedStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatHouseReviewRepository extends JpaRepository<FlatHouseReview, String> {

  long countByStatus(AppliedStatus status);

  List<FlatHouseReview> findByStatus(AppliedStatus status);

}
