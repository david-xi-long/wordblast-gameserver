package io.wordblast.gameserver.modules.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings.Builder;
import io.wordblast.gameserver.modules.miscellaneous.EnvProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * The mongo database configuration class.
 */
@Configuration
@EnableMongoRepositories
public class MongoConfig extends AbstractReactiveMongoConfiguration {
    /**
     * The name of the database which stores the game server documents.
     */
    private static final String DATABASE_NAME = "wordblast-io";
    /**
     * The database connection string for the development environment.
     */
    private static final String CONNECTION_STRING_DEV =
        "mongodb://localhost:27017/" + DATABASE_NAME;
    /**
     * The database connection string for the production environment.
     */
    private static final String CONNECTION_STRING_PROD =
        "mongodb+srv://%s:%s@wordblast-cluster.lh0xy.mongodb.net/" + DATABASE_NAME;

    @Autowired
    EnvProperties envProperties;
    @Autowired
    MongoProperties mongoProperties;

    @Override
    protected String getDatabaseName() {
        return DATABASE_NAME;
    }

    @Override
    protected void configureClientSettings(Builder builder) {
        String env = envProperties.getEnv();

        String connectionString = env.equals("development")
            ? CONNECTION_STRING_DEV
            : String.format(CONNECTION_STRING_PROD,
                mongoProperties.getUsername(),
                mongoProperties.getPassword());

        builder.applyConnectionString(new ConnectionString(connectionString));
    }
}
