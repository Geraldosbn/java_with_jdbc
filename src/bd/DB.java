package db;

import bd.DbException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new DbException("Erro ao obter conexão com o banco de dados: " + e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return conn;
    }

    public static void  closeConnection(){
        if (conn != null){
            try{
                conn.close();
            } catch (SQLException e) {
                throw new DbException("Erro ao fechar conexão com o banco de dados: " + e.getMessage());
            }
        }
    }

    private static Properties loadProperties() throws IOException {

        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DbException("Erro ao carregar arquivo de propriedades: " + e.getMessage());
        }
    }

    public static void closeStatement(PreparedStatement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException("Erro ao fechar statement: " + e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException("Erro ao fechar result set: " + e.getMessage());
            }
        }
    }
}
