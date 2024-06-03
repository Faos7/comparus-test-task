package com.example.comparus.testtask.config;

import com.example.comparus.testtask.repository.impl.MultitenantUserDao;
import com.example.comparus.testtask.properties.DataSourceConfigurationProperties;
import com.example.comparus.testtask.repository.UserDao;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Map;

@Slf4j
public class DatabaseTenancyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {


    private static final String ENTITY_MANAGER_BEAN_NAME = "entityManager_";
    private static final String REPOSIOTORY_BEAN_NAME    = "repository_";


    private final Map<String, String> jpaPropertyMap;
    private List<DataSourceConfigurationProperties> dataSourceConfigurationPropertiesList;

    public DatabaseTenancyBeanDefinitionRegistryPostProcessor(Environment environment) {
        Binder binder = Binder.get(environment);
        dataSourceConfigurationPropertiesList = binder.bind("datasources", Bindable.listOf(DataSourceConfigurationProperties.class))
                .orElseThrow(IllegalStateException::new);
        jpaPropertyMap = binder.bind("spring.jpa", Bindable.mapOf(String.class, String.class))
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        dataSourceConfigurationPropertiesList.forEach(dataSourceConfigurationProperties -> {

            log.debug("postProcessBeanDefinitionRegistry: {}", dataSourceConfigurationProperties);
            EntityManager entityManager = EntityManagerFactory.createEntityManager(dataSourceConfigurationProperties, jpaPropertyMap);

            BeanDefinitionBuilder builder       = BeanDefinitionBuilder.genericBeanDefinition(EntityManager.class, () -> entityManager);
            registry.registerBeanDefinition(ENTITY_MANAGER_BEAN_NAME + dataSourceConfigurationProperties.getName(),
                                            builder.getBeanDefinition());
            UserDao userDao    = new MultitenantUserDao(entityManager, dataSourceConfigurationProperties.getMapping(), dataSourceConfigurationProperties.getTable());
            BeanDefinitionBuilder daoBuilder = BeanDefinitionBuilder.genericBeanDefinition(UserDao.class, () -> userDao);
            registry.registerBeanDefinition(REPOSIOTORY_BEAN_NAME + dataSourceConfigurationProperties.getName(),
                                            daoBuilder.getBeanDefinition());
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinitionRegistryPostProcessor.super.postProcessBeanFactory(beanFactory);
    }



}
