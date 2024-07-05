package study.chat.repository.chat_message;

import org.springframework.data.jpa.repository.JpaRepository;
import study.chat.entity.ChatMessage;

import java.util.List;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> getAllByRoomId(final long roomId);
}
