package ComandosBase;

import Lava_Player.Music_Manager;
import Lava_Player.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.managers.GuildManager;

import java.net.URL;


public class Musica {
    public static void play(SlashCommandInteractionEvent event){
        if (!event.getMember().getVoiceState().inAudioChannel()) {
            event.reply("Você precisa estar em um canal de voz pra eu funcionar, SEU ANIMAL").queue();
            return;
        } else {
            final AudioManager audioManager = event.getGuild().getAudioManager();
            final VoiceChannel memberChannel = event.getMember().getVoiceState().getChannel().asVoiceChannel();
            audioManager.openAudioConnection(memberChannel);
        }
        String music;
            String link = event.getOption("nomeoulink").getAsString();
            if (isURL(link)) {
                music = link;
            } else {
                String nome = event.getOption("nomeoulink").getAsString();
                music = "scsearch:" + nome;
            }
            PlayerManager.getINSTANCE().loadAndPlayer(event, music);
    }

    public static void pause(SlashCommandInteractionEvent event){
        PlayerManager.getINSTANCE().pause(event);
    }

    public static void resume(SlashCommandInteractionEvent event){
        PlayerManager.getINSTANCE().resume(event);
    }

    public static void setVolume(SlashCommandInteractionEvent event){
        int volume = event.getOption("volume").getAsInt();
        if (volume < 1 || volume > 100) {
            event.reply("O volume deve estar entre 1 e 100.").setEphemeral(true).queue();
            return;
        }
        if(event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            Music_Manager musicManager = PlayerManager.getINSTANCE().getMusicManager(event.getGuild());
            musicManager.audioPlayer.setVolume(volume);
            event.reply("O volume foi alterado para " + volume).queue();
        }else{
            event.reply("Você deve ter a permissão de adm para usar esse comando").queue();
        }
    }

    public static boolean isURL(String url){
        try {
            new URL(url).toURI();
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
