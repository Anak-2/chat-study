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
import org.springframework.web.util.HtmlUtils;
import study.chat.application.subscription.SubscriptionService;
import study.chat.dto.chat_message.SimpleChatMessage;
import study.chat.dto.chat_message.SimpleChatMessageResponse;
import study.chat.entity.ChatMessage;
import study.chat.entity.Greeting;
import study.chat.entity.HelloMessage;
import study.chat.global.error.runtime_exception.BadRequestException;

import java.security.Principal;

@Slf4j
@Controller
public class ChatController {

    private final SubscriptionService subscriptionService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(final SubscriptionService subscriptionService,
                          final SimpMessagingTemplate messagingTemplate) {
        this.subscriptionService = subscriptionService;
        this.messagingTemplate = messagingTemplate;
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
        return SimpleChatMessageResponse.from(scm);
    }
}
