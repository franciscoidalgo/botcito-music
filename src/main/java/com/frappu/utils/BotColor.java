package com.frappu.utils;

import java.awt.Color;

public enum BotColor {
  INFO(new Color(21, 101, 192)),
  WARN(new Color(249, 168, 37)),
  ERROR(new Color(198, 40, 40)),
  OK(new Color(0, 105, 92));

  private final Color color;

  BotColor(Color color) {
    this.color = color;
  }

  public Color get() {
    return this.color;
  }
}
