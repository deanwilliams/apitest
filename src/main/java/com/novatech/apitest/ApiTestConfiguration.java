package com.novatech.apitest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.novatech.apitest.auth.PasswordManagementConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ApiTestConfiguration extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @Valid
    @NotNull
    private PasswordManagementConfiguration passwordManagement = new PasswordManagementConfiguration();

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty
    public void setPasswordManagement(final PasswordManagementConfiguration passwordManagement) {
        this.passwordManagement = passwordManagement;
    }

    @JsonProperty
    public PasswordManagementConfiguration getPasswordManagement() {
        return passwordManagement;
    }
}
