/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import com.payges.ussd.mtnlib.entities.Serials;
import com.payges.ussd.mtnlib.entities.Transactions;
import com.payges.ussd.mtnlib.ericsson.restmodels.Financialtransactionstatus;
import static com.payges.ussd.mtnlib.main.PODebitCompletedProcessor.dateFomrmatter;
import com.payges.ussd.mtnlib.util.UssdConstants;
import com.payges.ussd.mtnlib.waec.dto.VendPinResponse;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.MDC;

/**
 *
 * @author ptrack
 */
public class PODebitCompletedProcessor implements Runnable {

    UssdBean ussdBean = lookupUssdBeanBean();
    static SimpleDateFormat dateFomrmatter = new SimpleDateFormat("yy/MM/dd");
    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(getClass());
    final static String spId = UssdConstants.MESSAGES.getProperty(UssdConstants.HSDP_SP_ID);
    private final String transactionid;
    private final String externalid;
    private final Financialtransactionstatus status;

    public PODebitCompletedProcessor(String transactionid, String externalid, Financialtransactionstatus status) {
        this.transactionid = transactionid;
        this.externalid = (null==externalid || "".equals(externalid)) ? transactionid : externalid;
        this.status = status;
    }

    @Override
    public void run() {
        MDC.put("session", transactionid);
        try {
            logger.info("processing PO debitcompleted req for: " + this.toString());
            Transactions transaction = ussdBean.getTransaction(transactionid);
            if (null == transaction) {
                logger.info("could not find any PENDING transaction with id: " + transactionid);
            } else {
                if("SUCCESS".equals(transaction.getStatus())){
                    throw new IllegalAccessException("This transaction has already been completed in the past.");
                }
                transaction.setExternaltransactionid(externalid);
                transaction.setMomostatus(status.name());
                transaction.setStatusdesc(status.name());
                transaction.setLastresponsedate(new Date());
                if (null==status) {
                    logger.info(transactionid + " was declined!");
                    transaction.setStatus("FAILED");
                } else switch (status) {
                    case SUCCESSFUL:
                        //if(transaction.getMsisdn().equals("231888921776")){
                        logger.info("transaction was approved. going to call waec");
                        //TODO uncomment this for actual prod
                        transaction = callWaecToVend(transaction);
                        break;
                    case PENDING:
                        logger.info("going to call gettransaction status as the response received from PO is 'PENDING'");
                        break;
                    default:
                        logger.info(transactionid + " was declined!");
                        transaction.setStatus("FAILED");
                        break;
                }
                ussdBean.merge(transaction);
            }
        } catch (IllegalAccessException ex) {
            logger.error("IllegalAccessException " + ex.getMessage());
            logger.error(Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
        } catch (Throwable ex) {
            logger.error("Throwable " + ex.getMessage());
            logger.error(Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
        }
        MDC.remove("session");
    }

    private UssdBean lookupUssdBeanBean() {
        try {
            Context c = new InitialContext();
            return (UssdBean) c.lookup("java:global/MTNLibApp/UssdBean!com.payges.ussd.mtnlib.main.UssdBean");
        } catch (NamingException ne) {
            logger.error("exception caught " + ne.getMessage());
            logger.error(Arrays.toString(ne.getStackTrace()).replaceAll(", ", "\n"));
            throw new RuntimeException(ne);
        }
    }
    
    private Transactions callWaecToVend(Transactions transaction) {
        logger.info("callWaecToVend invoked");
        final VendPinResponse vendpinresponse = WAECUtil.generateWAECPin(transaction.getPintype());
        if (null == vendpinresponse) {
            logger.info("VEND_FAILED: " + transaction.getMsisdn() + "vending returned an error!. Please flag and reverse funds to the customer.");
            transaction.setStatus("VEND_FAIL");
        } else {
            logger.info("get PIN from waec API successful!");
            final Date date = new Date();
            Serials serial = new Serials();
            serial.setDateused(date);
            serial.setDateloaded(date);
            serial.setPin(vendpinresponse.getPIN());
            serial.setSerial(vendpinresponse.getSerial());
            serial.setTransactionid(transactionid);
            serial.setPin(vendpinresponse.getPIN());
            serial.setUsedby(transaction.getMsisdn());
            serial.setStatus("USED");
            //serial.setTransactionid(transactionid);
            ussdBean.persist(serial);
            transaction.setStatus("SUCCESS");

            String sms = UssdConstants.MESSAGES.getProperty(UssdConstants.PIN_PURCHASE_MESSAGE);
            logger.info(sms + "|" + transaction.getCurrency() + "|" + vendpinresponse.getPIN() + "|" + vendpinresponse.getSerial()
                    + "|" + dateFomrmatter.format(date) + "|" + externalid);
            sms = sms.replace("{CURRENCY}", transaction.getCurrency()).replace("{PIN}", vendpinresponse.getPIN()).replace("{SERIAL}", vendpinresponse.getSerial())
                    .replace("{DATE}", dateFomrmatter.format(date)).replace("{MOMO_TRANS_ID}", externalid);
            ussdBean.sendSMS(transaction.getMsisdn(), sms);
        }
        return transaction;
    }
    
    @Override
    public String toString() {
        return "PODebitCompletedProcessor{transactionid=" + transactionid + ", externalid=" + externalid + ", status='" + status.name() + '}';
    }
    

}
