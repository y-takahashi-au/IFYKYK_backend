package com.example.demo.utils;

import com.github.f4b6a3.ulid.UlidCreator;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CreateULID {

  public String UUID() {
    return UlidCreator.getMonotonicUlid().toString();
  }
}
