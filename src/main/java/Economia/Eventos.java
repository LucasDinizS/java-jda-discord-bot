package Economia;

import Conexao.BDComando;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.sql.SQLException;

public class Eventos {
    public static void ganharDinheiro(SlashCommandInteractionEvent event) {
        String idUser = event.getMember().getId();
        try {
            if (BDComando.isNewUser(idUser)){
                event.reply("Voce ainda não está cadastrado no banco de dados").queue();
            }else {
                event.reply("Voce já está cadastrado no banco de dados").queue();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
