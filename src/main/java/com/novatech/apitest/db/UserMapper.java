package com.novatech.apitest.db;

import com.novatech.apitest.auth.PasswordDigest;
import com.novatech.apitest.core.User;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User map(ResultSet rs, StatementContext ctx) throws SQLException {
        int id = rs.getInt("ID");
        String userName = rs.getString("USER_NAME");
        String digest = rs.getString("PASSWORD");

        return new User(id, userName, PasswordDigest.fromDigest(digest));
    }
}
