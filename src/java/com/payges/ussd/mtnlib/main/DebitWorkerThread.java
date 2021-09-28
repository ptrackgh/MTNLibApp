/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import com.payges.ussd.mtnlib.entities.Currencies;
import com.payges.ussd.mtnlib.util.UssdConstants;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author ptrack
 */
public class DebitWorkerThread implements Runnable {
    //static Logger logger = Logger.getLogger(DebitWorkerThread.getClass());
    DecimalFormat df = new DecimalFormat("####0.00");
    UssdBean ussdBean = lookupUssdBeanBean();
    String currency;
    String msisdn;
    UssdSession session;
    
    final static String spId = UssdConstants.MESSAGES.getProperty(UssdConstants.HSDP_SP_ID);
    final static String spPassword = UssdConstants.MESSAGES.getProperty(UssdConstants.HSDP_SP_PASSWORD);
    final static String serviceId = UssdConstants.MESSAGES.getProperty(UssdConstants.HSDP_MOMO_ID);
    final static String spTimestamp = UssdConstants.MESSAGES.getProperty(UssdConstants.HSDP_SP_TIMESTAMP);
    final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
    final static Random rand = new Random();
    
    public final String debitrequest_old = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:b2b=\"http://b2b.mobilemoney.mtn.zm_v1.0\">\n"
            + "<soapenv:Header>\n"
            + "<RequestSOAPHeader xmlns=\"http://www.huawei.com.cn/schema/common/v2_1\">\n"
            + "<spId>%1$s</spId>\n"
            + "<spPassword>%2$s</spPassword>\n"
            + "<bundleID></bundleID>\n"
            + "<serviceId>%3$s</serviceId>\n"
            + "<timeStamp>%4$s</timeStamp>\n"
            + "</RequestSOAPHeader>\n"
            + "</soapenv:Header>\n"
            + "<soapenv:Body>\n"
            + "<b2b:processRequest>\n"
            + "<serviceId>%3$s</serviceId>\n"
            + "<parameter><name>DueAmount</name><value>%5$s</value></parameter>\n"
            + "<parameter><name>MSISDNNum</name><value>FRI:%6$s/MSISDN</value></parameter>\n"
            + "<parameter><name>ProcessingNumber</name><value>%7$s</value></parameter>\n"
            + "<parameter><name>serviceId</name><value>%3$s</value></parameter>\n"
            + "<parameter><name>AcctRef</name><value>FRI:%6$s@%3$s/SP</value></parameter>\n"
            + "<parameter><name>AcctBalance</name><value>555</value></parameter>\n"
            + "<parameter><name>MinDueAmount</name><value>%5$s</value></parameter>\n"
            + "<parameter><name>Narration</name><value>waec bill payment for %7$s</value></parameter>\n"
            + "<parameter><name>PrefLang</name><value>121212121</value></parameter>\n"
            + "<parameter><name>OpCoID</name><value>0</value></parameter>\n"
            + "<parameter><name>CurrCode</name><value>%8$s</value></parameter>\n"
            + "</b2b:processRequest>\n"
            + "</soapenv:Body>\n"
            + "</soapenv:Envelope>";
    
    public final String debitrequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:b2b=\"http://b2b.mobilemoney.mtn.zm_v1.0\">\n"
            + "<soapenv:Header>\n"
            + "<RequestSOAPHeader xmlns=\"http://www.huawei.com.cn/schema/common/v2_1\">\n"
            + "<spId>%1$s</spId>\n"
            + "<spPassword>%2$s</spPassword>\n"
            + "<bundleID/>"
            + "<timeStamp>%4$s</timeStamp>\n"
            + "</RequestSOAPHeader>\n"
            + "</soapenv:Header>\n"
            + "<soapenv:Body>\n"
            + "<b2b:processRequest>\n"
            + "<serviceId>100</serviceId>\n"
            + "<parameter><name>DueAmount</name><value>%5$s</value></parameter>\n"
//            + "<parameter><name>MSISDNNum</name><value>FRI:%6$s/MSISDN</value></parameter>\n"
            + "<parameter><name>MSISDNNum</name><value>%6$s</value></parameter>\n"
            + "<parameter><name>ProcessingNumber</name><value>%7$s</value></parameter>\n"
            + "<parameter><name>serviceId</name><value>%3$s</value></parameter>\n"
//            + "<parameter><name>AcctRef</name><value>FRI:%6$s@%3$s/SP</value></parameter>\n"
            + "<parameter><name>AcctRef</name><value>WAEC</value></parameter>\n"
            + "<parameter><name>AcctBalance</name><value>0</value></parameter>\n"
            + "<parameter><name>MinDueAmount</name><value>0</value></parameter>\n"
            + "<parameter><name>Narration</name><value>waec bill payment for %7$s</value></parameter>\n"
            + "<parameter><name>PrefLang</name><value>en</value></parameter>\n"
            + "<parameter><name>OpCoID</name><value>23101</value></parameter>\n"
            + "<parameter><name>CurrCode</name><value>%8$s</value></parameter>\n"
            + "</b2b:processRequest>\n"
            + "</soapenv:Body>\n"
            + "</soapenv:Envelope>";

    public DebitWorkerThread(UssdSession session) {
        this.session = session;
        this.msisdn = session.getMsisdn();
        this.currency = session.getSelectedCurrency();
    }

    @Override
    public void run() {
        MDC.put("session", msisdn);
        Logger logger = Logger.getLogger(this.getClass());
        try {
            //new Thread().wait();
            //wait();
            Thread.sleep(5000);
            logger.info("sending debit request for: " + msisdn);
            final String url = UssdConstants.MESSAGES.getProperty(UssdConstants.HSDP_DEBIT_URL);
            final String transactionId = getTransactionId();
            CloseableHttpClient client = getClientSSL(url);
            HttpPost post = new HttpPost(url);
            final String xmlString;
            if("REGISTRATION".equals(session.getPintype())){
                Currencies debitAmount = ussdBean.getDebitAmount(currency);
                ussdBean.saveUssdLog(msisdn, "PURCHASE_PIN_"+currency, "PENDING");
                ussdBean.saveDebitTransaction(msisdn, debitAmount.getExchangerate(), currency, transactionId,"REGISTRATION");
                xmlString = String.format(debitrequest, spId,spPassword,serviceId,spTimestamp,
                        df.format(debitAmount.getExchangerate()),msisdn,transactionId,currency);
            }else{
//                Currencies debitAmount = ussdBean.getDebitAmount(currency);
                ussdBean.saveUssdLog(msisdn, "RESULT_CHECKER_"+currency, "PENDING");
                ussdBean.saveDebitTransaction(msisdn, Double.parseDouble(session.getVendstatus().getCost()), 
                        session.getVendstatus().getCurrency(), transactionId,"RESULT_CHECKER");
                xmlString = String.format(debitrequest, spId,spPassword,serviceId,spTimestamp,
                        df.format(Double.parseDouble(session.getVendstatus().getCost())),msisdn,transactionId,session.getVendstatus().getCurrency());
            }
            
            HttpEntity entity = new StringEntity(xmlString);
            post.setEntity(entity);
            final Date before = new Date();
            logger.info("Going to call URL: '" + url + "' request payload:\n" + xmlString.replace("\n", "") + "\n @ Date: " + before);
            try (CloseableHttpResponse response1 = client.execute(post)) {
                final Date after = new Date();
                logger.info("debit response @ " + new Date());
                logger.info("HSDP_Stats|" + ((after.getTime() - before.getTime()) / 1000) + "secs|" + msisdn);
                final HttpEntity entity1 = response1.getEntity();
                final String httpResp = EntityUtils.toString(entity1).trim();
//                final Unmarshaller unmarshaller = context.createUnmarshaller();
//                StringReader reader = new StringReader(httpResp);
//                pResp = (PostAgentTransactionResponse) unmarshaller.unmarshal(reader);
                logger.info("response from HSDP: " + httpResp);
                EntityUtils.consume(entity1);
                response1.close();
            } catch (IOException ex) {
                logger.error("IOException thrown: " + ex.getMessage());
                logger.error(Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
            }
            //ussdBean.sendSMS(msisdn, "This is a test message for " + currency + "{AMOUNT} sending PIN: 123456 with serial: 6541239834");
            //ussdBean.saveUssdLog(msisdn, "PIN_PURCHASE_"+currency, "DONE");
        } catch (InterruptedException ex) {
            logger.error("InterruptedException thrown: " + ex.getMessage());
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | UnsupportedEncodingException ex) {
            logger.error("KeyManagementException | NoSuchAlgorithmException | KeyStoreException | UnsupportedEncodingException thrown: " + ex.getMessage());
        }catch (Throwable ex) {
            logger.error("Throwable thrown: " + ex.getMessage());
            logger.error(Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
        }
        MDC.remove("session");
    }

//    public static CloseableHttpClient getHttpClient(String s)
//            throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
//        if (s.toLowerCase().startsWith("https")) {
//            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true)
//                .build();
//            return HttpClients.custom().setSslcontext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier())
//                .build();
//        } else {
//            return HttpClients.createDefault();
//        }
//    }
    public static CloseableHttpClient getClientSSL(String url) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        if (url.toLowerCase().startsWith("https")) {
            //javax.net.ssl.SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();
            //return HttpClients.custom().setSslcontext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
            final SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(),
                    new NoopHostnameVerifier());
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } else {
            return HttpClients.createDefault();
        }
    }

    private UssdBean lookupUssdBeanBean() {
        try {
            Context c = new InitialContext();
            return (UssdBean) c.lookup("java:global/MTNLibApp/UssdBean!com.payges.ussd.mtnlib.main.UssdBean");
        } catch (NamingException ne) {
            Logger.getLogger(this.getClass()).info("exception caught: "+ ne);
            throw new RuntimeException(ne);
        }
    }
    
    public static String getTransactionId(){
        return dateFormat.format(new Date()) + rand.nextInt(10);
    }
    
//    public static void main(String s[]){
//        System.out.println("transactionID1: "+getTransactionId());
//        System.out.println("transactionID2: "+getTransactionId());
//        System.out.println("transactionID3: "+getTransactionId());
//    }
}
