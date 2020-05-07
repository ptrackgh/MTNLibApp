/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 *
 * @author ptrack
 */
public class SecurityUtil {
    private static Logger logger = Logger.getLogger(SecurityUtil.class);
    private static Random rand = new Random();
    private static SimpleDateFormat sessionIdDateFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
    private static SimpleDateFormat waecDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    
    public static String generateOTP() {
        return "" + (100000 + rand.nextInt(899999));
    }

    public static String generateSessionId() {
        return sessionIdDateFormat.format(new Date());
    }
    
    public static String generateWAECDate() {
        return waecDateFormat.format(new Date());
    }
    
    public static String generateHash(String st) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-512");
            final byte[] encodedhash = digest.digest(st.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException ex) {
            logger.info("NoSuchAlgorithmException thrown. Reason:" + ex.getMessage());
            logger.debug("NoSuchAlgorithmException " + Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
        }
        return "";
    }

    private static String bytesToHex(byte[] hash) {
        final StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            final String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString().toUpperCase();
    }
}
