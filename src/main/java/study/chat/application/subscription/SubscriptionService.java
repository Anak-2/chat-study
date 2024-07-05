package study.chat.application.subscription;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.chat.application.chat_message.ChatMessageService;
import study.chat.dto.chat_message.ChatJoinMessageRequest;
import study.chat.dto.chat_message.ChatLeaveMessageRequest;
import study.chat.dto.subscription.UnsubscribeRequest;
import study.chat.dto.subscription.SubscriptionRequest;
import study.chat.global.error.runtime_exception.BadRequestException;
import study.chat.implement.chat_message.ChatMessageAppender;
import study.chat.repository.chat_room.ChatRoomRepository;
import study.chat.repository.subscription.SubscriptionRepository;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final ChatMessageAppender chatMessageAppender;

    public SubscriptionService(final SubscriptionRepository subscriptionRepository,
                               final ChatMessageAppender chatMessageAppender) {
        this.subscriptionRepository = subscriptionRepository;
        this.chatMessageAppender = chatMessageAppender;
    }

    @Transactional
    public void subscribe(final SubscriptionRequest subscriptionRequest){
        final long roomId = subscriptionRequest.roomId();
        final long memberId = subscriptionRequest.memberId();
        if(isSubscribed(memberId, roomId)){
            throw new BadRequestException("이미 구독한 채팅방");
        }
        subscriptionRepository.save(subscriptionRequest.toEntity());
        chatMessageAppender.appendJoinChat(ChatJoinMessageRequest.from(subscriptionRequest));
    }

    @Transactional
    public void unsubscribe(final UnsubscribeRequest unsubscribeRequest){
        final long roomId = unsubscribeRequest.roomId();
        final long memberId = unsubscribeRequest.memberId();
        subscriptionRepository.deleteByMemberIdAndRoomId(memberId, roomId);
        chatMessageAppender.appendLeaveChat(ChatLeaveMessageRequest.from(unsubscribeRequest));
    }

    @Transactional(readOnly = true)
    public boolean isSubscribed(final long memberId, final long roomId){
        return subscriptionRepository.existsByMemberIdAndRoomId(memberId, roomId);
    }
}
