package Conexao;

import java.sql.*;

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
            String sql = "INSERT INTO usuarios (id, dinheiro) VALUES (?, ?)";
            try (Connection conn = Conexao.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, userID);
                stmt.setInt(2, 0);
                stmt.executeUpdate();
                System.out.println("Usuário criado com sucesso!");
            }
    }

}
