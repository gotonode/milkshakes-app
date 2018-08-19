package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Database {

    private final String databaseAddress;

    public Database(String address) {
        this.databaseAddress = address;
    }

    public Connection getConnection() throws SQLException {

        String databaseUrl = System.getenv("JDBC_DATABASE_URL");

        if (databaseUrl != null && databaseUrl.length() > 0) {
            return DriverManager.getConnection(databaseUrl);
        } else {
            return DriverManager.getConnection(databaseAddress);
        }
    }

    public void delete(String sql, int key) throws SQLException {
        delete(sql, new int[]{key});
    }

    public void delete(String sql, int[] keys) throws SQLException {

        // TODO: Should this method reside here? Or even exist?
        try (Connection connection = getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < keys.length; i++) {
                preparedStatement.setInt(i + 1, keys[i]);
            }

            preparedStatement.executeUpdate();

            preparedStatement.close();
        }
    }

    public void executeTransaction(List<String> sql) throws SQLException {

        // TODO: Commit changes only after batch execution has finished.
        try (Connection connection = getConnection()) {

            Statement statement = connection.createStatement();

            String into = "";

            //connection.setAutoCommit(false);
            for (String s : sql) {
                if (s.trim().equals(("")) || s.trim().startsWith("/*")) {
                    continue;
                }
                into += s;

                if (s.endsWith(";")) {
                    statement.addBatch(into);
                    into = "";
                }
            }

            statement.executeBatch();

            statement.close();

            //connection.commit();
        }
    }
}
