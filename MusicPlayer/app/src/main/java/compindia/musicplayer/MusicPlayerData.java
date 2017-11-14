package compindia.musicplayer;

/**
 * Created by compindi on 01-11-2017.
 */

public class MusicPlayerData {
    int songImage;
    int songName;
    int singerName;
    int song;
    private  boolean isplaying;

    public void setIsplaying(boolean isplaying)
    {
        this.isplaying = isplaying;
    }

    public MusicPlayerData(int songImage, int songName, int singerName, int song, boolean isPlaying) {
        this.songImage = songImage;

        this.songName = songName;
        this.singerName = singerName;
        this.song = song;
        this.isplaying=isPlaying;
    }
    public int getSongImage()
    {
        return songImage;
    }
    public int getSongName()
    {
        return songName;
    }
    public  int getSingerName()
    {
        return singerName;
    }
    public int getSong()
    {
        return song;
    }

    public boolean isplaying() {
        return isplaying;
    }
}
