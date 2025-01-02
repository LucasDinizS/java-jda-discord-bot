package Conexao;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    static Dotenv config = Dotenv.configure().load();
    private static final String URL = config.get("CONECTIONURL");
    private static final String USUARIO = config.get("USUARIO");
    private static final String SENHA = config.get("SENHA");;

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}