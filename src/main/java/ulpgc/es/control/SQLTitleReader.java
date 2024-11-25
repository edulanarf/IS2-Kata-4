package ulpgc.es.control;

import ulpgc.es.model.Title;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Iterator;

public class SQLTitleReader implements TitleReader{
    private final Connection connection;
    private final PreparedStatement selectStatement;

    public SQLTitleReader(File dbFile) throws IOException {
        try {
            this.connection = openConnection(dbFile);
            this.selectStatement = connection.prepareStatement("SELECT * FROM titles");
            selectStatement.execute();
        } catch (SQLException e){
            throw new IOException(e);
        }
    }

    @Override
    public Iterator<Title> read() throws IOException {
        return new Iterator<>() {
            final ResultSet resultSet = executeQuery();
            @Override
            public boolean hasNext() {
                try{
                    return resultSet.next();
                } catch (SQLException e){
                    return false;
                }
            }

            @Override
            public Title next() {
                try{
                    return new Title(resultSet.getString(1),
                        Title.TitleType.valueOf(resultSet.getString(2)),
                        resultSet.getString(3));
                } catch (SQLException e){
                    return null;
                }
            }
        };
    }

    private ResultSet executeQuery() throws IOException {
        try{
            return selectStatement.executeQuery();
        } catch (SQLException e){
            throw new IOException(e);
        }
    }

    private Connection openConnection(File file) throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
    }
}
