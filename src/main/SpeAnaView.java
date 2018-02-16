package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JPanel;
import javax.swing.Timer;

import jouvieje.bass.utils.BufferUtils;
import util.GradationGenerator;
import util.InfoCrawlCallback;
import util.SongInfo;
import util.SongInfoReader;
import util.Visualizer;
import util.AudioCallback;

public class SpeAnaView extends JPanel implements AudioCallback, ComponentListener, ActionListener, InfoCrawlCallback {
  private static final long serialVersionUID = 4837783547828595605L;
  private GradationGenerator gradationGenerator;
  private FloatBuffer data;
  private Dimension size;
  private boolean pause = false;
  protected Color color;

  private int startBand = 2, bands = 7, distance = 5, barWidth = 25;
  private int frames = 0, fps = 0;
  private final int marginX = 20, marginY = 20;
  private SongInfo songInfo;
  private ExecutorService execService = Executors.newCachedThreadPool();
  private Timer timer;

  public SpeAnaView(int width, int height) {
    size = new Dimension(width, height);
    this.setPreferredSize(size);
    this.setBackground(Color.black);
    this.addComponentListener(this);
    timer = new Timer(1000, this);
    timer.start();

    gradationGenerator = new GradationGenerator();
    new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          color = gradationGenerator.nextColor();
          try {
            Thread.sleep(200);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }).start();
  }

  public void drawWaveform() {
    // int[] xpoints = new int[length];
    // int[] yponts = new int[length];
    // for (int i = 0, ep = 0, c = 0; i < length; i ++, c++, ep += interval) {
    // xpoints[c] = ep;
    // yponts[c] = data[i] + topOffset;
    //
    // }
  }

  public void paint(Graphics gr) {
    gr.setColor(color);
    Graphics2D g2d = (Graphics2D) gr;
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    int b0 = startBand;
    int height = getHeight();
    distance = 5;
    for (int i = 0; i < bands; i++) {
      int b1 = (int) Math.pow(2, i * 10 / (bands - 1));
      if (b1 > 1023) {
        b1 = 1023;
      }
      if (b1 < b0) {
        b1 = b0 + 1;
      }

      int sc = 10 + b1 - b0;
      float sum = 0;

      for (; b0 < b1; b0++) {
        sum += data.get(1 + b0);
      }

      int y = (int) (Math.sqrt(sum / Math.log10(sc)) * 1.7 * height);
      if (y > height) {
        y = height;
      }
      y -= 10;

      int[] xpoints = new int[4];
      int[] ypoints = new int[4];

      xpoints[0] = distance;
      ypoints[0] = height - y;

      xpoints[1] = distance + barWidth;
      ypoints[1] = height - y;

      xpoints[2] = distance + barWidth;
      ypoints[2] = height;

      xpoints[3] = distance;
      ypoints[3] = height;

      distance += barWidth * 2;
      g2d.drawPolyline(xpoints, ypoints, 4);
      g2d.fillPolygon(xpoints, ypoints, 4);
      // System.out.println(ypoints[1]);

      frames++;
      Font font = new Font("HelveticaNeue", Font.PLAIN, 16);
      gr.setFont(font);
      gr.drawString(Integer.toString(fps) + " fps", marginX, marginY);
      gr.drawString(songInfo.getInfo(SongInfo.SONG_NAME) + " by " + songInfo.getInfo(SongInfo.ARTIST_NAME) + " on " + songInfo.getInfo(SongInfo.ALBUM_NAME), marginX, marginY + 20);
    }
  }

  public void setDimension(Dimension newDimension) {
    this.size = newDimension;
  }

  public void togglePause() {
    pause = !pause;
    System.out.println(pause);
  }

  @Override
  public void dataArrived(ByteBuffer buffer) {
    data = buffer.asFloatBuffer();
    // length = data.limit();
    if (!pause)
      repaint();
  }

  @Override
  public void componentHidden(ComponentEvent arg0) {

  }

  @Override
  public void componentMoved(ComponentEvent arg0) {

  }

  @Override
  public void componentResized(ComponentEvent e) {
    this.size = ((JPanel) e.getSource()).getSize();
  }

  @Override
  public void componentShown(ComponentEvent arg0) {

  }

  // タイマー
  @Override
  public void actionPerformed(ActionEvent arg0) {
    fps = frames;
    frames = 0;
    getSongInfoFromSpotify();
  }

  public void getSongInfoFromSpotify() {
    execService.submit(new SongInfoReader(this));
  }

  @Override
  public void CrawlDone(SongInfo info) {
   this.songInfo=info;
  }

}
