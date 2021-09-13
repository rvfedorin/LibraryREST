package org.frv.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author Roman V. Fedorin
 */
@Configuration
@PropertySource("classpath:application.properties")
public class RootConfig {

    @Bean
    public Validator validator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    @Profile("prod")
    @Bean
    public DataSource dataSourceProd() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://172.17.0.2:3306/library?createDatabaseIfNotExist=true");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");
        return dataSource;
    }

    @Profile("dev_local")
    @Bean
    public DataSource dataSourceLocal() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/library?createDatabaseIfNotExist=true");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");
        return dataSource;
    }

//    @Profile("dev_compose")
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
////        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/library?createDatabaseIfNotExist=true");
//        dataSource.setUrl("jdbc:mysql://mysqldb:3306/library?createDatabaseIfNotExist=true");
//        dataSource.setUsername("admin");
//        dataSource.setPassword("admin");
//        return dataSource;
//    }

    @Profile("dev_compose")
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://db:5432/library");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");
        return dataSource;
    }

    @Profile({"prod", "dev_compose", "dev_local"})
    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("liquibase-changeLog.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}
