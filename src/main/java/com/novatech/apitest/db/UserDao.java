package com.novatech.apitest.db;

import com.novatech.apitest.core.User;
import org.jdbi.v3.sqlobject.config.RegisterArgumentFactory;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterRowMapper(UserMapper.class)
@RegisterArgumentFactory(PasswordDigestArgumentFactory.class)
public interface UserDao {

    @SqlQuery("select * from users")
    List<User> getUsers();

    @SqlQuery("select * from users where id = :id")
    User getUserById(@Bind("id") long id);

    @SqlUpdate("insert into users values (:id, :userName, :passwordDigest)")
    boolean createUser(@BindBean final User user);

    @SqlQuery("select * from users where user_name = :userName and password = :password")
    User validateUser(@Bind("userName") final String userName, @Bind("password") final String password);

}
