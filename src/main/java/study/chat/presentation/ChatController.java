package study.chat.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import study.chat.application.chat_message.ChatMessageService;
import study.chat.application.subscription.SubscriptionService;
import study.chat.dto.chat_message.ChatMessageRequest;
import study.chat.dto.chat_message.SimpleChatMessage;
import study.chat.dto.chat_message.SimpleChatMessageResponse;
import study.chat.entity.ChatMessage;
import study.chat.entity.Greeting;
import study.chat.entity.HelloMessage;
import study.chat.global.api_response.CollectionApiResponse;
import study.chat.global.error.runtime_exception.BadRequestException;
import study.chat.implement.chat_message.ChatMessageAppender;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
public class ChatController {

    private final ChatMessageService chatMessageService;

    public ChatController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/message")
    public HelloMessage sendMessage(final HelloMessage message) throws Exception {
        log.info("call message");
        return message;
    }

    @MessageMapping("/greeting")
    @SendTo("/topic/greetings")
    public Greeting sendGreeting(final HelloMessage message) throws Exception {
        log.info("call greeting");
        return new Greeting("Greetings, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public SimpleChatMessageResponse sendMessage(@DestinationVariable final long roomId, final SimpleChatMessage scm) {
        // todo: 메시지 큐에 저장 + 일정 시간 이후 DB에 저장
        ChatMessageRequest chatMessageRequest = new ChatMessageRequest(scm.memberId(), roomId, scm.content());
        chatMessageService.append(chatMessageRequest);
        return SimpleChatMessageResponse.from(scm);
    }

    @ResponseBody
    @GetMapping("api/v1/chat-history/{roomId}")
    public CollectionApiResponse<SimpleChatMessageResponse> getMessages(@PathVariable final long roomId){
        return CollectionApiResponse.from(chatMessageService.getAllByRoomId(roomId));
    }
}
