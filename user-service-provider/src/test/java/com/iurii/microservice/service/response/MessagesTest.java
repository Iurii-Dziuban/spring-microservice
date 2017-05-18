package com.iurii.microservice.service.response;

import com.iurii.microservice.api.resources.user.Messages;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by iurii.dziuban on 05/04/2017.
 */
public class MessagesTest {

    @Test
    public void testEquals() {
        Messages messages = new Messages();
        Messages emptyMessages = new Messages();
        Messages nullMessages = new Messages("null");

        assertThat(messages.equals(nullMessages)).isFalse();
        assertThat(nullMessages.equals(messages)).isFalse();
        assertThat(messages.equals(new Object())).isFalse();
        assertThat(messages.equals(null)).isFalse();
        assertThat(messages.equals(messages)).isTrue();
        assertThat(messages.equals(emptyMessages)).isTrue();
    }

    @Test
    public void testHashcode() {
        Messages messages = new Messages();
        Messages emptyMessages = new Messages();

        assertThat(messages.hashCode()).isNotEqualTo(new Object().hashCode());
        assertThat(messages.hashCode()).isEqualTo(messages.hashCode());
        assertThat(messages.hashCode()).isEqualTo(emptyMessages.hashCode());
    }

    @Test
    public void testAddMessage() {
        Messages messages = new Messages();
        messages.addMessage("hello");

        assertThat(messages.getMessages()).containsExactly("hello");
        assertThat(messages.toString()).isNotNull();
    }
}
