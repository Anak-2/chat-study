package study.chat.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import study.chat.global.annotation.Association;

@Entity
@Getter
public class ChatMessage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private long id;
    @Association @Column(name = "chat_room_id")
    private long roomId;
    @Association @Column(name = "member_id")
    private long memberId;
    private MessageType type;
    private String content;
    private String sender;

    protected ChatMessage() {
    }

    @Builder
    public ChatMessage(final long roomId, final MessageType type, final String content, final String sender) {
        this.roomId = roomId;
        this.type = type;
        this.content = content;
        this.sender = sender;
    }
}
