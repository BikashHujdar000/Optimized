package com.example.bikash.Optimized.QuartzConfigurations;
import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
public class QuartzConfig {

    @Autowired
    private  ApplicationContext applicationContext;

    @Autowired
    @Qualifier("quartzDataSource")
    private DataSource quartzDataSource;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("quartzDataSource") DataSource quartzDataSource) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();

        Properties quartzProperties = new Properties();
        quartzProperties.put("org.quartz.scheduler.instanceName", "MyQuartzScheduler");
        quartzProperties.put("org.quartz.scheduler.instanceId", "AUTO");
        quartzProperties.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        quartzProperties.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.MSSQLDelegate");
        quartzProperties.put("org.quartz.jobStore.dataSource", "quartzDataSource");
        quartzProperties.put("org.quartz.dataSource.quartzDataSource.provider", "hikaricp"); // Use HikariCP
        quartzProperties.put("org.quartz.dataSource.quartzDataSource.driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        quartzProperties.put("org.quartz.dataSource.quartzDataSource.URL", "");
        quartzProperties.put("org.quartz.dataSource.quartzDataSource.user", "");
        quartzProperties.put("org.quartz.dataSource.quartzDataSource.password", "");
        quartzProperties.put("org.quartz.dataSource.quartzDataSource.maxConnections", "10");
        quartzProperties.put("org.quartz.dataSource.quartzDataSource.idleTimeout", "30000");
        quartzProperties.put("org.quartz.dataSource.quartzDataSource.connectionTimeout", "20000");
        quartzProperties.put("org.quartz.dataSource.quartzDataSource.minimumIdle", "2");
        quartzProperties.put("org.quartz.dataSource.quartzDataSource.maximumPoolSize", "10");


        factory.setQuartzProperties(quartzProperties);
        factory.setDataSource(quartzDataSource);
        factory.setJobFactory(jobFactory());
        factory.setSchedulerName("QuartzScheduler");
        factory.setAutoStartup(false);
        return factory;
    }

    @Bean
    public JobFactory jobFactory() {
        SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean factory) {
        return factory.getScheduler();
    }

}


