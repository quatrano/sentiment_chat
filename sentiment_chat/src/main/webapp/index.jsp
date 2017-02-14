<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sentiment Chat</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div id="chatControls">
        <input id="message" placeholder="Type your message">
        <button id="send">Send</button>
    </div>
    <ul id="user-list"> <!-- Built by JS --> </ul>
    <div id="chat">    <!-- Built by JS --> </div>
    <script src="script.js"></script>

    <template id="message-template">
        <article class="user">
            <b>
                <!-- title goes here-->
            </b>
            <p>
                <!-- body goes here -->
            </p>
            <span class="timestamp">
                <!-- timestamp goes here -->
            </span>
            <span class="sentiment">
                <!-- sentiment score goes here -->
            <span>
        </article>
    </template>

    <template id="system-message-template">
        <article class="system">
            <p>
                <!-- body goes here -->
            </p>
            <span class="timestamp">
                <!-- timestamp goes here -->
            </span>
        </article>
    </template>

</body>
</html>