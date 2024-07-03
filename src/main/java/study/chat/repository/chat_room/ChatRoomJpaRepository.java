package study.chat.repository.chat_room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.OpAnd;
import study.chat.entity.ChatRoom;

import java.util.Optional;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom, Long> {
}
