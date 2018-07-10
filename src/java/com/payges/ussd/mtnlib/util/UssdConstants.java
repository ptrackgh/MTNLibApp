/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.util;

import java.util.Properties;

/**
 *
 * @author ptrack
 */
public class UssdConstants {
    public static final Properties MESSAGES = new Properties();
    
    public static final String MAIN_MENU="MAIN_MENU";
    public static final String BAD_REQUEST="BAD_REQUEST";
    public static final String CONTINUE="1,";
    public static final String END="2,";
    public static final String INVALID_INPUT="INVALID_INPUT";
    public static final String LRD_MESSAGE="LRD_MESSAGE";
    public static final String USD_MESSAGE="USD_MESSAGE";
    public static final String RESEND_PIN_MESSAGE="RESEND_PIN_MESSAGE";
    public static final String LRD_HOLDING_MESSAGE="LRD_HOLDING_MESSAGE";
//    public static final String USD_HOLDING_MESSAGE="USD_HOLDING_MESSAGE";
//    public static final String NO_LAST_PIN_MESSAGE="NO_LAST_PIN_MESSAGE";
    public static final String NO_LAST_PIN_MESSAGE="NO_LAST_PIN_MESSAGE";
    public static final String DEBIT_HOLDING_MESSAGE="DEBIT_HOLDING_MESSAGE";
    
    //BAD_REQUEST,INVALID_INPUT,LRD_MESSAGE,USD_MESSAGE,RESEND_PIN_MESSAGE,NO_LAST_PIN_MESSAGE
}
