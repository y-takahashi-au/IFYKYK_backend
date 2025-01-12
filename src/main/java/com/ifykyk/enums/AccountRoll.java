package com.ifykyk.enums;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public enum AccountRoll {
  ADMINISTRATOR, STUDENT, GRADUATE, GUEST;

  public static AccountRoll toEnum(String str) {
    return Arrays.stream(AccountRoll.values())
        .filter(accountRoll -> StringUtils.equals(accountRoll.toString(), str.toUpperCase()))
        .findFirst().orElseThrow(() -> new RuntimeException("AccountRoll not found."));
  }
}
