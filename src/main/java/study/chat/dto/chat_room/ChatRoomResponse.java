package study.chat.dto.chat_room;

import lombok.Builder;
import study.chat.entity.ChatRoom;

@Builder
public record ChatRoomResponse(
        long id,
        long ownerId,
        String name
) {

    public static ChatRoomResponse from(final ChatRoom chatRoom){
        return ChatRoomResponse.builder()
                .id(chatRoom.getId())
                .ownerId(chatRoom.getMemberId())
                .name(chatRoom.getName())
                .build();
    }
}
