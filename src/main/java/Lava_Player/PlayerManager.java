package Lava_Player;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {
    public static PlayerManager INSTANCE;
    private final Map<Long, Music_Manager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;
    public PlayerManager(){
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }
    public Music_Manager getMusicManager(Guild guild){
    return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
    final Music_Manager musicManager = new Music_Manager(this.audioPlayerManager);
    guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
    return musicManager;
    });
}

public void pause(SlashCommandInteractionEvent event){
    final Music_Manager musicManager = this.getMusicManager(event.getGuild());
    musicManager.scheduler.audioPlayer.setPaused(true);
    event.reply("Música pausada").queue();
}
public void resume(SlashCommandInteractionEvent event){
    final Music_Manager musicManager = this.getMusicManager(event.getGuild());
    musicManager.scheduler.audioPlayer.setPaused(false);
    event.reply("Música saiu do pause").queue();
}
public void loadAndPlayer(SlashCommandInteractionEvent event, String trackURL){
    final Music_Manager musicManager = this.getMusicManager(event.getGuild());
        this.audioPlayerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                musicManager.scheduler.queue(audioTrack);
                event.reply("Começando a tocar a música "+audioTrack.getInfo().title).queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                if (audioPlaylist.isSearchResult()) {
                    AudioTrack track = audioPlaylist.getTracks().get(0);
                    musicManager.scheduler.queue(track);
                    event.reply("Adicionando a música da busca: " + track.getInfo().title).queue();
                } else {
                    event.reply("Adicionando playlist: " + audioPlaylist.getName()).queue();
                    audioPlaylist.getTracks().forEach(musicManager.scheduler::queue);
                }
            }

            @Override
            public void noMatches() {
                System.out.println("No matches found for: " + trackURL);
                event.reply("Nenhuma música encontrada para: " + trackURL).queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                System.err.println("Load failed: " + e.getMessage());
                e.printStackTrace();
                event.reply("Erro ao carregar a música: " + e.getMessage()).queue();
            }
        });

    }
    public static PlayerManager getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
}
}
