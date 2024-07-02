package ru.netology.data;

import lombok.SneakyThrows;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class SQLHelper {

    private static final QueryRunner runner = new QueryRunner();

    @SneakyThrows
    public static Connection getConnection() {
        String dbUrl = System.getProperty("db.url");
        String user = System.getProperty("db.user");
        String password = System.getProperty("db.password");

        if (dbUrl == null || user == null || password == null) {
            throw new IllegalArgumentException("Database URL, user, or password is not set");
        }

        return DriverManager.getConnection(dbUrl, user, password);
    }

    @SneakyThrows
    public static void cleanDB() {
        var connection = getConnection();
        runner.execute(connection, "DELETE FROM payment_entity");
        runner.execute(connection, "DELETE FROM credit_request_entity");
    }

    @SneakyThrows
    public static String getDebitStatus() {
        var status = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        var connect = getConnection();
        return runner.query(connect, status, new ScalarHandler<String>());
    }

    @SneakyThrows
    public static String getCreditStatus() {
        var statusSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        var conn = getConnection();
        return runner.query(conn, statusSQL, new ScalarHandler<String>());
    }
}
