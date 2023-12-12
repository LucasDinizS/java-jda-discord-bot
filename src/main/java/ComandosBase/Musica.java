package ComandosBase;

import Lava_Player.PlayerManager;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;

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
                String nome = "";
                    nome += event.getOption("nomeoulink").getAsString();
                music = "ytsearch:" + nome + "audio";
            }
            PlayerManager.getINSTANCE().loadAndPlayer(event, music);
    }

    public static void pause(SlashCommandInteractionEvent event){
        PlayerManager.getINSTANCE().pause(event);
    }
    public static void resume(SlashCommandInteractionEvent event){
        PlayerManager.getINSTANCE().resume(event);
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
