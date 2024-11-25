package ulpgc.es.control;

import ulpgc.es.model.Title;

import java.io.File;
import java.io.IOException;
import java.sql.*;



public class SQLiteTitleWriter implements TitleWriter{
    private final Connection connection;
    private static final String createTable = """
            CREATE TABLE IF NOT EXISTS titles (
                id TEXT PRIMARY KEY,
                type TEXT NOT NULL,
                primaryTitle TEXT NOT NULL,
                originalTitle TEXT NOT NULL,
                genres TEXT)
            """;

    private final String insertSQL = "INSERT INTO titles(id, type, primaryTitle, originalTitle, genres) VALUES(?,?,?,?,?,?)";
    private PreparedStatement insertStatement;

    public SQLiteTitleWriter(File file) throws IOException {
        this.connection = openConnection(file);
        preparedDatabase();
    }

    private Connection openConnection(File file) throws IOException {
        try{
            return DriverManager.getConnection("jdbc:sqlite:"+ file.getAbsolutePath());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    private void preparedDatabase() throws IOException {
        try{
            Statement statement = connection.createStatement();
            statement.execute(createTable);
            connection.setAutoCommit(false);
            insertStatement = connection.prepareStatement(this.insertSQL);
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void write(Title title) throws IOException {
        try{
            insertStatement.setString(1, title.id());
            insertStatement.setString(2, title.titleType().name());
            insertStatement.setString(3, title.primaryTitle());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
