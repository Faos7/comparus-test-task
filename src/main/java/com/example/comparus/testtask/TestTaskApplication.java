package com.example.comparus.testtask;

import com.example.comparus.testtask.config.DatabaseTenancyBeanDefinitionRegistryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;



@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class TestTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestTaskApplication.class, args);
    }

    @Bean
    public static DatabaseTenancyBeanDefinitionRegistryPostProcessor beanDefinitionRegistrar(ConfigurableEnvironment environment) {
        return new DatabaseTenancyBeanDefinitionRegistryPostProcessor(environment);
    }

}
