package scoreboard.api.db;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.WebApplicationException;
import java.sql.*;

@Singleton
public class ApiDataSource {
    private final String driverClassName;
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    @Inject
    public ApiDataSource(@Named("driverClassName") String driverClassName, @Named("dbUrl") String dbUrl,
                         @Named("dbUser") String dbUser, @Named("dbPassword") String dbPassword) {
        this.driverClassName = driverClassName;
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getDbConnection() {
        System.out.println("getDbConnection dbUrl: " + dbUrl + " , dbUser: " + dbUser);
        try {
            Class.forName(driverClassName);
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Could't get DB ClassName(" + dbUrl + "): " + e);
            throw new WebApplicationException("Could't get DB ClassName(" + dbUrl + ")", e);
        }
    }

    public void closeDb(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            throw new WebApplicationException("Couldn't close Db connection (" + dbUrl + ")", e);
        }
    }
}
