package util;

import java.nio.ByteBuffer;

public interface AudioCallback {
  public void dataArrived(ByteBuffer buffer);
}
