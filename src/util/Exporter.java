package util;

import java.nio.ByteBuffer;

public class Exporter {
  public static byte[] byteBufferToArray(ByteBuffer buf) {
    byte[] res = new byte[buf.capacity()];
    buf.rewind();
    for (int i = 0; i < res.length; i++) {
      res[i] = buf.get();
    }
    return res;
  }
}
