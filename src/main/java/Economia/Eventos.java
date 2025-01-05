package Economia;

import Conexao.BDComando;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.sql.SQLException;
import java.util.Random;

public class Eventos {
    public static void ganharDinheiro(SlashCommandInteractionEvent event) {
        String idUser = event.getMember().getId();
        try {
            if (BDComando.isNewUser(idUser)){
                BDComando.createUser(idUser);
            }
            Random rand = new Random();
            int ganho = rand.nextInt(10,20);
                    event.reply(BDComando.ganharDinheiro(idUser,ganho)).queue();

        } catch (SQLException e) {
            event.reply("Ocorreu um erro ao se conectar com o Banco de Dados").queue();
        }
    }
    public static void rankingDinheiro(SlashCommandInteractionEvent event){
            event.reply(BDComando.Ranking(event.getJDA())).queue();
    }
}
