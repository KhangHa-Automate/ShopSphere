package khangha.com.shopsphere_server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseHealthCheck implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🔍 Checking database connection...");
        
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("✅ Database connection successful!");
            System.out.println("📊 Database: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("🔢 Version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("👤 User: " + connection.getMetaData().getUserName());
            System.out.println("🔗 URL: " + connection.getMetaData().getURL());
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed!");
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }
}
