package study.chat.repository.chat_message;

import org.springframework.stereotype.Repository;
import study.chat.entity.ChatMessage;

@Repository
public class ChatMessageRepository {

    private final ChatMessageJpaRepository chatMessageJpaRepository;

    public ChatMessageRepository(final ChatMessageJpaRepository chatMessageJpaRepository) {
        this.chatMessageJpaRepository = chatMessageJpaRepository;
    }

    public void save(final ChatMessage chatMessage){
        chatMessageJpaRepository.save(chatMessage);
    }
}
