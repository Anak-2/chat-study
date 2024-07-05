package study.chat.dto.chat_room;

public record ChatRoomLeaveRequest(
        long memberId,
        long roomId
) {
}
