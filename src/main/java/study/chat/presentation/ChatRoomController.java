package study.chat.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.chat.application.ChatRoomService;
import study.chat.dto.ChatRoomRequest;
import study.chat.dto.ChatRoomResponse;
import study.chat.global.api_response.CollectionApiResponse;

@RequestMapping("/api/v1")
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    public ChatRoomController(final ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @GetMapping("/chat-rooms")
    public CollectionApiResponse<ChatRoomResponse> findAllChatRoom(){
        return CollectionApiResponse.from(chatRoomService.getAll());
    }

    @PostMapping("/chat-rooms")
    public void appendChatRoom(final ChatRoomRequest chatRoomRequest){
        chatRoomService.append(chatRoomRequest);
    }
}
