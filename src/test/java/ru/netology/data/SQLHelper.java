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
        var dbUrl = System.getProperty("db.url");
        var user = System.getProperty("db.user");
        var password = System.getProperty("db.password");

        if (dbUrl == null || user == null || password == null) {
            throw new IllegalArgumentException("Database connection properties not set");
        }

        return DriverManager.getConnection(dbUrl, user, password);
    }

    @SneakyThrows
    public static void cleanDB() {
        try (var connection = getConnection()) {
            runner.execute(connection, "DELETE FROM payment_entity");
            runner.execute(connection, "DELETE FROM credit_request_entity");
        }
    }

    @SneakyThrows
    public static String getPaymentId() {
        var idSQL = "SELECT payment_id FROM order_entity ORDER BY created DESC LIMIT 1";
        try (var conn = getConnection()) {
            return runner.query(conn, idSQL, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static String getDebitStatus(String paymentId) {
        var status = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (var connect = getConnection()) {
            return runner.query(connect, status, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static String getCreditStatus(String paymentId) {
        var statusSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        try (var conn = getConnection()) {
            return runner.query(conn, statusSQL, new ScalarHandler<>());
        }
    }
}