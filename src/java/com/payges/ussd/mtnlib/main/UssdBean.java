/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import com.payges.ussd.mtnlib.entities.Currencies;
import com.payges.ussd.mtnlib.entities.Response;
import com.payges.ussd.mtnlib.entities.Serials;
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
    Logger logger=Logger.getLogger(this.getClass());
    @PersistenceContext(unitName = "MTNLibAppPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void persist(Object object) {
        try{
            em.persist(object);
        }catch (ConstraintViolationException ex) {
            logger.error("ConstraintViolationException " + Arrays.toString(ex.getConstraintViolations().toArray()));
            logger.error("ConstraintViolationException " + ex + " by " + Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
        }catch (Throwable ex) {
            logger.error("Throwable " + ex.getMessage());
            logger.error("Throwable " + ex + " by " + Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
        }
    }
    
    public void merge(Object object) {
        try{
            em.merge(object);
        }catch (ConstraintViolationException ex) {
            logger.error("ConstraintViolationException " + Arrays.toString(ex.getConstraintViolations().toArray()));
            logger.error("ConstraintViolationException " + ex + " by " + Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
        }catch (Throwable ex) {
            logger.error("Throwable " + ex.getMessage());
            logger.error("Throwable " + ex + " by " + Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
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
        logger.info("called getLastTransaction for "+ msisdn);
        List<Serials> resultList = em.createNamedQuery("Serials.findLastTransactionByMsisdn").setParameter("usedby", msisdn).setMaxResults(1).getResultList();
        logger.info("finished getLastTransaction for "+ msisdn);
        if(null == resultList || resultList.isEmpty()){
            return null;
        }else{
            return resultList.get(0);
        }
    }
    
    public Serials getNextUnusedSerial(){
        logger.info("checking for Un-used Serial");
        List<Serials> resultList = null;
        try{
            resultList = em.createNamedQuery("Serials.findNextUnusedSerial").setMaxResults(1).getResultList();
            logger.info("finished getNextUnusedSerial. Un-used serial found:"+resultList.isEmpty());
        } catch (Exception ex) {
            logger.error("Exception " + ex.getMessage());
        }
        if(null==resultList || resultList.isEmpty()){
            return null;
        }else{
            return resultList.get(0);
        }
    }
    
    public Currencies getDebitAmount(String currency){
        logger.info("called getDebitAmount for "+ currency);
        Currencies resultList = null;
        try {
            resultList = (Currencies) em.createNamedQuery("Currencies.findByCurrencycode").setParameter("currencycode", currency).getSingleResult();
        } catch (Exception e) {
            logger.error("Exception " + e.getMessage());
        }
        logger.info("finished getDebitAmount for "+ currency);
        return resultList;
    }
    
    public Transactions getTransaction(String transId){
        logger.info("called getTransaction for "+ transId);
        Transactions resultList = null;
        try {
            resultList = (Transactions) em.createNamedQuery("Transactions.findByTransactionid").setParameter("transactionid", transId).getSingleResult();
        } catch (Exception e) {
            logger.error("Exception " + e.getMessage());
        }
        logger.info("finished getTransaction for "+ transId);
        return resultList;
    }
    
    public void saveUssdLog(String msisdn, String operation, String status){
        Logger.getLogger(getClass().getName()).info("called saveUssdLog for "+ msisdn);
        Ussdlogs ussdlog = new Ussdlogs();
        ussdlog.setMsisdn(msisdn);
        ussdlog.setRequestdate(new Date());
        ussdlog.setStatus(status);
        ussdlog.setUseroperation(operation);
        persist(ussdlog);
        logger.info("finished saveUssdLog for "+ msisdn);
    }
    
    public void saveDebitTransaction(String msisdn, double amount, String currency, String transactionid, String pintype){
        Logger.getLogger(getClass().getName()).info("called saveDebitTransaction for "+ msisdn);
        Transactions trans = new Transactions();
        trans.setMsisdn(msisdn);
        trans.setCurrency(currency);
        trans.setRequestdate(new Date());
        trans.setStatus("PENDING");
        trans.setTransactionid(transactionid);
        trans.setAmount(amount);
        trans.setRetries(0);
        trans.setPintype(pintype);
        persist(trans);
        logger.info("finished saveDebitTransaction for "+ msisdn);
    }
    
    public void sendSMS(String msisdn, String message){
        logger.info("called sendSMS for "+ msisdn);
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
        logger.info("finished sendSMS for "+ msisdn);
    }
}
