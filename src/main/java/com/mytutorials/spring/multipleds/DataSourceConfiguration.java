package com.mytutorials.spring.multipleds;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.List;

@Configuration(proxyBeanMethods = false)
public class DataSourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.blog")
    public DataSourceProperties blogDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public HikariDataSource blogDataSource(DataSourceProperties blogDataSourceProperties) {

        return blogDataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    // Use the sql file 'blog-schema.sql' to build the schema
    @Bean
    DataSourceScriptDatabaseInitializer blogDataSourceScriptDatabaseInitializer(@Qualifier("blogDataSource") DataSource dataSource) {

        DatabaseInitializationSettings settings = new DatabaseInitializationSettings();
        settings.setSchemaLocations(List.of("classpath:blog-schema.sql"));
        settings.setMode(DatabaseInitializationMode.EMBEDDED);
        return new DataSourceScriptDatabaseInitializer(dataSource, settings);
    }

    // SUBSCRIBERS ====================================================================================================

    @Bean
    @ConfigurationProperties("app.datasource.subscribers")
    public DataSourceProperties subscribersDataSourceProperties() {
        return new DataSourceProperties();
    }

    // A different way to build a DataSource
    @Bean
    public DataSource subscriberDataSource(@Qualifier("subscribersDataSourceProperties") DataSourceProperties subscribersDataSourceProperties) {
        return DataSourceBuilder
                .create()
                .url(subscribersDataSourceProperties.getUrl())
                .username(subscribersDataSourceProperties.getUsername())
                .password(subscribersDataSourceProperties.getPassword())
                .build();
    }


    // Use the sql file 'subscribers-schema.sql' to build the schema
    @Bean
    DataSourceScriptDatabaseInitializer subscriberDataSourceScriptDatabaseInitializer(@Qualifier("subscriberDataSource") DataSource dataSource) {

        DatabaseInitializationSettings settings = new DatabaseInitializationSettings();
        settings.setSchemaLocations(List.of("classpath:subscribers-schema.sql"));
        settings.setMode(DatabaseInitializationMode.EMBEDDED);
        return new DataSourceScriptDatabaseInitializer(dataSource, settings);
    }
}
