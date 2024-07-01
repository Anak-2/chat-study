package study.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.chat.domain.ChatRoom;

import java.util.List;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom, Long> {

}
