package study.chat.implement.chat_message;

import study.chat.dto.chat_message.ChatJoinMessageRequest;
import study.chat.dto.chat_message.ChatLeaveMessageRequest;
import study.chat.dto.chat_message.ChatMessageRequest;
import study.chat.global.annotation.Implement;
import study.chat.repository.chat_message.ChatMessageRepository;

@Implement
public class ChatMessageAppender {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageAppender(final ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public void appendJoinChat(final ChatJoinMessageRequest chatJoinMessageRequest){
        String sender = String.valueOf(chatJoinMessageRequest.memberId());
        chatMessageRepository.save(chatJoinMessageRequest.toEntity(sender));
    }

    public void appendChat(final ChatMessageRequest chatMessageRequest){
        String sender = String.valueOf(chatMessageRequest.memberId());
        chatMessageRepository.save(chatMessageRequest.toEntity(sender));
    }

    public void appendLeaveChat(final ChatLeaveMessageRequest chatLeaveMessageRequest){
        String sender = String.valueOf(chatLeaveMessageRequest.memberId());
        chatMessageRepository.save(chatLeaveMessageRequest.toEntity(sender));
    }

}
