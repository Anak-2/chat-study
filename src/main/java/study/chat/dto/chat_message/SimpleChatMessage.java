package study.chat.dto.chat_message;

import lombok.Builder;
import study.chat.entity.ChatMessage;

@Builder
public record SimpleChatMessage(
        String content,
        long memberId
) {
}
