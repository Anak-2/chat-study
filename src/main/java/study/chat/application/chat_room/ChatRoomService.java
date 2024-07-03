package study.chat.application.chat_room;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.chat.dto.chat_room.ChatRoomAppendRequest;
import study.chat.dto.chat_room.ChatRoomResponse;
import study.chat.repository.chat_room.ChatRoomRepository;

import java.util.List;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomService(final ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @Transactional(readOnly = true)
    public List<ChatRoomResponse> getAll() {
        return chatRoomRepository.getAll().stream()
                .map(ChatRoomResponse::from).toList();
    }

    @Transactional
    public void append(final ChatRoomAppendRequest chatRoomAppendRequest) {
        chatRoomRepository.save(chatRoomAppendRequest.toEntity());
    }
}
