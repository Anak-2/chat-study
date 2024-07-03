package study.chat.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.chat.application.chat_room.ChatRoomService;
import study.chat.application.subscription.SubscriptionService;
import study.chat.dto.chat_room.ChatRoomAppendRequest;
import study.chat.dto.chat_room.ChatRoomResponse;
import study.chat.dto.subscription.SubscriptionRequest;
import study.chat.dto.subscription.UnsubscribeRequest;
import study.chat.global.api_response.CollectionApiResponse;

@RequestMapping("/api/v1")
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final SubscriptionService subscriptionService;

    public ChatRoomController(final ChatRoomService chatRoomService,
                              final SubscriptionService subscriptionService) {
        this.chatRoomService = chatRoomService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/chat-rooms")
    public CollectionApiResponse<ChatRoomResponse> findAllChatRoom(){
        return CollectionApiResponse.from(chatRoomService.getAll());
    }

    @PostMapping("/chat-rooms")
    public void appendChatRoom(@RequestBody final ChatRoomAppendRequest chatRoomAppendRequest){
        chatRoomService.append(chatRoomAppendRequest);
    }

    @GetMapping("/check-subscribe")
    public ResponseEntity<Boolean> checkSubscribe(@RequestParam final long memberId, @RequestParam final long roomId){
        return ResponseEntity.ok(subscriptionService.isSubscribed(memberId, roomId));
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestBody final SubscriptionRequest subscriptionRequest) {
        subscriptionService.subscribe(subscriptionRequest);
        return ResponseEntity.ok("채팅방 입장 완료.");
    }

    @DeleteMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestBody final UnsubscribeRequest unsubscribeRequest) {
        subscriptionService.unsubscribe(unsubscribeRequest);
        return ResponseEntity.ok("채팅방 탈퇴 완료.");
    }
}
