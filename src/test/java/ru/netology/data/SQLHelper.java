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
        String dbUrl = System.getProperty("db.url", "jdbc:mysql://localhost:3306/app");
        String user = System.getProperty("db.user", "user");
        String password = System.getProperty("db.password", "password");

        if (dbUrl == null || user == null || password == null) {
            throw new IllegalArgumentException("Database connection properties not set");
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
    public static String getPaymentId() {
        String payment_Id = null;
        var idSQL = "SELECT payment_id FROM order_entity order by created DESC;";
        try (var conn = getConnection();
             var statusStmt = conn.prepareStatement(idSQL)) {
            try (var rs = statusStmt.executeQuery()) {
                if (rs.next()) {
                    payment_Id = rs.getString("payment_id");
                }
            }
        }
        return payment_Id;
    }

    @SneakyThrows
    public static String getDebitStatus(String paymentId) {
        var status = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        var connect = getConnection();
        return runner.query(connect, status, new ScalarHandler<String>());
    }

    @SneakyThrows
    public static String getCreditStatus(String paymentId) {
        var statusSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        var conn = getConnection();
        return runner.query(conn, statusSQL, new ScalarHandler<String>());
    }
}
