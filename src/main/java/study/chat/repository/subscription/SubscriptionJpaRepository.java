package study.chat.repository.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import study.chat.entity.Subscription;

public interface SubscriptionJpaRepository extends JpaRepository<Subscription, Long> {
    boolean existsByMemberIdAndRoomId(final long memberId, final long roomId);
    void deleteByMemberIdAndRoomId(final long memberId, final long roomId);
}
