package study.chat.dto.subscription;

import study.chat.entity.Subscription;

public record SubscriptionRequest(
        long roomId,
        long memberId
) {

    public Subscription toEntity(){
        return Subscription.builder()
                .roomId(roomId)
                .memberId(memberId)
                .build();
    }
}
