function start(window) {

    //Establish the WebSocket connection and set up event handlers
    var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chat/");
    webSocket.onmessage = function (msg) { updateChat(msg); };
    webSocket.onclose = function () { alert("WebSocket connection closed") };

    //Send message if "Send" is clicked
    id("send").addEventListener("click", function () {
        sendMessage(id("message").value);
    });

    //Send message if enter is pressed in the input field
    id("message").addEventListener("keypress", function (e) {
        if (e.keyCode === 13) { sendMessage(e.target.value); }
    });

    //Send a message if it's not empty, then clear the input field
    function sendMessage(message) {
        if (message !== "") {
            webSocket.send(message);
            id("message").value = "";
        }
    }

    //Update the chat-panel, and the list of connected users
    function updateChat(msg) {
        var data = JSON.parse(msg.data);
        renderMessage(data);
        id("user-list").innerHTML = "";
        data.userList.forEach(function (user) {
            insert("user-list", "<li>" + user + "</li>");
        });
    }

    //Helper function for inserting HTML string as the first child of an element
    function insert(targetId, message) {
        id(targetId).insertAdjacentHTML("afterbegin", message);
    }
    // helper function for inserting constructed node
    function insert2(targetId, node) {
        id(targetId).prepend(node);
    }

    //Helper function for selecting element by id
    function id(id) {
        return document.getElementById(id);
    }

    var sentimentEmojis = [
        "\uD83D\uDE21", // 0 pouting face
        "\uD83D\uDE20", // 1 angry face
        "\u2639\uFE0F", // 2
        "\uD83D\uDE41", // 3
        "\uD83D\uDE10", // 4
        "\uD83D\uDE42", // 5
        "\uD83D\uDE00", // 6
        "\uD83D\uDE04", // 7
        "\uD83D\uDE06", // 8
        "\uD83D\uDE02", // 9
        "\uD83D\uDE02"  // 10 (same as 9)
    ];

    var template = id('message-template');
    var systemMessageTemplate = id('system-message-template');
    function renderMessage(data) {
        if (data.messageType == 'system') {

            // handle system message
            systemMessageTemplate.content.querySelector('p').textContent = data.body;
            systemMessageTemplate.content.querySelector('.timestamp').textContent = data.timestamp;
            var clone = document.importNode(systemMessageTemplate.content, true);
            insert2('chat', clone);
        } else if (data.messageType == 'user') {

            // handle user message
            template.content.querySelector('article').id = data.messageId;
            template.content.querySelector('b').textContent = data.sender + " says:";
            template.content.querySelector('p').textContent = data.body;
            template.content.querySelector('.timestamp').textContent = data.timestamp;
            var clone = document.importNode(template.content, true);
            insert2('chat', clone);
        } else if (data.messageType == 'sentiment') {

            // handle sentiment
            // sentiment always comes after the message
            // so find the message in the DOM and update sentiment value
            var sentimentScore = Number(data.body);
            if (!isNaN(sentimentScore) && sentimentScore > -1) {
                var sentimentEmoji = sentimentEmojis[sentimentScore];
                id(data.messageId).querySelector('.sentiment').textContent = sentimentEmoji;
            }
        }
    }
};

window.setTimeout(start, 0);