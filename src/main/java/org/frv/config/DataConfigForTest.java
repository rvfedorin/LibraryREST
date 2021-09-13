package org.frv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * @author Roman V. Fedorin
 */

@Configuration
public class DataConfigForTest {

    @Profile("test")
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:db_for_tests/library_addresses.sql")
                .addScript("classpath:db_for_tests/library_authors.sql")
                .addScript("classpath:db_for_tests/library_book_authors.sql")
                .addScript("classpath:db_for_tests/library_books.sql")
                .addScript("classpath:db_for_tests/library_genres.sql")
                .addScript("classpath:db_for_tests/library_rent_records.sql")
                .addScript("classpath:db_for_tests/library_roles.sql")
                .addScript("classpath:db_for_tests/library_users_roles.sql")
                .addScript("classpath:db_for_tests/library_users.sql")
                .build();
    }
}
