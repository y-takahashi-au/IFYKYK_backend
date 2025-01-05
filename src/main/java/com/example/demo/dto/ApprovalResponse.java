package com.example.demo.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApprovalResponse implements Serializable {

  private String accountId;
  private String userId;
  private String roll;

}
