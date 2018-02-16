package util;

import java.awt.List;
import java.util.ArrayList;

import jouvieje.bass.Bass;
import jouvieje.bass.structures.BASS_DEVICEINFO;

public class AudioDevicesManager {
  private AudioDevicesManager() {
  }

  /**
   * 入力デバイスの配列を返す。
   * 
   * @return 入力デバイスを表すBASS_DEVICEINFOのArrayList
   */
  public static ArrayList<BASS_DEVICEINFO> getInputDevices() {
    BASS_DEVICEINFO inDeviceInfo = BASS_DEVICEINFO.allocate();
    ArrayList<BASS_DEVICEINFO> arrayList = new ArrayList<>();
    for (int i = 0; Bass.BASS_RecordGetDeviceInfo(i, inDeviceInfo); i++) {
      arrayList.add(inDeviceInfo);
      inDeviceInfo = BASS_DEVICEINFO.allocate();
    }
    return arrayList;
  }

  /**
   * 出力デバイスの配列を返す。
   * 
   * @return 出力デバイスを表すBASS_DEVICEINFOのArrayList
   */
  public static ArrayList<BASS_DEVICEINFO> getOutputDevices() {
    BASS_DEVICEINFO inDeviceInfo = BASS_DEVICEINFO.allocate();
    ArrayList<BASS_DEVICEINFO> arrayList = new ArrayList<>();
    for (int i = 0; Bass.BASS_GetDeviceInfo(i, inDeviceInfo); i++) {
      arrayList.add(inDeviceInfo);
      inDeviceInfo = BASS_DEVICEINFO.allocate();
    }
    return arrayList;
  }

}
