package com.novatech.apitest.db;

import com.novatech.apitest.auth.PasswordDigest;
import com.novatech.apitest.core.CreateUserRequest;
import com.novatech.apitest.core.User;
import org.jdbi.v3.sqlobject.config.RegisterArgumentFactory;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterRowMapper(UserMapper.class)
@RegisterArgumentFactory(PasswordDigestArgumentFactory.class)
public interface UserDao {

    @SqlQuery("select * from users")
    List<User> getUsers();

    @SqlQuery("select * from users where id = :id")
    Optional<User> getUserById(@Bind("id") long id);

    @SqlQuery("select * from users where user_name = :userName")
    Optional<User> getUserByUserName(@Bind("userName") final String userName);

    @SqlUpdate("insert into users (user_name, password) values (:userName, :passwordDigest)")
    @GetGeneratedKeys
    int createUser(@Bind("userName") final String userName, @Bind("passwordDigest") final PasswordDigest passwordDigest);

}
