package com.example.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class PrimaryDataSourceConfig {

    @Bean(name = "primaryDataSourceProperties")
    @Primary
    @ConfigurationProperties("spring.primary-datasource")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "primaryDataSource")
    @Primary
    @ConfigurationProperties("spring.primary-datasource.configuration")
    public HikariDataSource primaryDataSource(@Qualifier("primaryDataSourceProperties")
            DataSourceProperties properties) {

        return properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "primaryJdbcTemplate")
    @Primary
    public JdbcTemplate primaryJdbcTemplate(
            @Qualifier("primaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
