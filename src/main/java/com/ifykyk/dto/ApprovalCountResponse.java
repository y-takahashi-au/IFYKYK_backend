package com.ifykyk.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApprovalCountResponse implements Serializable {

  private long accountCount;
  private long flatHouseReviewCount;

}
