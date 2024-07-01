package study.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.chat.domain.ChatMessage;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessage, Long> {
}
