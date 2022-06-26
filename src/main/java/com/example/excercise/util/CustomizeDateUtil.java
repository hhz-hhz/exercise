package com.example.excercise.util;

import java.time.Instant;

public class CustomizeDateUtil {
  public static Instant getStartTimeOfDay(Instant instant) {
    return Instant.parse(instant.toString().split("T")[0] + "T00:00:00.000Z");
  }
  public static Instant getStartTimeOfDay(String instant) {
    return Instant.parse(instant + "T00:00:00.000Z");
  }
}
