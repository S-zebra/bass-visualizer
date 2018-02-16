package main;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import jouvieje.bass.Bass;
import jouvieje.bass.BassInit;
import util.Player;
import util.Recorder;
import util.SongInfo;

public class MainWindow extends JFrame implements WindowListener, KeyListener {
  private static final long serialVersionUID = 8679779580592718175L;

  private Recorder recorder;
  private Player player;
  private SpeAnaView speAnaView;
  private static String appName = "BASS Visualizer";

  public MainWindow() {
    Dimension windowSize = new Dimension(400, 200);
    this.setSize(windowSize);

    speAnaView = new SpeAnaView(400, 200);
    player = new Player();
    player.play();
    recorder = new Recorder(1, speAnaView, player);
    recorder.start();
    getContentPane().add(speAnaView);
    addKeyListener(this);
    setTitle(getAppName());
  }

  public static String getAppName() {
    return appName;
  }

  @Override
  public void windowActivated(WindowEvent arg0) {

  }

  @Override
  public void windowClosed(WindowEvent arg0) {
  }

  @Override
  public void windowClosing(WindowEvent arg0) {
    recorder.stop();
    Bass.BASS_RecordFree();
    Bass.BASS_Free();
  }

  @Override
  public void windowDeactivated(WindowEvent arg0) {

  }

  @Override
  public void windowDeiconified(WindowEvent arg0) {

  }

  @Override
  public void windowIconified(WindowEvent arg0) {

  }

  @Override
  public void windowOpened(WindowEvent arg0) {

  }

  @Override
  public void keyPressed(KeyEvent arg0) {
    switch (arg0.getKeyCode()) {
      case KeyEvent.VK_P:
        speAnaView.togglePause();
        break;
    }

  }

  @Override
  public void keyReleased(KeyEvent arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void keyTyped(KeyEvent arg0) {
    // TODO Auto-generated method stub

  }

}

class Loader {
  public static void main(String[] args) {
    BassInit.loadLibraries();
    Bass.BASS_Init(1, 44100, 0, null, null);
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("apple.awt.application.name", MainWindow.getAppName());
    MainWindow dummyFrame = new MainWindow();
    dummyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    dummyFrame.setVisible(true);
  }
}
