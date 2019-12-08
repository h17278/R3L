package hei.devweb.projetit.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.mariadb.jdbc.MariaDbDataSource;

public class DataSourceProvider {

    private static MariaDbDataSource dataSource;

    private DataSourceProvider() {
        initDataSource();
    }

    private void initDataSource() {
        Properties jdbcProperties = new Properties();
        InputStream configFileStream = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            jdbcProperties.load(configFileStream);
        } catch (IOException e) {
            throw new RuntimeException("Could not load jdbc.properties",e);
        }

        try {
            dataSource = new MariaDbDataSource();
            dataSource.setServerName(jdbcProperties.getProperty("servername"));
            dataSource.setPort(Integer.parseInt(jdbcProperties.getProperty("port")));
            dataSource.setDatabaseName(jdbcProperties.getProperty("databasename"));
            dataSource.setUser(jdbcProperties.getProperty("user"));
            dataSource.setPassword(jdbcProperties.getProperty("password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    public static DataSource getDataSource() throws SQLException {
        if (dataSource == null) {
            Properties configuration = DataSourceProvider.loadProperties();
            dataSource = new MariaDbDataSource();
            dataSource.setServerName(configuration.getProperty("server.host"));
            dataSource.setPort(Integer.parseInt(configuration.getProperty("server.port")));
            dataSource.setDatabaseName(configuration.getProperty("database.name"));
            dataSource.setUser(configuration.getProperty("database.user"));
            dataSource.setPassword(configuration.getProperty("database.password"));
        }
        return dataSource;
    }

    private static Properties loadProperties() {
        try (InputStream input = DataSourceProvider.class.getClassLoader().getResourceAsStream("jdbc.properties")) {
            if (input == null) {
                throw new IllegalStateException("Properties file not found.");
            }

            Properties configuration = new Properties();
            configuration.load(input);
            return configuration;
        } catch (IOException e) {
            throw new RuntimeException("Problem when reading the properties file.", e);
        }
    }

}
