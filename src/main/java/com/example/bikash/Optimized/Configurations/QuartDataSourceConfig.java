package com.example.bikash.Optimized.Configurations;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class QuartDataSourceConfig {

    // Quartz DataSource for MSSQL
    @Bean(name = "quartzDataSource")
    public DataSource quartzDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        hikariConfig.setJdbcUrl("");
        hikariConfig.setUsername("");
        hikariConfig.setPassword("");
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setIdleTimeout(30000);
        hikariConfig.setConnectionTimeout(20000);
        hikariConfig.setPoolName("QuartzHikariPool");
        return new HikariDataSource(hikariConfig);
    }

    // Quartz JdbcTemplate
    @Bean(name = "quartzJdbcTemplate")
    public JdbcTemplate quartzJdbcTemplate(@Qualifier("quartzDataSource") DataSource quartzDataSource) {
        return new JdbcTemplate(quartzDataSource);
    }
}
