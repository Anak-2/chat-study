package study.chat.application.chat_room;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.chat.dto.chat_message.ChatJoinMessageRequest;
import study.chat.dto.chat_message.ChatLeaveMessageRequest;
import study.chat.dto.chat_room.ChatRoomAppendRequest;
import study.chat.dto.chat_room.ChatRoomLeaveRequest;
import study.chat.dto.chat_room.ChatRoomResponse;
import study.chat.entity.ChatRoom;
import study.chat.implement.chat_message.ChatMessageAppender;
import study.chat.repository.chat_room.ChatRoomRepository;

import java.util.List;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageAppender chatMessageAppender;

    public ChatRoomService(final ChatRoomRepository chatRoomRepository,
                           final ChatMessageAppender chatMessageAppender) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageAppender = chatMessageAppender;
    }

    @Transactional(readOnly = true)
    public List<ChatRoomResponse> getAll() {
        return chatRoomRepository.getAll().stream()
                .map(ChatRoomResponse::from).toList();
    }

    @Transactional
    public void append(final ChatRoomAppendRequest chatRoomAppendRequest) {
        ChatRoom chatRoom = chatRoomAppendRequest.toEntity();
        chatRoomRepository.save(chatRoom);
    }
}
