package ca.jrvs.apps.trading;


import ca.jrvs.apps.trading.Model.config.MarketDataConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

@Configuration
// @EnableTransactionManagement
public class AppConfig {
    private Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public MarketDataConfig marketDataConfig() {
        MarketDataConfig config = new MarketDataConfig();
        config.setToken(System.getenv("TOKEN"));
        config.setHost("https://cloud.iexapis.com/v1");
        return config;
    };

    @Bean
    public DataSource dataSource() {
        String jdbcUrl =
                "jdbc:postgresql://" +
                        System.getenv("PSQL_HOST") + ":" +
                        System.getenv("PSQL_PORT") +
                        "/" +
                        System.getenv("PSQL_DB");
        String user = System.getenv("PSQL_USER");
        String password = System.getenv("PSQL_PASSWORD");

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(jdbcUrl);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        return basicDataSource;
    }
}
