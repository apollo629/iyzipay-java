package com.iyzipay;

import com.iyzipay.exception.HttpClientException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

public final class HashGenerator {

    private HashGenerator() {
    }

    public static String generateHash(String apiKey, String secretKey, String randomString, Object request) {
        String input = apiKey + randomString + secretKey + request;
        StringBuilder sb = new StringBuilder();
        byte[] result = null;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA1");
            result = crypt.digest(input.getBytes());
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new HttpClientException(e.getMessage(), e);
        }
        return DatatypeConverter.printBase64Binary(result);
    }

}
