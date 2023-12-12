package ComandosBase;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Random;

public class ReplyQuestion {
    public static void Responder(SlashCommandInteractionEvent event){
        Random rand = new Random();
        String[] respostas = {
                "Eu acho que sim",
                "Sim",
                "Não",
                "Eu acho que não",
                "Eu não sei"
        };
        event.reply("\""+event.getOption("pergunta").getAsString()+"\"\n"+respostas[rand.nextInt(respostas.length)]).queue();
    }
}
