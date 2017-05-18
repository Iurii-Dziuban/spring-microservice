package com.iurii.microservice.api.resources.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ToString
@ApiModel(description = "free text lines with transaction related information which can be e.g. logged by the caller")
public class Messages {

    private List<String> messages = new ArrayList<>();

    public Messages() {
    }

    public Messages(String... messages) {
        this.messages = new ArrayList<>(Arrays.asList(messages));
    }

    public Messages addMessage(String message) {
        messages.add(message);
        return this;
    }

    @ApiModelProperty(required = true, value = "")
    public List<String> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Messages messages = (Messages) o;
        return Objects.equals(this.messages, messages.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messages);
    }

}