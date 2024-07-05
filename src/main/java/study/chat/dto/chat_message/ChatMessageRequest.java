package study.chat.dto.chat_message;

import study.chat.entity.ChatMessage;
import study.chat.entity.MessageType;

public record ChatMessageRequest(
        long memberId,
        long roomId,
        String content
) {

    public ChatMessage toEntity(final String sender){
        return ChatMessage.builder()
                .type(MessageType.CHAT)
                .roomId(roomId)
                .memberId(memberId)
                .content(content)
                .sender(sender)
                .build();
    }
}
