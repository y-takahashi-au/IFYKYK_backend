package com.ifykyk.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthentication implements Serializable {

  private String userId;
  private String password;
}
