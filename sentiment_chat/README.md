# about
"Sentiment Chat" is a simple chat room application.  The java server hosts a single chat room where users can post text messages.  Each text message is sent to an external NLP service (https://indico.io/) for sentiment analysis.  The sentiment score is then sent to all clients, and the original message is decorated with an emoji indicating the sentiment score (😡😠☹️🙁😐🙂😀😄😆😂).

# build and run
Run `mvn jetty:run` to build and deploy locally.
visit http://localhost:8080

## credential error
If you see a `credential error` logged when running this application, it means you that you did not have a properties file with the appropriate credentials in `src/main/java/resources` before building the project.
The file should be named `credentials.properties` and should contain credentials in the following format:
```properties
indico=PASSW0RD1234ASDF1234
```
#### indico credential error
You can sign up for an indico account at: https://indico.io/