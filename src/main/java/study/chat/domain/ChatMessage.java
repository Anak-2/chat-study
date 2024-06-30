package study.chat.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatMessage {
    private final MessageType type;
    private final String content;
    private final String sender;

    @Builder
    public ChatMessage(final MessageType type, final String content, final String sender) {
        this.type = type;
        this.content = content;
        this.sender = sender;
    }
}
