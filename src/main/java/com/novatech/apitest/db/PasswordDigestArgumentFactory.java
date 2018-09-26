package com.novatech.apitest.db;

import com.novatech.apitest.auth.PasswordDigest;
import org.jdbi.v3.core.argument.AbstractArgumentFactory;
import org.jdbi.v3.core.argument.Argument;
import org.jdbi.v3.core.config.ConfigRegistry;

import java.sql.Types;

public class PasswordDigestArgumentFactory extends AbstractArgumentFactory<PasswordDigest> {

    public PasswordDigestArgumentFactory() {
        super(Types.VARCHAR);
    }

    @Override
    protected Argument build(PasswordDigest passwordDigest, ConfigRegistry config) {
        return (position, statement, ctx) -> statement.setString(position, passwordDigest.getDigest());
    }

}
