package org.example.config;

import io.github.jhipster.config.h2.H2ConfigurationHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public DataSource dataSource() {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("sql/create-db.sql");

        return builder.build();
    }
}
