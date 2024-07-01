package study.chat.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import study.chat.global.annotation.Association;

@Getter
@Entity
public class ChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private long id;
    @Association @Column(name = "owner_id")
    private long ownerId;
    private String name;

    protected ChatRoom(){}

    @Builder
    public ChatRoom(final long ownerId, final String name) {
        this.ownerId = ownerId;
        this.name = name;
    }
}
