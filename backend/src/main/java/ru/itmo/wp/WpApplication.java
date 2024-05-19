package ru.itmo.wp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class WpApplication {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(WpApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception{
//        String sql = "ALTER TABLE user ADD password_sha VARCHAR(256) NULL AFTER login;";
//        jdbcTemplate.update(sql);
//    }
}
