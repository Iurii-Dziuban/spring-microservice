package com.iurii.microservice;

import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by iurii.koval on 09/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceStarter.class, webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"flyway.locations=filesystem:../database/h2", "server.port=9100", "spring.profiles.default=dev"})
@Category(IntegrationTest.class)
public abstract class AbstractModulIntegrationTest {

}
