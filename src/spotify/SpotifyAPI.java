package spotify;

public class SpotifyAPI {
  private static SpotifyAPI instance = new SpotifyAPI();

  private SpotifyAPI() {
  }

  public SpotifyAPI GetInstance() {
    return instance;
  }

}
