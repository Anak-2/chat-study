package study.chat.dto.chat_message;

public record SimpleChatMessage(
        String content,
        long memberId
) {
}
