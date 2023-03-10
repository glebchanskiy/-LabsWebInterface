package org.glebchanskiy.labTests.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Answer {
    private String content;

    public Answer() {}

    public Answer(String content) {
        this.content = content;
    }
}
