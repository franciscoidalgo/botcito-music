package com.frappu.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class TimeUtils {

  private TimeUtils() {
  }

  public static long getEpochSeconds() {
    return LocalDateTime
        .now()
        .toEpochSecond(ZoneOffset.UTC);
  }

}
