let stompClient = null;
let userName = "";

function connect() {
    userName = document.getElementById("name").value;
    if (userName) {
        let socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);

            // 최초 구독 시 사용자 이름과 함께 메시지 전송
            stompClient.subscribe('/topic/greetings', function (greeting) {
                showMessage(JSON.parse(greeting.body).content);
            }, {'name': userName});

            // 메시지 브로커를 거치지 않는 직접 메시지 구독
            stompClient.subscribe('/topic/message', function (message) {
                showMessage(JSON.parse(message.body).content);
            });
        });
    } else {
        alert("Please enter your name");
    }
}

function sendMessage() {
    let message = document.getElementById("messageInput").value;
    stompClient.send("/topic/message", {}, JSON.stringify({'name': userName, 'content': message}));
}

function sendGreeting() {
    stompClient.send("/app/greeting", {}, JSON.stringify({'name': userName}));
}

function showMessage(message) {
    let messageElement = document.createElement('div');
    messageElement.appendChild(document.createTextNode(message));
    document.getElementById('messages').appendChild(messageElement);
}