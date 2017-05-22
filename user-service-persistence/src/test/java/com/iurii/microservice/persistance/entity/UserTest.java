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

        assertThat(user.getBirthDate()).isEqualTo(birthDate);
        assertThat(user.getId()).isEqualTo(ID);
        assertThat(user.getName()).isEqualTo(IURII);
        assertThat(user.getMoney()).isEqualTo(MONEY);
        assertThat(user.getUpdatedTime()).isEqualTo(updatedTime);
        assertThat(user.isNew()).isTrue();
        assertThat(builder.toString()).isNotNull();
        assertThat(user.toString()).isNotNull();
    }

    @Test
    public void testEquals() {

        User user = getBuilder().build();
        User idIsNullUser = getBuilder().id(null).build();
        User updatedTimeUser = getBuilder().updatedTime(null).build();

        assertThat(user.equals(getBuilder().id("2").build())).isFalse();
        assertThat(user.equals(getBuilder().name("1").build())).isFalse();
        assertThat(user.equals(getBuilder().money(1).build())).isFalse();
        assertThat(user.equals(getBuilder().updatedTime(updatedTime.minusDays(1)).build())).isFalse();
        assertThat(user.equals(getBuilder().birthDate(birthDate.minusYears(2)).build())).isFalse();
        assertThat(user.equals(getBuilder().isNew(false).build())).isFalse();
        assertThat(user.equals(null)).isFalse();
        assertThat(user.equals(new Object())).isFalse();
        assertThat(user.equals(idIsNullUser)).isFalse();
        assertThat(user.equals(updatedTimeUser)).isFalse();
        assertThat(user.equals(user)).isTrue();
        assertThat(user.equals(getBuilder().build())).isTrue();

        assertThat(idIsNullUser.equals(user)).isFalse();
        assertThat(updatedTimeUser.equals(user)).isFalse();
        assertThat(idIsNullUser.equals(getBuilder().id(null).build())).isTrue();
        assertThat(updatedTimeUser.equals(getBuilder().updatedTime(null).build())).isTrue();
        assertThat(user.equals(new TestUser())).isFalse();
        assertThat(new TestUser().equals(user)).isFalse();

        assertThat(user.canEqual(null)).isFalse();
        assertThat(user.canEqual(new Object())).isFalse();
        assertThat(user.canEqual(user)).isTrue();
    }

    @Test
    public void testHashCode() {
        User.UserBuilder builder = getBuilder();

        User restriction = builder.build();
        User bcIsNullRestriction = builder.id(null).build();
        User currencyIsNullRestriction = builder.updatedTime(null).build();
        User notNewRestriction = builder.isNew(false).build();

        assertThat(restriction.hashCode()).isEqualTo(restriction.hashCode());
        assertThat(bcIsNullRestriction.hashCode()).isEqualTo(bcIsNullRestriction.hashCode());
        assertThat(currencyIsNullRestriction.hashCode()).isEqualTo(currencyIsNullRestriction.hashCode());
        assertThat(notNewRestriction.hashCode()).isEqualTo(notNewRestriction.hashCode());
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
