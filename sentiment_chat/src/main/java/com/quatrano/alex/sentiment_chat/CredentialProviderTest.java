package com.quatrano.alex.sentiment_chat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

class CredentialProviderTest {

    private static CredentialProvider instance;
    private static String BOGUS_SERVICE_NAME = "myService";
    private static String BOGUS_SERVICE_KEY = "PASSW0RD";

    @BeforeAll
    public static void beforeAll() throws Exception {
        CredentialProvider.reset();
        generateBogusCredentials();
        instance = CredentialProvider.getInstance();
    }

    @Test
    public void getCredentialTest() throws Exception {
        assert(instance.getCredential(BOGUS_SERVICE_NAME).equals(BOGUS_SERVICE_KEY));
    }

    private static void generateBogusCredentials() throws Exception {
        URL classLoaderUrl = CredentialProvider.class.getClassLoader().getResource(".");
        File credFile = new File(classLoaderUrl.getFile() + CredentialProvider.FILENAME);
        credFile.createNewFile();
        FileOutputStream fio = new FileOutputStream(credFile);
        fio.write((BOGUS_SERVICE_NAME + "=" + BOGUS_SERVICE_KEY).getBytes());
        fio.close();
    }

}