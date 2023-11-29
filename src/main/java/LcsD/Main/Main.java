package LcsD.Main;

import Listeners.SlashCommands;
import Listeners.ReadyEventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Main {
    private final Dotenv config;

    public Main() throws LoginException{
        //Inicializa o dotenv para resgatar o Token
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");
        //Inicializa o bot
        JDA jdaBuilder = JDABuilder.createDefault(token)
                .setActivity(Activity.playing("Um jogo legal!"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES)
                .addEventListeners(new ReadyEventListener(), new SlashCommands())
                .build();
    }

    public static void main(String[] args) throws LoginException {
        new Main();
    }
}