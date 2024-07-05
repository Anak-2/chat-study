package study.chat.dto.chat_message;

import lombok.Builder;
import study.chat.dto.chat_room.ChatRoomLeaveRequest;
import study.chat.dto.subscription.UnsubscribeRequest;
import study.chat.entity.ChatMessage;
import study.chat.entity.MessageType;

@Builder
public record ChatLeaveMessageRequest(
        long memberId,
        long roomId
) {

    public ChatMessage toEntity(final String sender) {
        return ChatMessage.builder()
                .memberId(memberId)
                .roomId(roomId)
                .type(MessageType.LEAVE)
                .sender(sender)
                .content(sender + "님이 퇴장했습니다.")
                .build();
    }

    public static ChatLeaveMessageRequest from(final UnsubscribeRequest unsubscribeRequest){
        return ChatLeaveMessageRequest.builder()
                .memberId(unsubscribeRequest.memberId())
                .roomId(unsubscribeRequest.roomId())
                .build();
    }
}