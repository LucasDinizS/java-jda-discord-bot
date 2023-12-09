package ComandosBase;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.internal.utils.PermissionUtil;

import java.util.List;

public class LimparChat {
    public static void clearMessages(SlashCommandInteractionEvent event){
        if(event.getMember().hasPermission(Permission.MESSAGE_MANAGE)){
            User bot = event.getJDA().getSelfUser();
            Guild server = event.getGuild();
            Member botMembro = server.getMember(bot);
            if(PermissionUtil.checkPermission(botMembro,Permission.MESSAGE_MANAGE)){
                int quantidadeMensagens = event.getOption("quantidade").getAsInt();
                //Evitando de tentar pegar mais mensagens que o limite ou menos que o mínimo
                if(quantidadeMensagens > 100 || quantidadeMensagens < 1 ){
                    event.reply("Apenas se pode apagar entre 1 a 100 mensagens por vez").setEphemeral(true).queue();
                }else {
                    TextChannel canal = event.getChannel().asTextChannel();
                    List<Message> pastMessages = canal.getHistory().retrievePast(quantidadeMensagens).complete();
                    canal.purgeMessages(pastMessages);
                    event.reply("Canal Limpo por " + event.getUser().getName()).queue();
                }
            }else{
                event.reply("O bot não possui a permissão de Gerenciar Mensagens para poder apagá-las").setEphemeral(true).queue();
            }
        }else {
            event.reply("É preciso ter a permissão de Gerenciar Mensagens para poder usar o comando").setEphemeral(true).queue();
        }

    }
}
