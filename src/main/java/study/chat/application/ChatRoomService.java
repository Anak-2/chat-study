package study.chat.application;

import org.springframework.stereotype.Service;
import study.chat.dto.ChatRoomRequest;
import study.chat.dto.ChatRoomResponse;
import study.chat.repository.ChatRoomRepository;

import java.util.List;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomService(final ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public List<ChatRoomResponse> getAll() {
        return chatRoomRepository.getAll().stream()
                .map(ChatRoomResponse::from).toList();
    }

    public void append(final ChatRoomRequest chatRoomRequest) {
        chatRoomRepository.save(chatRoomRequest.toEntity());
    }
}
