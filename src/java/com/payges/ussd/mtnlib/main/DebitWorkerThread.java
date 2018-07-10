/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

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
import org.apache.log4j.Logger;


/**
 *
 * @author ptrack
 */
public class DebitWorkerThread implements Runnable{
    
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
        } catch (InterruptedException ex) {
            //
        }
    }

    public void persist(Object object) {
        /* Add this to the deployment descriptor of this module (e.g. web.xml, ejb-jar.xml):
         * <persistence-context-ref>
         * <persistence-context-ref-name>persistence/LogicalName</persistence-context-ref-name>
         * <persistence-unit-name>MTNLibAppPU</persistence-unit-name>
         * </persistence-context-ref>
         * <resource-ref>
         * <res-ref-name>UserTransaction</res-ref-name>
         * <res-type>javax.transaction.UserTransaction</res-type>
         * <res-auth>Container</res-auth>
         * </resource-ref> */
        try {
            Context ctx = new InitialContext();
            UserTransaction utx = (UserTransaction) ctx.lookup("java:comp/env/UserTransaction");
            utx.begin();
            EntityManager em = (EntityManager) ctx.lookup("java:comp/env/persistence/LogicalName");
            em.persist(object);
            utx.commit();
        } catch (NamingException | NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            Logger.getLogger(this.getClass()).info("Exception. Reason: "+e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
}
