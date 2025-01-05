package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class FlatHouseReviewResponse {

  private String id;
  private int rent;
  private LocalDate moveIn;
  private LocalDate moveOut;
  private int star;
  private String review;
  private String accountId;
  private String accountName;
  private List<String> pictures;
}
