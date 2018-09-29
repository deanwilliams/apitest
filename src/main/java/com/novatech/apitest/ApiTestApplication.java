package com.novatech.apitest;

import com.novatech.apitest.auth.ApiAuthenticator;
import com.novatech.apitest.auth.ApiUnauthorizedHandler;
import com.novatech.apitest.api.User;
import com.novatech.apitest.db.StockDao;
import com.novatech.apitest.db.UserDao;
import com.novatech.apitest.errors.JsonProcessingExceptionMapper;
import com.novatech.apitest.errors.UnableToExecuteStatementExceptionMapper;
import com.novatech.apitest.errors.WebExceptionMapper;
import com.novatech.apitest.resources.StockResource;
import com.novatech.apitest.resources.UserResource;
import com.novatech.apitest.tasks.NewUserTask;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.bundles.redirect.HttpsRedirect;
import io.dropwizard.bundles.redirect.RedirectBundle;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jdbi.v3.core.Jdbi;

public class ApiTestApplication extends Application<ApiTestConfiguration> {

    public static void main(final String[] args) throws Exception {
        //new ApiTestApplication().run(args);
        new ApiTestApplication().run(new String[]{"server", "config.yml"});
    }

    @Override
    public String getName() {
        return "ApiTest";
    }

    @Override
    public void initialize(final Bootstrap<ApiTestConfiguration> bootstrap) {
        // Setup HTTPS redirect
        bootstrap.addBundle(new RedirectBundle(new HttpsRedirect(true)));
        // Setup the favicon
        bootstrap.addBundle(new AssetsBundle("/assets/favicon.ico", "/favicon.ico", null, "favicon"));
    }

    @Override
    public void run(final ApiTestConfiguration configuration,
                    final Environment environment) {
        // Database setup
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "database");
        final UserDao userDao = jdbi.onDemand(UserDao.class);
        final StockDao stockDao = jdbi.onDemand(StockDao.class);

        // Exception Handlers
        environment.jersey().register(new WebExceptionMapper());
        environment.jersey().register(new JsonProcessingExceptionMapper());
        environment.jersey().register(new UnableToExecuteStatementExceptionMapper());

        // Authentication
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new ApiAuthenticator(userDao))
                        .setUnauthorizedHandler(new ApiUnauthorizedHandler())
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);

        // Resources
        environment.jersey().register(new UserResource(configuration.getPasswordManagement(), userDao));
        environment.jersey().register(new StockResource(stockDao));

        // Admin tasks
        environment.admin().addTask(new NewUserTask(configuration.getPasswordManagement(), userDao));

        // TODO: implement application
    }

}
