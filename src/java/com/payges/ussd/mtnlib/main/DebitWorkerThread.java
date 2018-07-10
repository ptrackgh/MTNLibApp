/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.log4j.Logger;


/**
 *
 * @author ptrack
 */
public class DebitWorkerThread implements Runnable{

    UssdBean ussdBean = lookupUssdBeanBean();
    String currency;
    String msisdn;

    public DebitWorkerThread(String currency, String msisdn) {
        this.msisdn=msisdn;
        this.currency=currency;
    }
    
    @Override
    public void run() {
        try {
            wait(5*1000);
            Logger.getLogger(this.getClass()).info("sending debit request for: "+msisdn);
            ussdBean.sendSMS(msisdn, "This is a test message for "+currency+"{AMOUNT} sending PIN: 123456 with serial: 6541239834");
            ussdBean.saveUssdLog(msisdn, "PIN_PURCHASE", "PENDING");
        } catch (InterruptedException ex) {
            //
        }
    }
    
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

    private UssdBean lookupUssdBeanBean() {
        try {
            Context c = new InitialContext();
            return (UssdBean) c.lookup("java:global/MTNLibApp/UssdBean!com.payges.ussd.mtnlib.main.UssdBean");
        } catch (NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
