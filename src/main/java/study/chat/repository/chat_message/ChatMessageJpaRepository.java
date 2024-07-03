package study.chat.repository.chat_message;

import org.springframework.data.jpa.repository.JpaRepository;
import study.chat.entity.ChatMessage;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessage, Long> {
}
