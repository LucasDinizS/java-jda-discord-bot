package ComandosBase;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.Random;

public class Roleplay {
    public static void abraco(SlashCommandInteractionEvent event){
        Random rand = new Random();
        //Armazena os dois membros, o que deu o comando e o quer recebe o abraço, em um array
        String[] membros = {
                event.getUser().getName(),
                //TODO Make a way to work with mentions
                event.getOption("amigo").getAsString()
        };
        //Um array com todos os gifs que podem aparecer na mensagem
        String[] gifs = {
                "https://media.tenor.com/1fIciLLNlSQAAAAC/hug.gif",
                "https://media.tenor.com/RN7jCU8o7eAAAAAC/laverne-and.gif",
                "https://media.tenor.com/gMDaiGvsdZ0AAAAd/reencuentros-reencuentro.gif",
        };

        //Cria o Embed builder para poder realizar alterações na mensagem antes de enviar
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.GREEN);
        builder.addField(membros[0]+" deu um abraço em "+membros[1],"",false);

        //Escolhe um gif aleatório para a mensagem
        builder.setImage(gifs[rand.nextInt(gifs.length)]);
        MessageEmbed message = builder.build();
        event.reply("").addEmbeds(message).queue();
    }
}
