package LcsD.Main;

import ComandoBarra.Slash_commands;
import ComandosBase.ReadyEventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Main {
    private final Dotenv config;




    public Main() throws LoginException{
        //Inicializa o bot
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");
        JDA jdaBuilder = JDABuilder.createDefault(token)
                .setActivity(Activity.playing("Um jogo legal!"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES)
                .addEventListeners(new ReadyEventListener(), new Slash_commands())
                .build();
    }

    public static void main(String[] args) throws LoginException {
        new Main();
    }
}