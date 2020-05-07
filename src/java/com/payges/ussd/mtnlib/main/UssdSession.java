/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import com.payges.ussd.mtnlib.waec.dto.VendStatusResponse;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author ptrack
 */
public class UssdSession {
    public static ConcurrentHashMap<String, UssdSession> sessions = new ConcurrentHashMap<>(100);
    private String msisdn;
    private int menuLevel;
    private String selectedCurrency;
    private String selectedOperation;
    private String pintype;
    private VendStatusResponse vendstatus;
    

    /**
     * @return the msisdn
     */
    public String getMsisdn() {
        return msisdn;
    }

    /**
     * @param msisdn the msisdn to set
     */
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    /**
     * @return the selectedCurrency
     */
    public String getSelectedCurrency() {
        return selectedCurrency;
    }

    /**
     * @param selectedCurrency the selectedCurrency to set
     */
    public void setSelectedCurrency(String selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
    }

    /**
     * @return the selectedOperation
     */
    public String getSelectedOperation() {
        return selectedOperation;
    }

    /**
     * @param selectedOperation the selectedOperation to set
     */
    public void setSelectedOperation(String selectedOperation) {
        this.selectedOperation = selectedOperation;
    }

    /**
     * @return the menuLevel
     */
    public int getMenuLevel() {
        return menuLevel;
    }

    /**
     * @param menuLevel the menuLevel to set
     */
    public void setMenuLevel(int menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getPintype() {
        return pintype;
    }

    public void setPintype(String pintype) {
        this.pintype = pintype;
    }

    @Override
    public String toString() {
        return "UssdSession{" + "msisdn=" + msisdn + ", menuLevel=" + menuLevel + ", selectedCurrency=" + selectedCurrency + ", selectedOperation=" + selectedOperation + ", pintype=" + pintype + '}';
    }

    public VendStatusResponse getVendstatus() {
        return vendstatus;
    }

    public void setVendstatus(VendStatusResponse vendstatus) {
        this.vendstatus = vendstatus;
    }
}
