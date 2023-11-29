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
    //TODO: Apenas responder o evento de slashcommand quando todas as mensagens estiverem limpas
    public static void clearMessages(SlashCommandInteractionEvent event){
        //Certifica que apenas quem tem a permissão pode acessar esse recurso.
        if(event.getMember().hasPermission(Permission.MESSAGE_MANAGE)){
            User bot = event.getJDA().getSelfUser();
            Guild server = event.getGuild();
            Member botMembro = server.getMember(bot);
            //Verifica se o bot pode realizar a ação de apagar mensagens para tratar possíveis erros.
            if(PermissionUtil.checkPermission(botMembro,Permission.MESSAGE_MANAGE)){
                int quantidadeMensagens = event.getOption("quantidade").getAsInt();
                //Tratamento de erros para não pegar 0 mensagens passadas ou mais que 100, que é o limite.
                if(quantidadeMensagens > 100 || quantidadeMensagens < 1 ){
                    event.reply("Apenas se pode apagar entre 1 a 100 mensagens por vez").setEphemeral(true).queue();
                }else {
                    TextChannel canal = event.getChannel().asTextChannel();
                    //Armazena uma lista com as últimas mensagens do canal
                    List<Message> mensagens = canal.getHistory().retrievePast(quantidadeMensagens).complete();
                    //Apaga as mensagens e dá o retorno ao usuário
                    canal.purgeMessages(mensagens);
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
