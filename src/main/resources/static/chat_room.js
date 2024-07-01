document.addEventListener('DOMContentLoaded', () => {
    const createChatRoomForm = document.getElementById('createChatRoomForm');
    const chatRoomsList = document.getElementById('chatRoomsList');

    // Function to fetch and display chat rooms
    const fetchChatRooms = async () => {
        try {
            const response = await fetch('/api/v1/chat-rooms');
            if (!response.ok) throw new Error('Network response was not ok');
            const result = await response.json();
            displayChatRooms(result.data);
        } catch (error) {
            console.error('Error fetching chat rooms:', error);
        }
    };

    // Function to display chat rooms
    const displayChatRooms = (chatRooms) => {
        chatRoomsList.innerHTML = '';
        chatRooms.forEach(room => {
            const li = document.createElement('li');
            li.textContent = `ID: ${room.id}, Name: ${room.name}, Owner ID: ${room.ownerId}`;
            chatRoomsList.appendChild(li);
        });
    };

    // Event listener for form submission
    createChatRoomForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        const ownerId = document.getElementById('ownerId').value;
        const name = document.getElementById('name').value;

        try {
            const response = await fetch('/api/v1/chat-rooms', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ owner_id: ownerId, name: name })
            });
            if (!response.ok) throw new Error('Network response was not ok');
            createChatRoomForm.reset();
            fetchChatRooms();
        } catch (error) {
            console.error('Error creating chat room:', error);
        }
    });

    // Initial fetch to display chat rooms
    fetchChatRooms();
});
