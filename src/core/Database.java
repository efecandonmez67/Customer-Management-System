package core;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    private static Database instance = null;
    private Connection connection = null;

    private String DB_URL;
    private String DB_USERNAME;
    private String DB_PASSWORD;

    private Database() {
        loadConfig();
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        Properties props = new Properties();
        try {
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);
            this.DB_URL = props.getProperty("db.url");
            this.DB_USERNAME = props.getProperty("db.username");
            this.DB_PASSWORD = props.getProperty("db.password");
        } catch (IOException e) {
            System.out.println("Veritabani yapilandirma dosyasi okunamadi.");
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        return connection;
    }

    public static Connection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new Database();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance.getConnection();
    }
}
