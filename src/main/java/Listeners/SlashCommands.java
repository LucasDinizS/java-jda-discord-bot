package Listeners;

import ComandosBase.LimparChat;
import ComandosBase.ReplyQuestion;
import ComandosBase.Roleplay;
import Lava_Player.PlayerManager;
import ComandosBase.Musica;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SlashCommands extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        Random rand = new Random();
        switch (command) {
            case "coinflip":
                String result = rand.nextInt(2) == 0 ? "Cara" : "Coroa";
                event.reply(result).queue();
                break;
            case "ship":
                event.reply("O amor entre " + event.getOption("user1").getAsString() + " e " + event.getOption("user2").getAsString() + " é de " + rand.nextInt(101) + "%").queue();
                break;
            case "play" :
                Musica.play(event);
                break;
            case "skip" :
                PlayerManager.getINSTANCE().getMusicManager(event.getGuild()).scheduler.nextTrack();
                event.reply("Pulando para a próxima música da playlist...").queue();
                break;
            case "limpar" :
                LimparChat.clearMessages(event);
                break;
            case "xandao":
                ReplyQuestion.Responder(event);
                break;
            case "abraco":
                Roleplay.abraco(event);
                break;
        }
        }

        public void onGuildReady (GuildReadyEvent event){
            List<CommandData> commandData = new ArrayList<>();
            commandData.add(Commands.slash("coinflip", "Jogue cara ou coroa"));
            commandData.add(Commands.slash("ship", "shipe dois nomes ou dois usuários").addOption(OptionType.STRING, "user1", "primeiro usuário do ship", true).addOption(OptionType.STRING, "user2", "segundo usuário do ship", true));
            commandData.add(Commands.slash("play", "tocar música").addOption(OptionType.STRING,"nomeoulink","coloque o nome da música ou o link do youtube para buscar",true));
            commandData.add(Commands.slash("limpar", "limpa as mensagens pro chat (somente adms)").addOption(OptionType.INTEGER,"quantidade","quantidade de mensagens para serem limpadas",true));
            commandData.add(Commands.slash("xandao","Faça uma pergunta para o bot responder").addOption(OptionType.STRING,"pergunta","Qual a sua pergunta?",true));
            commandData.add(Commands.slash("skip","pular música"));
            commandData.add(Commands.slash("abraco","abrace um amigo").addOption(OptionType.STRING,"amigo","Adicione o amigo no qual você quer abraçar",true));
            event.getGuild().updateCommands().addCommands(commandData).queue();
        }


}
