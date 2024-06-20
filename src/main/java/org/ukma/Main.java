package org.ukma;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) throws IOException {
        byte[] message = "Hello World".getBytes(StandardCharsets.UTF_8);
        try {
            System.out.println("..........testing Message Digest.........");
            getMessageDigestHash(message, "SHA-256");
            getMessageDigestHash(message, "SHA-384");
            getMessageDigestHash(message, "SHA-512");
            System.out.println();
            System.out.println(".........testing Secure Random............");
            getSecureRandomHash(message, "SHA1PRNG");
            getSecureRandomHash(message, "DRBG");
            getSecureRandomHash(message, "Windows-PRNG");
            System.out.println();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }

    public static void getSecureRandomHash(byte[] message, String algorithm)
            throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance(algorithm);
        secureRandom.setSeed(1308);
        secureRandom.nextBytes(message);
        long hash = secureRandom.nextLong();
        System.out.println(hash);
    }

    private static void getMessageDigestHash(byte[] message, String algorithm)
            throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(message);
        String hash = convertToHex(messageDigest.digest());
        System.out.println(hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuilder hexString = new StringBuilder(2 * data.length);
        for (byte datum : data) {
            String hex = Integer.toHexString(0xff & datum);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
