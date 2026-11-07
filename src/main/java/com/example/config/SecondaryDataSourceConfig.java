package com.example.config;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
@Configuration
public class SecondaryDataSourceConfig {

    @Bean(name = "secondaryDataSourceProperties")
    @ConfigurationProperties("spring.secondary-datasource")
    public DataSourceProperties secondaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "secondaryDataSource")
    @ConfigurationProperties("spring.secondary-datasource.configuration")
    public HikariDataSource secondaryDataSource(
            @Qualifier("secondaryDataSourceProperties")
            DataSourceProperties properties) {

        return properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "secondaryJdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(
            @Qualifier("secondaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
