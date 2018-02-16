package util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class ByteUtil {
  public static byte[] byteBufferToArray(ByteBuffer buffer) {
    buffer.rewind();
    byte[] res = new byte[buffer.limit()];
    for (int i = 0; i < buffer.limit(); i++) {
      res[i] = buffer.get();
    }
    return res;
  }
  
  public static byte[] byteSlice(byte[] target, int srcPos, int count) {
    if(count>=target.length) return target;
    byte[] ret=new byte[count];
    System.arraycopy(target, srcPos, ret, 0, count);
    return ret;
  }
  
  public static void extract(ByteBuffer buffer) {
    byte[] outputArray = byteBufferToArray(buffer);
    AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_FLOAT, 44100, 32, 2, 8, 44100, false);
    BufferedInputStream audioSourceStream = new BufferedInputStream(new ByteArrayInputStream(outputArray));
    AudioInputStream audioInStream = new AudioInputStream(audioSourceStream, format, outputArray.length);
    System.out.println("Writing...");
    try {
      AudioSystem.write(audioInStream, AudioFileFormat.Type.WAVE, new File("/Users/kazu/Desktop/out.wav"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Success!");
  }
}
