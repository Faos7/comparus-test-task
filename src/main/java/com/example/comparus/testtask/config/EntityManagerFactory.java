package com.example.comparus.testtask.config;

import com.example.comparus.testtask.properties.DataSourceConfigurationProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;

public class EntityManagerFactory {

    public static String PACKAGES_TO_SCAN  = "com.example.testtask.data.entity";
    public static String DATABASE_PLATFORM = "hibernate.dialect";

    public static EntityManager createEntityManager(DataSourceConfigurationProperties properties, Map<String, String> jpaPropertyMap) {

        String     driverClassName = handleStrategy(properties, jpaPropertyMap);
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        DataSource dataSource      = initializeHikariDataSource(properties, driverClassName);
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = initializeLocalContainerEntityManagerFactoryBean(jpaPropertyMap, dataSource, vendorAdapter);

        return localContainerEntityManagerFactoryBean.getNativeEntityManagerFactory().createEntityManager();
    }

    public static DataSource initializeHikariDataSource(DataSourceConfigurationProperties properties, String driverClassName) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getUrl());
        hikariConfig.setUsername(properties.getUser());
        hikariConfig.setPassword(properties.getPassword());
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setMaximumPoolSize(8);
        return new HikariDataSource(hikariConfig);
    }

    private static LocalContainerEntityManagerFactoryBean initializeLocalContainerEntityManagerFactoryBean(Map<String, String> jpaPropertyMap, DataSource dataSource, JpaVendorAdapter vendorAdapter) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPackagesToScan(PACKAGES_TO_SCAN);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(jpaPropertyMap);
        localContainerEntityManagerFactoryBean.afterPropertiesSet();
        return localContainerEntityManagerFactoryBean;
    }

    private static String handleStrategy(DataSourceConfigurationProperties dataSourceConfigurationProperties, Map<String, String> jpaPropertyMap) {
        if (Objects.equals(dataSourceConfigurationProperties.getStrategy(), "postgres")) {
            jpaPropertyMap.put(DATABASE_PLATFORM, "org.hibernate.dialect.PostgreSQLDialect");
            return "org.postgresql.Driver";
        } else if (Objects.equals(dataSourceConfigurationProperties.getStrategy(), "oracle")) {
            jpaPropertyMap.put(DATABASE_PLATFORM, "org.hibernate.dialect.OracleDialect");
            return "oracle.jdbc.OracleDriver";
        } else if (Objects.equals(dataSourceConfigurationProperties.getStrategy(), "mysql")) {
            jpaPropertyMap.put(DATABASE_PLATFORM, "org.hibernate.dialect.MySQLDialect");
            return "com.mysql.cj.jdbc.Driver";
        } else if (Objects.equals(dataSourceConfigurationProperties.getStrategy(), "h2")) {
            jpaPropertyMap.put(DATABASE_PLATFORM, "org.hibernate.dialect.H2Dialect");
            return "org.h2.Driver";
        }
//        TODO: possibly add other dialects
        return null;
    }
}
