package ComandosBase;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.Random;

public class Roleplay {
    public static void abraco(SlashCommandInteractionEvent event){
        Random rand = new Random();

        //Armazena o membro que vai realizar a ação e o que vai receber em um array
        String[] membros = {
                event.getMember().getAsMention(),
                event.getOption("amigo").getAsString()
        };

        String[] gifs = {
                "https://media.tenor.com/1fIciLLNlSQAAAAC/hug.gif",
                "https://media.tenor.com/RN7jCU8o7eAAAAAC/laverne-and.gif",
                "https://media.tenor.com/gMDaiGvsdZ0AAAAd/reencuentros-reencuentro.gif",
        };

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.GREEN);
        builder.setDescription(membros[0]+" deu um abraço em "+membros[1]);

        builder.setImage(gifs[rand.nextInt(gifs.length)]);
        MessageEmbed message = builder.build();
        event.reply("").addEmbeds(message).queue();
    }
}
