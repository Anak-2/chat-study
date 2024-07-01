package study.chat.repository;

import org.springframework.stereotype.Repository;
import study.chat.domain.ChatRoom;

import java.util.List;

@Repository
public class ChatRoomRepository {

    private final ChatRoomJpaRepository chatRoomJpaRepository;

    public ChatRoomRepository(ChatRoomJpaRepository chatRoomJpaRepository) {
        this.chatRoomJpaRepository = chatRoomJpaRepository;
    }

    public void save(final ChatRoom chatRoom){
        chatRoomJpaRepository.save(chatRoom);
    }

    public List<ChatRoom> getAll(){
        return chatRoomJpaRepository.findAll();
    }
}
