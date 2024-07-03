package study.chat.repository.chat_room;

import org.springframework.stereotype.Repository;
import study.chat.entity.ChatRoom;

import java.util.Comparator;
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
        return chatRoomJpaRepository.findAll()
                .stream().sorted(Comparator.comparingLong(ChatRoom::getId)).toList();
    }
}
