package study.chat.application.subscription;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.chat.dto.subscription.UnsubscribeRequest;
import study.chat.dto.subscription.SubscriptionRequest;
import study.chat.global.error.runtime_exception.BadRequestException;
import study.chat.repository.chat_room.ChatRoomRepository;
import study.chat.repository.subscription.SubscriptionRepository;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final ChatRoomRepository chatRoomRepository;

    public SubscriptionService(final SubscriptionRepository subscriptionRepository,
                               final ChatRoomRepository chatRoomRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    @Transactional
    public void subscribe(final SubscriptionRequest subscriptionRequest){
        final long roomId = subscriptionRequest.roomId();
        final long memberId = subscriptionRequest.memberId();
        if(isSubscribed(memberId, roomId)){
            throw new BadRequestException("이미 구독한 채팅방");
        }
        subscriptionRepository.save(subscriptionRequest.toEntity());
    }

    @Transactional
    public void unsubscribe(final UnsubscribeRequest unsubscribeRequest){
        final long roomId = unsubscribeRequest.roomId();
        final long memberId = unsubscribeRequest.memberId();
        subscriptionRepository.deleteByMemberIdAndRoomId(memberId, roomId);
    }

    @Transactional(readOnly = true)
    public boolean isSubscribed(final long memberId, final long roomId){
        return subscriptionRepository.existsByMemberIdAndRoomId(memberId, roomId);
    }
}
