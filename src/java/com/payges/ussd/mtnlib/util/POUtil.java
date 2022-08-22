/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.util;

import com.payges.ussd.mtnlib.ericsson.financial.v1.restmodels.Debitrequest;
import com.payges.ussd.mtnlib.ericsson.financial.v1.restmodels.Debitresponse;
import com.payges.ussd.mtnlib.ericsson.financial.v1_1.restmodels.Gettransactionstatusrequest;
import com.payges.ussd.mtnlib.ericsson.financial.v1_1.restmodels.Gettransactionstatusresponse;
import com.payges.ussd.mtnlib.ericsson.lwac.restmodels.ErrorResponse;
import com.payges.ussd.mtnlib.ericsson.restmodels.Moneydetailstype;
import com.payges.ussd.mtnlib.ericsson.restmodels.Sptransferrequest;
import com.payges.ussd.mtnlib.ericsson.restmodels.Sptransferresponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author User
 */
public class POUtil {

    private final static Logger logger = Logger.getLogger(POUtil.class);

    final static String CERTIFICATE_PATH = UssdConstants.MESSAGES.getProperty("CERTIFICATE_PATH");
    final static String CERTIFICATE_PASSWORD = UssdConstants.MESSAGES.getProperty("CERTIFICATE_PASSWORD");
    
    final static String SP_USERNAME = UssdConstants.MESSAGES.getProperty("SP_USERNAME");
    final static String SP_PASSWORD = UssdConstants.MESSAGES.getProperty("SP_PASSWORD");
    final static String URL = UssdConstants.MESSAGES.getProperty("PO_URL"); //gettransactionstatus,debit,sptransfer
    final static String GET_TXN_STATUS_URL = UssdConstants.MESSAGES.getProperty("PO_GET_TXN_STATUS_URL"); //gettransactionstatus,debit,sptransfer
    final static String authHeader = "Basic " + new String(Base64.getEncoder().encode((SP_USERNAME + ":" + SP_PASSWORD).getBytes(StandardCharsets.ISO_8859_1)));

    public static Debitresponse sendDebitInternal(String refNo, String msisdn, Double amount_,String ccy, String narration) {
        logger.info("calling sendDebit on EWP for {" + msisdn + "} with amount{" + amount_ + "}");
        Debitresponse resp = null;
        final Debitrequest req = new Debitrequest();
        final Moneydetailstype amount = new Moneydetailstype();
        amount.setAmount(BigDecimal.valueOf(amount_));
        amount.setCurrency(ccy.equals("LD")?"LRD":ccy);
        req.setAmount(amount);
        req.setFromfri("FRI:"+msisdn+"/MSISDN");
        req.setTofri("FRI:"+SP_USERNAME+"/USER");
        req.setFrommessage(narration);
        req.setTomessage(narration);
        req.setReferenceid(refNo);
        req.setExternaltransactionid(refNo);
        CloseableHttpClient httpclient = null;
        try {
            httpclient = getHttpClient(URL);
            final HttpPost post = new HttpPost(URL+"debit");
            post.setHeader("Content-Type", "application/xml");
            post.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
            final JAXBContext jaxbContext = JAXBContext.newInstance(Debitrequest.class);
            final Marshaller reqMarshaller = jaxbContext.createMarshaller();
            reqMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            final StringWriter writer = new StringWriter();
            reqMarshaller.marshal(req, writer);
            //final Gson gson = new Gson();
            logger.info("Debit URL: " + URL + "debit\nRequest: " + writer.toString());
            final HttpEntity entity = new StringEntity(writer.toString());
            post.setEntity(entity);
            try (CloseableHttpResponse response1 = httpclient.execute(post)) {
                logger.info("http response received from debit endpoint");
                final HttpEntity entity1 = response1.getEntity();
                final String httpResp = EntityUtils.toString(entity1).trim();
                logger.info("response from debit call: " + httpResp);
                EntityUtils.consume(entity1);
                response1.close();
                resp = JAXB.unmarshal(new StringReader(httpResp), Debitresponse.class);
            }
        } catch (Throwable ex) {
            logger.info("Exception inside debitCustomer: " + ex.getMessage());
            logger.debug("Exception stacktrace: " + Arrays.toString(ex.getStackTrace()).replace(", ", "\n"));
        } finally {
            try {
                if (null != httpclient) {
                    httpclient.close();
                }
            } catch (IOException ex) {
                logger.info("unable to close httpclient inside debitCustomer: " + ex.getMessage());
            }
        }
        return resp;
    }
    
    public static Object sendDebitForExternal3PP(String username, String password, String refNo, String msisdn, Double amount_,String ccy, String narration) {
        logger.info("calling sendDebit on EWP for {" + msisdn + "} with amount{" + amount_ + "}");
        Object resp = null;
        final Debitrequest req = new Debitrequest();
        final Moneydetailstype amount = new Moneydetailstype();
        amount.setAmount(BigDecimal.valueOf(amount_));
        amount.setCurrency(ccy);
        req.setAmount(amount);
        req.setFromfri("FRI:"+msisdn+"/MSISDN");
        req.setTofri("FRI:"+username+"/USER");
        req.setFrommessage(narration);
        req.setTomessage(narration);
        req.setReferenceid(refNo);
        req.setExternaltransactionid(refNo);
        CloseableHttpClient httpclient = null;
        try {
            httpclient = getHttpClient(URL);
            final HttpPost post = new HttpPost(URL+"debit");
            post.setHeader("Content-Type", "application/xml");
            final String auth = "Basic " + new String(Base64.getEncoder().encode((username + ":" + password).getBytes(StandardCharsets.ISO_8859_1)));
            post.setHeader(HttpHeaders.AUTHORIZATION, auth);
            final JAXBContext jaxbContext = JAXBContext.newInstance(Debitrequest.class);
            final Marshaller reqMarshaller = jaxbContext.createMarshaller();
            reqMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            final StringWriter writer = new StringWriter();
            reqMarshaller.marshal(req, writer);
            //final Gson gson = new Gson();
            logger.info("Debit URL: " + URL + "debit\nRequest: " + writer.toString());
            final HttpEntity entity = new StringEntity(writer.toString());
            post.setEntity(entity);
            try (CloseableHttpResponse response1 = httpclient.execute(post)) {
                logger.info("http response received from debit endpoint");
                final HttpEntity entity1 = response1.getEntity();
                final String httpResp = EntityUtils.toString(entity1).trim();
                logger.info("response from debit call: " + httpResp);
                EntityUtils.consume(entity1);
                response1.close();
                try{
                    logger.info("going to convert to Debitresponse");
                    JAXBContext jContext1 = JAXBContext.newInstance(Debitresponse.class);
                    Unmarshaller unmarshallerObj = jContext1.createUnmarshaller();
                    //resp = JAXB.unmarshal(new StringReader(httpResp), Debitresponse.class);
                    resp = (Debitresponse) unmarshallerObj.unmarshal(new StringReader(httpResp));
                    logger.info("converted to Debitresponse");
                } catch (Throwable ex) {
                    logger.info("was unable to convert response to  Debitresponse. Attempt converting to ErrorResponse");
                    try{
                        JAXBContext jContext1 = JAXBContext.newInstance(ErrorResponse.class);
                    Unmarshaller unmarshallerObj = jContext1.createUnmarshaller();
                    resp = (ErrorResponse) unmarshallerObj.unmarshal(new StringReader(httpResp));
//                        resp =  JAXB.unmarshal(new StringReader(httpResp), ErrorResponse.class);
                    }catch (Throwable ex2) {
                        logger.info("converting to ErrorResponse failed. Reason: "+ex2.getMessage());
                        resp = null;
                    }
                }
            }
        } catch (Throwable ex) {
            logger.info("Exception inside debitCustomer: " + ex.getMessage());
            logger.debug("Exception stacktrace: " + Arrays.toString(ex.getStackTrace()).replace(", ", "\n"));
        } finally {
            try {
                if (null != httpclient) {
                    httpclient.close();
                }
            } catch (IOException ex) {
                logger.info("unable to close httpclient inside debitCustomer: " + ex.getMessage());
            }
        }
        return resp;
    }
    
    public static Gettransactionstatusresponse checkTransactionStatus(String refNo) {
        logger.info("calling checkTransactionStatus on EWP for: " + refNo);
        Gettransactionstatusresponse resp = null;
        Gettransactionstatusrequest req = new Gettransactionstatusrequest();
        req.setReferenceid(refNo);
        CloseableHttpClient httpclient = null;
        try {
            httpclient = getHttpClient(GET_TXN_STATUS_URL);
            final HttpPost post = new HttpPost(GET_TXN_STATUS_URL+"gettransactionstatus");
            post.setHeader("Content-Type", "application/xml");
            post.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
            final JAXBContext jaxbContext = JAXBContext.newInstance(Gettransactionstatusrequest.class);
            final Marshaller reqMarshaller = jaxbContext.createMarshaller();
            reqMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            final StringWriter writer = new StringWriter();
            reqMarshaller.marshal(req, writer);
            logger.info("Gettransactionstatusrequest URL: " + URL + "gettransactionstatus\nRequest: " + writer.toString());
            final HttpEntity entity = new StringEntity(writer.toString());
            post.setEntity(entity);
            try (CloseableHttpResponse response1 = httpclient.execute(post)) {
                logger.info("http response received from Gettransactionstatusrequest endpoint");
                final HttpEntity entity1 = response1.getEntity();
                final String httpResp = EntityUtils.toString(entity1).trim();
                logger.info("response from debit call: " + httpResp);
                EntityUtils.consume(entity1);
                response1.close();
                resp = JAXB.unmarshal(new StringReader(httpResp), Gettransactionstatusresponse.class);
//                JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
//                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//                StringReader reader = new StringReader("xml string here");
//                Person person = (Person) unmarshaller.unmarshal(reader);
            }
        } catch (Throwable ex) {
            logger.info("Exception inside Gettransactionstatus: " + ex.getMessage());
            logger.debug("Exception stacktrace: " + Arrays.toString(ex.getStackTrace()).replace(", ", "\n"));
        } finally {
            try {
                if (null != httpclient) {
                    httpclient.close();
                }
            } catch (IOException ex) {
                logger.info("unable to close httpclient inside Gettransactionstatus: " + ex.getMessage());
            }
        }
        return resp;
    }
    
    public static Sptransferresponse sendSpTransfer(String refNo, String msisdn, Double amount_,String ccy, String narration) {
        logger.info("caling sendSpTransfer on EWP for: " + msisdn);
        Sptransferresponse resp = null;
        Sptransferrequest req = new Sptransferrequest();
        req.setReceivingfri("FRI:"+msisdn+"/MSISDN");
        req.setSendingfri("FRI:"+SP_USERNAME+"/USER");
        req.setReferenceid(refNo);
        final Moneydetailstype amount = new Moneydetailstype();
        amount.setAmount(BigDecimal.valueOf(amount_));
        amount.setCurrency(ccy);
        req.setAmount(amount);
        req.setReceivermessage(narration);
        req.setSendernote(narration);
        CloseableHttpClient httpclient = null;
        try {
            httpclient = getHttpClient(URL);
            final HttpPost post = new HttpPost(URL+"sptransfer");
            post.setHeader("Content-Type", "application/xml");
            post.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
            final JAXBContext jaxbContext = JAXBContext.newInstance(Sptransferrequest.class);
            final Marshaller reqMarshaller = jaxbContext.createMarshaller();
            reqMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //reqMarshaller.setProperty(Marshaller.JAX, "http://www.mysite.com/abc.xsd");
            final StringWriter writer = new StringWriter();
            reqMarshaller.marshal(req, writer);
            logger.info("sendSpTransfer URL: " + URL + "sptransfer\nRequest: " + writer.toString());
            final HttpEntity entity = new StringEntity(writer.toString());
            post.setEntity(entity);
            try (CloseableHttpResponse response1 = httpclient.execute(post)) {
                logger.info("http response received from sendSpTransfer endpoint");
                final HttpEntity entity1 = response1.getEntity();
                final String httpResp = EntityUtils.toString(entity1).trim();
                logger.info("response from debit call: " + httpResp);
                EntityUtils.consume(entity1);
                response1.close();
            }
        } catch (Throwable ex) {
            logger.info("Exception inside sendSpTransfer: " + ex.getMessage());
            logger.debug("Exception stacktrace: " + Arrays.toString(ex.getStackTrace()).replace(", ", "\n"));
        } finally {
            try {
                if (null != httpclient) {
                    httpclient.close();
                }
            } catch (IOException ex) {
                logger.info("unable to close httpclient inside sendSpTransfer: " + ex.getMessage());
            }
        }
        return resp;
    }

    public static CloseableHttpClient getHttpClient(String s)
            throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        if (s.toLowerCase().startsWith("https")) {
            return HttpClients.custom().setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                    .setSSLSocketFactory(getCloseableHttpClient()).build();
        } else {
            return HttpClients.createDefault();
        }
    }
    
    public static CloseableHttpClient getHttpClientv2(String s)
            throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        if (s.toLowerCase().startsWith("https")) {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true)
                    .build();
            return HttpClients.custom().setSslcontext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier())
                    .build();
        } else {
            return HttpClients.createDefault();
        }
    }//com.paygesonline.ws.PaygesBean.validateRequest(PaygesBean.java:94)
    
    private static SSLConnectionSocketFactory getCloseableHttpClient() {
        try {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            KeyStore keystore = KeyStore.getInstance("JKS");
            FileInputStream instream = new FileInputStream(new File(CERTIFICATE_PATH));
            keystore.load(instream, CERTIFICATE_PASSWORD.toCharArray());
            keyManagerFactory.init(keystore, CERTIFICATE_PASSWORD.toCharArray());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(keystore);
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
            SSLConnectionSocketFactory sslsf1 = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1","TLSv1.1","TLSv1.2"},
                    null,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return sslsf1;
        } catch (java.security.KeyStoreException e) {
            logger.error("Error while creating SSL Factory.", e);
            return null;
        } catch (Exception e) {
            logger.error("Error while creating SSL Factory", e);
        }
        return null;
    }
}
