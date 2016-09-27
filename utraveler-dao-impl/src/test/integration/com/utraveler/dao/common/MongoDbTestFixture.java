package com.utraveler.dao.common;

import java.io.IOException;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.RuntimeConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.extract.UserTempNaming;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class MongoDbTestFixture {

    private static final String LOCALHOST = "127.0.0.1";
    private static final String DB_NAME = "utraveler-test";
    private static final int MONGO_TEST_PORT = 27028;

    protected SimpleMongoDbFactory dbFactory;
    protected MongodProcess mongoProcess;
    protected MongoTemplate mongoTemplate;


    @BeforeTest
    public void initializeDB()
            throws IOException {
        RuntimeConfig config = new RuntimeConfig();
        config.setExecutableNaming(new UserTempNaming());

        MongodStarter starter = MongodStarter.getInstance(config);

        MongodExecutable mongoExecutable = starter.prepare(new MongodConfig(Version.V2_2_0, MONGO_TEST_PORT, false));
        mongoProcess = mongoExecutable.start();

        MongoClient mongoClient = new MongoClient(LOCALHOST, MONGO_TEST_PORT);

        dbFactory = new SimpleMongoDbFactory(mongoClient, DB_NAME);
        mongoTemplate = new MongoTemplate(dbFactory);
    }


    @AfterTest
    public void shutdownDB()
            throws Exception {
        dbFactory.destroy();
        mongoProcess.stop();
    }

}
