/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import com.payges.ussd.mtnlib.util.UssdConstants;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

/**
 *
 * @author ptrack
 */
public class MiscFunctions {

    UssdBean ussdBean = lookupUssdBeanBean();
//    public static final String INVALID_INPUT="INVALID_INPUT";
//    public static final String LRD_MESSAGE="LRD_MESSAGE";
//    public static final String USD_MESSAGE="USD_MESSAGE";
//    public static final String RESEND_PIN_MESSAGE="RESEND_PIN_MESSAGE";
//    public static final String NO_LAST_PIN_MESSAGE="NO_LAST_PIN_MESSAGE";
//    public static final String LRD_HOLDING_MESSAGE="LRD_HOLDING_MESSAGE";
//    public static final String LRD_HOLDING_MESSAGE="LRD_HOLDING_MESSAGE";
    
//    final Runnable workerThread = new MiniStmtWorkerThread(accntNo, uInfo.getAffiliateCode(), sInfo.getLang(), sInfo.getMobileNo(),sInfo.getSessionId(), uInfo.getNetwork());
//    MenuUtil.miniStmtExecutor.execute(workerThread);
    public static ExecutorService threadsExecutor = Executors.newFixedThreadPool(100);
    
    String resendLastPin(UssdSession session) {
        return UssdConstants.END+UssdConstants.MESSAGES.getProperty(UssdConstants.INVALID_INPUT);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    String initiateDebit(UssdSession session) {
        Runnable runnable = new DebitWorkerThread(session.getSelectedCurrency(), session.getMsisdn());
        threadsExecutor.execute(runnable);
        return UssdConstants.END+UssdConstants.MESSAGES.getProperty(UssdConstants.DEBIT_HOLDING_MESSAGE);
//        if(session.getSelectedCurrency().equals("LRD")){
//            return UssdConstants.END+UssdConstants.MESSAGES.getProperty(UssdConstants.DEBIT_HOLDING_MESSAGE);
//        }else{
//            return UssdConstants.END+UssdConstants.MESSAGES.getProperty(UssdConstants.DEBIT_HOLDING_MESSAGE);
//        }
    }

    private UssdBean lookupUssdBeanBean() {
        try {
            Context c = new InitialContext();
            return (UssdBean) c.lookup("java:global/MTNLibApp/UssdBean!com.payges.ussd.mtnlib.main.UssdBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).info("NamingException caught. Reason: "+ ne.getMessage());
            throw new RuntimeException(ne);
        }
    }
    
    
    
}
