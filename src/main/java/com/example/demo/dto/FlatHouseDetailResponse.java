package com.example.demo.dto;

import java.util.List;
import lombok.Data;

@Data
public class FlatHouseDetailResponse {

  private String id;
  private String address;
  private List<FlatHouseReviewResponse> flatHouseReviews;
}
