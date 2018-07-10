/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import com.payges.ussd.mtnlib.entities.Transactions;
import java.util.Arrays;
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
    
    public Transactions getLastTransaction(String msisdn){
        List<Transactions> resultList = em.createNamedQuery("Transactions.findLastTransactionByMsisdn").setParameter("msisdn", msisdn).setMaxResults(1).getResultList();
        if(null == resultList || resultList.isEmpty()){
            return null;
        }else{
            return resultList.get(0);
        }
    }
}
