package com.example.demo.dto;

import java.util.List;
import lombok.Data;

@Data
public class FlatHouseListResponse {

  private List<FlatHouseOverviewResponse> flatHouseOverviews;
}
