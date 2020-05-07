/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import com.payges.ussd.mtnlib.entities.Serials;
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
    public static ExecutorService threadsExecutor = Executors.newFixedThreadPool(100);
    Logger logger = Logger.getLogger(getClass());
    
    String resendLastPin(UssdSession session) {
        logger.info("called resendLastPin for "+ session.getMsisdn());
        Serials serials = ussdBean.getLastTransaction(session.getMsisdn());
        if(null == serials){
            ussdBean.saveUssdLog(session.getMsisdn(), "RESEND_PIN", "FAILED");
            return UssdConstants.END+UssdConstants.MESSAGES.getProperty(UssdConstants.NO_LAST_PIN_MESSAGE);
        } else{
            ussdBean.saveUssdLog(session.getMsisdn(), "RESEND_PIN", "SUCCESS");
            final String response = UssdConstants.MESSAGES.getProperty(UssdConstants.RESEND_PIN_MESSAGE)
                    .replace("{PIN}", serials.getPin()).replace("{SERIAL}", serials.getSerial());
            ussdBean.sendSMS(session.getMsisdn(), response);
            return UssdConstants.END+response;
        }
    }
    
    boolean isAnySerialUnused(String msisdn) {
        logger.info("called isAnySerialUnused for "+ msisdn);
        Serials nextUnusedSerial = ussdBean.getNextUnusedSerial();
        //Serials serials = ussdBean.Serials nextUnusedSerial = ussdBean.getNextUnusedSerial();(session.getMsisdn());
        if(null == nextUnusedSerial){
            return false;
        } else{
            return true;
        }
    }
    
    String initiateDebit(UssdSession session) {
        logger.info("called initiate debit request for "+ session.getMsisdn());
        Runnable runnable = new DebitWorkerThread(session);
        threadsExecutor.execute(runnable);
        final String response = UssdConstants.END+UssdConstants.MESSAGES.getProperty(UssdConstants.DEBIT_HOLDING_MESSAGE);
        logger.info("sending {"+ response+"} to "+session.getMsisdn());
        return response;
    }

    private UssdBean lookupUssdBeanBean() {
        try {
            Context c = new InitialContext();
            return (UssdBean) c.lookup("java:global/MTNLibApp/UssdBean!com.payges.ussd.mtnlib.main.UssdBean");
        } catch (NamingException ne) {
            logger.info("NamingException caught. Reason: "+ ne.getMessage());
            throw new RuntimeException(ne);
        }
    }
    
    
    
}
