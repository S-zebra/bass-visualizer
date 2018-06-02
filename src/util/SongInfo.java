package util;

import java.util.HashMap;
import java.util.Iterator;

public class SongInfo {
  public static final String SONG_NAME = "name";
  public static final String ARTIST_NAME = "artist";
  public static final String ALBUM_NAME = "album";
  private HashMap<String, String> info;

  public SongInfo() {
    info = new HashMap<>();
  }

  public void setInfo(String key, String value) {
    info.put(key, value);
  }

  public boolean hasAllKeys() {
    return info.containsKey(SONG_NAME) && info.containsKey(ARTIST_NAME) && info.containsKey(ALBUM_NAME);
  }

  public String getInfo(String key) {
    return info.get(key);
  }

  protected void printList() {
    Iterator<String> it = info.keySet().iterator();
    while (it.hasNext()) {
      System.out.println(info.get(it.next()));
    }
  }
}
