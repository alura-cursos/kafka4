package br.com.alura.ecommerce.database;

import java.sql.*;

public class LocalDatabase {

    private final Connection connection;

    public LocalDatabase(String name) throws SQLException {
        String url = "jdbc:sqlite:target/" + name + ".db";
        connection = DriverManager.getConnection(url);
    }

    // yes, this is way too generic.
    // according to your database tool, avoid injection.
    public void createIfNotExists(String sql) {
        try {
            connection.createStatement().execute(sql);
        } catch (SQLException ex) {
            // be careful, the sql could be wrong, be reallllly careful
            ex.printStackTrace();
        }

    }

    public boolean update(String statement, String... params) throws SQLException {
        return prepare(statement, params).execute();
    }

    private PreparedStatement prepare(String statement, String[] params) throws SQLException {
        var preparedStatement = connection.prepareStatement(statement);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setString(i + 1, params[i]);
        }
        return preparedStatement;
    }

    public ResultSet query(String query, String... params) throws SQLException {
        return prepare(query, params).executeQuery();
    }
}
