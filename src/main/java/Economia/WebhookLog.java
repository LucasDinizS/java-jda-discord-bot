package Economia;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static club.minnced.discord.webhook.WebhookClient.WEBHOOK_URL;

public class WebhookLog {
    static Dotenv config = Dotenv.configure().load();
    private static final String WebhookURL = config.get("WEBHOOKURL");
    // Cliente HTTP
    private static final OkHttpClient client = new OkHttpClient();

    public static void sendLog(@NotNull String message) {
        // Criar o corpo da requisição
        String jsonPayload = "{\"content\": \"" + message + "\"}";

        RequestBody body = RequestBody.create(
                jsonPayload,
                MediaType.parse("application/json")
        );

        // Construir a requisição
        Request request = new Request.Builder()
                .url(WebhookURL)
                .post(body)
                .build();

        // Enviar a requisição
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Erro ao enviar log: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.println("Log enviado com sucesso!");
                } else {
                    System.out.println("Erro ao enviar log. Código HTTP: " + response.code());
                }
                response.close();
            }
        });
    }


}
