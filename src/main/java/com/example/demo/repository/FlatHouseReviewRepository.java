package com.example.demo.repository;

import com.example.demo.entity.FlatHouseReview;
import com.example.demo.enums.AppliedStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatHouseReviewRepository extends JpaRepository<FlatHouseReview, String> {

  long countByStatus(AppliedStatus status);

  List<FlatHouseReview> findByStatus(AppliedStatus status);

}
