package com.vpsy._2f.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author punith
 * @date 2020-04-24
 * @description The class is used to hash password with random salt
 */
public class Authorization {

    /** Used password hash function */
    private static final String HASH_FUNCTION = "SHA-256";

    /** Variable to hold length of OTP */
    private static final int OTP_LENGTH = 6;

    /** Variable is used to hold number of bytes the used salt has */
    private static final int BYTES_IN_SALT = 16;

    /**
     * @param password: Plain password to be hashed
     * The method is used to hash password with random salt
     */
    public static HashResult hash(String password) {
        /* Generating random salt */
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[BYTES_IN_SALT];
        random.nextBytes(saltBytes);

        String saltString = byteArrayToHexadecimalString(saltBytes);
        String hashString = hashWithSalt(password,saltString);

        HashResult hashResult = new HashResult(hashString, saltString);

        return hashResult;
    }

    /**
     * @param inputString: plain string to be hashed
     * @param saltString: salt to be used with hashing. The string is in hexadecimal format
     * @return hashed string
     */
    public static String hashWithSalt(String inputString, String saltString) {
        byte[] saltBytes = hexadecimalStringToByteArray(saltString);
        MessageDigest messageDigest;
        String hashString = null;
        try {
            messageDigest = MessageDigest.getInstance(HASH_FUNCTION);

            /* Pass the salt to digest */
            messageDigest.update(saltBytes);

            /* Generating salted hash */
            byte[] hashBytes = messageDigest.digest(inputString.getBytes(StandardCharsets.UTF_8));

            hashString = byteArrayToHexadecimalString(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error in hashing the string: ");
            e.printStackTrace();
        }

        return hashString;
    }

    /**
     * @param bytes: Array of byte elements
     * @return  hexadecimal representation of byte array
     */
    private static String byteArrayToHexadecimalString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    private static byte[] hexadecimalStringToByteArray(String hexadecimalString) {
        if(hexadecimalString.length() % 2 != 0) {
            return null;
        }

        int length = hexadecimalString.length();

        byte[] bytes = new byte[length/2];

        for (int i = 0; i < hexadecimalString.length(); i = i + 2) {
            int j = Integer.parseInt(hexadecimalString.substring(i, i + 2), 16);
            bytes[i/2] = (byte) j;
        }

        return bytes;
    }

    /**
     * @return Randomly generated OTP
     */
    public static String randomOTP() {

        int upperLimit = 0;
        for (int i = 0; i < OTP_LENGTH; i++) {
            upperLimit *= 10;
            upperLimit += 9;
        }

        int randomNumber = new Random().nextInt(upperLimit);

        String otp = String.valueOf(randomNumber);

        while (otp.length() < OTP_LENGTH) {
            otp = "0" + otp;
        }

        return otp;
    }


    /**
     * @description Method to generate salt randomly
     * @return random salt in hexadecimal format
     */
    public static String randomSalt() {
        /* Generating random salt */
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[BYTES_IN_SALT];
        random.nextBytes(saltBytes);

        return byteArrayToHexadecimalString(saltBytes);
    }

    public static void main(String[] args) {
        randomOTP();
    }
}

