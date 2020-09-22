'use strict';

var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');


var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

// 打开一个 web socket
var ws = new WebSocket("ws://localhost:8013/ws");
var userId;
ws.onopen = function (event) {
    console.log(event);
    var chatMessage = {
        sender: userId,
        content: 'Hello Server!',
        type: 'CHAT'
    };
    ws.send(JSON.stringify(chatMessage));
    alert("websocket连接已建立...");
};

ws.onclose = function (event) {
    var chatMessage = {
        sender: userId,
        content: 'I am leave!',
        type: 'CHAT'
    };
    ws.send(JSON.stringify(chatMessage));
    alert("连接已关闭...");
};

ws.onmessage = function (event) {
    var message = JSON.parse(event.data);
    var messageElement = document.createElement('li');

    messageElement.classList.add('chat-message');

    var avatarElement = document.createElement('i');
    var avatarText = document.createTextNode(message.sender[0]);
    avatarElement.appendChild(avatarText);
    avatarElement.style['background-color'] = getAvatarColor(message.sender);

    messageElement.appendChild(avatarElement);

    const usernameElement = document.createElement('span');
    var usernameText = document.createTextNode(message.sender);
    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
};


function sendMessage(event) {
    event.preventDefault();
    var messageContent = messageInput.value.trim();
    alert(messageContent);
    if (messageContent) {
        var chatMessage = {
            sender: userId,
            content: messageInput.value,
            type: 'CHAT'
        };

        ws.send(JSON.stringify(chatMessage));
        messageInput.value = '';
    }
}



function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

messageForm.addEventListener('submit', sendMessage, true);