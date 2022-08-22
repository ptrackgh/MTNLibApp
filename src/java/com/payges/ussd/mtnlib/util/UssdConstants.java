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
    
    public static final String DISABLE_PUBLIC_MSISDN="DISABLE_PUBLIC_MSISDN";
    public static final String MSISDN_WHITELIST="MSISDN_WHITELIST";
    public static final String MAIN_MENU="MAIN_MENU";
    public static final String BAD_REQUEST="BAD_REQUEST";
    public static final String CONTINUE="1,";
    public static final String END="2,";
//    public static final String CONTINUE="";
//    public static final String END="";
    public static final String INVALID_INPUT="INVALID_INPUT";
    public static final String EXIT="EXIT";
    public static final String DISCLAIMER_RESITTER="DISCLAIMER_RESITTER";
    public static final String DISCLAIMER_RESULT_CHECKER="DISCLAIMER_RESULT_CHECKER";
//    public static final String LRD_MESSAGE="LRD_MESSAGE";
//    public static final String USD_MESSAGE="USD_MESSAGE";
    public static final String PIN_PURCHASE_MESSAGE="PIN_PURCHASE_MESSAGE";
    public static final String RESEND_PIN_MESSAGE="RESEND_PIN_MESSAGE";
//    public static final String LRD_HOLDING_MESSAGE="LRD_HOLDING_MESSAGE";
//    public static final String USD_HOLDING_MESSAGE="USD_HOLDING_MESSAGE";
//    public static final String NO_LAST_PIN_MESSAGE="NO_LAST_PIN_MESSAGE";
    public static final String NO_LAST_PIN_MESSAGE="NO_LAST_PIN_MESSAGE";
    public static final String DEBIT_HOLDING_MESSAGE="DEBIT_HOLDING_MESSAGE";
    public static final String HSDP_DEBIT_URL="HSDP_DEBIT_URL";//http://IP:Port/ThirdPartyServiceUMMImpl/UMMServiceService/RequestPayment/v17
    public static final String HSDP_TRANSACTION_STATUS_URL="HSDP_TRANSACTION_STATUS_URL";//http://196.201.33.108:8323/mom/mt/gettransactionstatus
    public static final String HSDP_SP_ID="HSDP_SP_ID";
    public static final String HSDP_SP_PASSWORD="HSDP_SP_PASSWORD";
    public static final String HSDP_SP_TIMESTAMP="HSDP_SP_TIMESTAMP";
    public static final String HSDP_MOMO_ID="HSDP_MOMO_ID";//2310110002418
    public static final String NO_SERIALS_MESSAGE="NO_SERIALS_MESSAGE";//2310110002418
    
    //WAEC Entries
    public static final String WAEC_CLIENT_ID="WAEC_CLIENT_ID";
    public static final String WAEC_CLIENT_PASSWORD="WAEC_CLIENT_PASSWORD";
    public static final String WAEC_VENDING_URL="WAEC_VENDING_URL";
    public static final String WAEC_VENDING_STATUS_URL="WAEC_VENDING_STATUS_URL";
    public static final String WAEC_SERVICE_CODE="WAEC_SERVICE_CODE";
    public static final String PIN_TYPE_REGISTRATION="01";
    public static final String PIN_TYPE_MINI_REGISTRATION="01B";
    public static final String PIN_TYPE_RESULT_CHECKER="03";
    public static final String PAYMENT_PROVIDER="PAYMENT_PROVIDER";
    public static final String IS_RESIITER_CLOSED="IS_RESIITER_CLOSED";
    public static final String IS_RESULT_CHECKER_CLOSED="IS_RESULT_CHECKER_CLOSED";
    public static final String IS_MINI_RESULT_CHECKER_CLOSED="IS_MINI_RESULT_CHECKER_CLOSED";
    public static final String SERVICE_CLOSED_MESSAGE="SERVICE_CLOSED_MESSAGE";
    //BAD_REQUEST,INVALID_INPUT,LRD_MESSAGE,USD_MESSAGE,RESEND_PIN_MESSAGE,NO_LAST_PIN_MESSAGE
}
