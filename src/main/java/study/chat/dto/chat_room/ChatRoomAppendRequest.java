package study.chat.dto.chat_room;

import study.chat.entity.ChatRoom;

public record ChatRoomAppendRequest(
        long memberId,
        String name
) {

    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .name(this.name)
                .memberId(this.memberId)
                .build();
    }
}
