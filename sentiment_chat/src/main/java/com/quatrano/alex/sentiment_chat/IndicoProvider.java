/*
 * simple provider to fail gracefully if no/incorrect credentials are provided
 */

package com.quatrano.alex.sentiment_chat;

import io.indico.Indico;

public class IndicoProvider{

    private static String indicoCredential;
    static {
        try {
            indicoCredential = CredentialProvider.getInstance().getCredential("indico");
        } catch (Exception e) {
            e.printStackTrace();
            indicoCredential = null;
        }
    }

    public static Indico getIndico() {
        if (indicoCredential != null) {
            try {
                return new Indico(indicoCredential);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.err.println("credential error: \"indico\" see readme");
        return null;
    }
}