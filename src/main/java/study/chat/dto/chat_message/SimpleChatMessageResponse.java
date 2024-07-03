package study.chat.dto.chat_message;

public record SimpleChatMessageResponse(
        long memberId,
        String content
) {

    public static SimpleChatMessageResponse from(final SimpleChatMessage simpleChatMessage){
        return new SimpleChatMessageResponse(simpleChatMessage.memberId(), simpleChatMessage.content());
    }
}
