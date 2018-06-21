package com.iurii.microservice;

import com.iurii.microservice.util.dbtablemodels.UserTable;
import com.iurii.microservice.util.sql.DbConnector;
import com.iurii.microservice.util.sql.QueryExecutor;
import org.junit.After;
import org.junit.Before;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceStarter.class, webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT,
        // TODO filesystem: is not working for spring.flyway.location
        // TODO fix spring.config.additional-location
        properties = {"spring.flyway.locations=classpath:h2",
                "spring.config.additional-location=config",
                "logging.config=config/logback-spring.xml",
                "server.port=9100", "spring.profiles.default=dev"})
@Category(IntegrationTest.class)
public abstract class AbstractModuleIntegrationTest {

    @Value("${spring.datasource.driver-class-name}")
    private String DB_DRIVER;

    @Value("${spring.datasource.url}")
    private String DB_URL;

    @Value("${spring.datasource.username}")
    private String DB_USER;

    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;

    public static final String IURII = "iurii";
    public static final LocalDate BIRTH_DATE = LocalDate.parse("1990-04-16");
    public static final ZonedDateTime UPDATED_TIME = ZonedDateTime.parse("2015-12-24T18:21:05Z");
    public static final long MONEY = 1234;

    public static final String ID = "USER_ID_001";
    public static final String DUMMY_ID = "DUMMY_ID";

    public static final String USER_RESOURCE = "users";
    public Connection connection;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        connection = DbConnector.createDbConnection(DB_DRIVER, DB_URL, DB_USER, DB_PASSWORD);
        connection.setAutoCommit(true);
        try {
            QueryExecutor.deleteUser(connection, UserTable.TABLE_NAME, UserTable.ID_COLUMN, ID);
        } catch (Exception e) { }
        QueryExecutor.insertUser(connection, UserTable.TABLE_NAME, ID, UPDATED_TIME, BIRTH_DATE, MONEY, IURII);
    }

    @After
    public void cleanUp() throws SQLException {
        QueryExecutor.deleteUser(connection, UserTable.TABLE_NAME, UserTable.ID_COLUMN, ID);
        DbConnector.closeConnection(connection);
    }

    protected void assertNameEqualsTo(Connection connection, String name, String id) {
        assertThat(QueryExecutor.selectName(connection, UserTable.TABLE_NAME, UserTable.ID_COLUMN, id))
                .as("Name is incorrect!").isEqualTo(name);
    }

    protected void assertBirthDateEqualsTo(Connection connection, String birthDate, String id) {
        assertThat(QueryExecutor.selectBirthDate(connection, UserTable.TABLE_NAME, UserTable.ID_COLUMN, id))
                .as("BirthDate is incorrect!").isEqualTo(birthDate);
    }

    protected void assertMoneyEqualsTo(Connection connection, long money, String id) {
        assertThat(QueryExecutor.selectMoney(connection, UserTable.TABLE_NAME, UserTable.ID_COLUMN, id))
                .as("Money amount is incorrect!").isEqualTo(money);
    }

    protected void assertUpdatedTimeEqualsTo(Connection connection, String updatedTime, String id) {
        assertThat(QueryExecutor.selectUpdatedTime(connection, UserTable.TABLE_NAME, UserTable.ID_COLUMN, id))
                .as("UpdatedTime is incorrect!").isEqualTo(updatedTime);
    }
}
