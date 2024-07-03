package study.chat.entity;

import lombok.Getter;

@Getter
public class HelloMessage {

    private String name;

    public HelloMessage() {
    }

    public HelloMessage(final String name) {
        this.name = name;
    }
}