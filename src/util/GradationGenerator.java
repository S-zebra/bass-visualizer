package util;

import java.awt.Color;

public class GradationGenerator {
  float cHue, cSaturation, cBrightness;

  public GradationGenerator() {

  }

  public Color nextColor() {
    cHue += 0.00001f;
    cSaturation = 256f;
    cBrightness = 128f;
    // System.out.println("cHue:" + cHue);
    return Color.getHSBColor(cHue, cSaturation, cBrightness);
  }

  private int randBetween(int l, int h) {
    return (int) Math.round(Math.random() * (h - l + 1)) + l;
  }
}
