/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import com.payges.ussd.mtnlib.util.POUtil;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author ptrack
 */
public class PODebitWorkerThread implements Runnable {
    Logger logger = Logger.getLogger(PODebitWorkerThread.class);
    DecimalFormat df = new DecimalFormat("####0.00");
    UssdBean ussdBean = lookupUssdBeanBean();
    String currency;
    String msisdn;
    UssdSession session;
    
    final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
    final static Random rand = new Random();
    
    public PODebitWorkerThread(UssdSession session) {
        this.session = session;
        this.msisdn = session.getMsisdn();
        this.currency = session.getSelectedCurrency();
    }

    @Override
    public void run() {
        MDC.put("session", msisdn);
        try {
            Thread.sleep(5000);
            logger.info("sending debit request for: " + msisdn);
            final String transactionId = getTransactionId();
            Double cost = Double.parseDouble(session.getVendstatus().getCost());
            if("REGISTRATION".equals(session.getPintype())){
//                Currencies debitAmount = ussdBean.getDebitAmount(currency);
                ussdBean.saveUssdLog(msisdn, "PURCHASE_PIN_"+currency, "PENDING");
                ussdBean.saveDebitTransaction(msisdn, cost, currency, transactionId,"REGISTRATION");
                POUtil.sendDebitInternal(transactionId, msisdn, cost, currency, "waec registration");
            }
            else if("MINI-REGISTRATION".equals(session.getPintype())){
//                Currencies debitAmount = ussdBean.getDebitAmount(currency);
//                Double cost = Double.parseDouble(session.getVendstatus().getCost());
                ussdBean.saveUssdLog(msisdn, "PURCHASE_MINI-PIN_"+currency, "PENDING");
                ussdBean.saveDebitTransaction(msisdn, cost, currency, transactionId,"MINI-REGISTRATION");
                POUtil.sendDebitInternal(transactionId, msisdn, cost, currency, "waec registration");
            }
            else{
                final String ccy2= "LD".equalsIgnoreCase(session.getVendstatus().getCurrency())?"LRD":session.getVendstatus().getCurrency();
                ussdBean.saveUssdLog(msisdn, "RESULT_CHECKER_"+currency, "PENDING");
                ussdBean.saveDebitTransaction(msisdn, cost, ccy2, transactionId,"RESULT_CHECKER");
                POUtil.sendDebitInternal(transactionId, msisdn, cost, ccy2, "results checker");
//                xmlString = String.format(debitrequest, spId,spPassword,serviceId,spTimestamp,
//                        df.format(Double.parseDouble(session.getVendstatus().getCost())),msisdn,transactionId,session.getVendstatus().getCurrency());
            }
        } catch (InterruptedException ex) {
            logger.error("InterruptedException thrown: " + ex.getMessage());
        }catch (Throwable ex) {
            logger.error("Throwable thrown: " + ex.getMessage());
            logger.error(Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
        }
        MDC.remove("session");
    }

    private UssdBean lookupUssdBeanBean() {
        try {
            Context c = new InitialContext();
            return (UssdBean) c.lookup("java:global/MTNLibApp/UssdBean!com.payges.ussd.mtnlib.main.UssdBean");
        } catch (NamingException ne) {
            logger.info("exception caught: "+ ne);
            logger.error(Arrays.toString(ne.getStackTrace()).replaceAll(", ", "\n"));
            throw new RuntimeException(ne);
        }
    }
    
    public static String getTransactionId(){
        return dateFormat.format(new Date()) + rand.nextInt(10);
    }
    
}
