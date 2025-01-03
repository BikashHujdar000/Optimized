package com.example.bikash.Optimized.Configurations;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.example.bikash.Optimized.Repositories", // Package for primary database repositories
    entityManagerFactoryRef = "primaryEntityManagerFactory",
    transactionManagerRef = "primaryTransactionManager"
)
public class PrimaryDataSourceConfig {

    @Primary
    @Bean(name = "primaryDataSource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
                .url("")
                .username("")
                .password(")
                .build();
    }


    @Primary
    @Bean(name = "primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("primaryDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.bikash.Optimized.Entities") // Package for primary database entities
                .persistenceUnit("primary")
                .build();
    }

    @Primary
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("primaryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }


    // Primary JdbcTemplate
    @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("primaryDataSource") DataSource primaryDataSource) {
        return new JdbcTemplate(primaryDataSource);
    }

}
