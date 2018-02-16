package util;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import jouvieje.bass.Bass;
import jouvieje.bass.callbacks.RECORDPROC;
import jouvieje.bass.defines.BASS_CONFIG;
import jouvieje.bass.defines.BASS_DATA;
import jouvieje.bass.structures.HRECORD;
import jouvieje.bass.utils.BufferUtils;
import jouvieje.bass.utils.Pointer;

public class Recorder implements RECORDPROC {
  protected ByteArrayOutputStream outputBuffer;
  protected HRECORD hrecord;
  private boolean continueFlag = true;
  private AudioCallback analyzer;
  private AudioCallback player;

  /**
   * @param inDeviceId
   *          初期化したいデバイスのID。<br>
   *          <code>AudioDevicesManager.getInputDevices()</code>で一覧を取得できる。
   */
  public Recorder(int inDeviceId, AudioCallback fftDestination, AudioCallback rawDestination) {
    Bass.BASS_RecordInit(inDeviceId);
    Bass.BASS_SetConfig(BASS_CONFIG.BASS_CONFIG_REC_BUFFER, 1000 / 60);
    outputBuffer = new ByteArrayOutputStream();
    analyzer = fftDestination;
    player = rawDestination;
  }

  public void start() {
    hrecord = Bass.BASS_RecordStart(44100, 2, 0, this, null);
  }

  public void stop() {
    continueFlag = false;
    Bass.BASS_ChannelStop(hrecord.asInt());
  }

  public ByteArrayOutputStream getOutputStream() {
    return outputBuffer;
  }

  @Override
  public boolean RECORDPROC(HRECORD handle, ByteBuffer buffer, int length, Pointer user) {
    ByteBuffer afterFFTBuffer = BufferUtils.newByteBuffer(1024 * BufferUtils.SIZEOF_FLOAT * 2);
    Bass.BASS_ChannelGetData(hrecord.asInt(), afterFFTBuffer, BASS_DATA.BASS_DATA_FFT2048 | 0x40 | BASS_DATA.BASS_DATA_FFT_INDIVIDUAL);
    // Bass.BASS_ChannelGetData(hrecord.asInt(), afterFFTBuffer, 128 | 0x40);
    analyzer.dataArrived(afterFFTBuffer);
    if (player != null)
      player.dataArrived(buffer);
    return continueFlag;
  }

}
