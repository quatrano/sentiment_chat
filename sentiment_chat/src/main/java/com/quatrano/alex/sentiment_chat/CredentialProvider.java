package com.quatrano.alex.sentiment_chat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CredentialProvider {

    final static String FILENAME = "credentials.properties";
    private Properties credentials;

    private CredentialProvider() throws IOException {
        InputStream input = this.getClass().getClassLoader().getResourceAsStream(FILENAME);
        credentials = new Properties();
        credentials.load(input);
    }

    String getCredential(String serviceName) throws Exception {
        if (credentials.containsKey(serviceName)) {
            return credentials.getProperty(serviceName);
        } else {
            throw new Exception("there are no credentials for the service: " + serviceName);
        }
    }

    private static CredentialProvider instance;
    static CredentialProvider getInstance() throws IOException {
        if (instance == null) {
            instance = new CredentialProvider();
        }
        return instance;
    }
    static void reset() {
        instance = null;
    }
}
