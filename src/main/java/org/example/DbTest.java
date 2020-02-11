package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class DbTest {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DbTest(DataSource dataSource) {
        // JDBC template = wir brechen datenbank kommunikation auf ein pragmatisches
        // minim runter.
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<String> getTodos() {
        List<String> todos = new ArrayList<>();
        this.jdbcTemplate.query("select id, todo from todo", rs -> {
            todos.add(rs.getString("todo"));
        });

        return todos;
    }
}
