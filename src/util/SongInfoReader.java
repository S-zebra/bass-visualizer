package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SongInfoReader implements Runnable {
  static String[] fields = { "name", "artist", "album" };
  private InfoCrawlCallback callback;

  public SongInfoReader(InfoCrawlCallback target) {
    this.callback = target;
  }

  @Override
  public void run() {
    SongInfo info = new SongInfo();
    try {
      for (String field : fields) {
        String[] command = new String[] { "/usr/bin/osascript", "-e \'tell application \"Spotify\" to " + field + " of current track\'" };
        System.out.print(Arrays.toString(command));
        Process proc = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        System.out.println(" >" + reader.readLine());
        // info.setInfo(field, reader.readLine());
        callback.CrawlDone(info);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
