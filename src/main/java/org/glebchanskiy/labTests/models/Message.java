package org.glebchanskiy.labTests.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Message {
    private String content;

    public Message() {}

    public Message(String content) {
        this.content = content;
    }
}
