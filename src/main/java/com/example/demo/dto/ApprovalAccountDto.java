package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApprovalAccountDto implements Serializable {

  @NotBlank(message = "userId is mandatory")
  private String userId;

  @NotBlank(message = "givenName is mandatory")
  private String givenName;

  @NotBlank(message = "familyName is mandatory")
  private String familyName;

  @NotBlank(message = "roll is mandatory")
  private String roll;
}
