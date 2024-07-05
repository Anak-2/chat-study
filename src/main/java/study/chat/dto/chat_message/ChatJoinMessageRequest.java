package study.chat.dto.chat_message;

import lombok.Builder;
import study.chat.dto.subscription.SubscriptionRequest;
import study.chat.entity.ChatMessage;
import study.chat.entity.ChatRoom;
import study.chat.entity.MessageType;

@Builder
public record ChatJoinMessageRequest(
        long memberId,
        long roomId
) {

    public ChatMessage toEntity(final String sender){
        return ChatMessage.builder()
                .memberId(memberId)
                .roomId(roomId)
                .type(MessageType.JOIN)
                .sender(sender)
                .content(sender + "님이 입장했습니다.")
                .build();
    }

    public static ChatJoinMessageRequest from(final SubscriptionRequest subscriptionRequest){
        return ChatJoinMessageRequest.builder()
                .memberId(subscriptionRequest.memberId())
                .roomId(subscriptionRequest.roomId())
                .build();
    }
}
