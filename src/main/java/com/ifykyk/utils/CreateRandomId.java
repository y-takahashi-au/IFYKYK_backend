package com.ifykyk.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.math3.random.RandomDataGenerator;

@UtilityClass
public class CreateRandomId {

  public String randomId() {
    return new RandomDataGenerator().nextHexString(8);
  }
}