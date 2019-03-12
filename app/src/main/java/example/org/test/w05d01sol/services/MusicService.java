package example.org.test.w05d01sol.services;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
    MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{
    // our media player
    private MediaPlayer mediaPlayer;
    //song's arraylist'
    private ArrayList<Song> songs;
    // current song position
    private int songPson;
    // inner binder
    private final IBinder musicBind = new MusicBinder();

    private static final int NOTIFY_ID=1;

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.stop();
        mediaPlayer.release();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onCreate() {
        //we create the service, initialize the position, and create the player
        super.onCreate();
        songPson=0;
        mediaPlayer= new MediaPlayer();
        initMusicPlayer();
    }

    public void setList (ArrayList<Song> theSongs){
        //pass the list of songs from the Activity
        songs = theSongs;
    }

    public class MusicBinder extends Binder{
        MusicService getService(){
            return MusicService.this;
        }
    }

    public void initMusicPlayer(){
        // configure the music player
        mediaPlayer.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }
    public void playSong(){
        // will play a song
        mediaPlayer.reset();
        // get song
        Song playsong = songs.get(songPson);
        // get id
        long currSong = playsong.getId();
        // set uri
        Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currSong);
        try{
            mediaPlayer.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        mediaPlayer.prepareAsync();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();

    }
    public void setSong(int songIndex){
        songPson= songIndex;
    }

    public int getPosn(){
        return mediaPlayer.getCurrentPosition();
    }

    public int getDur(){
        return mediaPlayer.getDuration();
    }

    public boolean isPng(){
        return mediaPlayer.isPlaying();
    }

    public void pausePlayer(){
        mediaPlayer.pause();
    }

    public void seek(int posn){
        mediaPlayer.seekTo(posn);
    }

    public void go(){
        mediaPlayer.start();
    }

    public void playPrev(){
        //previous
        songPson--;
        if(songPson < 0)
        {
            songPson = songs.size() - 1;
            playSong();
        }
    }
    //skip to next
    public void playNext(){
        songPson++;
        if(songPson >= songs.size()) songPson=0;
        playSong();
    }


}
