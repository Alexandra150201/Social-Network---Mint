package socialnetwork.repository.database.db;

import socialnetwork.domain.LogInCredentials;
import socialnetwork.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class LogInDbRepository {
    private String url;
    private String username;
    private String password;

    public LogInDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public LogInCredentials findOne(String name){
        if (name == null)
            throw new IllegalArgumentException("Username must be not null!");
        try (Connection connection = getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM logins WHERE username = ?")){
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            LogInCredentials credentials = null;
            while(resultSet.next()){
                Long id = resultSet.getLong("user_id");
                String usname = resultSet.getString("username");
                String hashedPassword = resultSet.getString("hashed_password");
                credentials = new LogInCredentials(id, usname, hashedPassword);
                return credentials;
            }
            return credentials;
        } catch (SQLException e) {
            return null;
        }
    }
}
