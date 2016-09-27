package com.utraveler.dao.mysql.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Import({TestHibernateConfiguration.class})
public class TestConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        ResourceLoader resourceLoader = new DefaultResourceLoader();

        PropertySourcesPlaceholderConfigurer propertyPlaceholder = new PropertySourcesPlaceholderConfigurer();
        propertyPlaceholder.setLocations(new Resource[]{
                resourceLoader.getResource("properties/hibernate-test.properties"),
        });

        return propertyPlaceholder;
    }
}