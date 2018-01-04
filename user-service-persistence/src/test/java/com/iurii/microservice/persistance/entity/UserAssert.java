package com.iurii.microservice.persistance.entity;

import org.assertj.core.api.AbstractAssert;

/**
 * Custom example for the specific assert (https://dzone.com/articles/writing-custom-assertj-assertions)
 */
public class UserAssert extends AbstractAssert<UserAssert, User> {

    public UserAssert(User actual) {
        super(actual, UserAssert.class);
    }

    public static UserAssert assertThat(User actual) {
        return new UserAssert(actual);
    }

    public UserAssert isNew() {
        isNotNull();
        if (!actual.isNew()) {
            failWithMessage("Expected the user to be <New=true> but was <%s>", actual.isNew());
        }
        return this;
    }

}
