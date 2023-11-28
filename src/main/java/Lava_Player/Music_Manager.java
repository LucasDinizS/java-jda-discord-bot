package Lava_Player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class Music_Manager {

    public final AudioPlayer audioPlayer;
    public final Playlist scheduler;

    private final AudioPlayerSendHandler sendHandler;

    public Music_Manager(AudioPlayerManager manager) {
       this.audioPlayer = manager.createPlayer();
       this.scheduler = new Playlist(this.audioPlayer);
       this.audioPlayer.addListener((this.scheduler));
       this.sendHandler = new AudioPlayerSendHandler(this.audioPlayer);
    }
public AudioPlayerSendHandler getSendHandler(){
        return this.sendHandler;
}

}
