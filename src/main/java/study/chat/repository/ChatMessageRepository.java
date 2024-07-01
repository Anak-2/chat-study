package study.chat.repository;

import org.springframework.stereotype.Repository;
import study.chat.domain.ChatMessage;

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
