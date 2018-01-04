package com.iurii.microservice.persistance.entity;

import org.junit.Test;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    private static final String ID = "ID";
    private static final String IURII = "iurii";
    private static final String BIRTH_DATE = "1990-04-16";
    private static final String UPDATED_TIME = "2015-12-24T18:21:05Z";
    private static final long MONEY = 1234;

    private ZonedDateTime updatedTime = ZonedDateTime.parse(UPDATED_TIME);
    private LocalDate birthDate = LocalDate.parse(BIRTH_DATE);


    @Test
    public void testCreation() {
        assertThat(new User()).isNotNull();
    }

    @Test
    public void test() {
        User.UserBuilder builder = User.builder();

        User user = builder
                .name(IURII)
                .id(ID)
                .money(MONEY)
                .birthDate(birthDate)
                .updatedTime(updatedTime)
                .isNew(true)
                .build();

        user.setNew();

        assertThat(user.getBirthDate()).isEqualTo(birthDate);
        assertThat(user.getId()).isEqualTo(ID);
        assertThat(user.getName()).isEqualTo(IURII);
        assertThat(user.getMoney()).isEqualTo(MONEY);
        assertThat(user.getUpdatedTime()).isEqualTo(updatedTime);
        // example of using UserAssert
        UserAssert.assertThat(user).isNew();
        assertThat(builder.toString()).isNotNull();
        assertThat(user.toString()).isNotNull();
    }

    @Test
    public void testEquals() {

        User user = getBuilder().build();
        User idIsNull = getBuilder().id(null).build();
        User updatedTimeIsNull = getBuilder().updatedTime(null).build();
        User nameIsNull = getBuilder().name(null).build();
        User birthDateIsNull = getBuilder().birthDate(null).build();
        User notNew = getBuilder().isNew(false).build();

        assertThat(user.equals(getBuilder().id("2").build())).isFalse();
        assertThat(user.equals(getBuilder().name("1").build())).isFalse();
        assertThat(user.equals(getBuilder().money(1).build())).isFalse();
        assertThat(user.equals(getBuilder().updatedTime(updatedTime.minusDays(1)).build())).isFalse();
        assertThat(user.equals(getBuilder().birthDate(birthDate.minusYears(2)).build())).isFalse();
        assertThat(user.equals(getBuilder().isNew(false).build())).isFalse();
        assertThat(user.equals(null)).isFalse();
        assertThat(user.equals(new Object())).isFalse();
        assertThat(user.equals(idIsNull)).isFalse();
        assertThat(user.equals(updatedTimeIsNull)).isFalse();
        assertThat(user.equals(user)).isTrue();
        assertThat(user.equals(getBuilder().build())).isTrue();

        assertThat(idIsNull.equals(user)).isFalse();
        assertThat(updatedTimeIsNull.equals(user)).isFalse();
        assertThat(idIsNull.equals(getBuilder().id(null).build())).isTrue();
        assertThat(updatedTimeIsNull.equals(getBuilder().updatedTime(null).build())).isTrue();

        assertThat(nameIsNull.equals(user)).isFalse();
        assertThat(nameIsNull.equals(getBuilder().name(null).build())).isTrue();

        assertThat(birthDateIsNull.equals(user)).isFalse();
        assertThat(birthDateIsNull.equals(getBuilder().birthDate(null).build())).isTrue();

        assertThat(notNew.equals(user)).isFalse();
        assertThat(notNew.equals(getBuilder().isNew(false).build())).isTrue();

        assertThat(user.equals(new TestUser())).isFalse();
        assertThat(new TestUser().equals(user)).isFalse();

        assertThat(user.canEqual(null)).isFalse();
        assertThat(user.canEqual(new Object())).isFalse();
        assertThat(user.canEqual(user)).isTrue();
    }

    @Test
    public void testHashCode() {
        User.UserBuilder builder = getBuilder();

        User user = builder.build();
        User idIsNull = builder.id(null).build();
        User updatedTimeIsNull = builder.updatedTime(null).build();
        User nameIsNull = builder.name(null).build();
        User birthDateIsNull = builder.birthDate(null).build();
        User notNew = builder.isNew(false).build();

        assertThat(user.hashCode()).isEqualTo(user.hashCode());
        assertThat(idIsNull.hashCode()).isEqualTo(idIsNull.hashCode());
        assertThat(updatedTimeIsNull.hashCode()).isEqualTo(updatedTimeIsNull.hashCode());
        assertThat(nameIsNull.hashCode()).isEqualTo(nameIsNull.hashCode());
        assertThat(birthDateIsNull.hashCode()).isEqualTo(birthDateIsNull.hashCode());
        assertThat(notNew.hashCode()).isEqualTo(notNew.hashCode());
    }

    private User.UserBuilder getBuilder() {
        return User.builder()
                .name(IURII)
                .id(ID)
                .money(MONEY)
                .birthDate(birthDate)
                .updatedTime(updatedTime)
                .isNew(true);
    }

    class TestUser extends User {
        @Override
        protected boolean canEqual(Object other) {
            return other instanceof TestUser;
        }
    }
}
