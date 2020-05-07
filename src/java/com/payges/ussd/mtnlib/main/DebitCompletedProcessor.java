/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import com.payges.ussd.mtnlib.entities.Serials;
import com.payges.ussd.mtnlib.entities.Transactions;
import com.payges.ussd.mtnlib.ericsson.restmodels.Financialtransactionstatus;
import com.payges.ussd.mtnlib.ericsson.restmodels.Gettransactionstatusresponse;
import static com.payges.ussd.mtnlib.main.DebitCompletedProcessor.dateFomrmatter;
import static com.payges.ussd.mtnlib.main.DebitWorkerThread.getClientSSL;
import com.payges.ussd.mtnlib.util.UssdConstants;
import com.payges.ussd.mtnlib.waec.dto.VendPinResponse;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.MDC;

/**
 *
 * @author ptrack
 */
public class DebitCompletedProcessor implements Runnable {

    UssdBean ussdBean = lookupUssdBeanBean();
    static SimpleDateFormat dateFomrmatter = new SimpleDateFormat("yy/MM/dd");
    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(getClass());
    final static String spId = UssdConstants.MESSAGES.getProperty(UssdConstants.HSDP_SP_ID);
    private final String trace;
    private final String transactionid;
    private final String externalid;
    private final String statuscode;
    private final String statusdesc;

    private static String gettransactionstatusreq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<ns2:gettransactionstatusrequest xmlns:ns2=\"http://www.ericsson.com/em/emm/financial/v1_2\">"
            + "<referenceid>%s</referenceid>"
            + //20190614095708
            "</ns2:gettransactionstatusrequest>";

    public DebitCompletedProcessor(String trace, String transactionid, String externalid, String statuscode, String statusdesc) {
        this.trace = trace;
        this.transactionid = transactionid;
        this.externalid = null==externalid?transactionid:externalid;
        this.statuscode = statuscode;
        this.statusdesc = statusdesc;
    }

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        MDC.put("session", transactionid);
        try {
            logger.info("Going to process paymentcompleted callback received for: " + transactionid);
            Transactions transaction = ussdBean.getTransaction(transactionid);
            if (null == transaction) {
                logger.info("could not find any PENDING transaction with id: " + transactionid);
            } else {
                if("SUCCESS".equals(transaction.getStatus())){
                    //logger.info("This transaction has already been completed in the past." + transactionid);
                    throw new IllegalAccessException("This transaction has already been completed in the past.");
                }
                transaction.setExternaltransactionid(externalid);
                transaction.setMomostatus(statuscode);
                transaction.setStatusdesc(statusdesc);
                transaction.setTraceid(trace);
                transaction.setLastresponsedate(new Date());
                if (statuscode.equals("01")) {
//                if(transaction.getMsisdn().equals("231888921776")){
                    logger.info("transaction was approved. going to call waec");
                    transaction = callWaecToVend(transaction);
//                }else{
//                    logger.info(transactionid + " was approved!");
//                    transaction.setStatus("SUCCESS");
//                    Serials nextUnusedSerial = ussdBean.getNextUnusedSerial();
//                    final Date date = new Date();
//                    nextUnusedSerial.setDateused(date);
//                    nextUnusedSerial.setStatus("USED");
//                    nextUnusedSerial.setUsedby(transaction.getMsisdn());
//                    nextUnusedSerial.setTransactionid(transactionid);
//                    ussdBean.merge(nextUnusedSerial);
//                    //PIN_PURCHASE_MESSAGE=Dear customer, you have successfully purchased WAEC PIN with {CURRENCY}
//                    //on mobile money. Your PIN is: {PIN} with Serial No: {SERIAL} at {DATE}. Transaction ID: {MOMO_TRANS_ID}
//                    String sms = UssdConstants.MESSAGES.getProperty(UssdConstants.PIN_PURCHASE_MESSAGE);
//                    logger.info(sms+"|"+transaction.getCurrency()+"|"+nextUnusedSerial.getPin()+"|"+nextUnusedSerial.getSerial()
//                            +"|"+dateFomrmatter.format(date)+"|"+externalid);
//                        sms = sms.replace("{CURRENCY}", transaction.getCurrency()).replace("{PIN}", nextUnusedSerial.getPin()).replace("{SERIAL}", nextUnusedSerial.getSerial())
//                            .replace("{DATE}", dateFomrmatter.format(date)).replace("{MOMO_TRANS_ID}", externalid);
//                    ussdBean.sendSMS(transaction.getMsisdn(), sms);
//                }
                } else if (statuscode.equals("100")) {
                    logger.info("going to call gettransaction status as the response received from SDP is 'NOT COMPLETED'");
                    Gettransactionstatusresponse resp_ = getTransactionStatus(transaction.getMsisdn());
                    if(null==resp_ || resp_.getStatus().equals(Financialtransactionstatus.PENDING)){
                        logger.info("gettransaction status returned PENDING response. Going to try again in 5mins");
                        Thread.sleep(5*60*1000);
                        Gettransactionstatusresponse resp2 = getTransactionStatus(transaction.getMsisdn());
                        logger.info("gettransaction status called 2nd retry");
                        if(null==resp_ || !resp2.getStatus().equals(Financialtransactionstatus.SUCCESSFUL)){
                            Transactions transaction2 = ussdBean.getTransaction(transactionid);
                            if("SUCCESS".equals(transaction2.getStatus())){
                                logger.info("After 2nd retry found transaction to have already been completed successfully. Exiting to avoid duplicate pin vending");
                                throw new IllegalAccessException("This transaction has already been completed in the past.");
                            }
                            logger.info("No success after 2nd retry... marking this transaction as failed. No retries will be attempted.");
                            transaction.setStatus("FAILED");
                        }else{
                            logger.info("gettransaction status returned SUCCESSFUL response. going to vend pin");
                            transaction = callWaecToVend(transaction);
                        }
                    }else if(resp_.getStatus().equals(Financialtransactionstatus.SUCCESSFUL)){
                        logger.info("gettransaction status returned SUCCESSFUL response. going to vend pin");
                        transaction = callWaecToVend(transaction);
                    }else{
                        logger.info(transactionid + " was declined!");
                        transaction.setStatus("FAILED");
                    }
                } else {
                    logger.info(transactionid + " was declined!");
                    transaction.setStatus("FAILED");
                }
                ussdBean.merge(transaction);
            }
        } catch (IllegalAccessException ex) {
            logger.error("IllegalAccessException " + ex.getMessage());
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
            throw new RuntimeException(ne);
        }
    }

    private Gettransactionstatusresponse getTransactionStatus(String msisdn) {
        logger.error("called getTransactionStatus for " + transactionid);
        final String xmlString = String.format(gettransactionstatusreq, transactionid);
        Gettransactionstatusresponse gResp = null;
        try {
            HttpEntity entity = new StringEntity(xmlString);
            final String url = UssdConstants.MESSAGES.getProperty(UssdConstants.HSDP_TRANSACTION_STATUS_URL);
            CloseableHttpClient client = getClientSSL(url);
            HttpPost post = new HttpPost(url);
            post.setEntity(entity);
            post.addHeader("Content-Type", "application/xml");
            post.addHeader("X-WSSE", "UsernameToken Username=\""+spId+"\",PasswordDigest=\"Ia4fn9U88AVJ43SQ+04Vo61vztA=\",Nonce=\"1234\", Created=\"2015-11-19T17:36:57Z\"");
            post.addHeader("Authorization", "WSSE realm=\"SDP\", profile=\"UsernameToken\"");
            post.addHeader("X-RequestHeader", "request TransId=\"62254818734339\",ServiceId=\"\",LinkId=\"\",FA=\"tel:"+msisdn+"\",PresentId=\"\"");
            post.addHeader("Msisdn", msisdn);
            post.addHeader("Signature", "43AD232FD45FF");
            final Date before = new Date();
            logger.info("Going to call URL: '" + url + "' request payload:\n" + xmlString.replace("\n", "") + "\n @ Date: " + before);
            try (CloseableHttpResponse response1 = client.execute(post)) {
                final Date after = new Date();
                logger.info("http response received from HSDP @ " + new Date());
                logger.info("HSDP_Stats|" + ((after.getTime() - before.getTime()) / 1000) + "secs|" + transactionid);
                final HttpEntity entity1 = response1.getEntity();
                final String httpResp = EntityUtils.toString(entity1).trim();
                logger.info("response from HSDP: " + httpResp);
                JAXBContext jaxbContext = JAXBContext.newInstance(Gettransactionstatusresponse.class);
                final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                StringReader reader = new StringReader(httpResp);
                gResp = (Gettransactionstatusresponse) unmarshaller.unmarshal(reader);
                EntityUtils.consume(entity1);
                response1.close();
            } catch (IOException ex) {
                logger.error("IOException thrown: " + ex.getMessage());
            }
        } catch (Exception ex) {
            logger.error("Exception thrown: " + ex.getMessage());
        }
        return gResp;
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

}
