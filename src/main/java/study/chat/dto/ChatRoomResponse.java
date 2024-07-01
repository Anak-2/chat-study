package study.chat.dto;

import lombok.Builder;
import study.chat.domain.ChatRoom;

@Builder
public record ChatRoomResponse(
        long id,
        long ownerId,
        String name
) {

    public static ChatRoomResponse from(final ChatRoom chatRoom){
        return ChatRoomResponse.builder()
                .id(chatRoom.getId())
                .ownerId(chatRoom.getOwnerId())
                .name(chatRoom.getName())
                .build();
    }
}
