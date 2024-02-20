package first;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Util {
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/mydatabase?serverTimeZone=Asia/Jakarta");
        config.setUsername("postgres");
        config.setPassword("Handball123");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(60_000);
        config.setMaxLifetime(10*60_000);

        dataSource = new HikariDataSource(config);
    }

    public static HikariDataSource getDataSource(){
        return dataSource;
    }
}
