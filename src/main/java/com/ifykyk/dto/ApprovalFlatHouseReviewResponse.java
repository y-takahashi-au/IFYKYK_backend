package com.ifykyk.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApprovalFlatHouseReviewResponse implements Serializable {

  private List<FlatHouseReviewDto> flatHouseReviewDtoList;
}
