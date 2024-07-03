package study.chat.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import study.chat.application.subscription.SubscriptionService;
import study.chat.dto.chat_message.SimpleChatMessage;
import study.chat.entity.ChatMessage;
import study.chat.entity.Greeting;
import study.chat.entity.HelloMessage;
import study.chat.global.error.runtime_exception.BadRequestException;

import java.security.Principal;

@Slf4j
@Controller
public class ChatController {

    private final SubscriptionService subscriptionService;

    public ChatController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
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
    public String sendMessage(@DestinationVariable final long roomId, final SimpleChatMessage scm) {
        if (!subscriptionService.isSubscribed(scm.memberId(), roomId)) {
            throw new BadRequestException("User is not subscribed to this room");
        }
        return scm.content();
    }
}
