package study.chat.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import study.chat.domain.Greeting;
import study.chat.domain.HelloMessage;

@Slf4j
@Controller
public class ChatController {

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
