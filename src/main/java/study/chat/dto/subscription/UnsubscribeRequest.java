package study.chat.dto.subscription;

public record UnsubscribeRequest(
        long roomId,
        long memberId
) {
}
