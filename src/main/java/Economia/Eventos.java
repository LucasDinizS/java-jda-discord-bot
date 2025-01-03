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
                int retorno = BDComando.ganharDinheiro(idUser,ganho);
                if (retorno == -1){
                    event.reply("Parabéns, você ganhou "+ganho+" Super Coins e só podera trabalhar de novo daqui a 10 minutos").queue();
                }else {
                    event.reply("Você só poderá trabalhar de novo daqui a "+retorno+" minutos").queue();
                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
