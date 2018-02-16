package util;

import java.nio.ByteBuffer;

import jouvieje.bass.Bass;
import jouvieje.bass.enumerations.STREAMPROC_SPECIAL;
import jouvieje.bass.structures.HSTREAM;

public class Player implements AudioCallback {
  private HSTREAM stream;

  public Player() {
    stream = Bass.BASS_StreamCreate(44100, 2, 0, STREAMPROC_SPECIAL.STREAMPROC_PUSH, null);
    BassErrors.printError();
  }

  public void play() {
    Bass.BASS_ChannelPlay(stream.asInt(), false);
  }

  @Override
  public void dataArrived(ByteBuffer buffer) {
    Bass.BASS_StreamPutData(stream, buffer, buffer.limit());
  }

}