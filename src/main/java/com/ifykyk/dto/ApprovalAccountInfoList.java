package com.ifykyk.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApprovalAccountInfoList implements Serializable {

  private List<AccountInfoDto> accountInfos;
}
