package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class FlatHouseReviewDto {

  private String reviewId;
  private String address;
  private int rent;
  private LocalDate moveIn;
  private LocalDate moveOut;
  private int star;
  private String review;
  private List<String> pictures;
}