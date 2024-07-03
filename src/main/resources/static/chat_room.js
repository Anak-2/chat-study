const apiPrefix = '/api/v1'; // API prefix
let stompClient = null;
let currentRoomId = null;
const memberId = 1; // 예시 멤버 ID

document.addEventListener('DOMContentLoaded', function () {
    loadChatRooms();
});

function loadChatRooms() {
    axios.get(apiPrefix + '/chat-rooms')
        .then(response => {
            const chatRooms = response.data.data;
            displayChatRooms(chatRooms);
        })
        .catch(error => console.error('Error:', error));
}

function displayChatRooms(chatRooms) {
    const chatRoomsContainer = document.getElementById('chat-rooms');
    chatRoomsContainer.innerHTML = '';

    chatRooms.forEach(room => {
        const roomElement = document.createElement('div');
        roomElement.textContent = room.name;
        roomElement.onclick = () => enterChatRoom(room.id);

        const button = document.createElement('button');
        button.textContent = 'Subscribe';
        button.onclick = () => subscribeToRoom(room.id);

        roomElement.appendChild(button);
        chatRoomsContainer.appendChild(roomElement);
    });
}

function subscribeToRoom(roomId) {
    const subscriptionRequest = {
        memberId: memberId,
        roomId: roomId
    };

    axios.post(apiPrefix + '/subscribe', subscriptionRequest)
        .then(response => {
            alert(response.data);
            loadChatRooms();
        })
        .catch(error => {
            if (error.response) {
                const statusCode = error.response.status;
                const message = error.response.data;
                alert(`Error ${statusCode}: ${message}`);
            } else if (error.request) {
                alert('No response received from the server.');
            } else {
                alert(`Error: ${error.message}`);
            }
            console.error('Error:', error);
        });
}

function unsubscribeFromRoom(roomId) {
    const unsubscribeRequest = {
        memberId: memberId,
        roomId: roomId
    };

    axios.delete(apiPrefix + '/unsubscribe', {data: unsubscribeRequest})
        .then(response => {
            alert(response.data);
            loadChatRooms();
        })
        .catch(error => console.error('Error:', error));
}

function enterChatRoom(roomId) {
    currentRoomId = roomId;
    document.getElementById('chat-room-container').style.display = 'block';

    connectToWebSocket(roomId);
}

function connectToWebSocket(roomId) {
    const socket = new SockJS('/ws'); // WebSocket 엔드포인트
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        stompClient.subscribe(`/topic/chat/${roomId}`, (message) => {
            showMessage(JSON.parse(message.body).content);
        });
    });
}

function sendMessage() {
    const messageInput = document.getElementById('messageInput');
    const message = {
        memberId: memberId,
        content: messageInput.value
    };

    stompClient.send(`/app/chat/${currentRoomId}`, {}, JSON.stringify(message));
    messageInput.value = '';
}

function showMessage(message) {
    const messagesContainer = document.getElementById('messages');
    const messageElement = document.createElement('div');
    messageElement.textContent = message;
    messagesContainer.appendChild(messageElement);
}

function leaveRoom() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    currentRoomId = null;
    document.getElementById('chat-room-container').style.display = 'none';
    document.getElementById('chat-rooms').style.display = 'block';
}
