/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import com.google.gson.Gson;
import com.payges.ussd.mtnlib.util.SecurityUtil;
import com.payges.ussd.mtnlib.util.UssdConstants;
import com.payges.ussd.mtnlib.waec.dto.VendPinRequest;
import com.payges.ussd.mtnlib.waec.dto.VendPinResponse;
import com.payges.ussd.mtnlib.waec.dto.VendStatusRequest;
import com.payges.ussd.mtnlib.waec.dto.VendStatusResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author ptrack
 */
public class WAECUtil {
    private static final Logger logger = Logger.getLogger(WAECUtil.class);
    final static String clientId;
    final static String key;
    final static String url;
    final static String statusURL;
//    final static String serviceCode;
    static {
        clientId = UssdConstants.MESSAGES.getProperty(UssdConstants.WAEC_CLIENT_ID);
        key = UssdConstants.MESSAGES.getProperty(UssdConstants.WAEC_CLIENT_PASSWORD);
        url = UssdConstants.MESSAGES.getProperty(UssdConstants.WAEC_VENDING_URL);
        statusURL = UssdConstants.MESSAGES.getProperty(UssdConstants.WAEC_VENDING_STATUS_URL);
//        serviceCode = UssdConstants.MESSAGES.getProperty(UssdConstants.WAEC_SERVICE_CODE);
    }
    
    
    public static VendStatusResponse getWAECServiceStatus(String serviceCode){
        logger.info("inside getWAECServiceStatus. serviceCode: " + serviceCode);
        VendStatusResponse resp = null;
            try {
                final VendStatusRequest vendstatus = new VendStatusRequest();
                vendstatus.setClientID(clientId);
                vendstatus.setServiceCode(SecurityUtil.generateWAECDate());
                vendstatus.setServiceCode(serviceCode);
                logger.info(clientId+ "|" + key+"|"+serviceCode);
                vendstatus.setHASH(SecurityUtil.generateHash(clientId + key + serviceCode));
                
                CloseableHttpClient client = getHttpClient(statusURL);
                HttpPost post = new HttpPost(statusURL);
                final Gson gson = new Gson();
                final String xmlString = gson.toJson(vendstatus);
                HttpEntity entity = new StringEntity(xmlString);
                post.setHeader("Content-Type", "application/json");
                post.setEntity(entity);
                final Date before = new Date();
                logger.info("Going to call URL: '" + statusURL + "' request payload:\n" + xmlString + "\n @ Date: " + before);
                try (CloseableHttpResponse response1 = client.execute(post)) {
                    final Date after = new Date();
                    logger.info("http response received from WAEC @ " + after);
                    logger.info("HSDP_Stats|" + ((after.getTime() - before.getTime()) / 1000) + "secs|" + vendstatus.getClientID());
                    final HttpEntity entity1 = response1.getEntity();
                    final String httpResp = EntityUtils.toString(entity1).trim();
                    resp = gson.fromJson(httpResp, VendStatusResponse.class);
                    logger.info("response from WAEC: " + httpResp);
                    EntityUtils.consume(entity1);
                    response1.close();
                } catch (IOException ex) {
                    logger.error("IOException thrown: " + ex.getMessage());
                    logger.error(Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
                }
            } catch (KeyManagementException|UnsupportedEncodingException|NoSuchAlgorithmException|KeyStoreException ex) {
                logger.error("KeyManagementException|UnsupportedEncodingException|NoSuchAlgorithmException|KeyStoreException thrown: " + ex.getMessage());
                logger.error(Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
            } catch (Throwable ex) {
                    logger.error("Throwable thrown: " + ex.getMessage());
                    logger.error(Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
            }
            return resp;
    }
    
    public static VendPinResponse generateWAECPin(String pintype){
        VendPinResponse resp = null;
        String serviceCode;
        if("REGISTRATION".equals(pintype)){
            serviceCode = UssdConstants.PIN_TYPE_REGISTRATION;
        }else if("MINI-REGISTRATION".equals(pintype)){
            serviceCode = UssdConstants.PIN_TYPE_MINI_REGISTRATION;
        }
        else{
            serviceCode = UssdConstants.PIN_TYPE_RESULT_CHECKER;
        }
            try {
                final VendPinRequest vendpin = new VendPinRequest();
                vendpin.setClientID(clientId);
                vendpin.setDateTime(SecurityUtil.generateWAECDate());
                vendpin.setRequestID(SecurityUtil.generateSessionId());
                vendpin.setServiceCode(serviceCode);
                vendpin.setHASH(SecurityUtil.generateHash(vendpin.getRequestID() + clientId + key + serviceCode));
                CloseableHttpClient client = getHttpClient(url);
                HttpPost post = new HttpPost(url);
                final Gson gson = new Gson();
                final String xmlString = gson.toJson(vendpin);
                HttpEntity entity = new StringEntity(xmlString);
                post.setHeader("Content-Type", "application/json");
                post.setEntity(entity);
                final Date before = new Date();
                logger.info("Going to call URL: '" + url + "' request payload:\n" + xmlString + "\n @ Date: " + before);
                try (CloseableHttpResponse response1 = client.execute(post)) {
                    final Date after = new Date();
                    logger.info("http response received from WAEC @ " + after);
                    logger.info("HSDP_Stats|" + ((after.getTime() - before.getTime()) / 1000) + "secs|" + vendpin.getRequestID());
                    final HttpEntity entity1 = response1.getEntity();
                    final String httpResp = EntityUtils.toString(entity1).trim();
                    resp = gson.fromJson(httpResp, VendPinResponse.class);
                    logger.info("response from WAEC: " + httpResp);
                    EntityUtils.consume(entity1);
                    response1.close();
                } catch (IOException ex) {
                    logger.error("IOException in: " + ex.getMessage());
                    logger.error("Throwable " + ex + " by " + Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
                }
            } catch (KeyManagementException|UnsupportedEncodingException|NoSuchAlgorithmException|KeyStoreException ex) {
                logger.error("KeyManagementException|UnsupportedEncodingException|NoSuchAlgorithmException|KeyStoreException thrown: " + ex.getMessage());
                logger.error(Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
            } catch (Throwable ex) {
                    logger.error("Throwable in: " + ex.getMessage());
                    logger.error(Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
            }
            return resp;
    }
    
//    public static CloseableHttpClient getClientSSL(String url) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
//        if (url.toLowerCase().startsWith("https")) {
//            //javax.net.ssl.SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();
//            //return HttpClients.custom().setSslcontext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
//            final SSLContextBuilder builder = new SSLContextBuilder();
//            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
//            final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(),
//                    new NoopHostnameVerifier());
//            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
//        } else {
//            return HttpClients.createDefault();
//        }
//    }
    
public static CloseableHttpClient getHttpClient(String s)
            throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        if (s.toLowerCase().startsWith("https")) {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true)
                    .build();
            return HttpClients.custom().setSslcontext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier())
                    .build();
        } else {
            return HttpClients.createDefault();
        }
    }
    
    public static void main(String s[]){
//        ClientID: 6127186324422380
//        SecurityKey: WE2X71IWCMREE3DL28LM
        final VendPinRequest vendpin = new VendPinRequest();
        vendpin.setClientID("2176766865872170");
        vendpin.setDateTime(SecurityUtil.generateWAECDate());
        vendpin.setRequestID(SecurityUtil.generateSessionId());
        vendpin.setServiceCode("01");
//        vendpin.setHASH(SecurityUtil.generateHash(vendpin.getRequestID() + "6127186324422380" + "WE2X71IWCMREE3DL28LM" + "01"));
        vendpin.setHASH(SecurityUtil.generateHash(vendpin.getRequestID() + "2176766865872170" + "Q058H3ID8CN1K9G4DD4Y" + "01"));
        Gson gson = new Gson();
//        final VendPinRequest vendpin = new VendPinRequest();
//        vendpin.setClientID(clientId);
//        vendpin.setDateTime(SecurityUtil.generateWAECDate());
//        vendpin.setRequestID(SecurityUtil.generateSessionId());
//        vendpin.setServiceCode(serviceCode);
//        vendpin.setHASH(SecurityUtil.generateHash(vendpin.getRequestID() + clientId + key + serviceCode));
        final String xmlString = gson.toJson(vendpin);
        System.out.println("vend-request: " + xmlString);
    }
}
