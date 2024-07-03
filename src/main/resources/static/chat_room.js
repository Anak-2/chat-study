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

async function displayChatRooms(chatRooms) {
    const chatRoomsContainer = document.getElementById('chat-rooms');
    chatRoomsContainer.innerHTML = '';

    for (const room of chatRooms) {
        const roomElement = document.createElement('div');
        roomElement.textContent = room.name;
        roomElement.onclick = () => enterChatRoom(room.id);
        roomElement.style.cursor = 'pointer';

        const button = await displayButton(room); // await를 사용하여 버튼을 기다림
        console.log(button);

        if (button) {
            roomElement.appendChild(button);
        }
        roomElement.style.display = 'flex';
        chatRoomsContainer.appendChild(roomElement);
    }
}


async function displayButton(room) {
    try {
        const response = await axios.get(apiPrefix + '/check-subscribe', {
            params: {
                memberId: memberId,
                roomId: room.id
            }
        });

        const isSubscribed = response.data;
        let button = null;

        if (isSubscribed) {
            button = document.createElement('button');
            button.textContent = 'Unsubscribe';
            button.onclick = (event) => unsubscribeFromRoom(room.id, event);
        } else {
            button = document.createElement('button');
            button.textContent = 'Subscribe';
            button.onclick = (event) => subscribeToRoom(room.id, event);
        }

        return button;
    } catch (error) {
        console.error('Error:', error);
        return null; // 에러 발생 시 null을 반환하여 처리
    }
}


function subscribeToRoom(roomId, event) {
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

function unsubscribeFromRoom(roomId, event) {
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
