package spotify;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import util.InfoCrawlCallback;
import util.SongInfo;

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
        ProcessBuilder builder = new ProcessBuilder("/usr/bin/osascript", "-e", "tell application \"Spotify\" to " + field + " of current track");
//        System.out.println("\'tell application \"Spotify\" to " + field + " of current track\'");
        builder.redirectErrorStream(true);
        Process proc = builder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        proc.waitFor();
//        System.out.println(proc.exitValue());
        while (reader.ready()) {
          String line = reader.readLine();
          System.out.println(line);
          info.setInfo(field, line);
        }
        callback.CrawlDone(info);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
