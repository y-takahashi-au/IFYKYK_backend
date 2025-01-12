package com.ifykyk.dto;

import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class FlatHouseEditDto {

  private String id;
  private String address;
  private int rent;

  @DateTimeFormat(pattern = "DD/MM/yyyy")
  private Date moveIn;

  @DateTimeFormat(pattern = "DD/MM/yyyy")
  private Date moveOut;
  private int star;
  private String review;
  private String accountId;
  private List<String> pictures;
}
