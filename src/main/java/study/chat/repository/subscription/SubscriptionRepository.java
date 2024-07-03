package study.chat.repository.subscription;

import org.springframework.stereotype.Repository;
import study.chat.entity.Subscription;

@Repository
public class SubscriptionRepository {

    private final SubscriptionJpaRepository subscriptionJpaRepository;

    public SubscriptionRepository(SubscriptionJpaRepository subscriptionJpaRepository) {
        this.subscriptionJpaRepository = subscriptionJpaRepository;
    }

    public void save(final Subscription subscription){
        subscriptionJpaRepository.save(subscription);
    }
    
    public boolean existsByMemberIdAndRoomId(final long memberId, final long roomId){
        return subscriptionJpaRepository.existsByMemberIdAndRoomId(memberId, roomId);
    }

    public void deleteByMemberIdAndRoomId(final long memberId, final long roomId){
        subscriptionJpaRepository.deleteByMemberIdAndRoomId(memberId, roomId);
    }
}
