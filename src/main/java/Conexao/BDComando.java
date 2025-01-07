package Conexao;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class BDComando {
    public static boolean isNewUser(String userID) throws SQLException {
        String sql = "SELECT 1 FROM usuarios WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID); // Substitui o parâmetro "?" pelo valor de userID
            try (ResultSet rs = stmt.executeQuery()) {
                // Retorna falso se encontrar um resultado, verdadeiro caso contrário
                return !rs.next();
            }
        }
    }
    public static void createUser(String userID) throws SQLException {
            String sql = "INSERT INTO usuarios (id, dinheiro, proximoTrabalho) VALUES (?, ?,?)";
            try (Connection conn = Conexao.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                LocalDateTime horarioAtual = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String horarioFormatado = horarioAtual.format(formatter);
                stmt.setString(1, userID);
                stmt.setInt(2, 100);
                stmt.setString(3,horarioFormatado);
                stmt.executeUpdate();
            }
    }
    public static String ganharDinheiro(String userID, int ganho) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        LocalDateTime horarioEvento = null;
        LocalDateTime horarioAtual = null;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Timestamp horarioSalvo = rs.getTimestamp("proximoTrabalho");

                    if (horarioSalvo != null) {
                        horarioEvento = horarioSalvo.toLocalDateTime();
                        horarioAtual = LocalDateTime.now();

                        if (horarioAtual.isAfter(horarioEvento)) {
                            int dinheiroAtual = rs.getInt("dinheiro");
                            int dinheiroNovo = dinheiroAtual + ganho;

                            LocalDateTime horarioComMais10Minutos = horarioAtual.plusMinutes(10);
                            DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            String horarioFormatado = horarioComMais10Minutos.format(FORMATTER);

                            String sql2 = "UPDATE usuarios SET dinheiro = ?, proximoTrabalho = ? WHERE id = ?";
                            try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                                stmt2.setInt(1, dinheiroNovo);
                                stmt2.setString(2, horarioFormatado);
                                stmt2.setString(3, userID);

                                int linhasAfetadas = stmt2.executeUpdate();
                                if (linhasAfetadas > 0) {
                                    return "Parabéns, você ganhou "+ganho+" Super Coins e agora você possui no total "+dinheiroNovo+" Super Coins";
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        if (horarioAtual == null || horarioEvento == null) {
            return "Erro ao calcular o tempo restante. Por favor, tente novamente.";
        }else {
            long minutosRestantes = ChronoUnit.MINUTES.between(horarioAtual, horarioEvento);
            return "Você só poderá trabalhar de novo daqui a "+(int) Math.ceil(minutosRestantes)+" minutos";
        }
    }
    public static String Ranking(JDA jda) {
        StringBuilder retorno = new StringBuilder();
        String sql = "SELECT * FROM usuarios order by dinheiro desc";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                int i = 0;
                while (rs.next() && i <5) {
                    int dinheiro = rs.getInt("dinheiro");
                    String id = rs.getString("id");
                    User user = jda.retrieveUserById(id).complete();
                    String userName = user.getName().toUpperCase();
                    retorno.append(String.format("%d - %s - %d%n", i + 1, userName, dinheiro));
                    i++;
                }
                return "Aqui está os 5 maiores do ranking:\n"+ retorno;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
