/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import com.payges.ussd.mtnlib.entities.Response;
import com.payges.ussd.mtnlib.entities.Serials;
import com.payges.ussd.mtnlib.entities.Smslog;
import com.payges.ussd.mtnlib.entities.Transactions;
import com.payges.ussd.mtnlib.entities.Ussdlogs;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import org.apache.log4j.Logger;

/**
 *
 * @author ptrack
 */
@Stateless
public class UssdBean {

    @PersistenceContext(unitName = "MTNLibAppPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void persist(Object object) {
        try{
            em.persist(object);
        }catch (ConstraintViolationException ex) {
            Logger.getLogger("persist").error("ConstraintViolationException " + Arrays.toString(ex.getConstraintViolations().toArray()));
            Logger.getLogger("persist").error("ConstraintViolationException " + ex + " by " + Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
        }catch (Throwable ex) {
            Logger.getLogger("persist").error("Throwable " + ex.getMessage());
            Logger.getLogger("persist").error("Throwable " + ex + " by " + Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
        }
    }
    
//    public Transactions getLastTransaction(String msisdn){
//        List<Transactions> resultList = em.createNamedQuery("Transactions.findLastTransactionByMsisdn").setParameter("msisdn", msisdn).setMaxResults(1).getResultList();
//        if(null == resultList || resultList.isEmpty()){
//            return null;
//        }else{
//            return resultList.get(0);
//        }
//    }
    
    public Serials getLastTransaction(String msisdn){
        List<Serials> resultList = em.createNamedQuery("Serials.findLastTransactionByMsisdn").setParameter("msisdn", msisdn).setMaxResults(1).getResultList();
        if(null == resultList || resultList.isEmpty()){
            return null;
        }else{
            return resultList.get(0);
        }
    }
    
    public void saveUssdLog(String msisdn, String operation, String status){
        Ussdlogs ussdlog = new Ussdlogs();
        ussdlog.setMsisdn(msisdn);
        ussdlog.setRequestdate(new Date());
        ussdlog.setStatus(status);
        ussdlog.setUseroperation(operation);
        persist(ussdlog);
    }
    
    public void sendSMS(String msisdn, String message){
        Response sms = new Response();
        sms.setMsisdn(msisdn);
        sms.setMessage(message);
        sms.setStatus(Short.valueOf("0"));
        sms.setSenderId("WAEC");
        sms.setApplication("MTNLib USSD");
        sms.setFlag(Short.valueOf("0"));
        sms.setRequestDelivery(Short.valueOf("1"));
        sms.setAccount("PAYSOTUTION");
        sms.setSrcTon(Short.valueOf("5"));
        sms.setSrcNpi(Short.valueOf("1"));
        sms.setDestTon(Short.valueOf("1"));
        sms.setDestNpi(Short.valueOf("1"));
        persist(sms);
    }
}
