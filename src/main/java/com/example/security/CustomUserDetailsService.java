package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final JdbcTemplate primaryJdbcTemplate;

    public CustomUserDetailsService(@Qualifier("primaryJdbcTemplate") JdbcTemplate primaryJdbcTemplate) {
        this.primaryJdbcTemplate = primaryJdbcTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        final String USER_SQL = """
            SELECT
                username,
                password_hash,
                enabled
            FROM user_account
            WHERE username = ?
            """;

        try {

            return primaryJdbcTemplate.queryForObject(
                    USER_SQL,
                    (rs, rowNum) -> User.builder()
                            .username(rs.getString("username"))
                            .password(rs.getString("password_hash"))
                            .disabled(!rs.getBoolean("enabled"))
                            .authorities(Collections.emptyList())
                            .build(),
                    username
            );

        } catch (EmptyResultDataAccessException ex) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
