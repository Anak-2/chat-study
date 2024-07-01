package study.chat.dto;

import study.chat.domain.ChatRoom;

public record ChatRoomRequest(
        long ownerId,
        String name
) {

    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .name(this.name)
                .ownerId(this.ownerId)
                .build();
    }
}
